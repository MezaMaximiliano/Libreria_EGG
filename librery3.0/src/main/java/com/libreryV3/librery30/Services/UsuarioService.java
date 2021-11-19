package com.libreryV3.librery30.Services;

import com.libreryV3.librery30.Entitys.Usuario;
import com.libreryV3.librery30.Exception.UserValidationException;
import com.libreryV3.librery30.Repository.UsuarioRepository;
import com.libreryV3.librery30.Validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolService rolService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void crearUsuario(Usuario usuario) throws UserValidationException {
        //-------VALIDACION DE NOMBRE Y MAIL-------
        if(!UserValidation.emailValidation(usuario.getCorreo())){
            throw new UserValidationException("Email no valido.");
        }else if(!UserValidation.nameValidation(usuario.getNombre().trim())){
            throw new UserValidationException("El campo nombre no puede contener numeros ni estar vacio.");
        }else if(usuarioRepository.existsUsuarioByCorreo(usuario.getCorreo())){
            throw new UserValidationException("Ya existe un usuario registrado con el correo "+usuario.getCorreo());
        }
        //-------FIN DE VALIDACIONES -------

        if(usuario.getRol()==null){
            usuario.setRol(rolService.buscarPorId(3));
        }
        usuario.setAlta(true);
        usuario.setClave(encoder.encode(usuario.getClave()));
        usuario.setNombre(usuario.getNombre().trim());
        usuarioRepository.save(usuario);

    }

    @Transactional
    public Usuario buscarUsuarioId(Integer id){
        Optional<Usuario> optional = usuarioRepository.findById(id);
        return optional.orElse(null);
    }

    @Transactional
    public Usuario buscarUsuarioCorreo(String correo){
        Optional<Usuario> optional = usuarioRepository.findByCorreo(correo);
        return optional.orElse(null);
    }

    @Transactional
    public List<Usuario> traerUsuarios(){
        return usuarioRepository.findAll();
    }

    @Transactional
    public void bajaUsuario(Integer id){
        usuarioRepository.bajaUsuario(id);
    }

    @Transactional
    public void altaUsuario(Integer id){
        usuarioRepository.altaUsuario(id);
    }

    @Transactional
    public void modificarUsuario(Usuario usuario) throws UserValidationException{
        //-------VALIDACION DE NOMBRE Y MAIL-------
        if(!UserValidation.emailValidation(usuario.getCorreo())){
            throw new UserValidationException("Email no valido.");
        }else if(!UserValidation.nameValidation(usuario.getNombre().trim())){
            throw new UserValidationException("El campo nombre no puede contener numeros ni estar vacio.");
        }else if(usuarioRepository.existsUsuarioByCorreo(usuario.getCorreo())){
            throw new UserValidationException("Ya existe un usuario registrado con el correo "+usuario.getCorreo());
        }
        //-------FIN DE VALIDACIONES -------
        usuarioRepository.modificarUsuario(usuario.getId(), usuario.getNombre().trim());
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElseThrow(() -> new UsernameNotFoundException("CORREO NO REGISTRADO."));
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+usuario.getRol().getNombre());

        return new User(usuario.getCorreo(), usuario.getClave(), Collections.singletonList(authority));
    }
}
