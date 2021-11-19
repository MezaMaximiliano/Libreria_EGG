package com.libreryV3.librery30.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class PrincipalController {

    @GetMapping("/home")
    public ModelAndView inicio(){

        return new ModelAndView("index");
    }
}
