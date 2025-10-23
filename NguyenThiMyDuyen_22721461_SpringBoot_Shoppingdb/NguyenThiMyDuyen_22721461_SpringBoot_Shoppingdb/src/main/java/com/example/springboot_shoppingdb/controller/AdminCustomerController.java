package com.example.springboot_shoppingdb.controller;

import com.example.springboot_shoppingdb.entities.Customer;
import com.example.springboot_shoppingdb.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/customers")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping
	public String list(Model model) {
		List<Customer> customers = customerRepository.findAll();
		model.addAttribute("customers", customers);
		return "admin/customers/list";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		model.addAttribute("customer", new Customer());
		model.addAttribute("mode", "create");
		// provide roles for the select field to avoid null/binding issues in template
		model.addAttribute("roles", List.of("CUSTOMER", "ADMIN"));
		return "admin/customers/form";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute Customer customer, RedirectAttributes ra) {
		try {
			Customer toSave = new Customer();
			// copy allowed fields
			toSave.setName(customer.getName());
			toSave.setEmail(customer.getEmail());
			toSave.setRole(customer.getRole() != null && !customer.getRole().isBlank() ? customer.getRole() : "CUSTOMER");
			if (customer.getPassword() != null && !customer.getPassword().isBlank()) {
				toSave.setPassword(passwordEncoder.encode(customer.getPassword()));
			}
			customerRepository.save(toSave);
			ra.addFlashAttribute("message", "Customer created");
		} catch (Exception ex) {
			ra.addFlashAttribute("error", "Failed to create customer: " + ex.getMessage());
		}
		return "redirect:/admin/customers";
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Integer id, Model model) {
		Customer c = customerRepository.findById(id).orElse(null);
		if (c == null)
			return "redirect:/admin/customers";
		model.addAttribute("customer", c);
		model.addAttribute("mode", "edit");
		// provide roles for the select field
		model.addAttribute("roles", List.of("CUSTOMER", "ADMIN"));
		return "admin/customers/form";
	}

	@PostMapping("/edit/{id}")
	public String update(@PathVariable Integer id, @ModelAttribute Customer customer, RedirectAttributes ra) {
		try {
			Customer existing = customerRepository.findById(id).orElse(null);
			if (existing == null) {
				ra.addFlashAttribute("error", "Customer not found");
				return "redirect:/admin/customers";
			}
			// copy allowed fields only
			existing.setName(customer.getName());
			existing.setEmail(customer.getEmail());
			if (customer.getRole() != null && !customer.getRole().isBlank()) {
				existing.setRole(customer.getRole());
			}
			// update password only if provided
			if (customer.getPassword() != null && !customer.getPassword().isBlank()) {
				existing.setPassword(passwordEncoder.encode(customer.getPassword()));
			}
			customerRepository.save(existing);
			ra.addFlashAttribute("message", "Customer updated");
		} catch (Exception ex) {
			ra.addFlashAttribute("error", "Failed to update customer: " + ex.getMessage());
		}
		return "redirect:/admin/customers";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes ra) {
		if (customerRepository.existsById(id)) {
			customerRepository.deleteById(id);
			ra.addFlashAttribute("message", "Customer deleted");
		}
		return "redirect:/admin/customers";
	}
}
