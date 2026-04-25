package com.tienda.videojuegos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item_carrito")
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id", nullable = false)
    private Carrito carrito;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "videojuego_id", nullable = false)
    private Videojuego videojuego;

    @Column(nullable = false)
    private Integer cantidad;

    // Constructor vacío
    public ItemCarrito() {}

    public ItemCarrito(Carrito carrito, Videojuego videojuego, Integer cantidad) {
        this.carrito = carrito;
        this.videojuego = videojuego;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Carrito getCarrito() { return carrito; }
    public void setCarrito(Carrito carrito) { this.carrito = carrito; }

    public Videojuego getVideojuego() { return videojuego; }
    public void setVideojuego(Videojuego videojuego) { this.videojuego = videojuego; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getSubtotal() {
        if (videojuego != null && cantidad != null) {
            return videojuego.getPrecio().multiply(BigDecimal.valueOf(cantidad));
        }
        return BigDecimal.ZERO;
    }
}
