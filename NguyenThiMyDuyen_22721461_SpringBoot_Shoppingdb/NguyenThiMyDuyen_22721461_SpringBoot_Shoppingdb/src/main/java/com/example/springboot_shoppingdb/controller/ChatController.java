package com.example.springboot_shoppingdb.controller;

import com.example.springboot_shoppingdb.services.GeminiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springboot_shoppingdb.entities.Product;
import com.example.springboot_shoppingdb.repositories.ProductRepository;
import java.util.Optional;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final GeminiService geminiService;

    private final ProductRepository productRepository;

    // Inject cả 2 service/repository
    public ChatController(GeminiService geminiService, ProductRepository productRepository ) { // <-- Thêm repo vào đây
        this.geminiService = geminiService;
        this.productRepository = productRepository; // <-- Thêm repo vào đây
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> send(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        if (message == null || message.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "missing message"));
        }
        try {
            Optional<Product> foundProduct = findProductInMessage(message);

            String reply;

            if (foundProduct.isPresent()) {
                Product product = foundProduct.get();

                String prodName = product.getName();
                String prodId = String.valueOf(product.getId());
                String prodPrice = String.valueOf(product.getPrice());
                String prodCategory = product.getCategory().getName();

                String augmentedPrompt = String.format(
                        "Bạn là trợ lý bán hàng. Dựa vào thông tin sau: [Tên sản phẩm: %s, Mã: %s, Giá: %s, Loại: %s], " +
                                "hãy viết một câu giới thiệu ngắn (khoảng 2-3 câu) về sản phẩm này cho khách hàng. " +
                                "Chỉ trả lời câu giới thiệu, không nói gì thêm.",
                        prodName, prodId, prodPrice, prodCategory
                );

                log.info("Tìm thấy sản phẩm '{}', tạo augmented prompt.", prodName);
                reply = geminiService.generateText(augmentedPrompt);

            } else {
                // 3. Nếu không tìm thấy -> Gửi prompt gốc
                log.info("Không tìm thấy sản phẩm, gửi prompt gốc.");
                reply = geminiService.generateText(message);
            }

            return ResponseEntity.ok(Map.of("reply", reply != null ? reply : ""));
        } catch (Exception ex) {
            // Lỗi từ GeminiService (ví dụ: hết hạn key) sẽ được bắt ở đây
            return ResponseEntity.status(500).body(Map.of("error", "server error: " + ex.getMessage()));
        }
    }


    // Hàm trợ giúp (helper) để tìm sản phẩm
    // (Đây là cách làm đơn giản, có thể cải tiến sau)
    private Optional<Product> findProductInMessage(String message) {
        String lowerMessage = message.toLowerCase();

        // Cảnh báo: cách này rất chậm nếu có nhiều sản phẩm
        // Tốt hơn là nên có 1 hàm service/repo chỉ tìm theo tên
        // Ví dụ: productRepository.findByNameContainingIgnoreCase(someName)
        Iterable<Product> allProducts = productRepository.findAll();
        for (Product product : allProducts) {
            if (product.getName() != null && lowerMessage.contains(product.getName().toLowerCase())) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

}