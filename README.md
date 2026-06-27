# AgentBoard

Sistema de tablones con notas para explorar el funcionamiento de los MCP Servers.

## Stack Tecnológico

- **Frontend**: Angular 17+
- **Backend**: Java 25 + Spring Boot 3.4
- **Base de datos**: PostgreSQL 16
- **Autenticación**: JWT (JSON Web Tokens)
- **Contenedores**: Docker + Docker Compose

## Funcionalidades

- Gestión de usuarios (registro, login)
- CRUD completo de notas
- Estados de nota: `TODO`, `IN_PROGRESS`, `DONE`
- Autorización por usuario (solo el autor puede editar/eliminar sus notas)

## Estructura del Proyecto

```
AgentBoard/
├── backend/              # API REST Spring Boot
│   ├── src/main/java/com/agentboard/
│   │   ├── config/       # Configuración Security y ExceptionHandler
│   │   ├── controller/   # AuthController, NoteController
│   │   ├── dto/          # DTOs para request/response
│   │   ├── model/        # Entidades User, Note, NoteStatus
│   │   ├── repository/   # Repositorios JPA
│   │   ├── security/     # JWT filter y servicio
│   │   └── service/      # Lógica de negocio
│   └── sql/              # Scripts de inicialización
├── frontend/             # Aplicación Angular
│   └── nginx.conf        # Configuración nginx para producción
├── docker-compose.yml    # Orquestación de servicios
└── .env.example          # Variables de entorno (copiar a .env)
```

## Configuración

### Variables de Entorno

Copiar `.env.example` a `.env` y configurar:

```bash
cp .env.example .env
```

Editar `.env`:
- `JWT_SECRET`: Clave secreta para firmar tokens JWT (mínimo 256 bits)
- `JWT_EXPIRATION`: Tiempo de expiración del token en ms (default: 86400000 = 24h)

### Perfiles de Spring

- **dev**: Configuración para desarrollo local con BD en Docker
- **prod**: Configuración para producción con variables de entorno

Cambiar perfil activo en `backend/src/main/resources/application.properties`:
```properties
spring.profiles.active=dev
```

## Ejecución

### Desarrollo Local (Backend)

```bash
cd backend
./mvnw spring-boot:run
```

### Docker (Todo el stack)

```bash
docker-compose up -d
```

Servicios disponibles:
- API: http://localhost:8080
- Frontend: http://localhost:4200

## API Endpoints

### Autenticación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/auth/register` | Registrar usuario |
| POST | `/api/auth/login` | Iniciar sesión |

### Notas (requiere autenticación)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/notes` | Listar todas las notas |
| GET | `/api/notes/{id}` | Obtener nota por ID |
| GET | `/api/notes/my` | Listar notas del usuario actual |
| GET | `/api/notes/status/{status}` | Filtrar por estado |
| POST | `/api/notes` | Crear nota |
| PUT | `/api/notes/{id}` | Actualizar nota |
| DELETE | `/api/notes/{id}` | Eliminar nota |

### Ejemplo de Request

**Registro:**
```json
POST /api/auth/register
{
  "username": "usuario1",
  "email": "usuario@example.com",
  "password": "password123"
}
```

**Login:**
```json
POST /api/auth/login
{
  "username": "usuario1",
  "password": "password123"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "usuario1",
  "userId": 1
}
```

**Crear Nota:**
```json
POST /api/notes
Authorization: Bearer <token>
{
  "title": "Mi primera nota",
  "content": "Contenido de la nota",
  "status": "TODO"
}
```

## Estados de Nota

- `TODO`: Nota pendiente
- `IN_PROGRESS`: Nota en curso
- `DONE`: Nota finalizada

## Seguridad

- Contraseñas encriptadas con BCrypt
- Tokens JWT para autenticación stateless
- Endpoints `/api/auth/**` públicos
- Resto de endpoints requieren token válido

## Docker

### Servicios

1. **postgres**: Base de datos PostgreSQL
2. **backend**: Aplicación Spring Boot
3. **frontend**: Aplicación Angular servida con nginx

### Comandos útiles

```bash
# Iniciar todos los servicios
docker-compose up -d

# Ver logs
docker-compose logs -f

# Parar servicios
docker-compose down

# Reconstruir imágenes
docker-compose up -d --build
```

## Próximos Pasos

- [ ] Frontend Angular completo
- [ ] Tests del backend
- [ ] Integración con MCP Servers
- [ ] Sistema de comentarios en notas
