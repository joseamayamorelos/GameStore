package com.tienda.videojuegos.service;

import com.tienda.videojuegos.model.*;
import com.tienda.videojuegos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    /**
     * Obtiene el carrito del usuario o crea uno nuevo si no tiene.
     */
    public Carrito obtenerOCrearCarrito(Usuario usuario) {
        return carritoRepository.findByUsuario(usuario)
                .orElseGet(() -> carritoRepository.save(new Carrito(usuario)));
    }

    /**
     * Agrega un videojuego al carrito. Si ya existe, incrementa la cantidad.
     */
    @Transactional
    public void agregarAlCarrito(Usuario usuario, Long videojuegoId, int cantidad) {
        Videojuego juego = videojuegoRepository.findById(videojuegoId)
                .orElseThrow(() -> new RuntimeException("Videojuego no encontrado: " + videojuegoId));

        if (juego.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente para: " + juego.getNombre());
        }

        Carrito carrito = obtenerOCrearCarrito(usuario);

        Optional<ItemCarrito> itemExistente = itemCarritoRepository
                .findByCarritoAndVideojuego(carrito, juego);

        if (itemExistente.isPresent()) {
            ItemCarrito item = itemExistente.get();
            int nuevaCantidad = item.getCantidad() + cantidad;
            if (nuevaCantidad > juego.getStock()) {
                nuevaCantidad = juego.getStock();
            }
            item.setCantidad(nuevaCantidad);
            itemCarritoRepository.save(item);
        } else {
            ItemCarrito nuevoItem = new ItemCarrito(carrito, juego, cantidad);
            itemCarritoRepository.save(nuevoItem);
        }
    }

    /**
     * Actualiza la cantidad de un ítem del carrito.
     */
    @Transactional
    public void actualizarCantidad(Long itemId, int nuevaCantidad) {
        ItemCarrito item = itemCarritoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado: " + itemId));

        if (nuevaCantidad <= 0) {
            itemCarritoRepository.delete(item);
        } else {
            if (nuevaCantidad > item.getVideojuego().getStock()) {
                nuevaCantidad = item.getVideojuego().getStock();
            }
            item.setCantidad(nuevaCantidad);
            itemCarritoRepository.save(item);
        }
    }

    /**
     * Elimina un ítem del carrito.
     */
    @Transactional
    public void eliminarItem(Long itemId) {
        itemCarritoRepository.deleteById(itemId);
    }

    /**
     * Calcula el total del carrito.
     */
    public BigDecimal calcularTotal(Carrito carrito) {
        return carrito.getItems().stream()
                .map(ItemCarrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Simula la compra: descuenta stock y vacía el carrito.
     */
    @Transactional
    public void procesarCompra(Carrito carrito) {
        for (ItemCarrito item : carrito.getItems()) {
            Videojuego juego = item.getVideojuego();
            int nuevoStock = juego.getStock() - item.getCantidad();
            if (nuevoStock < 0) {
                throw new RuntimeException("Stock insuficiente para: " + juego.getNombre());
            }
            juego.setStock(nuevoStock);
            videojuegoRepository.save(juego);
        }
        // Vaciar carrito
        carrito.vaciar();
        carritoRepository.save(carrito);
    }

    /**
     * Cuenta la cantidad total de ítems en el carrito.
     */
    public int contarItems(Carrito carrito) {
        return carrito.getItems().stream()
                .mapToInt(ItemCarrito::getCantidad)
                .sum();
    }
}
