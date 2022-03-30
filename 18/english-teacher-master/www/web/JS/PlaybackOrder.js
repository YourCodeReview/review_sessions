let block = []; // список слов который будет воспроизводиться

let blockPosition = 0; // какая позиция в блоке будет воспроизводиться
let blockStart = 0; // ключ в массиве playbackOrder с которого начинается воспроизведение блока
let playbackPause = true; //true - проигрывание на паузе
let playbackOrderKey = -1; // ключ массива воспроизведения
let pauseNow = 0;
let timerId;
let wordIdNowPlay;
const player = document.getElementById('player');

// воспроизводит слово
function playBlockElement() {
    if (playbackPause) {
        return false;
    }
    player.src = mp3Dir + block[blockPosition]['wordId'] + ".mp3";

    if (block[blockPosition]['wordType'] == 'word') {
        document.getElementById('word').innerHTML = block[blockPosition]['wordSpelling'];
        document.getElementById('translate').innerHTML = '&nbsp;';

        wordIdNowPlay = block[blockPosition]['wordId'];

        document.getElementById("wordEnabled").innerHTML = countWordEnabled();
        document.getElementById("wordNumNow").innerHTML = wordNumPlay();
    } else {
        document.getElementById('translate').innerHTML = block[blockPosition]['wordSpelling'];
    }

    player.play();

    pauseNow = block[blockPosition]['afterWordPause'];

    blockPosition++;
    while (block[blockPosition] == undefined || !(block[blockPosition]['enable'])) {
        blockPosition++;
        if (block[blockPosition] == undefined) {
            blockPosition = 0;
        }
    }
}

// заказывает воспроизведение следующего слово после паузы
function nextWord() {
    timerId = setTimeout(playBlockElement, pauseNow);
}

// останавливает пригрыватель и перезагружает проигрыватель
function clearPlay()
{
    clearTimeout(timerId);

    player.pause();
    player.load();
}

// ищет и заказывает воспроизведение предыдущего слова
function previousWord()
{
    clearPlay();

    // ищет предыдущее прослушенное в блоке слово
    if (block[blockPosition]['wordType'] == 'word') {
        blockPosition++;
    }

    getBlockIdWord();
    getBlockPositionPrevious();
    getBlockIdWord();

    //запустить воспроизведение
    if (!playbackPause) {
        nextWord();
    }
}

// сделать активным номер блока с wordType = word
function getBlockIdWord()
{
    while (block[blockPosition]['wordType'] != 'word' || (!block[blockPosition]['enable'])) {
        getBlockPositionPrevious();
    }

    return blockPosition;
}

// делает активным предыдущий номер воспроизведения блока
function getBlockPositionPrevious()
{
    blockPosition--;
    if (blockPosition < 0) {
        blockPosition = block.length - 1;
    }
}

// переход к следующему блоку воспроизведения
function next() {
    clearPlay()

    if (!createBlock(1)) {
        return false;
    }

    if (!playbackPause) {
        nextWord();
    }
}

// переход к предыдущему блоку воспроизведения
function previous() {
    clearPlay()

    if (!createBlock(-1)) {
        return false;
    }

    if (!playbackPause) {
        nextWord();
    }
}

// начинает или останавливает воспроизведение
function play() {
    if (playbackPause) {
        player.pause();
        return false;
    }

    if (block[0] == undefined) {
        if (!createBlock()) {
            return false;
        }

        nextWord();
        return true;
    }

    if (player.src == "") {
        nextWord();
        return true;
    } else {
        player.play();
    }
}


// проверяет наличие в списке воспроизведения
// слов доступных к воспроизведению
function isAvailableWord() {
    for (let key in wordStudyList) {
        if (wordStudyList[key]['enabled']) {
            return true;
        }
    }

    alert('Нет слов для воспроизведения');
    playbackPause = true;
    player.pause();

    return false;
}

// формирует блок для проигрывания
function createBlock(direction) {
    if (!isAvailableWord()) {
        return false;
    }

    if (block[0] != undefined) {
        // получить номер с которого начнется формирование блока
        blockStart = getBlockStart(direction);
    }

    block = []; // зачистить ранее сформированный блок

    playbackOrderKey = blockStart;

    blockPosition = 0; // позиция блока воспроизведения которая будет сейчас формироваться
    let blockNum = 0;

    let wordInBlock = getWordInBlock();

    while (blockNum < wordInBlock) {

        let wordStudyId = getWordStudyId();

        addWordToBlock(wordStudyId);

        playbackOrderKey = getNextPlaybackOrderKey();
        blockNum++;
    }

    blockPosition = 0; // это номер позиции с которй начнется воспроизведение

    return true;
}

// получает из текствозо поля с id = wordInBlock сколько нужно включить слов в блок
function getWordInBlock()
{
    let wordInBlock = document.getElementById("wordInBlock").value;
    wordInBlock = parseInt(wordInBlock);

    if (isNaN(wordInBlock)) {
        wordInBlock = 0;
    }

    document.getElementById("wordInBlock").value = wordInBlock;

    if (wordInBlock == 0) {
        // в блок включаются все доступные к воспроизведению слова
        playbackOrderKey = 0; // формирование начинается с нулевого элемента
        blockStart = 0;

        // подсчитать сколько всего доступно слов для воспроизведения
        wordInBlock = countWordEnabled();
    }

    return wordInBlock;
}

// подсчитывает число слов доступных к воспроизведению
function countWordEnabled()
{
    let countWordEnabled = 0;

    for (let key in wordStudyList) {
        if (wordStudyList[key]['enabled']) {
            countWordEnabled++
        }
    }

    return countWordEnabled;
}

