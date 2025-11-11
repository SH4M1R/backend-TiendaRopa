# AI Agent Instructions for Tienda Ropa Backend

This is a Spring Boot-based clothing store backend application using Java 21 and Maven. Follow these guidelines when working with the codebase.

## Architecture Overview

The application follows a layered architecture:

1. **Controllers** (`RestControl/`): REST endpoints handling HTTP requests
2. **Services** (`Servicios/` and `ServiciosImpl/`): Business logic implementation
3. **DAOs** (`DAO/`): Data access layer for database operations
4. **Entities** (`Entidad/`): Domain models representing database tables
5. **DTOs** (`DTO/`): Data transfer objects for API communication

### Key Design Patterns

- Repository Pattern: Each entity has its own DAO interface in `DAO/` package
- Service Layer Pattern: Business logic is isolated in service implementations
- DTO Pattern: `LoginDTO` for authentication-related data transfer

## Development Environment

- Java 21
- Maven
- Spring Boot framework
- Port: 8080 (backend), CORS enabled for ports 7500 and 3000 (frontend)

### Project Structure Conventions

1. Package naming: `fullstack.demo.<layer>`, where layer is:
   - `Entidad` for domain models
   - `DAO` for data access objects
   - `Servicios` for service interfaces
   - `ServiciosImpl` for service implementations
   - `RestControl` for REST controllers

2. Resource handling:
   - Static files: `src/main/resources/static/`
   - File uploads: `upload/` directory with configured resource handler

## Integration Points

1. **Cross-Origin Configuration**:
   - Frontend origins: `http://localhost:7500`, `http://localhost:3000`
   - Allowed methods: GET, POST, PUT, DELETE, OPTIONS, PATCH
   - Credentials allowed with 1-hour max age

2. **File Upload Integration**:
   - Upload directory: `/upload/`
   - Cache period: 3600 seconds
   - Accessible via both file system and classpath

## Common Development Tasks

1. **Building the Project**:
   ```bash
   ./mvnw clean install
   ```

2. **Running the Application**:
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Testing**:
   Test classes should be placed in corresponding packages under `src/test/java/`