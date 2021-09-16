package com.star.controller;



import java.security.Principal;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;
import com.star.dao.QueryRespostory;
import com.star.dao.UserRespostory;
import com.star.entity.Question;
import com.star.entity.User;
import com.star.helper.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class home_controller {

    @Autowired
    private BCryptPasswordEncoder passwardEncoder;

    @Autowired
    private UserRespostory userrepo;

    @Autowired
    private QueryRespostory queryRespostory;

    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home Psge");
        return "home";

    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About Page");
        return "about";
    }

    @RequestMapping("/signin")
    public String login(Model model) {
        model.addAttribute("title", "Log-in");
        return "login";
    }

    @RequestMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("title", "Sign-up");
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("user") User user ,BindingResult bResult,boolean argeement ,Model model, HttpSession session) {
        
        try {

        if(bResult.hasErrors()) {
            // System.out.println("ERROR" + bResult.toString());
            model.addAttribute("user", user);
            return "sign-up";
        }
        user.setRole("ROLE_USER");
        user.setPassward(passwardEncoder.encode(user.getPassward()));
        
        // System.out.println(" User " + user.toString());
        

        this.userrepo.save(user);

        model.addAttribute("user", new User());
        session.setAttribute("message", new Message("Data Saved Successfully", "alert-success"));
        model.addAttribute("user",user); 
        return "sign-up";
          
        } catch (Exception e) {
           e.printStackTrace();
           model.addAttribute("user", user);
           session.setAttribute("message", new Message("Something went wring!!" + e.getMessage(), "alert-danger"));
           return "sign-up";
        }

         
    }
    @RequestMapping("/queries/{page}")
    public String all_Queries(@PathVariable("page") int page,Model model,Principal principal){

        model.addAttribute("title", "Queries");

        
        Pageable pageable = PageRequest.of(page,5);
        Page<Question> Queries = this.queryRespostory.findAll(pageable);
        model.addAttribute("Queries", Queries);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage" , Queries.getTotalPages());
        
        return "all_queries";

    }

    @RequestMapping("/forgetpassword")
    public String forgetPassword() {

        return "forgetpassword";

    }

    @PostMapping("/forgetpassword/generateOTP")
    public String GenerateOTP(@RequestParam("email") String email,HttpSession session) {
        
        
        session.setAttribute("message", new Message("6 Number OTP sent to ur Register Email", "alert-success"));

        Random random = new Random(100001);
        int otp = random.nextInt(999999);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+otp);
        return "send-otp";

    }

}