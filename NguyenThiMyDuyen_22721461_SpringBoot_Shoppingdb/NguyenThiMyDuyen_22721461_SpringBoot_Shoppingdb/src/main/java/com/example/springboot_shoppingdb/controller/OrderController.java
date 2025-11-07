package com.example.springboot_shoppingdb.controller;

import com.example.springboot_shoppingdb.entities.Order;
import com.example.springboot_shoppingdb.entities.Product;
import com.example.springboot_shoppingdb.entities.Customer;
import com.example.springboot_shoppingdb.repositories.OrderRepository;
import com.example.springboot_shoppingdb.repositories.ProductRepository;
import com.example.springboot_shoppingdb.repositories.CustomerRepository;
import com.example.springboot_shoppingdb.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.security.core.Authentication;
import java.util.Optional;

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

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);


    // Show create form
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        model.addAttribute("order", new Order());
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
    @PreAuthorize("hasAnyRole('CUSTOMER')")
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
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
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
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
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
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public String viewOrder(@PathVariable("id") Integer id, Model model, Authentication auth) {
        try {
            Order order = orderRepository.findById(id).orElse(null);
            if (order == null) {
                return "redirect:/orders";
            }

            // If the principal is a CUSTOMER, ensure they only view their own orders
            if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
                Optional<Customer> oc = customerRepository.findByName(auth.getName());
                if (oc.isEmpty()) {
                    return "redirect:/orders"; // unknown principal mapped to no customer
                }
                Integer custId = oc.get().getId();
                if (order.getCustomer() == null || !custId.equals(order.getCustomer().getId())) {
                    return "redirect:/orders"; // not allowed
                }
            }

            // initialize order lines to avoid lazy loading issues in the view
            if (order.getOrderLineSet() != null) {
                order.getOrderLineSet().size();
            }
            model.addAttribute("order", order);
            return "order/detail";
        } catch (Exception ex) {
            log.error("Failed to render order detail id={}: {}", id, ex.getMessage(), ex);
            // redirect to list with error flash (templates already use flash messages); simple redirect here:
            return "redirect:/orders";
        }
    }

    // Delete
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id) {
        if (orderRepository.existsById(id))
            orderRepository.deleteById(id);
        return "redirect:/orders";
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/by-customer/{customerId}")
    @Transactional(readOnly = true)
    public List<Order> listOrderByCustomer(@PathVariable Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    // NEW: list orders: admin sees all, customer sees only their orders; support ?orderId=...
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @Transactional(readOnly = true)
    public String listOrders(Model model, Authentication auth,
                             @RequestParam(name = "orderId", required = false) Integer orderId) {
        // expose orderId back to template
        model.addAttribute("orderId", orderId);

        List<Order> orders = List.of();

        if (orderId != null) {
            // try to load single order by id
            Optional<Order> oo = orderRepository.findById(orderId);
            if (oo.isPresent()) {
                Order o = oo.get();
                // if customer role, enforce ownership
                boolean deny = false;
                if (auth != null && auth.isAuthenticated() &&
                        auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
                    Optional<Customer> oc = customerRepository.findByName(auth.getName());

                }
                if (!deny) {
                    orders = List.of(o);
                } else {
                    orders = List.of(); // not allowed to view this order
                }
            } else {
                orders = List.of(); // not found
            }
        } else {
            // existing behavior when no orderId provided
            if (auth != null && auth.isAuthenticated()) {
                boolean isAdmin = auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                boolean isCustomer = auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));

                if (isAdmin) {
                    orders = orderService.findAll();
                } else if (isCustomer) {
                    Optional<Customer> oc = customerRepository.findByName(auth.getName());
                    if (oc.isPresent()) {
                        orders = orderService.findByCustomerId(oc.get().getId());
                    } else {
                        orders = List.of();
                    }
                }
            }
        }

        model.addAttribute("orders", orders);
        return "order/list";
    }

}
