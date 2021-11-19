package com.libreryV3.librery30.Repository;

import com.libreryV3.librery30.Entitys.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository <Rol,Integer> {

}
