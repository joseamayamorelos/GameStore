package com.tienda.videojuegos.repository;

import com.tienda.videojuegos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);
}
