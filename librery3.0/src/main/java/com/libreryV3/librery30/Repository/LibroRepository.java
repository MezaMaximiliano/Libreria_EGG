package com.libreryV3.librery30.Repository;

import com.libreryV3.librery30.Entitys.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro,String> {

    //-----QUERYS PROPIAS DE ENTIDAD LIBRO
    @Modifying
    @Query("UPDATE Libro l SET l.isbn = :isbn, l.titulo = :titulo, l.anio = :anio, l.ejemplares = :ejemplares, l.autor.id = :autor, l.editorial.id = :editorial WHERE l.id = :id")
    void modificarLibro(@Param("id") String id, @Param("isbn") Long isbn, @Param("titulo") String titulo, @Param("anio") Integer anio, @Param("ejemplares") Integer ejemplares, @Param("autor") String idAutor, @Param("editorial") String editorial);

    @Modifying
    @Query("UPDATE Libro l SET l.alta = false WHERE l.id = :id")
    void bajaLibro(@Param("id") String id);

    @Modifying
    @Query("UPDATE Libro l SET l.alta = true WHERE l.id = :id")
    void altaPorIdLibro(@Param("id") String id);

    @Query("SELECT l FROM Libro l WHERE l.id = :id")
    Libro buscarLibroPorId(@Param("id") String id);

    @Query("SELECT l FROM Libro l")
    List<Libro> traerLibros();

    // -------ALTAS Y BAJAS EN CASCADA POR AUTOR--------
    @Modifying
    @Query("UPDATE Libro l SET l.alta = false WHERE l.autor.id = :idAutor")
    void bajaPorIdAutor(@Param("idAutor") String idAutor);

    @Modifying
    @Query("UPDATE Libro l SET l.alta = true WHERE l.autor.id = :idAutor")
    void altaPorIdAutor(@Param("idAutor") String idAutor);


    // -------ALTAS Y BAJAS EN CASCADA POR EDITORIAL--------
    @Modifying
    @Query("UPDATE Libro l SET l.alta = false WHERE l.editorial.id = :idEditorial")
    void bajaPorIdEditorial(@Param("idEditorial") String idEditorial);

    @Modifying
    @Query("UPDATE Libro l SET l.alta = true WHERE l.editorial.id = :idEditorial")
    void altaPorIdEditorial(@Param("idEditorial") String idEditorial);
}
