<?php
// форма для поиска слов в БЛ системы

use yii\helpers\Html;
use yii\widgets\ActiveForm;


/** @var $model app\models\admin\WordSearchForm */
/** @var $this yii\web\View */

echo "<H3>Поиск слов для добавления</H3>";

$enableClientValidation = false;

if (!is_null($model->errorText)) {
    echo "<div class=\"col-lg-8\" style='color: red'>{$model->errorText}</div>";
}

$form = ActiveForm::begin([
    'id' => 'word-search',
    'options' => ['class' => 'form-horizontal'],
    'fieldConfig' => [
        'template' => "<b>{label}</b>\n<div class=\"col-lg-5\">{input}</div>\n
                        <div class=\"col-lg-8\" style='color: red'>{error}</div>",
        'labelOptions' => ['class' => 'col-lg-5 col-form-label'],
    ]]);


echo $form->field($model, 'lettersWord', ['enableClientValidation' => $enableClientValidation])->textInput([
    'style' => 'width: 300px',
    'autofocus' => 'true',
    'autocomplete' => "off"]);

?>
<div class="form-group">
    <div class="col-lg-offset-1 col-lg-11">
        <?= Html::submitButton("Поиск", ['class' => 'btn btn-primary']) ?>
    </div>
</div>


<?php ActiveForm::end();

if ($model->findWordInd === -1 && $model->lettersWord != "") {
    echo "<h5>Новое слово</h5>";
    $word = ["word_id" => 0, "word_spelling" => $model->lettersWord, "is_voiced" => 0];
    echo $this->render("_word-search-render-word", ['word' => $word]);
} elseif ($model->findWordInd >= 0) {
    echo "<h5>Найдено точное совпадение</h5>";

    $word = $model->wordList[$model->findWordInd];
    echo $this->render("_word-search-render-word", ['word' => $word]);
} else {
    // ни какие действия не нужны
}

echo "<br /><br />";

if (count($model->wordList) > 0) {
    echo "<h5>Найденные слова</h5>";
}
echo "<table style='border: 0 solid black; margin-left: 20px'>";
$i = 1;
foreach ($model->wordList as $word) {
    echo $this->render("_word-search-render-word", ['word' => $word]) . "<br />";

    $i++;
}
echo "</table>";
?>

