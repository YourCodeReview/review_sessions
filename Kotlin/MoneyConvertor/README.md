# Конвертор валют
Переводит сумму из одной валюты в другую
## Технологии:
- Kotlin 
- Junit
- Espresso
- MVVM
- ROOM
- Retrofit2

## Описание:
Данное приложение написано согласно архитектурному паттерну MVVM в стиле SingleActivity. В качестве языка программирования был выбран Kotlin. Для тестирования кода используется JUnit. Для тестирования UI, используется Espresso. В качетсве базы данных выбрана ROOM. Все данные приложение получает с https://www.cbr.ru/scripts/XML_daily.asp. Сохранение данных осуществляется с использованием библиотеки Retrofit2.

## Интерфейс
Во время загрузки приложения, пользователю отображается SplashActivity с логотипом пока загружается приложение.

<img src="https://user-images.githubusercontent.com/37947343/142003039-a3a03ea8-4924-4206-b439-a069be1b1f2f.jpg" style="width:150px;height:300px;">

После загрузки приложения пользователю открывается фрагмент с вобором валют и полем для ввода суммы. А так же кнопка для смены направления конвертации валют. В случае, если пользователь попытается ввести сумму для конвертации, не выбрав валюту, ему будет показано соотвествующее сообщение с просьбой сначала выбрать валюту.

<img src="https://user-images.githubusercontent.com/37947343/142005376-b9e69945-4904-4fca-9712-34285823ac75.png" style="width:150px;height:300px;">     <img src="https://user-images.githubusercontent.com/37947343/142090679-c6d778b9-1105-4a28-8dff-42d6091e898e.png" style="width:150px;height:300px;">     <img src="https://user-images.githubusercontent.com/37947343/142090700-76216274-c2a6-4b3f-b4ec-ae9c13bbd8b2.png" style="width:150px;height:300px;">     <img src="https://user-images.githubusercontent.com/37947343/142090688-cc61b480-412e-476e-a02e-f213417123e3.png" style="width:150px;height:300px;">

По нажатию на кнопку выбора валюты, пользователь переходит на второй экран приложения с списком валют и возможностью поиска по списку.

<img src="https://user-images.githubusercontent.com/37947343/142091057-77927f04-0680-450d-a2e0-ed23ad33a5dc.png" style="width:150px;height:300px;">     <img src="https://user-images.githubusercontent.com/37947343/142091066-79f85588-aa66-40b4-9bfe-5c43bcd9219d.png" style="width:150px;height:300px;">
