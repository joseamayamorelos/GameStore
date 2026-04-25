package com.tienda.videojuegos.controller;

import com.tienda.videojuegos.model.Carrito;
import com.tienda.videojuegos.model.Usuario;
import com.tienda.videojuegos.model.Videojuego;
import com.tienda.videojuegos.service.CarritoService;
import com.tienda.videojuegos.service.VideojuegoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private VideojuegoService videojuegoService;

    @Autowired
    private CarritoService carritoService;

    // ── GET /home ────────────────────────────────────────────────
    @GetMapping
    public String mostrarHome(@RequestParam(required = false) String busqueda,
                              @RequestParam(required = false) String genero,
                              HttpSession session,
                              Model model) {

        // Verificar sesión activa
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        // Obtener videojuegos según filtro
        List<Videojuego> juegos;
        if (busqueda != null && !busqueda.trim().isEmpty()) {
            juegos = videojuegoService.buscarPorNombre(busqueda.trim());
            model.addAttribute("busqueda", busqueda);
        } else if (genero != null && !genero.trim().isEmpty()) {
            juegos = videojuegoService.buscarPorGenero(genero.trim());
            model.addAttribute("generoSeleccionado", genero);
        } else {
            juegos = videojuegoService.listarDisponibles();
        }

        // Carrito para mostrar contador en navbar
        Carrito carrito = carritoService.obtenerOCrearCarrito(usuario);
        int itemsEnCarrito = carritoService.contarItems(carrito);

        model.addAttribute("juegos", juegos);
        model.addAttribute("generos", videojuegoService.listarGeneros());
        model.addAttribute("usuario", usuario);
        model.addAttribute("itemsEnCarrito", itemsEnCarrito);

        return "home";
    }
}
