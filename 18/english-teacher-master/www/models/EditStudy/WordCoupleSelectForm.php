<?php

namespace app\models\EditStudy;

use app\models\db\StudyKitList;
use app\models\db\DicEntry;
use app\models\db\DicEntryList;
use app\models\db\WordCoupleList;
use app\models\db\WordList;
use app\models\helpers\UserAccess;
use yii\base\Model;

/**
 * модель для поиска вариантов перевода слова
 * с включением выбранных пользователем вариантов в словарную статью
 * для изучения
 */
class WordCoupleSelectForm extends Model
{

    public $wordForStudyId; // Id слова, словрная статья которого создается
    public $kitId; // id коллекции слов для изучения, которая формируется
    public $languageTranslationId = 1; // Id языка перевода // пока используется только русский
    public $wordCouple = []; // массив пар слов (переводов) который выбран для заучивания слова $wordForStudyId

    public $errorText = null;

    /**
     * Возвращает массив из вариантов перевода слова
     * - с id = $this->wordForStudyId
     * - на язык c id = $this->languageTranslationId
     */
    public function getWordCoupleList()
    {
        return WordCoupleList::getWordCouple($this->wordForStudyId, $this->languageTranslationId);
    }

    /**
     * Добавляет в словарную статью выбранные пары слов $this->wordCouple
     * Перед добавлением проверяет наличие словарной статьи с
     * таким массивом словарных пар в БД
     * По результатам возвращает id новой или найденной в БД словарной статьи
     *
     * @return int|mixed
     */
    public function addWordCoupleToKit()
    {
        // проверить наличие такой статьи в БД
        $dicEntryId = DicEntry::findEntryByCouple($this->wordCouple);

        if ($dicEntryId) {
            // словарная статья найдена // создавать новую не нужно
            return $dicEntryId;
        }

        // статья отсутствует
        // создать новую
        $dicEntryId = DicEntryList::getIdNewRecord();

        //поместить в статью новые пары слов
        $i = 1;
        foreach ($this->wordCouple AS $coupleId) {
            DicEntry::addCoupleToDicEntry($dicEntryId, $coupleId, $i);

            $i++;
        }

        return $dicEntryId;
    }

    /**
     * проверяет принадлежность редактируемой коллекции слов для изучени
     * текущему пользователю
     *
     * @param $attribute
     * @param $params
     */
    public function validateKitId($attribute, $params)
    {
        UserAccess::redirectIfKitNotUser($this->$attribute);
    }

    /**
     * проверяет существование слова
     *
     * @param $attribute
     * @param $params
     */
    public function validateWordForStudyId($attribute, $params)
    {
        if (WordList::isWordIdNotFound((int)$this->$attribute)) {
            // слово отсутствует
            \Yii::$app->response->redirect('/', 302)->send();
        }
    }


    /**
     * Проверяет переданный массив id пар слов перед
     * добавлением в словарную статью
     *
     * @param $attribute
     * @param $params
     */
    public function validateWordCouple($attribute, $params)
    {
        $dicEntryList = $this->$attribute;

        if (!is_array($dicEntryList)) {
            // не является массивом // не штатная ситуация
            \Yii::$app->response->redirect('/', 302)->send();
        }

        if (count($dicEntryList) == 0) {
            // пустой массив
            $this->addError($attribute, 'Вы не выбрали ни одного варианта перевода.');
        }

        // проверка существования в БД добавляемого перевода
        $this->checkExistenceWordCouple($dicEntryList);
    }


    /**
     * Проверяет существование выбранных словарных пар для
     * переводимого слова
     *
     * @param $translationList
     */
    private function checkExistenceWordCouple($translationList)
    {
        // получить доступные пары слов для переводимого слова
        $wordCoupleList = $this->getWordCoupleList();

        // Вынести индексы найденных пар слов в индексы массива
        $arrKey = array_column($wordCoupleList, "word_couple_id");
        $wordCoupleList = array_combine($arrKey, $arrKey);

        // проверить существование для переводимого слова добавляемых пар
        foreach ($translationList AS $translation) {
            if (!isset($wordCoupleList[$translation])) {
                // нештатная ситуация
                \Yii::$app->response->redirect('/', 302)->send();
            }
        }
    }

    public function rules()
    {
        return [
            [['wordForStudyId', 'kitId', 'languageTranslationId',], 'trim'],
            ['kitId', 'validateKitId'],
            ['wordForStudyId', 'validateWordForStudyId'],
            ['wordCouple', 'required', 'message' => 'Вы не выбрали ни одного варианта перевода.'],
            ['wordCouple', 'validateWordCouple'],
        ];
    }

    public function attributeLabels()
    {
        return [
            'wordCouple' => '',
        ];
    }
}