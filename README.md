## Backend Tienda de Ropa

Este proyecto corresponde al backend del sistema **TiendaRopa**, una aplicación desarrollada en **Spring Boot** que gestiona la información de productos, clientes, pedidos y ventas de una tienda de ropa.  
Proporciona una API REST que puede ser consumida por un frontend web o aplicación móvil.

---

## Tecnologías Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA / Hibernate**
- **Spring Security**
- **MySQL** como base de datos relacional
- **Maven** para la gestión de dependencias
- **Lombok** para simplificar código
- **JWS** 

---

## Arquitectura

El proyecto sigue el patrón **MVC (Modelo - Vista - Controlador)** con una división clara de responsabilidades:

- **Entidad:** Representa las tablas del modelo de datos.
- **DAO:** Acceso a datos mediante JPA (Repositorio).
- **Servicio:** Contiene la lógica de negocio.
- **RestControl:** Expone endpoints RESTful.
- **DTO:** Estructura los datos que se envían o reciben del cliente.

---

## Estructura del Proyecto

```
backend-TiendaRopa/
├── src/
│   ├── main/
│   │   ├── java/fullstack/demo/
│   │   │   ├── Configuracion/         # Configuración general del proyecto (CORS, seguridad, beans, etc.)
│   │   │   ├── DAO/                   # Interfaces DAO o repositorios JPA
│   │   │   ├── Data/                  # Carga inicial de datos o componentes utilitarios
│   │   │   ├── DTO/                   # Clases Data Transfer Object (transferencia de datos)
│   │   │   ├── Entidad/               # Clases de entidad que representan las tablas de la BD
│   │   │   │  ├── App/                # Exclusivas del aplicativo
│   │   │   │  ├── Intranet/           # Exclusivas de la intranet
│   │   │   ├── RestControl/           # Controladores REST que exponen los endpoints
│   │   │   │  ├── App/                # Exclusivas del aplicativo
│   │   │   │  ├── Intranet/           # Exclusivas de la intranet
│   │   │   ├── Servicios/             # Interfaces de servicios
│   │   │   ├── ServiciosImpl/         # Implementaciones de los servicios
│   │   │   └── DemoApplication.java   # Clase principal (punto de entrada del proyecto)
│   │   └── resources/
│   │       ├── static.upload/         # Carpeta de subida de archivos (imágenes, etc.)
│   │       └── application.properties # Configuración de la base de datos y propiedades del sistema
│   └── test/                          # Pruebas unitarias
├── pom.xml                            # Archivo de dependencias Maven
├── README.md                          # Documentación del proyecto
└── target/                            # Archivos compilados (se genera automáticamente)
```

---

## Configuración del Proyecto

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/usuario/backend-TiendaRopa.git
---
  ## Ramas principales
  - main
  - feat/nombreColaborador-funcionalidad
     

2. **Configuracion del software**
   Editar el archivo `application.properties` :

   ```properties
    spring.application.name=demo
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
    spring.datasource.url=jdbc:mysql://localhost:3306/bd_fullstack
    spring.datasource.username=root
    spring.datasource.password=
    server.port=8500

   ```

3. **Compilar y ejecutar**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Acceder a la aplicación**
   ```
   http://localhost:8500
   ```

---


##  Funcionalidades

- CRUD de productos, clientes, pedidos y ventas.
- Subida de archivos (imágenes de productos).
- Validación de datos.
- Persistencia con MySQL.
- Autenticación con Spring Security.

---

## Video explicativo

Link del video:

https://youtu.be/0xc_w_y50wQ


## Autores (INTRANET)

- Abel Castillo
- Juan Pablo Inonan
- Jeffrey Perez
- Elmer Calisalla

## Autores (APP)

- Abel Castillo
- Juan Pablo Inonan
- Michael Reques
- 
-