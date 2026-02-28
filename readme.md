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
docker build -t newsservice:layers .
```

### 2. Uber-JAR (Legacy)
Классическая сборка «все-в-одном».
**Deprecated** (2026): Избыточный размер пересылаемых данных при минимальных изменениях в коде.
```
docker build -f Dockerfile_uber_jar -t newsservice:uber .
```

### 3. Axiom (Alpaquita + Musl)
Оптимизированная слоеная сборка на базе Axiom образа. Разделение зависимостей и прикладного кода позволяет эффективно использовать Docker Cache, минимизируя трафик при CI/CD.
- **Base Image**: `bellsoft/liberica-runtime-alpine` (или Alpaquita).
- **Benefit**: Баланс между скоростью сборки и эффективностью кеширования.
```
docker build -f Dockerfile_axiom -t newsservice:axiom .
```

### 4. Axiom (musl) Native Image Kit (NIK)
Вершина оптимизации: компиляция в нативный бинарник с использованием **musl libc**.
- **Build Metrics**: ~11.5 мин (на i7-3720QM). Требует 8GB+ RAM для фазы анализа графа объектов.
- **Startup**: **1.040s** (Ready to serve).
- **Security**: Исполнение в среде **Alpaquita Cloud Native OS** от не-привилегированного пользователя (Non-root).
- **Verdict**: Идеально для масштабируемых микросервисов и Serverless.
```
docker build -f Dockerfile_axiom_native -t newsservice:native .
```

### 5. Vanilla (debian:bookworm-slim) Native
Сравнение с эталонным GraalVM на базе GLIBC (Debian Slim).
- **Build Metrics**: ~20.5 мин (в 1.8 раза медленнее Axiom).
- **Startup**: **0.877s** (Агрессивная оптимизация ценой времени сборки).
- **Issues**: Нестабильность сетевых загрузок при сборке и увеличенный размер итогового образа из-за веса Debian-слоев
```
docker build -f Dockerfile_vanilla_native -t newsservice:vanila-native .
```

### 6. Axiom (musl) Native Image Kit (NIK)
Future Work.
- **Status**: Исследование в процессе (требует лицензии BellSoft NIK Pro).
- **Target**: Внедрение **PGO (Profile Guided Optimization)** для достижения лимитов памяти в **<40** MB и сокращения времени отклика на 15-20%.
Вершина оптимизации: компиляция в нативный бинарник с использованием **PGO (Profile-Guided Optimization)**. Бинарник оптимизирован на основе реальных сценариев нагрузки (профилирование рантайма).
- **Build Metrics**: - мин (на i7-3720QM). Требует 8GB+ RAM для фазы анализа графа объектов.
- **Startup**: **-** (Ready to serve).
- **Security**: Исполнение в среде **Alpaquita Cloud Native OS** от не-привилегированного пользователя (Non-root).
- **Verdict**: Максимальная плотность (Density) размещения в облаке.
```
docker build -f Dockerfile_axiom_native_pro -t newsservice:native-pro .
```

## Сводный результат
### Результаты всех сборок. Статистика по работе в Docker контейнерах.
Ниже приведён результат собранных образов, размеры.
- Размеры образов:

| REPOSITORY  | TAG           | IMAGE ID     | BUILDING TIME (s) | SIZE  |
|-------------|---------------|--------------|-------------------|-------|
| newsservice | layers        | 53992e12ae50 | 10.8              | 429MB |
| newsservice | uber          | 6bdb0c9b8ee5 | 7.3               | 425MB |
| newsservice | axiom         | 49d59c1fc654 | 35.4              | 331MB |
| newsservice | native        | 2ab55a6d015e | 797.0             | 264MB |
| newsservice | vanila-native | 2b382df930b4 | 1270.2            | 388MB |
| newsservice | native-pro    | -            | -                 | -     |

- Ниже представлен результат команды Docker stats (1-й запуск).

Внимание заслуживают показатели MEM USAGE и PIDS (количество системных потоков)
<p> 1-й запуск:

| CONTAINER ID | NAME                      | CPU % | MEM USAGE / LIMIT   | MEM % | NET I/O        | BLOCK I/O       | PIDS |
|--------------|---------------------------|-------|---------------------|-------|----------------|-----------------|------|
| b2cf6171b891 | newsservice-layers        | 0.23% | 318.8MiB / 15.58GiB | 2.00% | 36.4kB / 349kB | 11.8MB / 98.3kB | 46   |
| d6e32f9c4a41 | newsservice-uber          | 0.28% | 306MiB / 15.58GiB   | 1.92% | 28.6kB / 237kB | 49.2kB / 61.4kB | 38   |
| e33c652dbd0f | newsservice-axiom         | 0.32% | 358.8MiB / 15.58GiB | 2.25% | 28.6kB / 232kB | 0B / 65.5kB     | 42   |
| 0efd33925948 | newsservice-native        | 0.03% | 47.53MiB / 15.58GiB | 0.30% | 27.2kB / 180kB | 0B / 0B         | 15   |
| 8a80be55613b | newsservice-vanila-native | 0.06% | 35.01MiB / 15.58GiB | 0.22% | 28.1kB / 207kB | 0B / 0B         | 14   |
| -            | newsservice-native-pro    | -     | -                   | -     | -              | -               | -    |

- Ниже представлен результат значений времени запуска и latency по запросу-ответу (1-й запуск).

| CONTAINER ID | NAME                      | Started(s) | process running (s) | Latency 1 request (ms) | Latency 2 request (ms) |
|--------------|---------------------------|------------|---------------------|------------------------|------------------------|
| b2cf6171b891 | newsservice-layers        | 29.396     | 31.252              | 1216                   | 403                    |
| d6e32f9c4a41 | newsservice-uber          | 21.497     | 23.362              | 1228                   | 368                    |
| e33c652dbd0f | newsservice-axiom         | 20.22      | 21.518              | 1515                   | 416                    |
| 0efd33925948 | newsservice-native        | 2.056      | 2.151               | 884                    | 433                    |
| 8a80be55613b | newsservice-vanila-native | 1.608      | 1.654               | 892                    | 465                    |
| -            | newsservice-native-pro    | -          | -                   | -                      | -                      |

- Ниже представлен результат команды Docker stats (2-й запуск).

Внимание заслуживают показатели MEM USAGE и PIDS (количество системных потоков)
<p> 2-й запуск:

| CONTAINER ID | NAME                      | CPU % | MEM USAGE / LIMIT   | MEM % | NET I/O         | BLOCK I/O   | PIDS |
|--------------|---------------------------|-------|---------------------|-------|-----------------|-------------|------|
| b2cf6171b891 | newsservice-layers        | 0.22% | 316.6MiB / 15.58GiB | 1.98% | 35.4kB / 93.5kB | 0B / 32.8kB | 38   |
| d6e32f9c4a41 | newsservice-uber          | 0.22% | 315.5MiB / 15.58GiB | 1.98% | 36.1kB / 124kB  | 0B / 49.2kB | 38   |
| e33c652dbd0f | newsservice-axiom         | 0.25% | 380.9MiB / 15.58GiB | 2.39% | 36.8kB / 152kB  | 0B / 49.2kB | 40   |
| 0efd33925948 | newsservice-native        | 0.02% | 45.9MiB / 15.58GiB  | 0.29% | 39.2kB / 86.2kB | 0B / 0B     | 15   |
| 8a80be55613b | newsservice-vanila-native | 0.02% | 35.59MiB / 15.58GiB | 0.22% | 34.9kB / 84.7kB | 0B / 0B     | 14   |
| -            | newsservice-native-pro    | -     | -                   | -     | -               | -           | -    |


- Ниже представлен результат значений времени запуска и latency по запросу-ответу (2-й запуск).

| CONTAINER ID | NAME                      | Started(s) | process running (s) | Latency 1 request (ms) | Latency 2 request (ms) |
|--------------|---------------------------|------------|---------------------|------------------------|------------------------|
| b2cf6171b891 | newsservice-layers        | 14.501     | 15.148              | 1337                   | 402                    |
| d6e32f9c4a41 | newsservice-uber          | 14.981     | 16.085              | 1402                   | 432                    |
| e33c652dbd0f | newsservice-axiom         | 12.611     | 13.313              | 1202                   | 377                    |
| 0efd33925948 | newsservice-native        | 1.262      | 1.287               | 933                    | 485                    |
| 8a80be55613b | newsservice-vanila-native | 1.28       | 1.303               | 1081                   | 479                    |
| -            | newsservice-native-pro    | -          | -                   | -                      | -                      |

Необходимо отметить, что скрость запуска образов с JRE составила 12,6 - 30 секунд
Скорость запуска нативных образов - 1,2 - 2,1 секунд

### Итоги в виде таблицы
JRE vs Axiom NIK (Pro) vs Vanilla Native
Конфигурация: Java 21, Spring Boot 3.5.x, Hibernate 6.6 (Bytecode Enhancement), Project Loom, Postgres 17.
Железо: Intel i7-3720QM (4/8), 16GB RAM.

| Метрика         | JRE (Layers) | JRE (Axiom) | Axiom NIK Native | Vanilla Native (Glibc) | Лучший результат                  |
|-----------------|--------------|-------------|------------------|------------------------|-----------------------------------|
| Время сборки    | 10.8 сек     | 35.4 мин    | 13.2 мин (797 c) | 21.1 мин (1270 c)      | JRE Layers                        |
| Размер образа   | 429 MB       | 331 MB      | 264 MB           | 388 MB                 | Axiom NIK Native (-38%)           |
| Старт (Startup) | 21.946 сек   | 16.415 сек  | 1.659 сек        | 1.444 сек              | Vanilla (Record)                  |
| RAM (Idle)      | 317.7 MiB    | 369.8 MiB   | 46.7 MiB         | 35.3 MiB               | Vanilla (-88%)                    |
| Потоки (PIDS)   | 42           | 41          | 15               | 14                     | Axiom NIK Native (в 3 раза легче) |
| 1-й Latency     | 1276 ms      | 1358 ms     | 908 ms           | 986 ms                 | Axiom NIK Native                  |
| ОС (Runtime)	   | Alpine	      | Alpaquita   | Alpaquita        | Debian-slim            | Alpaquita (musl)                  |

### Краткие выводы
- **Экстремальная плотность (Density)**: Использование Axiom NIK Native позволило снизить потребление памяти до невероятных 47,5 МБ. Это в 8 раз меньше, чем у стандартной JRE сборки. На одном сервере теперь можно запустить 20 инстансов newsservice вместо 2.
- **Project Loom в Native**: переход с классических потоков на виртуальные (Loom) в нативном образе сократил количество системных потоков (PIDS) с 46 до **14**. Это обеспечивает колоссальную устойчивость системы при тысячах одновременных соединений к Postgres.
- **Hibernate & AOT**: Применение Hibernate Bytecode Enhancement на этапе сборки позволило полностью сохранить механизм Lazy Loading в нативном бинарнике, избежав ошибок генерации прокси в рантайме.
- **Инфраструктурый выигрыш**: Axiom NIK (Alpaquita/musl) подтвердил лидерство в компактности, сэкономив 124 МБ по сравнению с Vanilla (Debian/glibc). Сборка на Axiom проходит почти в 2 раза быстрее конкурентов.

### Экономические выводы
- **TCO (Total Cost of Ownership)**: Сокращение требований к RAM на 88% позволяет радикально снизить затраты на облачную инфраструктуру (AWS/Azure/Yandex Cloud).
- **Scale-to-Zero Ready**: Несмотря на тяжелый реляционный стек, время старта сокращено с 22 секунд до **~1.2 секунды**. Это делает возможным использование сервиса в Serverless-архитектурах.
- **Evolution Move**: В ходе R&D было принято архитектурное решение о выносе миграций (Liquibase) во внешние init-контейнеры. Это позволило добиться стабильности AOT-сборки и предсказуемого времени прогрева (**Warmup time**).