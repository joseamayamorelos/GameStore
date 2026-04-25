-- ============================================
-- DATA INICIAL - TIENDA DE VIDEOJUEGOS
-- ============================================

-- Insertar usuarios de prueba
INSERT INTO usuario (username, password) VALUES ('admin', 'admin123');
INSERT INTO usuario (username, password) VALUES ('jose', 'jose123');
INSERT INTO usuario (username, password) VALUES ('gamer', 'gamer123');

-- Insertar videojuegos
INSERT INTO videojuego (nombre, descripcion, precio, stock, imagen_url, genero) VALUES
('The Legend of Zelda: Tears of the Kingdom', 'Aventura epica en Hyrule con mecanicas de construccion.', 299900, 25, 'zelda.jpg', 'Aventura'),
('God of War Ragnarok', 'Kratos y Atreus enfrentan el fin del mundo nordico.', 249900, 18, 'gow.jpg', 'Accion'),
('Elden Ring', 'RPG de mundo abierto del creador de Dark Souls.', 229900, 30, 'eldenring.jpg', 'RPG'),
('Spider-Man 2', 'Peter Parker y Miles Morales luchan contra Kraven.', 279900, 15, 'spiderman.jpg', 'Accion'),
('Hogwarts Legacy', 'Explora el mundo magico de Harry Potter en los 1800s.', 199900, 22, 'hogwarts.jpg', 'RPG'),
('FIFA 25', 'La simulacion de futbol mas popular del mundo.', 189900, 40, 'fifa.jpg', 'Deportes'),
('Baldurs Gate 3', 'RPG por turnos basado en Dungeons and Dragons.', 209900, 20, 'bg3.jpg', 'RPG'),
('Call of Duty Modern Warfare III', 'El legendario shooter en primera persona regresa.', 259900, 35, 'codmw3.jpg', 'Shooter'),
('Starfield', 'Explora el universo en el RPG espacial de Bethesda.', 219900, 12, 'starfield.jpg', 'RPG'),
('Street Fighter 6', 'El iconico juego de peleas con nuevos personajes.', 179900, 28, 'sf6.jpg', 'Pelea'),
('Resident Evil 4 Remake', 'El remake del clasico survival horror.', 189900, 16, 're4.jpg', 'Terror'),
('Mortal Kombat 1', 'La nueva era del universo de Mortal Kombat.', 239900, 14, 'mk1.jpg', 'Pelea');
