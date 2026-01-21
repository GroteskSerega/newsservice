# Базовый образ, содержащий Java
# FROM openjdk:27-ea-trixie
FROM eclipse-temurin:21-jre-alpine

# Директория приложения внутри контейнера
WORKDIR /app

# Копирование JAR-файла приложения в контейнер
COPY build/libs/newsservice-0.0.1-SNAPSHOT.jar app.jar

# Определение переменной среды
ENV APP_SERVER_PORT=8080

# Команда для запуска приложения
CMD ["java", "-jar", "app.jar"]