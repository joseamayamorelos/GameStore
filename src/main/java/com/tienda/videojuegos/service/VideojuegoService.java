package com.tienda.videojuegos.service;

import com.tienda.videojuegos.model.Videojuego;
import com.tienda.videojuegos.repository.VideojuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VideojuegoService {

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    /**
     * Retorna todos los videojuegos disponibles (stock > 0).
     */
    public List<Videojuego> listarDisponibles() {
        return videojuegoRepository.findByStockGreaterThan(0);
    }

    /**
     * Retorna todos los videojuegos sin filtro.
     */
    public List<Videojuego> listarTodos() {
        return videojuegoRepository.findAll();
    }

    /**
     * Busca un videojuego por ID.
     */
    public Optional<Videojuego> buscarPorId(Long id) {
        return videojuegoRepository.findById(id);
    }

    /**
     * Busca videojuegos por nombre (búsqueda parcial).
     */
    public List<Videojuego> buscarPorNombre(String nombre) {
        return videojuegoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Busca videojuegos por género.
     */
    public List<Videojuego> buscarPorGenero(String genero) {
        return videojuegoRepository.findByGenero(genero);
    }

    /**
     * Obtiene todos los géneros disponibles (únicos).
     */
    public List<String> listarGeneros() {
        return videojuegoRepository.findAll()
                .stream()
                .map(Videojuego::getGenero)
                .filter(g -> g != null && !g.isEmpty())
                .distinct()
                .sorted()
                .toList();
    }
}
