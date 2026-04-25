package com.tienda.videojuegos.repository;

import com.tienda.videojuegos.model.ItemCarrito;
import com.tienda.videojuegos.model.Carrito;
import com.tienda.videojuegos.model.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
    Optional<ItemCarrito> findByCarritoAndVideojuego(Carrito carrito, Videojuego videojuego);
    void deleteByCarritoId(Long carritoId);
}
