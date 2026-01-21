# Описание проекта
Проект предназначен для ведения новостей. Есть возможности создавать пользователей, категории новостей, новости, оставлять комментарии.

# Сборка проекта
- Запустить gradle сборку через команду Gradle -> Tasks -> build -> bootJar
- В каталоге build/libs будет jar файл
- Для сборки docker образа необходимо использовать команду в терминале проекта:
```
docker build -t app-newsservice .
```

# Подготовка к запуску проекта
- Создать каталог C:/apps/newsservice
- Разместить в каталоге jar файл
- Разместить в каталоге файл application.yml

## Сконфигурировать параметры приложения в application.yml
```yaml
spring:
  application:
    name: newsservice
  jpa:
    generate-ddl: true
  datasource:
    # строка подключения к БД
    # ${DB_HOST:localhost} - хост БД
    # ${DB_PORT:5434} - порт БД
    # ${DB_NAME:contacts} - наименование БД
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5434}/${DB_NAME:contacts} 
    username: ${DB_USER:postgres} # имя пользователя для работы с БД
    password: ${DB_PASSWORD:postgres} # пароль для работы с БД
    hikari:
      schema: ${DB_SCHEMA:contacts_schema} # наименование схемы в БД

server:
  port: ${APP_SERVER_PORT:8080}
```

# Запуск проекта
- Запуск приложения на локальной машине:
```
java -jar -Dspring.config.location=application.yaml newsservice-0.0.1-SNAPSHOT.jar
```
- Запуск приложения в Docker контейнере:
```
docker run --rm -e DB_HOST=localhost -e DB_PORT=5434 -e DB_NAME=contacts -e DB_SCHEMA=contacts_schema -e DB_USER=postgres -e DB_PASSWORD=postgres -e APP_SERVER_PORT=8080 app-newsservice
```
- Запуск приложения с помощью Docker-compose:
```
cd docker
docker-compose up
```
- Остановка приложения с помощью Docker-compose:
```
docker-compose down
```

# Инструкция для пользователя
Описание API:
http://localhost:8080/swagger-ui/index.html#/