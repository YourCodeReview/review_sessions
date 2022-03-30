<?php

namespace app\models\admin;

use app\models\db\WordList;
use yii\base\Model;
use yii\db\Exception;
use yii\web\UploadedFile;

/**
 * модель для работы с формой добавления нового слова в БД
 */
class WordAddEditForm extends Model
{
    const FOLDER_SOUND = "wav-mp3";

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
    public function addWord()
    {
        $wordList = new WordList();

        $wordList->word_spelling = $this->spelling;
        $wordList->word_transcription = $this->transcription;
        $wordList->word_description = $this->description;
        $wordList->language_id = $this->languageId;


        if (!$wordList->save()) {
            throw new \Exception("Ошибка при добавлении нового слова. " . print_r($this, true));
        }

        $this->wordId = $wordList->word_id;

        // добавление загруженного файла
        if ($this->upload()) {
            $wordList->is_voiced = 1;

            if (!$wordList->save()) {
                throw new \Exception("Ошибка при добавлении нового слова. " . print_r($this, true));
            }
        }

        return $wordList->word_id;
    }

    /**
     * Редактирует слово в БД и возвращает id отредактированного слова
     * @throws \Exception
     */
    public function editWord()
    {
        $wordList = WordList::findOne($this->wordId);

        if (is_null($wordList)) {
            throw new \Exception("Нет слова с переданным id");
        }

        // добавление загруженного файла
        if ($this->upload()) {
            $wordList->is_voiced = 1;

            if (!$wordList->save()) {
                throw new \Exception("Ошибка при добавлении нового слова. " . print_r($this, true));
            }
        }

        return $wordList->word_id;
    }

    /**
     * перемещает загруженный mp3 файл в целевую директорию
     */
    public function upload()
    {
        if (isset($this->mp3File)) {
            $fileName = self::FOLDER_SOUND .'/' . $this->wordId . '.mp3';

            return $this->mp3File->saveAs($fileName);
        }

        return false;
    }

    /**
     * определяет к какому языку принадлежит слово
     *
     * @param $attribute
     * @param $params
     */
    public function validateLanguage($attribute, $params)
    {
        $containsCyrillic = (bool)preg_match('/[\p{Cyrillic}]/u', $this->spelling);

        $this->languageId = 2;
        if ($containsCyrillic) {
            $this->languageId = 1;
        }
    }

    public function rules()
    {
        return [
            [['spelling', 'transcription', 'description', 'wordId'], 'trim'],
            [['spelling', 'wordId'], 'required', 'message' => 'Это поле нужно заполнить.'],
            [['spelling',], 'string', 'max' => 100, 'tooLong' => 'Поле Слово не должно быть больше 100-та символов'],
            [['transcription',], 'string', 'max' => 45, 'tooLong' => 'Поле Транскрипция не должно быть больше 45-ти символов'],
            [['description',], 'string', 'max' => 255, 'tooLong' => 'Поле Описание не должно быть больше 255-ти символов'],
            [['languageId',], 'validateLanguage'],
            [['wordId',], 'integer', 'min' => 0, 'message' => '', 'tooSmall' => '', 'tooBig' => ''],
            [['mp3File'], 'file', 'skipOnEmpty' => true, 'extensions' => 'mp3', 'maxSize' => 1024 * 1024, 'maxFiles' => 1],
        ];
    }

    public function attributeLabels()
    {
        return [
            'spelling' => 'Слово',
            'transcription' => 'Транскрипция',
            'description' => 'Описание',
            'languageId' => 'Язык',
            'mp3File' => 'Mp3 файл с озвучкой слова',
        ];
    }
}