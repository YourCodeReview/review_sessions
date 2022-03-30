<?php

use app\models\user\UserManagement;
use yii\helpers\Html;
use yii\widgets\ActiveForm;


/** @var $model app\models\user\UserManagement */
/** @var $this yii\web\View */
// форма для выбора слов

$title = "Вход на сайт";

if ($model->scenario == UserManagement::SCENARIO_REGISTER) {
    $title = "Регистрация";
}
echo "<h3>$title</h3>";

if ($model->registrationSuccessful) {
    // регистрация прошла успешно
    echo $this->render("_user-registration-successful", ['model' => $model]);
} else {
    // регистрация в процессе
    echo $this->render("_user-registration-in-progress", ['model' => $model]);
}
?>


