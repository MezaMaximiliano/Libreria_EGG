package com.libreryV3.librery30.Controller;

import com.libreryV3.librery30.Entitys.Editorial;
import com.libreryV3.librery30.Exception.EditorialValidationException;
import com.libreryV3.librery30.Services.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/editoriales")
public class EditorialController {

    @Autowired
    private EditorialService editorialService;

    @GetMapping
    public ModelAndView listaEditorial(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("editoriales");
        Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
        if(map!=null){
            mav.addObject("exito",map.get("exito"));
        }
        mav.addObject("editoriales",editorialService.traerEditoriales());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearEditoroial(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("editorial-formulario");
        Map<String,?> map = RequestContextUtils.getInputFlashMap(request);

        if(map!=null){
            mav.addObject("title","Crear Editorial");
            mav.addObject("action","guardar");
            mav.addObject("editorial",map.get("editorial"));
            mav.addObject("error",map.get("error-name"));
        }else{
            mav.addObject("editorial",new Editorial());
            mav.addObject("title","Crear Editorial");
            mav.addObject("action","guardar");
        }


        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarEditorial(@PathVariable String id){
        ModelAndView mav = new ModelAndView("editorial-formulario");
        mav.addObject("editorial",editorialService.buscarEditorialPorId(id));
        mav.addObject("title","Editar Editorial");
        mav.addObject("action","modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardarEditorial(@ModelAttribute Editorial editorial, RedirectAttributes attributes){
        RedirectView reMav = new RedirectView("/editoriales");
        try {
            editorialService.crearEditorial(editorial.getNombre().trim());
            attributes.addFlashAttribute("exito","Editorial creado con exito");
        } catch (EditorialValidationException e) {
            attributes.addFlashAttribute("error-name",e.getMessage());
            attributes.addFlashAttribute("editorial",editorial);
            reMav.setUrl("/editoriales/crear");
        }
        return reMav;
    }

    @PostMapping("/modificar")
    public RedirectView modificarEditorial(@RequestParam String nombre,@RequestParam String id){
        editorialService.modificarEditorial(id,nombre);
        return new RedirectView("/editoriales");
    }

    @PostMapping("/baja/{id}")
    public RedirectView bajaEditorial(@PathVariable String id){
        editorialService.eliminarEditorial(id);
        return new RedirectView("/editoriales");
    }

    @PostMapping("/alta/{id}")
    public RedirectView altaEditorial(@PathVariable String id){
        editorialService.darAlta(id);
        return new RedirectView("/editoriales");
    }

}
