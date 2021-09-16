package com.star.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.star.dao.QueryRespostory;
import com.star.dao.UserRespostory;
import com.star.entity.User;
import com.star.helper.Message;
import com.star.entity.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/user")
public class user_controlller {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRespostory userRespostory;

    @Autowired
    private QueryRespostory queryRespostory;
    

    @ModelAttribute
    public void addCommonData(Model model,Principal principal){
        String userName = principal.getName();
    
        User user = userRespostory.getUserFromUserEmail(userName);
        model.addAttribute("user", user);

    }
    
    @RequestMapping("/profile")
    public String userProfile(Model model,Principal  principal){

        return "normal/user_profile";
    }

    @RequestMapping("/settings")
    public String userSetting(Model model,Principal  principal){

        return "normal/settingPage";
    }

    @PostMapping("/profile/edit")
    public String processingEditedProfile(@ModelAttribute("user") User user,BindingResult bResult,Model model,Principal principal){
        
        try {
            this.userRespostory.save(user);
            List<Question> queries = this.queryRespostory.findAllByUser(user);
            queries.forEach(e -> {
                e.setUser(user);    
                this.queryRespostory.save(e);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/user/profile";
    }

    @PostMapping("/profile/password")
    public String processingEditedPassword( @RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,@RequestParam("confirmNewPassword") String confirmNewPassword,Model model,HttpSession session,Principal principal){

        System.out.println(newPassword + confirmNewPassword);
        if(!newPassword.matches(confirmNewPassword)){
            session.setAttribute("message", new Message("Password doesn't match🤔! ~~ Your Confirmed Passord didn't match with New Password!! \n try again", "alert-danger"));

            model.addAttribute("oldPassword", oldPassword);
            model.addAttribute("confirmNewPassword", confirmNewPassword);
            return "normal/settingPage";
        }
        User user = this.userRespostory.getUserFromUserEmail(principal.getName());
        
        if(bCryptPasswordEncoder.matches(oldPassword, user.getPassward())){
            //change the passward
            user.setPassward(bCryptPasswordEncoder.encode(newPassword));
            this.userRespostory.save(user);
            session.setAttribute("message", new Message("Password matched!! ~~ Your Passward Change Sucessfully!!", "alert-success"));
            return "redirect:/user/profile";

        } else{
            session.setAttribute("message", new Message("Password doesn't match!! ~~ Your Old Password didn't match with Original Password!! \n try again", "alert-danger"));
            return "normal/settingPage";
        }
    }


    @RequestMapping("/dashboard")
    public String user_dashboard(Model model,Principal  principal){

        return "normal/user_dashboard";
    }

    @RequestMapping("/askquery")
    public String ask_query(Model model,Principal principal) {
        model.addAttribute("title", "Ask-Query");
        model.addAttribute("query", new Question());
        
        return "normal/ask_query";

    }

    @PostMapping("/process-contact")
    public String process_contact(@Valid @ModelAttribute("query") Question query,BindingResult bResult ,Model model,HttpSession session,Principal principal,@RequestParam("imgURL") MultipartFile file){

        try {
            // if (bResult.hasErrors()) {
            //     session.setAttribute("message", new Message("Something went Wrong plz try again", "alert-danger"));
            //     return "normal/ask_query";
            // }
            
            //processing img and file 
            if(!file.isEmpty()) {
                //file the file to folder and update the name to query
                query.setImgURL(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename()); 
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Images is uploaded");
            }else{
                System.out.println("file is empty");
            }
            
    
            String email = principal.getName();
            User user = this.userRespostory.getUserFromUserEmail(email);
            query.setQueryEmail(user.getEmail());
            query.setUser(user);
            user.getQueries().add(query);
            this.userRespostory.save(user);  
            model.addAttribute("title", "Ask-Query");
            model.addAttribute("query",query); 
            session.setAttribute("message", new Message("Your Query is Active now", "alert-success"));                        
            // System.out.println("Data :" + query);
            
            return "normal/ask_query";

        } catch (Exception e) {
            session.setAttribute("message", new Message(e.getMessage(), "alert-danger"));
            System.out.println("ERROR :" + e.getMessage());
            e.printStackTrace();

            return "normal/ask_query";
        }
        
    }
    @RequestMapping("/queries/{page}")
    public String all_Queries(@PathVariable("page") Integer page,Model model,Principal principal){
        /*
        to apply pagination 
        -you have to first add pathvariable 
        -show contact table
        -per page = n
        -current page = 0 [page] 
        */
         model.addAttribute("title", "Queries");

         //getting list of queries
         String userName = principal.getName();
         User user = this.userRespostory.getUserFromUserEmail(userName);
        
        Pageable pageable = PageRequest.of(page,5);
        
         //method--1
        // List<Question> userQueries = user.getQueries();
        //method--2
        Page<Question> userQueries = this.queryRespostory.getQueriesByUserId(user.getUserId(),pageable);
        model.addAttribute("userQueries", userQueries);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage" , userQueries.getTotalPages()); 
        
        return "normal/user_queries";

    }

    
    
}