<?php

namespace app\models\Study;

use app\models\db\ListToStudy;
use app\models\db\StudyKitList;
use app\models\helpers\UserAccess;
use Exception;
use yii\base\Model;
use yii\db\ActiveRecord;
use yii\helpers\Html;

/**
 * модель для работы с view списков коллекций слов для изучения
 *
 */
class StudyKit extends Model
{
    public $kitId = []; // массив id коллекций для изучения

    public $errorText = null; // содежит текст ошибки // если = null - ошибок нет


    /**
     * Возвращает список коллекций слов для изучения
     *
     * @return array|ListToStudy[]|ActiveRecord[]
     * @throws Exception
     *
     */
    public function getStudyKitList()
    {
        try {
            $userId = \Yii::$app->user->id;

            if (is_null($userId)) {
                throw new Exception("");
            }

            $studyKitList = StudyKitList::getStudyKitList($userId);
        } catch (\Exception $e) {
            $studyKitList = [];
            $this->errorText = \Yii::$app->params['error']['site'];
        }

        return $studyKitList;
    }

    /**
     * возвращает словарные статьи из массива коллекций для формирования списка проигрывания
     */
    public function getWordListFromStudyKit()
    {
        // проверить коллекции на принадлежность текущему пользователю
        $this->validateKitId($this->kitId);

        try {
            // получить состав коллекций
            $wordList = [];
            foreach ($this->kitId as $kitId) {
                $wordListAdd = ListToStudy::getDicEntryForStudy($kitId);
                if (count($wordListAdd) > 0) {
                    $wordList = array_merge($wordList, $wordListAdd);
                }
            }

        } catch (\Exception $e) {
            $this->errorText = \Yii::$app->params['error']['site'];
            $wordList = [];
        }

        // преобразовать линейный массив дерево

        return $this->convertWordList($wordList);
    }

    /**
     * Преобразует линейный массив словарных статей в иерархический массив
     */
    private function convertWordList($dicEntryList)
    {
        $studyKitCntTree = [];

        foreach ($dicEntryList as $dicEntry) {
            $wordId1 = $dicEntry['word_id_1'];
            $wordSpelling1 = $dicEntry['word_spelling_1'];
            $wordId2 = $dicEntry['word_id_2'];
            $wordSpelling2 = $dicEntry['word_spelling_2'];

            $studyKitCntTree[$wordId1]['wordSpelling'] = $wordSpelling1;
            $studyKitCntTree[$wordId1]['dicEntryId'] = $dicEntry['dic_entry_id']; // id словарной статьи

            $studyKitCntTree[$wordId1]['translate'][$wordId2] = $wordSpelling2;

        }

        return $studyKitCntTree;
    }


    /**
     * проверяет принадлежность коллекции слов
     * текущему пользователю
     *
     * @param $kitId
     */
    public
    function validateKitId($kitIdList)
    {
        foreach ($kitIdList as $kitId) {
            UserAccess::redirectIfKitNotUser($kitId);
        }
    }
}