package com.libreryV3.librery30.Repository;

import com.libreryV3.librery30.Entitys.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor,String> {

    @Modifying
    @Query("UPDATE Autor a SET a.nombre = :nombre WHERE a.id = :id")
    void modificarAutor(@Param("id") String id, @Param("nombre") String nombre);

    @Modifying
    @Query("UPDATE Autor a SET a.alta = false WHERE a.id = :id")
    void bajaAutor(@Param("id") String id);

    @Query("SELECT a FROM Autor a WHERE a.alta=true")
    List<Autor> traerAutores();

    @Query("Select a FROM Autor a WHERE a.nombre = :nombre")
    Autor buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT a FROM Autor a WHERE a.id = :id")
    Autor buscarPorId(@Param ("id") String id);

    //------DAR ALTA
    @Modifying
    @Query("UPDATE Autor a SET alta=true WHERE a.id = :id")
    void altaAutor(@Param("id") String id);

    //------TRAER AUTORES EN BAJA
    @Query("SELECT a FROM Autor a WHERE a.alta = true")
    List<Autor> traerAltas();

    //------VERIFICAR SI YA EXISTE AUTOR EN LA BASE DE DATOS
    @Query("SELECT COUNT(LOWER(a)) FROM Autor a WHERE a.nombre = TRIM(LOWER(:nombre))")
    Integer existByName(@Param ("nombre") String nombre);

}
