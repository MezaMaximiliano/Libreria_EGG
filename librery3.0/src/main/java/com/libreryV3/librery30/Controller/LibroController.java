package com.libreryV3.librery30.Controller;

import com.libreryV3.librery30.Entitys.Libro;
import com.libreryV3.librery30.Services.AutorService;
import com.libreryV3.librery30.Services.EditorialService;
import com.libreryV3.librery30.Services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private AutorService autorService;

    @GetMapping
    public ModelAndView listaLibros(){
        ModelAndView mav = new ModelAndView("libros");
        mav.addObject("libros",libroService.traerLibros());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearLibro(){
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("libro",new Libro());
        mav.addObject("autores",autorService.traerAltas());
        mav.addObject("editoriales",editorialService.traerAltas());
        mav.addObject("title","Crear Libro");
        mav.addObject("action","guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarLibro(@PathVariable String id){
        ModelAndView mav = new ModelAndView("libro-formulario");
        mav.addObject("libro",libroService.buscarLibroPorId(id));
        mav.addObject("autores",autorService.traerAltas());
        mav.addObject("editoriales",editorialService.traerAltas());
        mav.addObject("title","Modificar Libro");
        mav.addObject("action","modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarLibro(@ModelAttribute Libro libro){
        libroService.crearLibro(libro);
        return new RedirectView("/libros");
    }

    @PostMapping("/modificar")
    public RedirectView editarLibro(@ModelAttribute("libro") Libro libro){
        libroService.modificarLibro(libro);
        return new RedirectView("/libros");
    }

    @PostMapping("/baja/{id}")
    public RedirectView bajaLibro(@PathVariable String id){
        libroService.eliminarLibro(id);
        return new RedirectView("/libros");
    }

    @PostMapping("/alta/{id}")
    public RedirectView altaLibro(@PathVariable String id){
        libroService.altaLibro(id);
        return new RedirectView("/libros");
    }

}
