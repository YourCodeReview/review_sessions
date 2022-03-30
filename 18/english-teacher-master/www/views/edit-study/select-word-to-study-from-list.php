<?php
// форма для выбора слов из найденного по первым буквам
// списка для включения выбранного слова в коллекцию для заучивания

/** @var $model app\models\EditStudy\WordSelectToStudyForm */

/** @var $this yii\web\View */

use yii\helpers\Html;

// выводит форму поиска слов
echo $this->render("_form-word-select", ['model' => $model]);

echo "<p>Кликните по слову которое хотите добавить. Если слова в списке 
        отсутствует, вероятно Вы ввели слишком мало букв для поиска 
        или ошиблись при наборе. Вы можете ввести в поле для поиска 
        новое слово и повторить поиск.</p>";

foreach ($model->getWordsFound() as $word) {
    echo Html::a($word['word_spelling'],
            ["/kit-fill?kitId={$model->kitId}"], [
                'data-method' => 'POST',
                'data-params' => [
                    'WordSelectToStudyForm[firstLettersWordLooking]' => $word['word_spelling'],
                    'WordSelectToStudyForm[selectedOneWord]' => 1
                ],
            ]) . "<br />";
}


// список уже выбранных слов