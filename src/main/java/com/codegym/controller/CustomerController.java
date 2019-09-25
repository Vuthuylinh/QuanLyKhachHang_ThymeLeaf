package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.CustomerForm;
import com.codegym.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;

@Controller

public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/")
//    public ModelAndView listCustomer() {
//        ModelAndView modelAndView = new ModelAndView("/customer/list");
//        List<Customer> customerList = customerService.findByAll();
//        modelAndView.addObject("customerList", customerList);
//        return modelAndView;
//    }

    public String listCustomer(Model model) {
        model.addAttribute("customerList", customerService.findByAll());
        return "customer/list";
    }

    @GetMapping("/create-customer")
    public String createCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "/customer/create";
    }


// @PostMapping("/save-customer")
//    public String saveCustomer(Customer customer, RedirectAttributes redirect){
//        customer.setId((int)(Math.random()*1000));
//        customerService.save(customer);
//        redirect.addFlashAttribute("success","Saved customer successfully!");
//        return "redirect:/";
// }

    @PostMapping("/save-customer")
    public ModelAndView saveCustomer(@ModelAttribute CustomerForm customerForm, BindingResult result) {
        // tham khảo: https://github.com/codegym-vn/spring-static-resources

        // tao doi tuong de luu vao db
        String fileName = customerService.uploadFile(customerForm, result);
        customerForm.setId((int) (Math.random() * 1000));
        Customer customerObject = new Customer(customerForm.getId(), customerForm.getName(), customerForm.getAddress(), customerForm.getGender(), fileName);
        // luu vao db
        customerService.save(customerObject);
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("success", "A new Customer was just created");
        return modelAndView;
    }

    @GetMapping("/customer/{id}/edit")
    public String editCustomerForm(@PathVariable int id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "/customer/update";
    }

    @PostMapping("/customer/update")
    public ModelAndView updateCustomer(@ModelAttribute CustomerForm customerForm, BindingResult result) {
        Customer customerObject = customerService.findById(customerForm.getId());
        String fileName = customerService.uploadFile(customerForm, result);
        customerObject.setId(customerForm.getId());
        customerObject.setName(customerForm.getName());
        customerObject.setAddress(customerForm.getAddress());
        customerObject.setGender(customerForm.getGender());
        if(!fileName.equals("")){
            customerObject.setAvatar(fileName);
        }

        // luu vao db
        customerService.save(customerObject);
        ModelAndView modelAndView = new ModelAndView("/customer/update");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("success", "Customer is updated successfully!");
        return modelAndView;
    }

    @GetMapping("/customer/{id}/delete")
    public String deleteCustomer(@PathVariable int id, Model model ){
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "customer/delete";
    }

    @PostMapping("/customer/delete")
    public String delete(Customer customer, RedirectAttributes redirect) {
        customerService.remove(customer.getId());
        redirect.addFlashAttribute("success", "Removed customer successfully!");
        return "redirect:/";
    }

    @GetMapping("/customer/{id}/view")
    public String viewDetailCustomer(@PathVariable int id, Model model){
        Customer customer = customerService.findById(id);
        model.addAttribute("customer",customer);
        return "customer/view";
    }
}
