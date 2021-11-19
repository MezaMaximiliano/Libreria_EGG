package com.libreryV3.librery30.Controller;

import com.libreryV3.librery30.Services.RolService;
import com.libreryV3.librery30.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ModelAndView mostrarRoles(){
        ModelAndView mav = new ModelAndView("roles");
        mav.addObject("roles",rolService.traerRoles());
        mav.addObject("title","Lista Roles");
        return mav;
    }
}
