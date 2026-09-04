package com.example.simpleecommerceapp.controller;

import com.example.simpleecommerceapp.entity.Admin;
import com.example.simpleecommerceapp.entity.Order;
import com.example.simpleecommerceapp.entity.Product;
import com.example.simpleecommerceapp.entity.User;
import com.example.simpleecommerceapp.service.AdminService;
import com.example.simpleecommerceapp.service.OrderService;
import com.example.simpleecommerceapp.service.ProductService;
import com.example.simpleecommerceapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

     @Autowired
     private ProductService productService;

     @Autowired
     private UserService userService;

     @Autowired
     private OrderService orderService;




    @GetMapping("/admin/verify/credentials")
    public String verifyCredentials(@ModelAttribute("admin") Admin admin, Model model) {
        if (adminService.verifyCredentials(admin.getEmail(), admin.getPassword())) {
            model.addAttribute("admin", new Admin());
            model.addAttribute("user", new User());
            model.addAttribute("product", new Product());
            return "redirect:/admin/home";
        }

        model.addAttribute("error", "Invalid email or password");
        return "LoginPage";
    }
    @GetMapping("/admin/home")
    public String home(Model model){
        model.addAttribute("adminList", adminService.getAllAdmin());
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("orderList", orderService.getAllOrders());
        model.addAttribute("productList", productService.getAllProducts());

        return "AdminHomePage";
    }



    @PostMapping("/add/admin")
    public String createAdmin(Admin admin){
        adminService.createUser(admin);
        return "redirect:/admin/home";
    }

    @GetMapping("/update/admin/{id}")
    public String updateAdmin(@PathVariable("id") Long id, Model model){
        Admin admin = adminService.getAdminById(id);
        model.addAttribute("admin", adminService.getAdminById(id));
        return "UpdateAdmin";
    }

    @PostMapping("/update/admin")
    public String updateAdmin(Admin admin){
        adminService.updateAdmin(admin);
        return "redirect:/admin/home";
    }

    @GetMapping("/delete/admin/{id}")
    public String deleteAdmin(@PathVariable("id") Long id){
        adminService.deleteAdmin(id);
        return "redirect:/admin/home";
    }

    @PostMapping("/user/login")
    public String userLogin(User user,
                            HttpSession session,
                            Model model) {

        boolean isValid = userService.verifyCredentials(
                user.getEmail(),
                user.getPassword()
        );

        if (isValid) {

            User existingUser = userService.findUserByEmail(user.getEmail());

            session.setAttribute("loggedUser", existingUser);

            model.addAttribute("ordersList",
                    orderService.findOrdersByUser(existingUser));

            return "BuyProductPage";
        }

        model.addAttribute("error", "Invalid email or password");
        return "LoginPage";
    }

    @GetMapping("/user/home")
    public String userHome(HttpSession session,
                           Model model) {

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("ordersList",
                orderService.findOrdersByUser(loggedUser));

        return "BuyProductPage";
    }

    @PostMapping("/product/search")
    public String productSearch(String name,
                                HttpSession session,
                                Model model) {

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        Product product = productService.findProductByName(name);

        model.addAttribute("ordersList",
                orderService.findOrdersByUser(loggedUser));

        if (product != null) {
            model.addAttribute("product", product);
        } else {
            model.addAttribute("messageError",
                    "Sorry, product was not found...");
        }

        return "BuyProductPage";
    }


    @PostMapping("/place/order")
    public String placeOrder(Order order,
                             HttpSession session) {

        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        double totalAmount = order.getPrice() * order.getQuantity();
        order.setAmount(totalAmount);
        order.setUser(loggedUser);
        order.setDate(new Date());

        orderService.createOrder(order);

        return "redirect:/user/home";
    }
}
