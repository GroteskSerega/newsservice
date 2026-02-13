# News Service: High-Performance AOT Reference
Микросервис Новостной Портал с ролевой моделью доступа (RBAC), оптимизированный для запуска в среде **Axiom NIK**.

## Core Features
- **RBAC Model**: Предустановленный ADMIN для инициализации системы, поддержка ролей ADMIN, MODERATOR и USER.
- **Async Engine**: Стек на базе Spring MVC с поддержкой Project Loom.
- **Cloud Native**: Полная готовность к нативной компиляции и работе в Read-Only файловых системах.
- **Restful API**: Описание API доступно в формате swagger по адресу http://localhost:8080/swagger-ui/index.html#/

## Build & Deployment
Проект поддерживает стандартный цикл сборки Gradle и контейнеризацию:

```bash
# Сборка артефакта
./gradlew bootJar
```

```bash
docker build -t app-newsservice .
```
## Environment Variables

| Переменная         | Описание                         | Дефолтное значение |
|--------------------|----------------------------------|--------------------|
| APP_SERVER_PORT    | Порт публикации сервиса          | 8080               |
| APP_ADMIN_USERNAME | Логин системного администратора  | admin              |          
| APP_ADMIN_PASSWORD | Пароль системного администратора | admin              |
| DB_HOST            | Host подключения к Postgres      | localhost          |
| DB_PORT            | Port подключения к Postgres      | 5432               |
| DB_NAME            | База данных в Postgres           | news_db            |
| DB_SCHEMA          | Схема в Postgres                 | news_schema        |
| DB_USER            | Логин в Postgres                 | postgres           |
| DB_PASSWORD        | Пароль в Postgres                | postgres           |


## Deployment Options

Приложение подготовлено к работе в различных окружениях: от локальной разработки до высокоплотных кластеров.

### 1. Bare Metal / Local JRE
Для запуска классического JAR-файла (используется Axiom JDK 21+):
```bash
java -jar newsservice.jar --spring.config.import=optional:file:./application.yml
```

### 2. Docker (Standalone)
```bash
docker run --rm -p 8080:8080 \
  --name newsservice \
  -e APP_ADMIN_USERNAME=admni \
  -e APP_ADMIN_PASSWORD=admin \
  -e DB_HOST=host.docker.internal \
  -e DB_PORT=5432 \
  -e DB_NAME=news_db \
  -e DB_SCHEMA=news_schema \
  -e DB_USER=postgres \
  -e DB_PASSWORD=postgres \
  newsservice
```

### 3. Docker Compose (Full Stack)
```bash
# Развертывание стека в фоновом режиме
docker-compose up -d

# Мониторинг логов
docker-compose logs -f app
```

### 4. Native Image Execution (Axiom NIK)
```bash
./newsservice
```

