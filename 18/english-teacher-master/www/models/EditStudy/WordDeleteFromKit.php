<?php

namespace app\models\EditStudy;

use app\models\db\StudyKit;
use app\models\db\StudyKitList;
use app\models\db\DicEntry;
use app\models\helpers\UserAccess;
use yii\base\Model;

/**
 * модель для удаления словарных статей
 * из коллекции для изучения
 */
class WordDeleteFromKit extends Model
{

    public $dicEntryId; // Id словарной статьи (вариантов перевода), которую нужно удалить из коллекции для заучивания
    public $kitId; // id коллекции слов для изучения, из которой будет удалена словарная статья

    /**
     * Удаляет словарную статью из коллекции слов для изучения
     */
    public function delDicEntryFromStudy()
    {
        // удалить словарную статью их коллекции
        StudyKit::delDicEntryFromKit($this->kitId, $this->dicEntryId);
    }

    /**
     * проверяет принадлежность редактируемой коллекции слов для изучения текущему пользователю
     *
     * @param $attribute
     * @param $params
     */
    public function validateKitId($attribute, $params)
    {
        UserAccess::redirectIfKitNotUser($this->$attribute);
    }

    public function rules()
    {
        return [
            [['dicEntryId', 'kitId', ], 'integer'],
            ['kitId', 'validateKitId'],
        ];
    }

    public function attributeLabels()
    {
        return [
            'wordTranslationForStudy' => '',
        ];
    }
}