# Usa una imagen base con Java 21
FROM eclipse-temurin:21-jdk

# Crea un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado por Maven
COPY target/backend-TiendaRopa-feature-impl-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080 (por defecto en Spring Boot)
EXPOSE 8500

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
