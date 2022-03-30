<?php
// вид выводит список всех сформированных пользователем коллекций

/** @var $model app\models\Study\StudyKit */

/** @var $this yii\web\View */

use yii\bootstrap\ActiveForm;
use yii\bootstrap\Html;

?>

<h3>Ваши коллекции для изучения</h3>

<?php
if (!is_null($model->errorText)) {
    echo "<div class=\"col-lg-8\" style='color: red'>{$model->errorText}</div>";
}

echo "<h5 style='margin: 30px 20px'><a href='/new-kit'><b>+</b> Добавить новую коллекцию</a></h5>";

$form = ActiveForm::begin([
    'id' => 'kit-to-study-form',
    'action' => '/study',
    'options' => ['class' => 'form-horizontal'],
    'fieldConfig' => [
        'template' => "<b>{label}</b>\n<div class=\"col-lg-5\">{input}</div>\n<div class=\"col-lg-8\" style='color: red'>{error}</div>",
        'labelOptions' => ['class' => 'col-lg-5 col-form-label'],
    ]]);

echo "<div class='col-lg-offset-1 col-lg-11'>" .
    Html::submitButton('Слушать выбранные коллекции', ['class' => 'btn btn-primary']) . "
    </div><br />";

$studyKitList = $model->getStudyKitList();
$i = 0;
foreach ($studyKitList as $studyKit) {
    $kitName = Html::encode($studyKit['study_kit_name']);

    // ссылка на удаление коллекции
    $delete = Html::a("Удалить",
        ["/delete-kit"], [
            'data-method' => 'POST',
            'data-params' => [
                'KitToStudyForm[kitId]' => $studyKit['study_kit_id'],
            ],
        ]);

    $study = Html::a("<b>Изучить</b>",
        ["/study"], [
            'data-method' => 'POST',
            'data-params' => [
                'kitId[]' => $studyKit['study_kit_id'],
            ],
        ]);

    echo "<input id='ch$i' type='checkbox' name='kitId[]' value='{$studyKit['study_kit_id']}'";
    echo "<label for='ch$i' style='margin: 10px 0; border: 2px solid black;'>
         &nbsp; <b>$kitName</b>
        &nbsp; &nbsp;  $study
            <p style='font-size: 10pt; margin:  0 0 0 50px; text-align: left'><a href='/kit-fill?kitId={$studyKit['study_kit_id']}'>Редактировать</a></p>
        <p style='font-size: 10pt; margin: 0; padding: 0 0 10px; text-align: right; border-bottom: 1px solid lightgray'>$delete</p></label>";

    $i++;
}

ActiveForm::end();
?>
