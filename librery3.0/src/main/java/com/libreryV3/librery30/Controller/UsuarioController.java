package com.libreryV3.librery30.Controller;

import com.libreryV3.librery30.Entitys.Usuario;
import com.libreryV3.librery30.Exception.UserValidationException;
import com.libreryV3.librery30.Services.RolService;
import com.libreryV3.librery30.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @GetMapping
    public ModelAndView listaUsuarios(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("usuarios");
        Map<String,?> map = RequestContextUtils.getInputFlashMap(request);

        mav.addObject("usuarios",usuarioService.traerUsuarios());
        mav.addObject("roles",rolService.traerRoles());
        mav.addObject("title","Lista de Usuarios");

        if(map!=null){

            mav.addObject("nombre",map.get("nombre"));
            mav.addObject("correo",map.get("correo"));
            mav.addObject("error",map.get("error-name"));

        }

        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearUsuario(){
        ModelAndView mav = new ModelAndView("usuario-formulario");
        mav.addObject("usuario",new Usuario());
        mav.addObject("roles",rolService.traerRoles());
        mav.addObject("title","Registrar usuario");
        mav.addObject("action","guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ModelAndView editarUsuario(@PathVariable Integer id, HttpServletRequest request){
        Map<String , ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        ModelAndView mav = new ModelAndView("usuario-formulario");
        mav.addObject("usuario",usuarioService.buscarUsuarioId(id));
        mav.addObject("roles",rolService.traerRoles());

        if(flashMap!=null){
            mav.addObject("error",flashMap.get("error-name"));
        }

        mav.addObject("title","Editar usuario");
        mav.addObject("action","modificar");
        return mav;
    }

    @GetMapping("/editar_user")
    public ModelAndView editarUsuario(Principal principal){
        ModelAndView mav = new ModelAndView("usuario-formulario");
        Usuario usuario = usuarioService.buscarUsuarioCorreo(principal.getName());

        mav.addObject("usuario",usuario);
        mav.addObject("title","Editar usuario");
        mav.addObject("action","modificar");

        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes attributes){
        try {
            usuarioService.crearUsuario(usuario);
        } catch (UserValidationException e) {
            attributes.addFlashAttribute("error-name",e.getMessage());
            attributes.addFlashAttribute("nombre",usuario.getNombre());
            attributes.addFlashAttribute("correo",usuario.getCorreo());

            return new RedirectView("/usuario-formulario");
        }
        return new RedirectView("/usuarios");
    }

    @PostMapping("/modificar")
    public RedirectView modificarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes attributes){
        RedirectView reMav = new RedirectView("/usuarios");

        try {
            usuarioService.modificarUsuario(usuario);
            attributes.addFlashAttribute("exito","Modificacion exitosa");
        } catch (UserValidationException e) {
            attributes.addFlashAttribute("error-name",e.getMessage());
            attributes.addFlashAttribute("usuario",usuario);
            reMav.setUrl("/usuarios/editar/"+usuario.getId());
        }
        return reMav;
    }

    @PostMapping("/baja/{id}")
    public RedirectView bajaUsuario(@PathVariable Integer id){
        usuarioService.bajaUsuario(id);
        return new RedirectView("/usuarios");
    }

    @PostMapping("/alta/{id}")
    public RedirectView altaUsuario(@PathVariable Integer id){
        usuarioService.altaUsuario(id);
        return new RedirectView("/usuarios");
    }

}
