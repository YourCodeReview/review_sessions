<?php

use app\models\user\UserManagement;
use yii\helpers\Html;
use yii\widgets\ActiveForm;


/** @var $model app\models\user\UserManagement */
/** @var $this yii\web\View */
// форма для выбора слов

if (!is_null($model->errorText)) {
    echo "<div class=\"col-lg-8\" style='color: red'>{$model->errorText}</div>";
}

$form = ActiveForm::begin([
    'id' => 'user-management',
    'options' => ['class' => 'form-horizontal'],
    'fieldConfig' => [
        'template' => "<b>{label}</b>\n<div class=\"col-lg-5\">{input}</div>\n
                        <div class=\"col-lg-8\" style='color: red'>{error}</div>",
        'labelOptions' => ['class' => 'col-lg-5 col-form-label'],
    ]]);


echo $form->field($model, 'email',['enableClientValidation' => true]);
echo $form->field($model, 'password',['enableClientValidation' => true])->passwordInput();

$submitButtonText = "Войти";

if ($model->scenario == UserManagement::SCENARIO_REGISTER) {
    // если регистрируется новый пользователь
    echo $form->field($model, 'passwordRepeat',['enableClientValidation' => true])->passwordInput();

    $submitButtonText = "Зарегистрироваться";
}
?>

<div class="form-group">
    <div class="col-lg-offset-1 col-lg-11">
        <?= Html::submitButton("$submitButtonText", ['class' => 'btn btn-primary']) ?>
    </div>
</div>
<?php ActiveForm::end();

if ($model->scenario == UserManagement::SCENARIO_LOGIN) {
    echo '<p><a href="/registration">Зарегистрироваться можно тут >>></a></p>';
} else {
    echo '<p><a href="/login">Войти на сайт можно тут >>></a></p>';
}
