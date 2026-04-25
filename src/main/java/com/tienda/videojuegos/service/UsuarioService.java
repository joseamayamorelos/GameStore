package com.tienda.videojuegos.service;

import com.tienda.videojuegos.model.Usuario;
import com.tienda.videojuegos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Autentica un usuario con username y password.
     * Retorna el usuario si las credenciales son correctas, vacío si no.
     */
    public Optional<Usuario> autenticar(String username, String password) {
        return usuarioRepository.findByUsernameAndPassword(username, password);
    }

    /**
     * Busca un usuario por su username.
     */
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    /**
     * Busca un usuario por ID.
     */
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Registra un nuevo usuario.
     */
    public Usuario registrar(String username, String password) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new RuntimeException("El nombre de usuario ya existe: " + username);
        }
        Usuario usuario = new Usuario(username, password);
        return usuarioRepository.save(usuario);
    }
}
