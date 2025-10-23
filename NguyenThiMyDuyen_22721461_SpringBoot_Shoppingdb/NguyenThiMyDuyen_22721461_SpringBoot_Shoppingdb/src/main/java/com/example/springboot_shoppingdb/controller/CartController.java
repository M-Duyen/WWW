package com.example.springboot_shoppingdb.controller;

import com.example.springboot_shoppingdb.entities.Product;
import com.example.springboot_shoppingdb.repositories.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.springboot_shoppingdb.services.OrderService;
import com.example.springboot_shoppingdb.repositories.CustomerRepository;
import com.example.springboot_shoppingdb.entities.Order;
import com.example.springboot_shoppingdb.entities.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerRepository customerRepository;

    private static final String CART_SESSION_KEY = "cart";

    @SuppressWarnings("unchecked")
    private Map<Integer, Integer> getCart(HttpSession session) {
        Object o = session.getAttribute(CART_SESSION_KEY);
        if (o instanceof Map) {
            return (Map<Integer, Integer>) o;
        } else {
            Map<Integer, Integer> cart = new LinkedHashMap<>();
            session.setAttribute(CART_SESSION_KEY, cart);
            return cart;
        }
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Integer productId,
            @RequestParam(name = "quantity", defaultValue = "1") Integer quantity,
            HttpSession session,
            RedirectAttributes ra,
            @RequestHeader(value = "referer", required = false) String referer) {
        Map<Integer, Integer> cart = getCart(session);
        if (quantity == null || quantity < 1)
            quantity = 1;
        cart.merge(productId, quantity, Integer::sum);
        ra.addFlashAttribute("message", "Added to cart");
        return "redirect:" + (referer != null ? referer : "/products");
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Map<Integer, Integer> cart = getCart(session);
        List<Product> products = cart.isEmpty() ? Collections.emptyList()
                : productRepository.findAllById(cart.keySet());
        // maintain order of product listing
        List<CartItem> items = products.stream()
                .map(p -> new CartItem(p, cart.getOrDefault(p.getId(), 0)))
                .collect(Collectors.toList());
        double total = items.stream().mapToDouble(CartItem::getSubtotal).sum();
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        return "cart/view";
    }

    @PostMapping("/update")
    public String updateCart(HttpSession session, HttpServletRequest request, RedirectAttributes ra) {
        Map<Integer, Integer> cart = getCart(session);
        // for each cart key, read quantity_{id}
        List<Integer> toRemove = new ArrayList<>();
        for (Integer pid : new ArrayList<>(cart.keySet())) {
            String q = request.getParameter("quantity_" + pid);
            if (q == null)
                continue;
            try {
                int qty = Integer.parseInt(q);
                if (qty <= 0) {
                    toRemove.add(pid);
                } else {
                    cart.put(pid, qty);
                }
            } catch (NumberFormatException ex) {
                // ignore
            }
        }
        toRemove.forEach(cart::remove);
        ra.addFlashAttribute("message", "Cart updated");
        return "redirect:/cart";
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable("productId") Integer productId, HttpSession session,
            RedirectAttributes ra) {
        Map<Integer, Integer> cart = getCart(session);
        cart.remove(productId);
        ra.addFlashAttribute("message", "Removed from cart");
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session, RedirectAttributes ra) {
        session.removeAttribute(CART_SESSION_KEY);
        ra.addFlashAttribute("message", "Cart cleared");
        return "redirect:/cart";
    }

    // Render checkout page (prefill from session cart)
    @GetMapping("/checkout")
    public String showCheckout(HttpSession session, Model model, RedirectAttributes ra) {
        Map<Integer, Integer> cart = getCart(session);
        if (cart.isEmpty()) {
            ra.addFlashAttribute("message", "Cart is empty");
            return "redirect:/cart";
        }
        List<Product> products = productRepository.findAllById(cart.keySet());
        List<CartItem> items = products.stream()
                .map(p -> new CartItem(p, cart.getOrDefault(p.getId(), 0)))
                .collect(Collectors.toList());
        double total = items.stream().mapToDouble(CartItem::getSubtotal).sum();

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("selectedQuantities", new HashMap<>(cart));
        return "cart/checkout";
    }

    // Submit checkout: create customer if needed, create order, clear cart and
    // redirect to order detail
    @PostMapping("/checkout")
    @Transactional
    public String performCheckout(@RequestParam(name = "customerId", required = false) Integer customerId,
            @RequestParam(name = "newCustomerName", required = false) String newCustomerName,
            @RequestParam(name = "newCustomerEmail", required = false) String newCustomerEmail,
            HttpSession session,
            HttpServletRequest request,
            RedirectAttributes ra) {
        Map<Integer, Integer> cart = getCart(session);
        if (cart == null || cart.isEmpty()) {
            ra.addFlashAttribute("error", "Cart is empty");
            return "redirect:/cart";
        }

        // create new customer if provided
        if ((customerId == null || customerId <= 0) && newCustomerName != null && !newCustomerName.isBlank()) {
            Customer c = new Customer();
            c.setName(newCustomerName.trim());
            if (newCustomerEmail != null && !newCustomerEmail.isBlank())
                c.setEmail(newCustomerEmail.trim());
            Customer saved = customerRepository.save(c);
            customerId = saved.getId();
        }

        // If still no customer selected/created, create a Guest customer to satisfy
        // not-null constraint
        if (customerId == null || customerId <= 0) {
            Customer guest = new Customer();
            guest.setName("Guest");
            Customer savedGuest = customerRepository.save(guest);
            customerId = savedGuest.getId();
        }

        // prepare productIds and quantities (allow override from form fields
        // quantity_{id})
        List<Integer> productIds = new ArrayList<>(cart.keySet());
        Map<Integer, Integer> quantities = new HashMap<>();
        for (Integer pid : productIds) {
            String q = request.getParameter("quantity_" + pid);
            int qty = 1;
            try {
                if (q != null && !q.isBlank())
                    qty = Integer.parseInt(q);
            } catch (NumberFormatException ex) {
                qty = cart.getOrDefault(pid, 1);
            }
            if (qty < 1)
                qty = 1;
            quantities.put(pid, qty);
        }

        try {
            Order order = new Order();
            Order saved = orderService.create(order, productIds, quantities, customerId);
            // clear cart
            session.removeAttribute(CART_SESSION_KEY);
            ra.addFlashAttribute("message", "Order created (id=" + saved.getId() + ")");
            return "redirect:/orders/" + saved.getId();
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "Failed to create order: " + ex.getMessage());
            return "redirect:/cart";
        }
    }

    // simple DTO for template
    public static class CartItem {
        private final Product product;
        private final int quantity;

        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getSubtotal() {
            return product.getPrice() * quantity;
        }
    }
}
