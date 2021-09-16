package com.star.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.star.dao.AnswerRespostory;
import com.star.dao.CommentRespostory;
import com.star.dao.QueryRespostory;
import com.star.dao.UserRespostory;
import com.star.entity.Answer;
import com.star.entity.AnswerComments;
import com.star.entity.Comment;
import com.star.entity.Question;
import com.star.entity.User;
import com.star.helper.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user/query")
public class Query_Handler {

    @Autowired
    private UserRespostory userRespostory;

    @Autowired
    private QueryRespostory queryRespostory;

    @Autowired
    private CommentRespostory commentRespostory;

    @Autowired
    private AnswerRespostory answerRespostory;


    @ModelAttribute
    public void addCommonData(Model model,Principal principal){
        String userName = principal.getName();
    
        User user = userRespostory.getUserFromUserEmail(userName);
        model.addAttribute("user", user);

    }

    /** showing particular query */
    @GetMapping("/{queryId}")
    public String showQueryDetail(@PathVariable("queryId") Integer queryId,Model model,Principal principal){

        String userName = principal.getName();
        User user = this.userRespostory.getUserFromUserEmail(userName);
        
        Optional<Question> queryTemp = this.queryRespostory.findById(queryId);
        Question query = queryTemp.get();

        
        String title = "Query- " + queryId;

        List<Comment> comments = query.getComments();
        model.addAttribute("comments", comments);

        List<Answer> answers = query.getAnswers();
        model.addAttribute("answers", answers);
  

        // justing trying temp
        

        // temp ends here
        model.addAttribute("answer", new Answer());
        model.addAttribute("title", title);
        model.addAttribute("comment", new Comment());
        model.addAttribute("query", query);
        model.addAttribute("user", user);
       
        return "showQueryById";
    }

    /* editing query */
    @RequestMapping("/{queryId}/edit")
    public String editQuery(@PathVariable Integer queryId,Model model){
        
        Question query = this.queryRespostory.findById(queryId).get();
        model.addAttribute("query", query);

        return "normal/edit-query";
    }

    /*processing edited query */
    @PostMapping("/{queryId}/process-edit")
    public String processingEdiedtQuery(@PathVariable("queryId") Integer queryId,@ModelAttribute("query") Question query, BindingResult bResult ,Model model, Principal principal,@RequestParam("imgURL") MultipartFile file){
        
        try {

            Question oldQueryDeatails = this.queryRespostory.getById(queryId);
            
            User user = this.userRespostory.getUserFromUserEmail(principal.getName());
            query.setUser(user);
            query.setQueryEmail(user.getEmail());

            if(!file.isEmpty()){
                //delete the previous one and update it
                //deleting 
                File deleteFile = new ClassPathResource("static/img").getFile();
                File tempFile = new File(deleteFile, oldQueryDeatails.getImgURL());
                tempFile.delete();

                //updating
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename()); 
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                query.setImgURL(file.getOriginalFilename());

            } else{
                query.setImgURL(oldQueryDeatails.getImgURL());
            }
            this.queryRespostory.save(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        return "redirect:/user/query/{queryId}";
    }


    /**processing answer  */
    @PostMapping("/answerProcessing/{queryId}")
    public String processingAnswer(@PathVariable("queryId") Integer queryId,@Valid @ModelAttribute("answer") Answer answer,BindingResult bResult,Model model,Principal principal){
        
        String userName = principal.getName();
        User user = this.userRespostory.getUserFromUserEmail(userName);

        Question query = this.queryRespostory.findById(queryId).get();

        answer.setQuery(query);
        answer.setAnsEmail(user.getEmail());
        answer.setAnsName(user.getName());
        answer.setCreatedAt(java.time.LocalDate.now());
        answer.setParentId(queryId);


        query.getAnswers().add(answer);
        this.queryRespostory.save(query);

        String title = "Query- " + queryId;
        List<Answer> answers = query.getAnswers();

        List<AnswerComments> answerComments = answer.getAnswerComments();

        model.addAttribute("answerComments", answerComments);
        model.addAttribute("title", title);
        model.addAttribute("answers", answers);
        model.addAttribute("user", user);
        model.addAttribute("query", query);

        return "redirect:/user/query/{queryId}";
    }

    // processing comment 
    @PostMapping("/commentProcessing/{queryId}")
    public String commentProcessing(@PathVariable("queryId") Integer queryId,@Valid @ModelAttribute("comment") Comment comment,BindingResult bResult ,Model model,HttpSession session,Principal principal){

        try {
            String userName = principal.getName();
            User user = this.userRespostory.getUserFromUserEmail(userName);

            Optional<Question> queryTemp = this.queryRespostory.findById(queryId);
            Question query = queryTemp.get();

            comment.setQuery(query);

            comment.setCommentEmail(user.getEmail());
            comment.setCommentName(user.getName());
            comment.setParentId(query.getQueryId());
            comment.setCreatedAt(java.time.LocalDate.now());
            query.getComments().add(comment);
            this.queryRespostory.save(query);
            
            String title = "Query- " + queryId;
            List<Comment> comments = query.getComments();
            model.addAttribute("comments", comments);
            model.addAttribute("title", title);
            model.addAttribute("user", user);
            model.addAttribute("query", query);

            session.setAttribute("message", new Message("Your Query is Active now", "alert-success"));
            

            return "redirect:/user/query/{queryId}";
            
        } catch (Exception e) {
            session.setAttribute("message", new Message(e.getMessage(), "alert-danger"));
            System.out.println("ERROR :" + e.getMessage());
            e.printStackTrace();

            return"redirect:/user/query/{queryId}";
        }
       
    }

    /**deleting the comment */
    @RequestMapping("/{queryId}/delete/comment/{commentId}")
    public String deletingComment(@PathVariable("commentId") Integer commentId,@PathVariable("queryId") Integer queryId,HttpSession session,Model model){
        
        try {

            Comment comment =  this.commentRespostory.findById(commentId).get();
            Question query = comment.getQuery();
            // System.out.println(commentId);
            // // tempory 
            query.getComments().remove(comment);

            // //ends here
            comment.setQuery(null);
            // System.out.println(comment.getQuery());
            this.commentRespostory.save(comment);
            this.queryRespostory.save(query);
            this.commentRespostory.delete(comment);
            session.setAttribute("message", new Message("Comment is deleted", "alert-success"));
            
            return"redirect:/user/query/{queryId}";   

    } catch (Exception e) {
        e.printStackTrace();
        return"redirect:/user/query/{queryId}";       
     }
        
    }

    /**deleting the answer */
    @RequestMapping("/{queryId}/delete/answer/{ansId}")
    public String deletingAnswer(@PathVariable("ansId") Integer ansId,@PathVariable("queryId") Integer queryId,HttpSession session,Model model){
    
        try {

            Answer answer = this.answerRespostory.getById(ansId);
            Question query = answer.getQuery();

            query.getAnswers().remove(answer);
            answer.setQuery(null);

            this.answerRespostory.save(answer);
            this.queryRespostory.save(query);
            this.answerRespostory.delete(answer);

            session.setAttribute("message", new Message("answer is deleted", "alert-success"));

            return"redirect:/user/query/{queryId}";

        } catch (Exception e) {

            e.printStackTrace();
            return"redirect:/user/query/{queryId}"; 

        }
       
    }

    
    
}
