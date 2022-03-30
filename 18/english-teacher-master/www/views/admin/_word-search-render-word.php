<?php
// отрисовывает форму добавления / редактирования слова

use yii\helpers\Html;

/** @var $this yii\web\View */
/** @var $word - передается из вызвавшего view */
$enableClientValidation = false;

$fomaName = "WordAddEditForm";

echo "

<form action='/admin/word-add-edit' method='post' enctype='multipart/form-data'>
{$word['word_spelling']} &nbsp; {$word['is_voiced']} &nbsp; 
    <input type='hidden' name='" . \Yii::$app->request->csrfParam . "' value='" . \Yii::$app->request->getCsrfToken() . "' />
    <input type='hidden' name='{$fomaName}[wordId]' value='{$word['word_id']}'>
    <input type='hidden' name='{$fomaName}[spelling]' value='" . Html::encode($word['word_spelling']) . "'>
    <input type='file' name='{$fomaName}[mp3File]'>
    ";


$btnName = "Применить";
if ($word['word_id'] == 0) {
    $btnName = "Добавить";
}

echo "<input type='submit' value='$btnName'>";

if ($word['word_id'] > 0) {
    echo " &nbsp; <a href='/admin/couple?wordId={$word['word_id']}'>Перевод >>></a>";
}
?>



</form>

