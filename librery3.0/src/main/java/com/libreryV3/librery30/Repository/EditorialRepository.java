package com.libreryV3.librery30.Repository;

import com.libreryV3.librery30.Entitys.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial,String> {

    @Query(value = "SELECT * FROM Editorial",nativeQuery = true)
    List<Editorial> traerEditoriales();

    @Query("SELECT e FROM Editorial e WHERE e.id = :id")
    Editorial buscarPorId(@Param("id") String id);

    @Modifying
    @Query("UPDATE Editorial e SET e.nombre = :nombre WHERE e.id = :id")
    void modificarEditorial(@Param("id") String id, @Param("nombre") String nombre);

    @Modifying
    @Query("UPDATE Editorial e SET e.alta = false WHERE e.id = :id")
    void bajaEditorial(@Param("id") String id);

    @Modifying
    @Query("UPDATE Editorial e SET alta=true WHERE e.id = :id")
    void altaEditorial(@Param("id") String id);

    @Query("SELECT e FROM Editorial e WHERE e.alta = true")
    List<Editorial> traerAltas();

}
