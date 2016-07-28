package com.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Controller
public class UserController {

private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

@RequestMapping(value = "/user/registration", method = RequestMethod.GET)
public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
}

@RequestMapping(value = "/user/registration", method = RequestMethod.POST)
public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto accountDto,
                                        BindingResult result, WebRequest request, Errors errors) {
        User registered = new User();
        if (!result.hasErrors()) {
                registered = createUserAccount(accountDto, result);
        }
        if (registered == null) {
                result.rejectValue("email", "message.regError");
        }
        // rest of the implementation
}
private User createUserAccount(UserDto accountDto, BindingResult result) {
        User registered = null;
        try {
                registered = service.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e) {
                return null;
        }
        return registered;

}
}
