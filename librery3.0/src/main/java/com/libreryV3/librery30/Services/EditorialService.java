package com.libreryV3.librery30.Services;

import com.libreryV3.librery30.Entitys.Editorial;
import com.libreryV3.librery30.Exception.EditorialValidationException;
import com.libreryV3.librery30.Repository.EditorialRepository;
import com.libreryV3.librery30.Validation.EditorialValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialService {

    @Autowired
    private EditorialRepository editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws EditorialValidationException {
        if(EditorialValidation.nameValidation(nombre)){
            Editorial editorial = new Editorial();
            editorial.setNombre(nombre);
            editorial.setAlta(true);
            editorialRepositorio.save(editorial);
        }else{
            throw new EditorialValidationException("Campo nombre no puede contener numeros ni estar vacio");
        }
    }

    @Transactional(readOnly = true)
    public Editorial buscarEditorialPorId(String id) {
        Optional<Editorial> editorialOptional = editorialRepositorio.findById(id);
        return editorialOptional.orElse(null);

    }

    @Transactional(readOnly = true)
    public List<Editorial> traerEditoriales() {
        return editorialRepositorio.findAll();
    }

    @Transactional
    public void modificarEditorial(String id, String nombre) {
        editorialRepositorio.modificarEditorial(id, nombre);
    }

    @Transactional
    public void eliminarEditorial(String id) {
        editorialRepositorio.bajaEditorial(id);
    }

    @Transactional
    public void darAlta(String id) {
        editorialRepositorio.altaEditorial(id);
    }

    @Transactional(readOnly = true)
    public List<Editorial> traerAltas() {
        return editorialRepositorio.traerAltas();
    }

}
