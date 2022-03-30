<?php

namespace app\models\admin;

use app\models\db\WordList;
use yii\base\Model;

/**
 * модель для работы с формой добавления нового слова в БД
 */
class WordSearchForm extends Model
{
    public $lettersWord = ""; // написание искомого слова // получается из формы

    public $wordList = []; // масив найденных слов
    public $findWordInd = -1; // индекс массива $wordList в котором точно совпадающий с искомым элементю Если (-1) совпадений не найдено

    public $errorText = null; // содежит текст ошибки // если = null - ошибок нет

    /**
     * Формирует и сохраняет во внутренней публичной переменной $wordList
     * список слов в которых есть буквы  $this->$lettersWord переданные их формы
     * в случае точного совпадения индекс массива совпадающего элеменита сохраняется в $this->findWordInd
     *
     * @return void
     */
    public function findWord()
    {
        try {
            $this->wordList = WordList::getWordsByLetters($this->lettersWord);

            if (count($this->wordList) == 0) {
                $this->errorText = "Совпадений не найдено";

                return;
            }
        } catch (\Exception $e) {
            $this->errorText = \Yii::$app->params['error']['site'];

            return;
        }

        // определение индекса массива с точно совпадающим элементом
        foreach ($this->wordList AS $ind => $word) {
            if ($word['word_spelling'] == $this->lettersWord) {
                $this->findWordInd = $ind;

                return;
            }
        }
    }


    public function rules()
    {
        return [
            [['lettersWord',], 'trim'],
            ['lettersWord', 'required', 'message' => 'Это поле нужно заполнить.'],
            [['lettersWord',], 'string', 'max' => 100, 'tooLong' => 'Значение в поле не должно быть больше 100-та символов'],
        ];
    }

    public function attributeLabels()
    {
        return [
            'lettersWord' => 'Слово или его часть:',
        ];
    }
}