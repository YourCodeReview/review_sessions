# Приложение ToDo-лист

Приложение сделано с помощью createReactApp с использованием JS и SASS. Тестировалось в Яндекс браузере v.22.1.2.834 (64-bit).

## Что сделано

* Приложение включает в себя 2 компонента: TodoList - вертикальный список с наименованиями заметок и TodoEdit - Область взаимодействия заметки.
* Реализовать возможность добавления, редактирования и удаления заметок .
* Обрезать конец наименования заметки TODO “…”, если имя не влезает в вертикальный список наименований заметок TODO.
* Цветовая индикация состояния прогресса выполнения заметок.
* Поиск заметок по имени.
* Возможность изменения ширины списка наименований заметок.

## Баги

Во время тестирования я нашел пару багов, но не успел их исправить

### Баг с поиском

Если использовать поиск, а затем выбрать и изменить обьект, то изменение не отобразится до повторного использования строки поиска или перезагрузки страницы. Это происходит потому что для вывода искомых элементов используется дополнительный массив, который не изменяется до повтороного применения функции search.

### Баг с изменением ширины списка заметок

Если изменить ширину списка заметок, а затем изменить размер страницы, то список заметок и поле для изменения заметок будут занимать не всю площадь приложения.
