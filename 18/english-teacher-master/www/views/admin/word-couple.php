<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;


/** @var $model app\models\admin\CoupleEditForm */
/** @var $this yii\web\View */

// форма для создания вариантов перевода слова

$enableClientValidation = false;

echo "<a href='/admin'><<< к поиску слов</a>";

if (!is_null($model->errorText)) {
    echo "<div class=\"col-lg-8\" style='color: red'>{$model->errorText}</div>";
}

try {
    $coupleList = $model->getCoupleList();
} catch (Exception $e) {
    echo \Yii::$app->params['error']['site'];
    return;
}

echo "<H1>" . Html::encode($coupleList['modelWord']->word_spelling) . "</H1>";

foreach ($coupleList['modelCouple'] AS $couple) {
    echo Html::encode($couple->wordLinkedSpelling[0]->word_spelling) . "<br />";
}


$form = ActiveForm::begin([
    'id' => 'word-couple',
    'options' => ['class' => 'form-horizontal'],
    'fieldConfig' => [
        'template' => "<b>{label}</b>\n<div class=\"col-lg-5\">{input}</div>\n
                        <div class=\"col-lg-8\" style='color: red'>{error}</div>",
        'labelOptions' => ['class' => 'col-lg-5 col-form-label'],
    ]]);


echo $form->field($model, 'spelling', ['enableClientValidation' => $enableClientValidation])->textInput([
    'style' => 'width: 300px',
    'autofocus' => 'true',
    // 'list' => 'wordSelect',
    'oninput' => 'updateList();',
    'onkeyup' => 'inpKeyUp(event.code);',
    // 'onselect' => 'alert(1)',
    'autocomplete' => "off"]);

echo $form->field($model, 'wordLinkedId', ['enableClientValidation' => $enableClientValidation])->hiddenInput();

// отрисовка динамического элемента поиска и выбора слов по первым введенным символам
echo $this->render("_word-couple-js");

?>
    <div class="form-group">
        <div class="col-lg-offset-1 col-lg-11">
            <?= Html::submitButton("Сохранить", ['class' => 'btn btn-primary']) ?>
        </div>
    </div>

<?php ActiveForm::end() ?>