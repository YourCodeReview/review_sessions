<?php
// форма ля формирования коллекции слов для заучивания

/** @var $model app\models\EditStudy\WordSelectToStudyForm */
/** @var $this yii\web\View */

echo "<h3>Выбор слов для изучения</h3>";

if (!is_null($model->errorText)) {
    // вывод ошибки
    echo "<div class=\"col-lg-8\" style='color: red'>{$model->errorText}</div>";
}

// форма для выбора слов
echo $this->render("_form-word-select", ['model' => $model]);

// список уже выбранных слов
echo $this->render("_form-word_study_list", ['model' => $model]);