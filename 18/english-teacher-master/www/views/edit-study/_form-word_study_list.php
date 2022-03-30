<?php
// выводит коллекцию слов для заучивания с переводами (словарными парами)

use app\models\db\ListToStudy;
use yii\bootstrap\Html;

/* @var $this \yii\web\View */
/* @var $model \app\models\EditStudy\WordSelectToStudyForm */

$userId = \Yii::$app->user->id;

// получает список словарных статей в виде каталога [dic_entry_id][НаписаниеИзучаемого слова][] = Написание перевода
$kitForStudy = ListToStudy::getDicEntryContent($model->kitId, $userId);

$title = "Ваши слова";
$kitCount = "Всего слов: " . count($kitForStudy);
// $linkStudy = "<a href='/study?kitId={$model->kitId}'>Перейти к изучению >>></a>";

$linkStudy = Html::a("Перейти к изучению >>>",
    ["/study"], [
        'data-method' => 'POST',
        'data-params' => [
            'kitId[]' => $model->kitId,
        ],
    ]);

if (count($kitForStudy) == 0) {
    $title = "Вы пока не добавили ни одного слова";
    $kitCount = "";
    $linkStudy = "";
}

echo "<div style='margin-left: 20px'>
    <h4>$title</h4>
    <p>$kitCount</p>
    <p>$linkStudy</p>";

foreach ($kitForStudy AS $entryId => $entry) {
    // из ключа массива берется Написание переводимого слова
    $word = array_key_first($entry);
    // из вложенного массива формируется список переводов (словарных пар) $word
    $translate = implode(", ", $entry[$word]);

    echo "
    <div style='margin-left: 20px'>
        <b>" . Html::encode($word) . "</b>
        <span style='margin-left: 20px; font-size: 10pt; color: lightgray'>";

    echo Html::a("исключить",
        ["/delete-word"], [
            'data-method' => 'POST',
            'data-params' => [
                'WordDeleteFromKit[kitId]' => $model->kitId,
                'WordDeleteFromKit[dicEntryId]' => $entryId
            ],
        ]);

    echo "
        </span><br />
        <div style='margin-left: 20px; font-size: 11pt'>" . Html::encode($translate) . "</div>
    </div>";
}

echo "</div>";