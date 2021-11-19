package com.libreryV3.librery30.Services;


import com.libreryV3.librery30.Entitys.Autor;
import com.libreryV3.librery30.Repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepositorio;

    @Transactional
    public void crearAutor(String nombre){

        Autor autor = new Autor();
        autor.setAlta(true);
        autor.setNombre(nombre);

        autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public Autor buscarAutorPorId(String id) {
        return autorRepositorio.buscarPorId(id);
    }

    @Transactional
    public void modificarAutor(String id, String nombre){
        autorRepositorio.modificarAutor(id, nombre);
    }

    @Transactional
    public void eliminarAutor(String id) {
        autorRepositorio.bajaAutor(id);
    }

    @Transactional(readOnly = true)
    public List<Autor> traerTodos() {
        return autorRepositorio.findAll();
    }

    @Transactional
    public void darAltarAutor(String id) {
        autorRepositorio.altaAutor(id);
    }

    @Transactional(readOnly = true)
    public List<Autor> traerAltas() {
        return autorRepositorio.traerAltas();
    }
}
