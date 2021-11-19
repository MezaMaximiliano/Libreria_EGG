package com.libreryV3.librery30.Controller;

import com.libreryV3.librery30.Entitys.Usuario;
import com.libreryV3.librery30.Exception.UserValidationException;
import com.libreryV3.librery30.Services.RolService;
import com.libreryV3.librery30.Services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {

        ModelAndView mav = new ModelAndView("login");
        if (error != null) {
            mav.addObject("error", "Correo o clave incorrecta");
        }
        if (logout != null) {
            mav.addObject("logout", "Sesión Finalizada");
        }
        if (principal != null) {
            LOGGER.info("Principal -> {}", principal.getName());
            mav.setViewName("redirect:/home");
        }
        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView signup(@RequestParam(required = false) String error,HttpServletRequest request, Principal principal) {
        ModelAndView mav = new ModelAndView("signup");
        mav.addObject("title","Registrar usuario");
        Map<String , ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error-name"));
            mav.addObject("usuario", flashMap.get("usuario"));
        }else{
            mav.addObject("usuario",new Usuario());
        }
        if (principal != null) {
            LOGGER.info("Principal -> {}", principal.getName());
            mav.setViewName("redirect:/home");
        }

        return mav;
    }

    @PostMapping("/registro")
    public RedirectView signup(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes attributes, HttpServletRequest request) {
        RedirectView redirectView = new RedirectView("/login");
        try {
            usuarioService.crearUsuario(usuario);
            attributes.addFlashAttribute("exito", "Usuario registrado con exito.");
            request.login(usuario.getCorreo(),usuario.getClave());
            redirectView.setUrl("/home");

        } catch (UserValidationException e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
            attributes.addFlashAttribute("usuario", usuario);
            redirectView.setUrl("/signup");

        } catch (ServletException e){
            attributes.addFlashAttribute("error-name", "Error al iniciar sesion. Intente nuevamente");
        }
        return redirectView;
    }
}
