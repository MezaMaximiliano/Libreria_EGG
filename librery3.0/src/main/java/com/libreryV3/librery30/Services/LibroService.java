package com.libreryV3.librery30.Services;

import com.libreryV3.librery30.Entitys.Libro;
import com.libreryV3.librery30.Repository.AutorRepository;
import com.libreryV3.librery30.Repository.EditorialRepository;
import com.libreryV3.librery30.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepositorio;

    @Autowired
    private EditorialRepository editorialRepositorio;

    @Autowired
    private AutorRepository autorRepositorio;

   /* @Transactional
    public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) {
        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresRestantes(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setEditorial(editorialRepositorio.buscarPorId(idEditorial));
        libro.setAutor(autorRepositorio.buscarPorId(idAutor));
        libro.setAlta(true);

        libroRepositorio.save(libro);

    }*/

    @Transactional
    public void crearLibro(Libro libro) {

            libro.setEjemplaresPrestados(0);
            libro.setEjemplaresRestantes(libro.getEjemplares());
            libroRepositorio.save(libro);

    }

    /*@Transactional
    public void modificarLibro( String id, Long isbn, String titulo, Integer anio, Integer ejemplares,String idAutor,String idEditorial) {
        libroRepositorio.modificarLibro(id,isbn, titulo, anio, ejemplares,idAutor,idEditorial);
    }*/

    @Transactional
    public void modificarLibro(Libro libro) {
        libroRepositorio.modificarLibro(libro.getId(),libro.getIsbn(),libro.getTitulo(),libro.getAnio(),libro.getEjemplares(),libro.getAutor().getId(),libro.getEditorial().getId());
    }

    @Transactional
    public void eliminarLibro(String id) {
        libroRepositorio.bajaLibro(id);
    }

    @Transactional(readOnly = true)
    public Libro buscarLibroPorId(String id) {
        return libroRepositorio.buscarLibroPorId(id);
    }

    @Transactional(readOnly = true)
    public List<Libro> traerLibros() {
        return libroRepositorio.findAll();
    }

    @Transactional
    public void altaLibro(String id) {
        libroRepositorio.altaPorIdLibro(id);
    }

    //---ALTAS Y BAJAS POR ID DE AUTOR
    @Transactional
    public void eliminarLibroPorAutorId(String autorId) {
        libroRepositorio.bajaPorIdAutor(autorId);
    }

    @Transactional
    public void altaLibroPorAutorId(String autorId) {
        libroRepositorio.altaPorIdAutor(autorId);
    }
    //---ALTAS Y BAJAS POR ID DE EDITORIAL
    @Transactional
    public void eliminarLibroPorEditorialId(String editorialId) {
        libroRepositorio.bajaPorIdEditorial(editorialId);
    }

    @Transactional
    public void altaLibroPorEditorialId(String editorialId) {
        libroRepositorio.altaPorIdEditorial(editorialId);
    }
}
