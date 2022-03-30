<?php

// отрисовывается форма поиска слов для добавления в коллекцию для заучивания
/** @var $model app\models\EditStudy\WordSelectToStudyForm */

/** @var $this yii\web\View */

use yii\helpers\Html;
use yii\widgets\ActiveForm;

echo " &nbsp; &nbsp; <a href='/study-kit'><<< назад к списку коллекций</a>";

$form = ActiveForm::begin([
    'id' => 'word-select-to-study-form',
    'action' => "/kit-fill?kitId={$model->kitId}",
    'options' => ['class' => 'form-horizontal'],
    'fieldConfig' => [
        'template' => "<b>{label}</b>\n<div class=\"col-lg-5\">{input}</div>\n<div class=\"col-lg-8\" style='color: red'>{error}</div>",
        'labelOptions' => ['class' => 'col-lg-5 col-form-label'],
    ]]);

// поле поиска слов
echo $form
    ->field($model, 'firstLettersWordLooking', ['enableClientValidation' => true])
    ->textInput([
        'style' => 'width: 300px',
        'autofocus' => 'true',
        // 'list' => 'wordSelect',
        'oninput' => 'updateList();',
        'onkeyup' => 'inpKeyUp(event.code);',
        // 'onselect' => 'alert(1)',
        'autocomplete' => "off"]);

// отрисовка динамического элемента поиска и выбора слов по первым введенным символам
echo $this->render("_form-word-select-js");
?>


<div class="form-group">
    <div class="col-lg-offset-1 col-lg-11">
        <?php
        echo Html::submitButton('Искать', ['class' => 'btn btn-primary']);

        ?>
    </div>
</div>
<?php ActiveForm::end() ?>


