package myapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AdviceController {

@RequestMapping(value = "/advice", method = RequestMethod.GET)
public String showAdvice() {
        return "advice";
}

}
