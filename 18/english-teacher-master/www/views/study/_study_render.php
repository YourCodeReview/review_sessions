<?php
// отрисовывает страницу произрывания изучаемого альбома

use app\models\db\SettingPlayback;

/** @var var $studyKitCnt - передается из view/study/study.php */
//


$this->render("_study_render_js_obj", ['studyKitCnt' => $studyKitCnt]);

// получить правила воспроизведения слов
$settingPlayback = SettingPlayback::getSettingUser(\Yii::$app->user->id);
?>

<audio id='player' onended='nextWord()'>
    <source id='playerSource' src='' type='audio/mpeg'>
</audio>


<div id="word" onclick="playbackPause = !playbackPause; play()" style="font-size: 26pt">
    Начать
</div>

<div id="translate" onclick="playbackPause = !playbackPause; play()" style="font-size: 26pt; color: red">&nbsp;</div>

<button onclick="pauseNow = 1000; previous()"><<<</button> группа
<button onclick="pauseNow = 1000; next()">>>></button><br /><br />
<button onclick="previousWord()"><<<</button> слово
<button onclick="delWord()">исключить</button><br /><br />
<button onclick="repetitionWord()">В коллекцию "Повторение"</button><br /><br />
<button onclick="shuffle(playbackOrder)">Перемешать слова</button><br/><br/><br />

Слов в блоке <input type="text" id="wordInBlock" value="<?= $settingPlayback->word_in_block?>" style="width: 40px">
<span id="wordEnabled"></span> <span id="wordNumNow"></span>
<br/><br/>


<p style="margin-top: 40px "><a href="/study-kit">К списку коллекций >>></a></p>



<SCRIPT>
    // число слов в блоке
    //  let wordInBlock = <?= $settingPlayback->word_in_block?>
    // пауза между изучаемыми словами
    let pauseWordMilSec = <?= $settingPlayback->word_pause_mil_sec?>
    // пауза между словом и переводом
    let pauseWordTrnMilSec = <?= $settingPlayback->word_translate_mil_sec?>
    // пауза между вариантами перевода
    let pauseTrnTrnMilSec = <?= $settingPlayback->translate_option_mil_sec?>

    const mp3Dir = '/wav-mp3/'; // директория в которой лежит озвучка

    const timestampStar = <?= time()?>; // время начала прослушивания коллекции

    <?php
    // params сформированы в _study_render_js_obj.php
    echo $this->params['jsObj'];
    echo $this->params['jsPlaybackOrder']
    ?>
</SCRIPT>
<SCRIPT src='/JS/PlaybackOrder.js?time=<?= time() ?>'></SCRIPT>