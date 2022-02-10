<?php
// форма для создания новой коллекции слов для заучивания

/** @var $model app\models\EditStudy\KitToStudyForm */
/** @var $this yii\web\View */

use yii\bootstrap\ActiveForm;
use yii\bootstrap\Html;

// форма для выбора слов
?>

<h3>Новая коллекция слов для изучения</h3>

<?php
if (!is_null($model->errorText)) {
    echo "<div class=\"col-lg-8\" style='color: red'>{$model->errorText}</div>";
}

$form = ActiveForm::begin([
    'id' => 'kit-to-study-form',
    'options' => ['class' => 'form-horizontal'],
    'fieldConfig' => [
        'template' => "<b>{label}</b>\n<div class=\"col-lg-5\">{input}</div>\n<div class=\"col-lg-8\" style='color: red'>{error}</div>",
        'labelOptions' => ['class' => 'col-lg-5 col-form-label'],
    ]]);
?>

<?= $form->field($model, 'kitName',['enableClientValidation' => true]) ?>
<?= $form->field($model, 'kitDescription',['enableClientValidation' => true])->textarea() ?>

<div class="form-group">
    <div class="col-lg-offset-1 col-lg-11">
        <?= Html::submitButton('Создать', ['class' => 'btn btn-primary']) ?>
    </div>
</div>
<?php ActiveForm::end() ?>