# Запуск проекта
- Запуск приложения на локальной машине:
```
java -jar -Dspring.config.location=application.yaml newsservice-0.0.1-SNAPSHOT.jar
```
- Запуск приложения в Docker контейнере:
```
docker run --rm -e APP_ADMIN_USERNAME=admin -e APP_ADMIN_PASSWORD=admin -e DB_HOST=localhost -e DB_PORT=5436 -e DB_NAME=news_db -e DB_SCHEMA=news_schema -e DB_USER=postgres -e DB_PASSWORD=postgres -e APP_SERVER_PORT=8080 newsservice
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

# Продвинутый блок (исследования)
## Сборка проекта с разными Docker-образами

### 1. Слоеная сборка (Temurin)
Оптимизированная слоеная сборка.
**Benefit**: Разделение зависимостей и прикладного кода позволяет эффективно использовать Docker Cache, минимизируя трафик при CI/CD.

```
docker build -t newservice:layers .
```

### 2. Uber-JAR (Legacy)
Классическая сборка «все-в-одном».
**Deprecated** (2026): Избыточный размер пересылаемых данных при минимальных изменениях в коде.
```
docker build -f Dockerfile_uber_jar -t newservice:uber .
```

### 3. Axiom (Alpaquita + Musl)
Оптимизированная слоеная сборка на базе Axiom образа. Разделение зависимостей и прикладного кода позволяет эффективно использовать Docker Cache, минимизируя трафик при CI/CD.
- **Base Image**: `bellsoft/liberica-runtime-alpine` (или Alpaquita).
- **Benefit**: Баланс между скоростью сборки и эффективностью кеширования.
```
docker build -f Dockerfile_axiom -t newservice:axiom .
```

### 4. Axiom (musl) Native Image Kit (NIK)
Вершина оптимизации: компиляция в нативный бинарник с использованием **musl libc**.
- **Build Metrics**: ~11.5 мин (на i7-3720QM). Требует 8GB+ RAM для фазы анализа графа объектов.
- **Startup**: **1.040s** (Ready to serve).
- **Security**: Исполнение в среде **Alpaquita Cloud Native OS** от не-привилегированного пользователя (Non-root).
- **Verdict**: Идеально для масштабируемых микросервисов и Serverless.
```
docker build -f Dockerfile_axiom_native -t newservice:native .
```

### 5. Vanilla (debian:bookworm-slim) Native
Сравнение с эталонным GraalVM на базе GLIBC (Debian Slim).
- **Build Metrics**: ~20.5 мин (в 1.8 раза медленнее Axiom).
- **Startup**: **0.877s** (Агрессивная оптимизация ценой времени сборки).
- **Issues**: Нестабильность сетевых загрузок при сборке и увеличенный размер итогового образа из-за веса Debian-слоев
```
docker build -f Dockerfile_vanilla_native -t newservice:vanila-native .
```

### 6. Axiom (musl) Native Image Kit (NIK)
Вершина оптимизации: компиляция в нативный бинарник с использованием **PGO (Profile-Guided Optimization)**. Бинарник оптимизирован на основе реальных сценариев нагрузки (профилирование рантайма).
- **Build Metrics**: ~15.0 мин (на i7-3720QM). Требует 8GB+ RAM для фазы анализа графа объектов.
- **Startup**: **1.085s** (Ready to serve).
- **Security**: Исполнение в среде **Alpaquita Cloud Native OS** от не-привилегированного пользователя (Non-root).
- **Verdict**: Максимальная плотность (Density) размещения в облаке.
```
docker build -f Dockerfile_axiom_native_pro -t newservice:native-pro .
```

## Сводный результат
### Результаты всех сборок. Статистика по работе в Docker контейнерах.
Ниже приведён результат собранных образов, размеры.
- Размеры образов:

| REPOSITORY | TAG           | IMAGE ID     | CREATED        | SIZE  |
|------------|---------------|--------------|----------------|-------|
| newservice | layers        | abb75642c12a | 5 hours ago    | 422MB |
| newservice | uber          | a82dfce79025 | 5 hours ago    | 419MB |
| newservice | axiom         | 62b77b83e600 | 11 hours ago   | 324MB |
| newservice | native        | d780bc3334a7 | 5 minutes ago  | 262MB |
| newservice | vanila-native | 2c7917bbec95 | 32 seconds ago | 384MB |
| newservice | native-pro    | 2742b5f971bd | 46 seconds ago | 262MB |

- Ниже представлен результат команды Docker stats (1-й запуск).

Внимание заслуживают показатели MEM USAGE и PIDS (количество системных потоков)
<p> 1-й запуск:

| CONTAINER ID | NAME                      | CPU % | MEM USAGE / LIMIT   | MEM % | NET I/O         | BLOCK I/O   | PIDS |
|--------------|---------------------------|-------|---------------------|-------|-----------------|-------------|------|
| 1c642efb721d | newsservice-layers        | 0.23% | 291MiB / 15.58GiB   | 1.82% | 33.4kB / 38.8kB | 0B / 32.8kB | 33   |
| 440ca1610a73 | newsservice-uber          | 0.28% | 298.9MiB / 15.58GiB | 1.87% | 22.7kB / 26.6kB | 0B / 32.8kB | 34   |
| 65dad413d1e6 | newsservice-axiom         | 0.32% | 352.4MiB / 15.58GiB | 2.21% | 22.7kB / 26.5kB | 0B / 32.8kB | 33   |
| 03fa2facc54f | newsservice-native        | 0.03% | 110.5MiB / 15.58GiB | 0.69% | 25.4kB / 28.6kB | 0B / 0B     | 20   |
| 5233bbdddada | newsservice-vanila-native | 0.06% | 74.89MiB / 15.58GiB | 0.47% | 22.6kB / 26.2kB | 2.58MB / 0B | 10   |
| b14f4d920027 | newsservice-native-pro    | 0.06% | 44.85MiB / 15.58GiB | 0.28% | 22.5kB / 26kB   | 0B / 0B     | 11   |

- Ниже представлен результат команды Docker stats (2-й запуск).

Внимание заслуживают показатели MEM USAGE и PIDS (количество системных потоков)
<p> 2-й запуск:

| CONTAINER ID | NAME                      | CPU % | MEM USAGE / LIMIT   | MEM % | NET I/O         | BLOCK I/O   | PIDS |
|--------------|---------------------------|-------|---------------------|-------|-----------------|-------------|------|
| 1c642efb721d | newsservice-layers        | 0.22% | 293.7MiB / 15.58GiB | 1.84% | 33.4kB / 38.8kB | 0B / 32.8kB | 33   |
| 440ca1610a73 | newsservice-uber          | 0.22% | 352.7MiB / 15.58GiB | 2.21% | 33.4kB / 38.9kB | 0B / 32.8kB | 34   |
| 65dad413d1e6 | newsservice-axiom         | 0.25% | 360.9MiB / 15.58GiB | 2.26% | 33.4kB / 38.9kB | 0B / 32.8kB | 33   |
| 03fa2facc54f | newsservice-native        | 0.02% | 93.97MiB / 15.58GiB | 0.59% | 33.2kB / 38.2kB | 0B / 0B     | 11   |
| 5233bbdddada | newsservice-vanila-native | 0.02% | 74.58MiB / 15.58GiB | 0.47% | 33.2kB / 38.1kB | 0B / 0B     | 10   |
| b14f4d920027 | newsservice-native-pro    | 0.06% | 44.6MiB / 15.58GiB  | 0.28% | 33.2kB / 38.3kB | 0B / 0B     | 11   |

Необходимо отметить, что скрость запуска образов с JRE составила 13 - 16 секунд
Скорость запуска нативных образов - 1,1 - 1,5 секунд

### Итоги в виде таблицы
JRE vs Axiom NIK (Pro) vs Vanilla Native
Конфигурация: Java 21, Spring Boot 3.5.x, Hibernate 6.6 (Bytecode Enhancement), Project Loom, Postgres 17.
Железо: Intel i7-3720QM (4/8), 16GB RAM.

| Метрика         | JRE (Axiom/Layers) | Axiom NIK Native | Axiom NIK PRO (PGO) | Vanilla Native (Glibc) | Лучший результат        |
|-----------------|--------------------|------------------|---------------------|------------------------|-------------------------|
| Время сборки    | ~0.5 мин           | 11.5 мин         | 15.0 мин            | 20.5 мин               | Axiom NIK               |
| Размер образа   | 422 MB             | 262 MB           | 262 MB              | 384 MB                 | Axiom NIK (-38%)        |
| Старт (Startup) | 13.054 сек         | 1.040 сек        | 1.085 сек           | 0.877 сек              | Vanilla (Record)        |
| RAM (Idle)      | 291.0 MiB          | 93.9 MiB         | 44.8 MiB            | 74.8 MiB               | Axiom PRO (-84%)        |
| Потоки (PIDS)   | 33                 | 11               | 11                  | 10                     | Native (в 3 раза легче) |
| 1-й Latency     | 1431 ms            | 946 ms           | 875 ms              | 885 ms                 | Axiom PRO               |
| ОС (Runtime)	   | Alpine	            | Alpaquita        | Alpaquita           | Debian-slim            | Alpaquita (musl)        |

### Краткие выводы
- **Экстремальная плотность (Density)**: Использование Axiom NIK Pro с профилированием (PGO) позволило снизить потребление памяти до невероятных 44,8 МБ. Это в 8 раз меньше, чем у стандартной JRE сборки. На одном сервере теперь можно запустить 20 инстансов newsservice вместо 2
- **Project Loom в Native**: переход с классических потоков на виртуальные (Loom) в нативном образе сократил количество системных потоков (PIDS) с 33 до **11**. Это обеспечивает колоссальную устойчивость системы при тысячах одновременных соединений к Postgres.
- **Hibernate & AOT**: Применение Hibernate Bytecode Enhancement на этапе сборки позволило полностью сохранить механизм Lazy Loading в нативном бинарнике, избежав ошибок генерации прокси в рантайме.
- **Инфраструктурый выигрыш**: Axiom NIK (Alpaquita/musl) подтвердил лидерство в компактности, сэкономив 122 МБ по сравнению с Vanilla (Debian/glibc). Сборка на Axiom проходит почти в 2 раза быстрее конкурентов.

### Экономические выводы
- **TCO (Total Cost of Ownership)**: Сокращение требований к RAM на 84% позволяет радикально снизить затраты на облачную инфраструктуру (AWS/Azure/Yandex Cloud).
- **Scale-to-Zero Ready**: Несмотря на тяжелый реляционный стек, время старта сокращено с 13 секунд до **~1 секунды**. Это делает возможным использование сервиса в Serverless-архитектурах.
- **Evolution Move**: В ходе R&D было принято архитектурное решение о выносе миграций (Liquibase) во внешние init-контейнеры. Это позволило добиться стабильности AOT-сборки и предсказуемого времени прогрева.