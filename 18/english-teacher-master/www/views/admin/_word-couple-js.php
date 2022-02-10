<?php
// отрисовывает динамический элемент поиска и выбора слов по первым введенным словам

// в этом div-e будут выведены варинты слов найденные по
// символам введенным п поле поиска?>

<div id="wordSelect"
     style="display: block; position: absolute; z-index: 100; background-color: white; margin: -10px 15px; padding: 0; display: none; width: 300px; border: 1px solid black; border-top: 0px;">
</div>




<script>
    // html представление которое должно быть выведено в div с вариантами перевода
    let html;
    // номер выделенного элемента до выделения первого элемента
    let wordSelect = -1;
    // массив который вернулся из контрола поиска слов
    let wordFind = {};
    // элемент с полем поиска слов
    const inpt = document.getElementById('coupleeditform-spelling');
    // элемент с полем Id выбранного слова
    const inpId = document.getElementById('coupleeditform-wordlinkedid');
    // элемент в котором отображаются найденные варианты слов
    const listElement = document.getElementById('wordSelect');
    // цвет фона выделенного элемента
    const colorSelect = "lightgray";
    // цвет фона не выделенного элемента
    const colorNotSelect = "white";
    // URL на который будем отправлять GET запрос на получение вариантов
    // найденных слов
    const requestURL = '/edit-study/select-word?language=1&id=1&word=';

    // управляет выделением найденных элементов в div-e с вариантами найденых слов
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
        inpt.value = wordFind[wordSelect]['word_spelling'];
        inpId.value = wordFind[wordSelect]['word_id'];

        document.getElementById('word-couple').submit();
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
        // передает значение в выбранного элемента в поле поиска
        inpt.value = wordFind[wordSelect]['word_spelling'];
        inpId.value = wordFind[wordSelect]['word_id'];

        console.log(inpId.value);
    }

    // отправляет запрос на волучение вариантов перевода,
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


            wordFind = {};
            html = [];
            for (let i = 0, length = response.length; i < length; i++) {
                // html представление одного из вариантов перевода
                html[i] = "<p style='margin: 5px 0; padding: 0 10px' id='wordNum" + i + "' onclick='sendWord(" + i + ");' onmouseover='wordOver(" + i + ")'>" + response[i]['word_spelling'] + "</p>";

                wordFind[i] = {};

                wordFind[i]['word_spelling'] = response[i]['word_spelling'];
                wordFind[i]['word_id'] = response[i]['word_id'];
            }

            let htmlText = "";
            if (html.length == 0) {
                // если вариантов перевода не найдено div с вариантами скрывается
                listElement.style.display = 'none';
            } else {
                // массив с html представлениями вариантов перевода
                //соединяется в один блок
                htmlText = html.join('');
                // включается отображение блока с вариантами перевода
                listElement.style.display = '';
            }

            // вывод данных в блок отображения вариантов перевода
            listElement.innerHTML = htmlText;
        }

        // отправка запроса на сервер
        xhr.send();
    }
</script>
