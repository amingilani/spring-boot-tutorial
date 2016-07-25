// package com.example;
//
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.MediaType;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.ui.ModelMap;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.ModelAndView;
//
// import javax.validation.Valid;
//
// import com.example.UserService;
//
//
//
// @Controller
// public class UserController {
//
//   @Autowired
//   private UserRepository userRepository;
//
//   @RequestMapping(value = "/user/register", method = RequestMethod.GET)
//   public String register(User user) {
//       return "user/register";
//   }
//
//   @RequestMapping(value = "/user/register", method = RequestMethod.POST)
//   public String registerPost(@Valid User user, BindingResult result) {
//       if (result.hasErrors()) {
//           return "user/register";
//       }
//
//       User registeredUser = userService.register(user);
//       if (registeredUser != null) {
//           return "user/register-success";
//       } else {
//           result.rejectValue("email", "error.alreadyExists", "This username or email already exists, please try to reset password instead.");
//           return "user/register";
//       }
//   }
//
// }
