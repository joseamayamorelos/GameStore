package com.tienda.videojuegos.controller;

import com.tienda.videojuegos.model.Carrito;
import com.tienda.videojuegos.model.Usuario;
import com.tienda.videojuegos.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Helper: obtener usuario de sesión o redirigir
    private Usuario getUsuarioONull(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }

    // ── GET /carrito ─────────────────────────────────────────────
    @GetMapping
    public String mostrarCarrito(HttpSession session, Model model) {
        Usuario usuario = getUsuarioONull(session);
        if (usuario == null) return "redirect:/login";

        Carrito carrito = carritoService.obtenerOCrearCarrito(usuario);
        BigDecimal total = carritoService.calcularTotal(carrito);
        int itemsEnCarrito = carritoService.contarItems(carrito);

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        model.addAttribute("usuario", usuario);
        model.addAttribute("itemsEnCarrito", itemsEnCarrito);

        return "carrito";
    }

    // ── POST /carrito/agregar ─────────────────────────────────────
    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam Long videojuegoId,
                                   @RequestParam(defaultValue = "1") int cantidad,
                                   HttpSession session,
                                   RedirectAttributes redirectAttrs) {
        Usuario usuario = getUsuarioONull(session);
        if (usuario == null) return "redirect:/login";

        try {
            carritoService.agregarAlCarrito(usuario, videojuegoId, cantidad);
            redirectAttrs.addFlashAttribute("exito", "¡Juego agregado al carrito!");
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/home";
    }

    // ── POST /carrito/actualizar ──────────────────────────────────
    @PostMapping("/actualizar")
    public String actualizarCantidad(@RequestParam Long itemId,
                                     @RequestParam int cantidad,
                                     HttpSession session,
                                     RedirectAttributes redirectAttrs) {
        Usuario usuario = getUsuarioONull(session);
        if (usuario == null) return "redirect:/login";

        try {
            carritoService.actualizarCantidad(itemId, cantidad);
            redirectAttrs.addFlashAttribute("exito", "Carrito actualizado.");
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/carrito";
    }

    // ── POST /carrito/eliminar ────────────────────────────────────
    @PostMapping("/eliminar")
    public String eliminarItem(@RequestParam Long itemId,
                               HttpSession session,
                               RedirectAttributes redirectAttrs) {
        Usuario usuario = getUsuarioONull(session);
        if (usuario == null) return "redirect:/login";

        try {
            carritoService.eliminarItem(itemId);
            redirectAttrs.addFlashAttribute("exito", "Producto eliminado del carrito.");
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/carrito";
    }

    // ── POST /carrito/comprar ─────────────────────────────────────
    @PostMapping("/comprar")
    public String procesarCompra(HttpSession session, RedirectAttributes redirectAttrs) {
        Usuario usuario = getUsuarioONull(session);
        if (usuario == null) return "redirect:/login";

        Carrito carrito = carritoService.obtenerOCrearCarrito(usuario);

        if (carrito.getItems().isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Tu carrito está vacío.");
            return "redirect:/carrito";
        }

        try {
            BigDecimal total = carritoService.calcularTotal(carrito);
            carritoService.procesarCompra(carrito);
            redirectAttrs.addFlashAttribute("compraExitosa", true);
            redirectAttrs.addFlashAttribute("totalPagado", total);
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/carrito";
    }
}
