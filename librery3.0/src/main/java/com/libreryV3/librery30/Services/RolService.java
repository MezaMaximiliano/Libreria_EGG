package com.libreryV3.librery30.Services;


import com.libreryV3.librery30.Entitys.Rol;
import com.libreryV3.librery30.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    @Transactional
    public List<Rol> traerRoles(){
        return rolRepository.findAll();
    }

    @Transactional
    public Rol buscarPorId(Integer id){
        Optional<Rol> optional = rolRepository.findById(id);
        return optional.orElse(null);
    }
}
