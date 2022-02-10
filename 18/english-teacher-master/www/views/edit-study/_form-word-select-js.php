<?php
// отрисовывает JS крипт который формирует динамический элемент поиска слов
// по первым введенным символам

// в этом div-e будут выведены варинты слов найденные по
// символам введенным в поле поиска?>
<div id="wordSelect"
     style="display: block; position: absolute; z-index: 100; background-color: white; margin: -10px 15px; padding: 0; display: none; width: 300px; border: 1px solid black; border-top: 0px;">
</div>

<script>
    // html представление которое должно быть выведено в div с вариантами слов
    let html;
    // номер выделенного элемента
    let wordSelect = -1; // значение до выделения первого элемента
    // массив который вернулся из контрола поиска слов
    let wordFind = [];
    // элемент с полем поиска слов
    const inpt = document.getElementById('wordselecttostudyform-firstletterswordlooking');
    // элемент в котором отображаются найденные варианты слов
    const listElement = document.getElementById('wordSelect');
    // цвет фона выделенного элемента
    const colorSelect = "lightgray";
    // цвет фона не выделенного элемента
    const colorNotSelect = "white";
    // URL на который будем отправлять GET запрос на получение вариантов
    // найденных слов
    const requestURL = '/edit-study/select-word?language=2&word=';

    // управляет выделением элементов в div-e с вариантами найденых слов
    function inpKeyUp(key) {
        switch (key) {
            case 'ArrowDown':
                wordSelect++;
                selectWord();
                break;
            case 'ArrowUp':
                wordSelect--;
                selectWord();
                break;
            case 'Escape':
                listElement.style.display = 'none';
                break;
        }
    }

    // отправляет выбранное слово для добавления в коллекцию изучения
    function sendWord(wordSelect)
    {
        inpt.value = wordFind[wordSelect].replace("&#039;", "'");
        document.getElementById('word-select-to-study-form').submit();
    }

    function wordOver(wordSelect)
    {
        for (let i = 0; (i < html.length); i++) {
            document.getElementById('wordNum' + i).style.backgroundColor = colorNotSelect;
        }

        document.getElementById('wordNum' + wordSelect).style.backgroundColor = colorSelect;

    }

    // подсвечивает выбранное слово и снимает подсветку с других слов
    // в div-e с найденными словами
    function selectWord() {
        // нет элементов для выделения
        if (html.length == 0) {
            return false;
        }

        // включает отображение div-a с найденными словами
        listElement.style.display = '';

        // выбирает первый или последний элемент в списке в зависимости от
        // того за верхний или нижний диапазон вышел указатель на элемент в
        // массиве найденных слов
        if (wordSelect >= html.length) {
            wordSelect = 0;
        } else if (wordSelect < 0) {
            wordSelect = html.length - 1;
        }

        // снимает выделение со всех элементов
        for (let i = 0; (i < html.length); i++) {
            document.getElementById('wordNum' + i).style.backgroundColor = colorNotSelect;
        }

        // подсвечивает выбранный элемент
        document.getElementById('wordNum' + wordSelect).style.backgroundColor = colorSelect;
        // передает значение выбранного элемента в поле поиска
        inpt.value = wordFind[wordSelect].replace("&#039;", "'");
    }

    // отправляет запрос на получение вариантов слов,
    // получает и выводит результаты запроса в div с вариантами найденных слов
    function updateList() {
        const requestURLComplete = requestURL + inpt.value;

        const xhr = new XMLHttpRequest();
        xhr.open('GET', requestURLComplete);

        // ожидает ответа сервера
        xhr.onreadystatechange = function () {
            if (xhr.readyState !== 4 || xhr.status !== 200) {
                return;
            }
            const response = JSON.parse(xhr.responseText);


            wordFind = [];
            html = [];
            for (let i = 0, length = response.length; i < length; i++) {
                // html представление одного из вариантов слова
                html[i] = "<p style='margin: 5px 0; padding: 0 10px' id='wordNum" + i + "' onclick='sendWord(" + i + ");' onmouseover='wordOver(" + i + ")'>" + response[i] + "</p>";
                wordFind[i] = response[i];
            }

            let htmlText = "";
            if (html.length == 0) {
                // если вариантов слова не найдено div с вариантами скрывается
                listElement.style.display = 'none';
            } else {
                // массив с html представлениями вариантов слова
                //соединяется в один блок
                htmlText = html.join('');
                // включается отображение блока с вариантами слова
                listElement.style.display = '';
            }

            // вывод данных в блок отображения вариантов слова
            listElement.innerHTML = htmlText;
        }

        // отправка запроса на сервер
        xhr.send();
    }
</script>


