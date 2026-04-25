package com.tienda.videojuegos.controller;

import com.tienda.videojuegos.model.Usuario;
import com.tienda.videojuegos.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // ── GET /login ──────────────────────────────────────────────
    @GetMapping("/login")
    public String mostrarLogin(HttpSession session) {
        // Si ya está logueado, redirigir a home
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/home";
        }
        return "login";
    }

    // ── GET /registro ───────────────────────────────────────────
    @GetMapping("/registro")
    public String mostrarRegistro(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/home";
        }
        return "registro";
    }

    // ── POST /registro ──────────────────────────────────────────
    @PostMapping("/registro")
    public String procesarRegistro(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String confirmPassword,
                                   RedirectAttributes redirectAttrs) {

        if (!password.equals(confirmPassword)) {
            redirectAttrs.addFlashAttribute("error", "Las contraseñas no coinciden.");
            return "redirect:/registro";
        }

        try {
            usuarioService.registrar(username, password);
            redirectAttrs.addFlashAttribute("mensaje", "Registro exitoso. Ahora puedes iniciar sesión.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/registro";
        }
    }

    // ── POST /login ─────────────────────────────────────────────
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session,
                                RedirectAttributes redirectAttrs) {

        Optional<Usuario> usuario = usuarioService.autenticar(username, password);

        if (usuario.isPresent()) {
            session.setAttribute("usuarioLogueado", usuario.get());
            session.setAttribute("username", usuario.get().getUsername());
            return "redirect:/home";
        } else {
            redirectAttrs.addFlashAttribute("error", "Usuario o contraseña incorrectos.");
            return "redirect:/login";
        }
    }

    // ── GET /logout ─────────────────────────────────────────────
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttrs) {
        session.invalidate();
        redirectAttrs.addFlashAttribute("mensaje", "Sesión cerrada correctamente.");
        return "redirect:/login";
    }

    // ── GET / ────────────────────────────────────────────────────
    @GetMapping("/")
    public String raiz(HttpSession session) {
        if (session.getAttribute("usuarioLogueado") != null) {
            return "redirect:/home";
        }
        return "redirect:/login";
    }
}
