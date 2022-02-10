<?php
// вид выводит страницу для озвучивания коллекции для изучения

/** @var $model app\models\Study\StudyKit */

/** @var $this yii\web\View */
if (!is_null($model->errorText)) {
    echo "<div class=\"col-lg-8\" style='color: red'>{$model->errorText}</div>
        <a href='/'>Вернуться на главную страницу >>></a>";

    return;
}

// получить слова из коллекции
$studyKitCnt = $model->getWordListFromStudyKit();

if (count($studyKitCnt) == 0) {
    echo "<h6 style='margin: 30px 20px'>В этой коллекции нет ни одного слова.<br>
            <a href='/kit-fill?kitId={$model->kitId[0]}'>Перейдите по ссылке если хотите наполнить ее словами.</a></h6>";

    return;
}

echo $this->render("_study_render", ['studyKitCnt' => $studyKitCnt]);
