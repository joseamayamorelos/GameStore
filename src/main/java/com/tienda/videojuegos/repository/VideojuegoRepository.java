package com.tienda.videojuegos.repository;

import com.tienda.videojuegos.model.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {
    List<Videojuego> findByGenero(String genero);
    List<Videojuego> findByNombreContainingIgnoreCase(String nombre);
    List<Videojuego> findByStockGreaterThan(Integer stock);
}
