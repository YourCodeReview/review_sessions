<?php

use app\models\db\SettingPlayback;
use yii\helpers\Html;
use yii\widgets\ActiveForm;


/** @var $model app\models\user\UserManagement */
/** @var $this yii\web\View */

// форма для настройкм характеристик воспроизведения

if (!is_null($model->errorText)) {
    echo "<div class=\"col-lg-8\" style='color: red'>{$model->errorText}</div>";
}



$form = ActiveForm::begin([
    'id' => 'setting-playback',
    'options' => ['class' => 'form-horizontal'],
    'fieldConfig' => [
        'template' => "<b>{label}</b>\n<div class=\"col-lg-5\">{input}</div>\n
                        <div class=\"col-lg-8\" style='color: red'>{error}</div>",
        'labelOptions' => ['class' => 'col-lg-5 col-form-label'],
    ]]);


// echo $form->field($model, 'userId', ['enableClientValidation' => false]);
echo $form->field($model, 'word_in_block', ['enableClientValidation' => true]);
echo $form->field($model, 'word_translate_mil_sec', ['enableClientValidation' => true])
    ->dropDownList(getArray(500, 4000, 100));;
echo $form->field($model, 'translate_option_mil_sec', ['enableClientValidation' => true])
    ->dropDownList(getArray(100, 4000, 100));
echo $form->field($model, 'word_pause_mil_sec', ['enableClientValidation' => true])
    ->dropDownList(getArray(1000, 4000, 100));

/**
 * Формирует список элементов для выпадающего списка формы
 *
 * @param $start
 * @param $end
 * @param $step
 * @return mixed
 */
function getArray($start, $end, $step)
{
    for ($i = $start; $i <= $end; $i += $step) {
        $arr["$i"] = round(($i / 1000), 1);
    }

    return $arr;
}

?>
    <div class="form-group">
        <div class="col-lg-offset-1 col-lg-11">
            <?= Html::submitButton("Сохранить", ['class' => 'btn btn-primary']) ?>
        </div>
    </div>
<?php ActiveForm::end() ?>