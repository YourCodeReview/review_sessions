<?php

use app\models\db\SettingPlayback;
use yii\helpers\Html;
use yii\widgets\ActiveForm;


/** @var $model app\models\admin\WordAddNewForm */
/** @var $this yii\web\View */

// форма для добавления нового слова


$enableClientValidation = false;

if (!is_null($model->errorText)) {
    echo "<div class=\"col-lg-8\" style='color: red'>{$model->errorText}</div>";
}


$form = ActiveForm::begin([
    'id' => 'word-add-edit',
    'options' => ['class' => 'form-horizontal'],
    'fieldConfig' => [
        'template' => "<b>{label}</b>\n<div class=\"col-lg-5\">{input}</div>\n
                        <div class=\"col-lg-8\" style='color: red'>{error}</div>",
        'labelOptions' => ['class' => 'col-lg-5 col-form-label'],
    ]]);

echo Html::activeHiddenInput($model, 'wordId', $options = []);


echo $form->field($model, 'spelling', ['enableClientValidation' => $enableClientValidation]);
echo $form->field($model, 'transcription', ['enableClientValidation' => $enableClientValidation]);
echo $form->field($model, 'description', ['enableClientValidation' => $enableClientValidation]);

echo $form->field($model, 'mp3File', ['enableClientValidation' => $enableClientValidation])->fileInput();

?>

    <div class="form-group">
        <div class="col-lg-offset-1 col-lg-11">
            <?= Html::submitButton("Сохранить", ['class' => 'btn btn-primary']) ?>
        </div>
    </div>

<?php ActiveForm::end() ?>