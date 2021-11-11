![](https://i.ibb.co/PNq2bYt/788135203-w640-h640-pchelovodbiz.png)
# Bumblebee GeneratorService 
## Version : 1.0.0
## Назначение :
`Платформа генерации тестовых данных для ручного или автоматизированного тестирования.`
## Описание генераторов:
* Классы отвечающие за генерацию тестовых данных находятся в пакете **.generators** и реализуют интерфейс **DataGenerator** для работы с генераторами используются две аннотации:
---
```java
@GeneratorDescription(
        generatorName = "Название генератора. Используется при создание объекта и валидации параметров",
        generatorClass = generatorClass.class,
        description = "Описание генератора. Используется при запросе информации о имеющихся генераторах"
)
```
**Пример:**
```java
@GeneratorDescription(
        generatorName = "SymbolGenerator",
        generatorClass = SymbolDataGenerator.class,
        description = "Generator for create random values"
)
```
---
```java
@GeneratorParameter(
        name = "Имя параметра. Используется для установки значения",
        description = "Описание параметра.Используется при запросе информации о параметрах генератора",
        InClass = "InClass.class, класс параметра. Используется для установки значени. !Не использовать примитивные типы!"
)
```
**Пример:**
```java
@GeneratorParameter(
        name = "count",
        description = "Number of text values in the list",
        InClass = Integer.class
)
```
---
* Примером реализации класса-генератора является SymbolDataGenerator в пакете **.generators**

## Описание основных url:
1. *Swagger UI доступен по адресу: http://localhost:8081/generator-service/swagger-ui.html#*
2. *Запрос на добавление контейнера(контейнер - объект агрегирующий в себе объекты генераторы) http://localhost:8081/generator-service/containers*
---
**Формат запроса:**
```json
{
  "historyOn": "true/false : рубильник включающий/отключающий сохранение тестовых данных в БД" ,
  "name": "имя контейнера: должно быть уникальным",
  "reportType": "EXCEL_TYPE : формат отчета c тестовыми данными"
}
```
**Пример:**
```json
{
  "historyOn": true,
  "name": "test",
  "reportType": "EXCEL_TYPE"
}
```
**Формат ответа:**
```json
{
  "cuid": "уникальный идентификатор контейнера",
  "name": "имя контейнера",
  "status": "статус: может быть в одном из состояний NEW, PREPARATION_FOR_GENERATION, GENERATION_COMPLETED, GENERATION_ERROR, CONTAINER_REMOVED"
}
```
**Пример:**
```json
{
  "cuid": "a084c619-669f-40c2-a030-53c7742dc5cc",
  "name": "test",
  "status": "NEW"
}
```

---
3. *Процесс инициализация и запуск генераторов доступен по адресу http://localhost:8081/generator-service/generators*
---
**Формат запроса:**
```json
{
  "cuid": "Уникальный идентификатор контейнера",
  "generatorInfo": [
    {
      "generatorName": "Имя генератора из аннотации @GeneratorParameter.generatorName",
      "values": {
        "Название параметра из аннотации @GeneratorParameter.name": "Значение параметра"
      }
    }
  ]
}
```
**Пример:**
```json
{
  "cuid": "112311134664332",
  "generatorInfo": [
    {
      "generatorName": "SymbolGenerator",
      "values": {
        "len": "25",
        "count": "22",
        "mode": "STRING",
        "isCascade":"true",
        "isNull":"false"
      }
    }
  ]
}
```
---
3.1 *Для запрос информации по всем имеющимся генераторам и их параметров воспользуйтесь http://localhost:8081/generator-service/generators/information*

---
**Пример:**
```json
[
  {
    "generatorName": "SymbolGenerator",
    "generatorDescription": "Generator for create random values",
    "parameters": [
      {
        "parameter": "len",
        "parameterDescription": "The length of the text value, applied if isCascade = false"
      },
      {
        "parameter": "count",
        "parameterDescription": "Number of text values in the list"
      },
      {
        "parameter": "mode",
        "parameterDescription": "Maybe value STRING or NUMBER"
      },
      {
        "parameter": "isNull",
        "parameterDescription": "The presence of a NULL value"
      },
      {
        "parameter": "isCascade",
        "parameterDescription": "Cascading increment of values in a text expression"
      }
    ]
  }
]
```
---

## Дополнительная информация:
1. *Профили приложения: **dev**-> для локального запуска, **docker**-> только для запуска внутри docker*
2. *Основные настройки: **thread-pool**-> количество потоков для генерации тестовых данных, **kafka**-> основные настройки кафки (используется для передачи тестовых данных в сервис отчетов)*






