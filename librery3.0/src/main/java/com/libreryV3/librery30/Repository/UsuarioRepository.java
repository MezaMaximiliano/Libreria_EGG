package com.libreryV3.librery30.Repository;

import com.libreryV3.librery30.Entitys.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    @Modifying
    @Query(value = "UPDATE Usuario SET alta = false WHERE id = ?1",nativeQuery = true)
    void bajaUsuario(@Param("id") Integer id);

    @Modifying
    @Query(value = "UPDATE usuario SET alta = true WHERE id = ?1",nativeQuery = true)
    void altaUsuario(Integer id);

    @Modifying
    @Query(value = "UPDATE usuario SET nombre = ?2 WHERE id = ?1",nativeQuery = true)
    void modificarUsuario(Integer id, String nombre);



    Optional<Usuario> findByCorreo(String correo);

    Boolean existsUsuarioByCorreo(String correo);
}
