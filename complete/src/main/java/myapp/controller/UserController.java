package myapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import javax.validation.Valid;

import myapp.repository.UserRepository;
import myapp.entity.User;




@Controller
public class UserController {

@Autowired
private UserRepository repository;

@RequestMapping(value = "/", method = RequestMethod.GET)
public String showLoginForm() {
        return "login";
}

@RequestMapping(value = "/register", method = RequestMethod.POST)
public String registerUserAccount(WebRequest request,
                                  @ModelAttribute("user") @Valid User user,
                                  BindingResult result) {
        if (!result.hasErrors()) {
                repository.save(user);

                System.out.println("saved the user!");
                System.out.println(
                        repository.findByUserName(user.getUserName() ));


        }
        return "redirect:/advice";

}
}
