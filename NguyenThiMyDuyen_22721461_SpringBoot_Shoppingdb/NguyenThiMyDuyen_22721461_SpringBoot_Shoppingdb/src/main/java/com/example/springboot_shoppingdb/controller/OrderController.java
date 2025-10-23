package com.example.springboot_shoppingdb.controller;

import com.example.springboot_shoppingdb.entities.Order;
import com.example.springboot_shoppingdb.entities.Product;
import com.example.springboot_shoppingdb.entities.Customer;
import com.example.springboot_shoppingdb.repositories.OrderRepository;
import com.example.springboot_shoppingdb.repositories.ProductRepository;
import com.example.springboot_shoppingdb.repositories.CustomerRepository;
import com.example.springboot_shoppingdb.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
//    private Map<String, Integer> usernameToCustomerId; // injected from SecurityConfig

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    // List orders
//    @GetMapping
//    @Transactional(readOnly = true)
//    public String listOrders(Model model,
//            Authentication auth,
//            @RequestParam(name = "productName", required = false) String productName,
//            @RequestParam(name = "customerName", required = false) String customerName) {
//
//        List<Order> orders;
//
//        boolean hasProduct = productName != null && !productName.isBlank();
//        boolean hasCustomer = customerName != null && !customerName.isBlank();
//
//        // If authenticated user is a CUSTOMER (not ADMIN), restrict to their orders
//        if (auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
//            Integer custId = usernameToCustomerId.get(auth.getName());
//            if (custId == null) {
//                // no mapping, return empty list for safety
//                orders = Collections.emptyList();
//            } else {
//                if (!hasProduct && !hasCustomer) {
//                    orders = orderRepository.findByCustomer_Id(custId);
//                } else if (hasProduct && !hasCustomer) {
//                    // filter product results limited to this customer
//                    orders = orderRepository.findDistinctByOrderLineSet_Product_NameContainingIgnoreCase(productName.trim()).stream()
//                            .filter(o -> o.getCustomer() != null && o.getCustomer().getId() == custId)
//                            .collect(Collectors.toList());
//                } else if (!hasProduct && hasCustomer) {
//                    // if customerName filter provided but user is customer, ignore provided name and show only their orders
//                    orders = orderRepository.findByCustomer_Id(custId);
//                } else {
//                    // both present: intersect then restrict to custId
//                    List<Order> byProduct = orderRepository.findDistinctByOrderLineSet_Product_NameContainingIgnoreCase(productName.trim());
//                    orders = byProduct.stream().filter(o -> o.getCustomer() != null && o.getCustomer().getId() == custId)
//                            .collect(Collectors.toList());
//                }
//            }
//        } else {
//            // admin or anonymous (anonymous won't reach here due to security rule allowing only authenticated for /orders)
//            if (!hasProduct && !hasCustomer) {
//                orders = orderRepository.findAll();
//            } else if (hasProduct && !hasCustomer) {
//                orders = orderRepository.findDistinctByOrderLineSet_Product_NameContainingIgnoreCase(productName.trim());
//            } else if (!hasProduct && hasCustomer) {
//                orders = orderRepository.findByCustomer_NameContainingIgnoreCase(customerName.trim());
//            } else {
//                List<Order> byProduct = orderRepository
//                        .findDistinctByOrderLineSet_Product_NameContainingIgnoreCase(productName.trim());
//                List<Order> byCustomer = orderRepository.findByCustomer_NameContainingIgnoreCase(customerName.trim());
//                Set<Integer> ids = byCustomer.stream().map(Order::getId).collect(Collectors.toSet());
//                orders = byProduct.stream().filter(o -> ids.contains(o.getId())).collect(Collectors.toList());
//            }
//        }
//
//        // initialize order lines
//        orders.forEach(o -> {
//            if (o.getOrderLineSet() != null)
//                o.getOrderLineSet().size();
//        });
//
//        model.addAttribute("orders", orders);
//        model.addAttribute("productName", productName != null ? productName : "");
//        model.addAttribute("customerName", customerName != null ? customerName : "");
//        return "order/list";
//    }
//
//    // Show create form
    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        model.addAttribute("order", new Order());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("products", productRepository.findAll());

        // read cart from session and prefill selectedProductIds and selectedQuantities
        Object o = session.getAttribute("cart");
        if (o instanceof Map) {
            Map<Integer, Integer> cart = (Map<Integer, Integer>) o;
            Set<Integer> selectedProductIds = cart.keySet();
            Map<Integer, Integer> selectedQuantities = new HashMap<>(cart);
            model.addAttribute("selectedProductIds", selectedProductIds);
            model.addAttribute("selectedQuantities", selectedQuantities);
        } else {
            model.addAttribute("selectedProductIds", Collections.emptySet());
            model.addAttribute("selectedQuantities", Collections.emptyMap());
        }
        return "order/create";
    }

    // Handle create: delegate to service, create new customer if provided, clear
    // cart after success
    @PostMapping("/create")
    @Transactional
    public String createOrder(@ModelAttribute Order order,
            @RequestParam(name = "customerId", required = false) Integer customerId,
            @RequestParam(name = "newCustomerName", required = false) String newCustomerName,
            @RequestParam(name = "newCustomerEmail", required = false) String newCustomerEmail,
            @RequestParam(name = "productIds", required = false) List<Integer> productIds,
            @RequestParam(name = "date", required = false) String dateStr,
            HttpServletRequest request,
            HttpSession session) {

        // if new customer info provided, create customer and use its id
        if ((customerId == null || customerId <= 0) && newCustomerName != null && !newCustomerName.isBlank()) {
            Customer c = new Customer();
            c.setName(newCustomerName.trim());
            if (newCustomerEmail != null && !newCustomerEmail.isBlank())
                c.setEmail(newCustomerEmail.trim());
            Customer saved = customerRepository.save(c);
            customerId = saved.getId();
        }

        // parse date (keep behaviour)
        if (dateStr != null && !dateStr.isBlank()) {
            try {
                order.setDate(LocalDate.parse(dateStr));
            } catch (Exception e) {
                order.setDate(LocalDate.now());
            }
        } else if (order.getDate() == null) {
            order.setDate(LocalDate.now());
        }

        // build quantities map from params named "quantity_{productId}"
        Map<Integer, Integer> quantities = new HashMap<>();
        if (productIds != null) {
            for (Integer pid : productIds) {
                String q = request.getParameter("quantity_" + pid);
                try {
                    int qty = (q == null || q.isBlank()) ? 1 : Integer.parseInt(q);
                    if (qty < 1)
                        qty = 1;
                    quantities.put(pid, qty);
                } catch (NumberFormatException ex) {
                    quantities.put(pid, 1);
                }
            }
        }

        orderService.create(order, productIds, quantities, customerId);

        // clear cart from session after successful order creation
        session.removeAttribute("cart");
        return "redirect:/orders";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    @Transactional(readOnly = true)
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        try {
            Order order = orderRepository.findById(id).orElse(null);
            if (order == null) {
                log.warn("Order not found for id={}", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found: " + id);
            }
            // initialize order lines and products while persistence context is open
            if (order.getOrderLineSet() != null) {
                order.getOrderLineSet().forEach(ol -> {
                    // touch product to initialize if lazy
                    if (ol.getProduct() != null)
                        ol.getProduct().getName();
                });
            }

            // build set of selected product ids for template
            Set<Integer> selectedProductIds = (order.getOrderLineSet() == null)
                    ? Collections.emptySet()
                    : order.getOrderLineSet().stream()
                            .map(ol -> ol.getProduct() != null ? ol.getProduct().getId() : null)
                            .filter(idv -> idv != null)
                            .collect(Collectors.toSet());
            // build map of selected quantities for template (productId -> quantity)
            Map<Integer, Integer> selectedQuantities = (order.getOrderLineSet() == null)
                    ? Collections.emptyMap()
                    : order.getOrderLineSet().stream()
                            .filter(ol -> ol.getProduct() != null)
                            .collect(Collectors.toMap(ol -> ol.getProduct().getId(), ol -> ol.getQuantity()));

            model.addAttribute("order", order);
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("products", productRepository.findAll());
            model.addAttribute("selectedProductIds", selectedProductIds);
            model.addAttribute("selectedQuantities", selectedQuantities);
            return "order/edit";
        } catch (ResponseStatusException ex) {
            throw ex; // rethrow 404
        } catch (Exception ex) {
            log.error("Failed to load edit form for order id={}: {}", id, ex.getMessage(), ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to open edit form");
        }
    }

    // Handle update: delegate to service
    @PostMapping("/edit/{id}")
    @Transactional
    public String updateOrder(@PathVariable("id") Integer id,
            @ModelAttribute Order order,
            @RequestParam(name = "customerId", required = false) Integer customerId,
            @RequestParam(name = "productIds", required = false) List<Integer> productIds,
            @RequestParam(name = "date", required = false) String dateStr,
            HttpServletRequest request) {
        // parse date (service will respect order.date if provided)
        if (dateStr != null && !dateStr.isBlank()) {
            try {
                order.setDate(LocalDate.parse(dateStr));
            } catch (Exception e) {
                /* ignore */ }
        }

        Map<Integer, Integer> quantities = new HashMap<>();
        if (productIds != null) {
            for (Integer pid : productIds) {
                String q = request.getParameter("quantity_" + pid);
                try {
                    int qty = (q == null || q.isBlank()) ? 1 : Integer.parseInt(q);
                    if (qty < 1)
                        qty = 1;
                    quantities.put(pid, qty);
                } catch (NumberFormatException ex) {
                    quantities.put(pid, 1);
                }
            }
        }

        orderService.update(id, order, productIds, quantities, customerId);
        return "redirect:/orders";
    }

    // View detail
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
//    public String viewOrder(@PathVariable("id") Integer id, Model model, Authentication auth) {
//        Order order = orderRepository.findById(id).orElse(null);
//        if (order == null)
//            return "redirect:/orders";
//
//        // If the principal is a CUSTOMER, ensure they only view their own orders
//        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
//            Integer custId = usernameToCustomerId.get(auth.getName());
//            if (custId == null || order.getCustomer() == null || order.getCustomer().getId() != custId) {
//                return "redirect:/orders"; // not allowed
//            }
//        }
//
//        if (order.getOrderLineSet() != null)
//            order.getOrderLineSet().size();
//        model.addAttribute("order", order);
//        return "order/detail";
//    }

    // Delete
    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id) {
        if (orderRepository.existsById(id))
            orderRepository.deleteById(id);
        return "redirect:/orders";
    }
}
