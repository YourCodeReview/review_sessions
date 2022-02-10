<?php

namespace app\models\admin;

use app\models\db\WordCoupleList;
use app\models\db\WordList;
use yii\base\Model;

/**
 * модель для создания словарных пар (слово - вариант перевода)
 */
class CoupleEditForm extends Model
{
    public $wordId; // id добавленного слова
    public $spelling;
    public $wordLinkedId; // Id выбранного слова

    public $errorText = null; // содежит текст ошибки // если = null - ошибок нет

    /**
     * Возвращает массив объектов с наименованием редактируемого слова
     * и со связанными с ним словами (вариантами перевода)
     *
     * @throws \Exception
     */
    public function getCoupleList()
    {
        $coupleList['modelWord'] = WordList::findOne($this->wordId);
        $coupleList['modelCouple'] = WordCoupleList::find()
            ->select(['word_couple_id', 'word_linked_id'])
            ->where(['word_id' => $this->wordId])
            ->all();

        return $coupleList;
    }

    // добавляет новую пару связанных слов $wordId - $wordLinkedId (вариант первода)
    public function addNewCouple()
    {
        $couple = new WordCoupleList();

        $couple->word_id = $this->wordId;
        $couple->word_linked_id = $this->wordLinkedId;

        $couple->save();
    }

    public function rules()
    {
        return [
            ['wordLinkedId', 'required', 'message' => ''],
            ['wordLinkedId', 'integer', 'message' => ''],
        ];
    }

    public function attributeLabels()
    {
        return [
            'spelling' => 'Перевод',
            'wordLinkedId' => '',
        ];
    }
}