// добавляет слово с вариантами перевода в блок для воспроизведения
function addWordToBlock(wordStudyId) {
    // добавляется само слово
    block[blockPosition] = getBlockElement(wordStudyId, 'word', pauseWordTrnMilSec, -1);

    blockPosition++;

    // добавляет переводы
    for (let key in wordStudyList[wordStudyId]['wordTranslate']) {
        block[blockPosition] = getBlockElement(wordStudyId, 'translate', pauseTrnTrnMilSec, key);

        blockPosition++;
    }

    // вставляется пауза между словами
    block[(blockPosition - 1)]['afterWordPause'] = pauseWordMilSec; // пауза между изучаемыми словами
}

// формирует объект из свойст слова для включения в блок воспроизведения
function getBlockElement(wordStudyId, wordType, afterWordPause, key)
{
    let wordId = wordStudyId;
    let wordSpelling = wordStudyList[wordStudyId]['wordSpelling'];

    if (wordType == 'translate') {
        // добавляется перевод
        wordId = wordStudyList[wordStudyId]['wordTranslate'][key]['wordId'];
        wordSpelling = wordStudyList[wordStudyId]['wordTranslate'][key]['wordSpelling'];
    }

    let elem = {
        wordId: wordId,
        wordIdParent: wordStudyId, // добавлено для упрощения исключения слова из пригрывания
        enable: true,
        wordSpelling: wordSpelling,
        afterWordPause: afterWordPause, // пауза между переводами
        wordType: wordType,
    };

    return elem;
}

// возвращает ключ слова которое нужно добавить в блок воспроизведения
function getWordStudyId() {
    wordStudyId = playbackOrder[playbackOrderKey];

    while (!wordStudyList[wordStudyId]['enabled']) {
        playbackOrderKey = getNextPlaybackOrderKey();
        wordStudyId = playbackOrder[playbackOrderKey];
    }

    return wordStudyId;
}

// возвращает следующий ключ массива playbackOrder или 0 если массив закончиля
function getNextPlaybackOrderKey() {
    playbackOrderKey++;

    if (playbackOrder[playbackOrderKey] == undefined) {
        playbackOrderKey = 0;
    }

    return playbackOrderKey;
}

function getBlockStart(direction)
{
    if (direction == 1) {
        return getBlockStartNext();
    } else {
        return getBlockStartPrevious();
    }
}

// получает номер ключа в playbackOrder с которого начнется воспроизведение следующего блока
function getBlockStartNext() {
    // найти в объекте блока id последнего изучаемого слова
    for (key in block) {
        if (block[key]['wordType'] == 'word') {
            blockStart = block[key]['wordId'];
        }
    }

    // найти порядковый номер слова по id
    blockStart = playbackOrder.indexOf(blockStart, 0);
    blockStart++;

    if (playbackOrder[blockStart] == undefined) {
        blockStart = 0;
    }

    return blockStart;
}

// получает номер ключа в playbackOrder с которого начнется воспроизведение
// предыдущего блока
function getBlockStartPrevious(direction) {
    // получит в объекте блока id первого изучаемого слова
    blockStart = block[0]['wordId'];


    // найти порядковый номер слова по id в массиве воспроизведения
    blockStart = playbackOrder.indexOf(blockStart, 0);

    // идя обратным циклом по массиву воспроизведения найти wordInBlock
    // элементов для включения в блок
    let elemFind = 0;

    let wordInBlock = getWordInBlock();

    while (elemFind < wordInBlock) {
        blockStart--;

        if (blockStart < 0) {
            blockStart = playbackOrder.length - 1;
        }

        let wordId = playbackOrder[blockStart];

        if (wordStudyList[wordId]['enabled']) {
            elemFind++;
        }
    }

    return blockStart;
}

// удаляет слово из проигрывания
function delWord() {
    // отметить слово исключенным из проигрывания
    wordStudyList[wordIdNowPlay]['enabled'] = false;

    // отметить слово выключенным для проигрывания в блоке
    for (key in block) {
        if (block[key]['wordIdParent'] == wordIdNowPlay) {
            block[key]['enable'] = false;
        }
    }

    // проверить наличие слов доступных к воспроизведению
    if (!isAvailableWord()) {
        return false;
    }

    // проверить наличие в блоке слов доступных к воспроизведению
    for (key in block) {
        if (block[key]['enable']) {
            return true;
        }
    }

    // в блоке нет слов доступных к воспроизведению
    // создать новую группу
    next();
}

// перемешивает массив соспроизведения
// и начинает проигрывание с первого блока
function shuffle(array) {
    for (let i = array.length - 1; i > 0; i--) {
        let j = Math.floor(Math.random() * (i + 1)); // случайный индекс от 0 до i

        [array[i], array[j]] = [array[j], array[i]];
    }

    block = [];
    blockStart = 0;

    next();
}

// отправляет данные для добавления в коллекцию повторения
function repetitionWord()
{
    updateList();
}

function updateList() {
    const requestURLComplete = '/edit-study/add-to-repetition-kit?timestampStar=' + timestampStar + '&dicEntryId=' + wordStudyList[wordIdNowPlay]['dicEntryId'];

    const xhr = new XMLHttpRequest();
    xhr.open('GET', requestURLComplete);

    // отправка запроса на сервер
    xhr.send();
}

// определяет порядковый номер элемента в списке воспроизведения
function wordNumPlay()
{
    let wordNumPlay = 0;

    for (key in playbackOrder) {
        // проверить слово на enabled
        let wordId = playbackOrder[key];

        if (wordStudyList[wordId]['enabled']) {
            wordNumPlay++;
        }

        if (wordIdNowPlay == wordId) {
            return wordNumPlay;
        }
    }
}

