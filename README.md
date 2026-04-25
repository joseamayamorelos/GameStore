
# Requisitos Previos
Para ejecutar este proyecto, asegúrese de tener instalado:
- **Java 17** o superior.
- **Maven*
- **Docker Desktop** (recomendado para la base de datos).


#  Instrucciones de Configuración y Ejecución

### 1. Levantar la Base de Datos (Docker)
El proyecto incluye un archivo `docker-compose.yml` para facilitar la configuración. Abra una terminal en la raíz del proyecto tienda-videojuegos, antes de ejecutar el siguoente comando asegurese de que docker este abierto y corriendo luego si ejecute el sigueinte comando:
```
docker-compose up -d
```
Esto levantará un contenedor de SQL Server en el puerto `1433`.

### 2. Crear la Base de Datos
Conéctese a la instancia de SQL Server (usando SSMS, DBeaver o el mismo sql server) con las siguientes credenciales:
- **Host o nombredelservidor:** `localhost,1433`
- **Usuario:** `sa`
- **Password:** `Password123!`
- **Autenticación:** SQL Server Authentication


nota; si esta en sql server click en marcar confiar en el certificado del servidor

Una vez conectado, cree una base de datos vacía llamada:
**`tiendadb`**

### 3. Ejecutar la Aplicación
DEsde visualstudio o el ide de preferencia ejecute 
```bash
mvn spring-boot:run
```

### 4. Acceso a la Aplicación
Una vez que la aplicación haya iniciado correctamente, podrá acceder desde su navegador en:
**[http://localhost:8080](http://localhost:8080)**

---

##  Notas Adicionales
- Al iniciar por primera vez, Hibernate creará automáticamente la estructura de tablas gracias a `spring.jpa.hibernate.ddl-auto=update`.
- El archivo `src/main/resources/data.sql` se ejecutará automáticamente para poblar la tienda con videojuegos y usuarios de prueba.
- **Usuarios de prueba:**
    - `admin` / `admin123`
    - `jose` / `jose123`
    - `gamer` / `gamer123`
