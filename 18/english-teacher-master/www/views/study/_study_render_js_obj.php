<?php
// формируется JS объект со словарными статьями и массив с порядком воспроизведения
// для вывода на странице воспроизведения изучаемой коллекции


use app\models\db\SettingPlayback;
use yii\helpers\Html;

/** @var var $studyKitCnt - состав изучаемой коллекции //передается из view/study/study.php */

$this->params['jsObj'] = createJsObj($studyKitCnt);
$this->params['jsPlaybackOrder'] = createPlaybackOrder($studyKitCnt);

function createJsObj($studyKitCnt)
{
    $tabNum = 1;
    // объект со списком слов для воспроизведения
    $jsObj = tab($tabNum++) . "let wordStudyList = {\n";

    // список слов для изучения заносится в JS объект
    foreach ($studyKitCnt as $wordId => $wordCnt) {
        $jsObj .= tab($tabNum) . "$wordId: {\n";

        $jsObj .= tab(++$tabNum) . "wordSpelling: '" . Html::encode($wordCnt['wordSpelling']) . "',\n";
        // id словарной статьи в которую входит изучаемое слово
        $jsObj .= tab($tabNum) . "dicEntryId: '{$wordCnt['dicEntryId']}',\n";
        $jsObj .= tab($tabNum) . "enabled: true,\n";
        $jsObj .= tab($tabNum) . "wordTranslate: {\n";

        // Сформировать список переводов слова
        $jsObj .= renderWordTranslate($wordCnt['translate'], ++$tabNum);

        // закрыт элемент с переводами
        $jsObj .= tab(--$tabNum) . "},\n";

        // закрыто изучаемое слово
        $jsObj .= tab(--$tabNum) . "},\n";
    }

    $jsObj .= "\t};\n";

    return $jsObj;
}

/**
 * Формирует js массив с последовательностью воспроизведения словарных статей
 * во время проигрывания изучаемой коллекции
 *
 * @param $studyKitCnt
 * @return string
 */
function createPlaybackOrder($studyKitCnt)
{
    // массив с последовательностью проигрывания. Нужен из-за специфики работы JS со свойствами объектов
    $tabNum = 1;
    $jsPlaybackOrder = tab($tabNum) . "let playbackOrder = [";
    $tabNum++;

    foreach ($studyKitCnt as $wordId => $wordCnt) {
        $jsPlaybackOrder .= "$wordId, ";
    }

    $jsPlaybackOrder .= "];\n";

    return $jsPlaybackOrder;
}

/**
 * Возвращает стороку из табуляций повторенных $tabNum раз
 *
 * @param $tabNum
 * @return string
 */
function tab($tabNum)
{
    return str_repeat("\t", $tabNum);
}

// Формирует список переводов слова
function renderWordTranslate($wordTranslateList, $tabNum)
{
    $jsObjTranslateList = "";
    $translatePosition = 0;
    foreach ($wordTranslateList as $wordId => $wordSpelling) {
        $jsObjTranslateList .= tab($tabNum) . "$translatePosition: {\n";

        $jsObjTranslateList .= tab(++$tabNum) . "wordId: $wordId,\n";
        $jsObjTranslateList .= tab($tabNum) . "wordSpelling: '" . Html::encode($wordSpelling) . "',\n";

        $jsObjTranslateList .= tab(--$tabNum) . "},\n";

        $translatePosition++;
    }

    return $jsObjTranslateList;
}
