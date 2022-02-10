<?php

namespace app\models\admin;

use app\models\admin\WordAddEditForm;
use app\models\db\WordCoupleList;
use app\models\db\WordList;
use yii\base\Model;

/**
 * модель для добавления в базу новых слов и озвучки
 */

class WordAddList extends Model
{
    public $wordId = 0; // id добавленного слова
    public $spelling; // написание
    public $transcription; // транскрипция
    public $languageId = 2; // id языка
    public $description = ""; // описание
    public $mp3File; // файл с озвучкой слова

    public $errorText = null; // содежит текст ошибки // если = null - ошибок нет

    /**
     * Добавляет новое слово в БД и возвращает id добавленного слова
     * @throws \Exception
     */
    public function addWord($wordSpelling)
    {
        $word = new WordList();

        $word->word_spelling = $wordSpelling;
        $word->word_transcription = "";
        $word->word_description = "";

        $containsCyrillic = (bool)preg_match('/[\p{Cyrillic}]/u', $wordSpelling);

        $this->languageId = 2;
        if ($containsCyrillic) {
            $this->languageId = 1;
        }

        $word->language_id = $this->languageId;
        $word->is_voiced = 0;


        if (!$word->save()) {
            throw new \Exception("Ошибка при добавлении нового слова. " . print_r($this, true));
        }

        $word->save();

        return $word;
    }

    /**
     * Добавляет список новых слов
     * @throws \Exception
     */
    public function addWordList($folderWord, $fileWordList, $reSound)
    {
        $wordList = file($fileWordList);

        foreach($wordList AS $key => $wordSpelling) {
            $wordSpelling = trim($wordSpelling);

            if ($wordSpelling == "") {
                continue;
            }

            // получить объект обрабатываемого слова
            $word = $this->getWord($wordSpelling);

            // добавить к слову озвучку
            if (!$this->addSoundWord($word, $folderWord, $reSound)) {
                throw new \Exception("Ошибка при добавлении нового слова. <br />Не удалось переместить или удалить файл: $wordSpelling");
            }

            $word->is_voiced = 1;
            $word->save();

            $wordListAtr[$key]['id'] = $word->word_id;
            $wordListAtr[$key]['lng'] = $word->language_id;
        }

        // добавить словарные пары к новым словам
        $this->addWordCouple($wordListAtr);
    }

    /**
     * Создает словарные пары для добавленных слов
     */
    private function addWordCouple($wordList)
    {
        foreach ($wordList AS $word) {
            if ($word['lng'] == 2) {
                $enId = $word['id'];
                continue;
            }

            $ruId = $word['id'];

            $findCouple = WordCoupleList::findOne([
                "word_id" => $enId,
                "word_linked_id" => $ruId]);

            if (!is_null($findCouple)) {
                continue;
            }

            $couple = new WordCoupleList();

            $couple->word_id = $enId;
            $couple->word_linked_id = $ruId;

            $couple->save();
        }
    }


    /**
     *  Возвращает id обрабатываемого слова
     *
     * @param $wordSpelling - написание добавляемого слова
     * @return WordList[]|array|\yii\db\ActiveRecord[]
     * @throws \Exception
     */
    private function getWord($wordSpelling)
    {
        // проверить наличие слова в БД
        $word = WordList::find()
            ->where(['word_spelling' => $wordSpelling])
            ->all();

        if (count($word) == 1) {
            $word = $word[0];
            return $word;
        }

        if (count($word) == 0) {
            // добавить слово в БД
            return $this->addWord($wordSpelling);
        }

        throw new \Exception("Ошибка при добавлении нового слова. <br />В базе два слова: $wordSpelling");
    }

    /**
     * Добавляет к слову озвучку
     *
     * @throws \Exception
     */
    private function addSoundWord($word, $folderSource, $reSound)
    {
        // проверить существование файла
        $fileNameSource = $folderSource . $word->word_spelling . ".mp3";
        $fileNameTarget = WordAddEditForm::FOLDER_SOUND . "/" . $word->word_id . ".mp3";

        // переместить файл в целевую директорию
        // только если файл не был озвучен до этого
        // или не было заказано переозвучивание
        if ($word->is_voiced == 0 || $reSound) {
            if (!file_exists($fileNameSource)) {
                throw new \Exception("Ошибка при добавлении нового слова. <br />Нет файла для: {$word->word_spelling}");
            }

            return rename($fileNameSource, $fileNameTarget);
        }

        unlink($fileNameSource);

        return true;
    }
}