package com.libreryV3.librery30.Controller;


import com.libreryV3.librery30.Entitys.Autor;
import com.libreryV3.librery30.Entitys.Usuario;
import com.libreryV3.librery30.Services.AutorService;
import com.libreryV3.librery30.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.websocket.server.PathParam;
import java.security.Principal;

@Controller
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ModelAndView listaAutores(Principal principal){
        ModelAndView mav = new ModelAndView("autores");
        /*UserDetails userDetails = usuarioService.loadUserByUsername(principal.getName());
        Usuario usuario = usuarioService.buscarUsuarioCorreo(userDetails.getUsername());
        String correo = principal.getName();*/ //Recuperar informacion de sesion

        mav.addObject("autores",autorService.traerTodos());
        return mav;
    }

    @GetMapping("/crear")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ModelAndView crearAutor(){
        ModelAndView mav = new ModelAndView("autor-formulario");
        mav.addObject("autor",new Autor());
        mav.addObject("title", "Crear Autor");
        mav.addObject("action","guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarAutor(@PathVariable String id){
        ModelAndView mav = new ModelAndView("autor-formulario");
        mav.addObject("autor",autorService.buscarAutorPorId(id));
        mav.addObject("title","Editar Autor");
        mav.addObject("action","modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarAutor(@RequestParam String nombre){
        autorService.crearAutor(nombre.trim());
        return new RedirectView("/autores");
    }

    @PostMapping("/modificar")
    public RedirectView editarAutor(@RequestParam String id, @RequestParam String nombre){
        autorService.modificarAutor(id,nombre);
        return new RedirectView("/autores");
    }

    @PostMapping("/baja/{id}")
    public RedirectView bajaAutor(@PathVariable String id){
        autorService.eliminarAutor(id);
        return new RedirectView(("/autores"));
    }

    @PostMapping("/alta/{id}")
    public RedirectView altaAutor(@PathVariable String id){
        autorService.darAltarAutor(id);
        return  new RedirectView(("/autores"));
    }

}
