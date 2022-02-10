<?php

namespace app\models\EditStudy;

use app\models\db\StudyKit;
use app\models\db\StudyKitList;
use app\models\db\WordList;
use app\models\helpers\UserAccess;
use yii\base\Model;

/**
 * модель для работы с формой выбора слов для изучения
 * результатом работы формы будет список слов для
 * добавления в коллекцию слов для заучивания
 */
class WordSelectToStudyForm extends Model
{

    public $firstLettersWordLooking = ""; // буквы из начала искомого слова
    public $selectedOneWord = 0; // 0 - ищется список слов; 1 - ищется точное совпадение слова с образцом
    public $kitId; // id коллекции слов для изучения, которая наполняется словами

    public $errorText = null;

    private $_wordsFound = []; // список найденных слов по переданным для поиска первым букам слова

    public function __construct($kitId, $config = [])
    {
        parent::__construct($config);

        $this->kitId = $kitId;

        // проверка на принадлежность коллекции пользователю
        // специально вынесено в отдельную проверку что бы при первом заходе
        // общая проверка не инициировала вывод ошибок в форме
        $this->validateKitId("kitId", null);
    }

    /**
     * добавляет новую словарную статью в коллекцию для изучения
     * @param $dicEntryId
     */
    public function addDicEntryToKit($dicEntryId)
    {
        StudyKit::addDicEntryToKit($this->kitId, $dicEntryId);
    }

    /**
     * добавляет новую словарную статью в коллекцию для изучения
     * @param $kitId - id коллекции
     * @param $dicEntryId - id словарной статьи
     */
    public static function addDicEntryToKitSt($kitId, $dicEntryId)
    {
        StudyKit::addDicEntryToKit($kitId, $dicEntryId);
    }

    /**
     * Возвращает Id первого слова из найденных
     */
    public function getWordToStudyId()
    {
        return $this->_wordsFound[0]['word_id'];
    }

    /**
     * Возвращает Написание первого слова из найденных
     */
    public function getSpellingFindWord()
    {
        return $this->_wordsFound[0]['word_spelling'];
    }

    /**
     * Ищет слова соответствующие начальным буквам переданным из формы
     * Если слова не найдены формируется ошибка
     *
     * @param $attribute
     * @param $params
     */
    public function validateFindWord($attribute, $params)
    {
        try {
            $this->_wordsFound = WordList::getWordsFoundByFirstLetters($this->$attribute);
        } catch (\Exception $e) {
            $this->_wordsFound = [];
            $this->addError($attribute,  \Yii::$app->params['error']['site']);
            return;
        }

        if (count($this->_wordsFound) == 0) {
            $this->addError($attribute, 'Слово не найдено. Такого слова пока нет в нашем словаре.');
        }
    }

    /**
     * Из массива найденных слов удаляются все слова кроме выбранного пользователем
     *
     * @param $attribute
     * @param $params
     */
    public function validateSelectedOneWord($attribute, $params)
    {
        $q = $this->$attribute;
        if ($q === "1") {
            // данные пришли из формы где из набора слов выбрано одно слово
            // нужно удалить из спика найденных слов $this->_wordsFound
            // все слова кроме выбранного

            // поиск индекса слова в массиве найденных слов
            $keySearch = array_search($this->firstLettersWordLooking,
                array_column($this->_wordsFound, "word_spelling"));

            if ($keySearch !== false) { // индекс $keySearch может быть = 0 поэтому проверяется строгое НЕ соответствие
                $word = $this->_wordsFound[$keySearch];
                $this->_wordsFound = [];
                $this->_wordsFound[] = $word;
            }
        }
    }

    /**
     * проверяет принадлежность редактируемой словарной статьи
     * текущему пользователю
     *
     * @param $attribute
     * @param $params
     */
    public function validateKitId($attribute, $params)
    {
        UserAccess::redirectIfKitNotUser($this->$attribute);
    }

    public function getWordsFoundCount()
    {
        return count($this->_wordsFound);
    }

    public function getWordsFound()
    {
        return $this->_wordsFound;
    }

    public function rules()
    {
        return [
            ['kitId', 'validateKitId'],
            ['firstLettersWordLooking', 'trim'],
            ['firstLettersWordLooking', 'required', 'message' => 'Вы не ввели слово для добавления'],
            ['firstLettersWordLooking', 'validateFindWord'],
            ['selectedOneWord', 'validateSelectedOneWord'],

        ];
    }

    public function attributeLabels()
    {
        return [
            'firstLettersWordLooking' => 'Введите английское слово которое хотите добавить:',
        ];
    }
}