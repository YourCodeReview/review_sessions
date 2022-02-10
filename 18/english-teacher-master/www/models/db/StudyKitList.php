<?php

namespace app\models\db;

use Yii;
use yii\db\ActiveRecord as ActiveRecordAlias;

/**
 * This is the model class for table "study_kit_list".
 *
 * @property int $study_kit_id
 * @property int $user_id id владельца коллекции для изучения
 * @property string|null $study_kit_name Название коллекции слов для того что бы пользователь мог отличить одну коллекцию от другой
 * @property string|null $study_kit_description Описание коллекции
 * @property int $study_kit_created_timestamp Тайм стамп UTC времени создания коллекции. Сделано для независимости времени от часового пояса установленного в MySql и PHP.
 */
class StudyKitList extends ActiveRecordAlias
{
    /**
     * Создает новую запись в таблице
     *
     * @param $valueOfFields - массив с данными для добавления
     * @return int
     * @throws \Exception
     */
    public static function createNewRecord($valueOfFields)
    {
        $kitNew = new StudyKitList();

        $kitNew->user_id = $valueOfFields['user_id'];
        $kitNew->study_kit_name = $valueOfFields['study_kit_name'];
        $kitNew->study_kit_description = $valueOfFields['study_kit_description'];
        $kitNew->study_kit_created_timestamp = $valueOfFields['study_kit_created_timestamp'];

        $kitNew->save();

        if (!$kitNew->save()) {
            throw new \Exception(print_r($kitNew->errors, true));
        }

        return $kitNew->study_kit_id;
    }

    /**
     * удаляет коллекцию и все не использующиеся больше
     * словарные статьи из БД
     *
     * @param $kitId
     */
    public static function delStudyKit($kitId)
    {
        // получить состав коллекции
        $dicEntryList = StudyKit::findDicEntryInKit($kitId);

        // удалить из коллекции все элементы
        // цикл нужен для проверки нужности полного
        // удаления словарной статьи  из БД
        foreach ($dicEntryList AS $dicEntry) {
            StudyKit::delDicEntryFromKit($kitId, $dicEntry['dic_entry_id'], true);
        }

        // удалить саму коллекцию
        self::deleteAll(['study_kit_id' => $kitId]);
    }

    /**
     * Пытается найти у пользлвателя $userId коллекцию для изучения $kitId
     * если коллекция не найдена, возвращает true, найдена false
     *
     * @param $userId
     * @param $kitId
     * @return bool
     */
    public static function isNotFindKitUser($userId, $kitId)
    {
        $KitListArray = self::findOne([
            'study_kit_id' => $kitId,
            'user_id' => $userId]);

        return !$KitListArray;
    }

    /**
     * возвращает список коллекций для изучения
     * сформированный пользователем $userId
     *
     */
    public static function getStudyKitList($userId)
    {
        $StudyKitList = self::find()
            ->select([
                'study_kit_id',
                'study_kit_name',
            ])
            ->where(['user_id' => $userId])
            ->orderBy('study_kit_created_timestamp DESC')
            ->asArray()
            ->all();

        return $StudyKitList;
    }

    public static function tableName()
    {
        return 'study_kit_list';
    }

    public function rules()
    {
        return [
            [['user_id', 'study_kit_created_timestamp'], 'required'],
            [['user_id', 'study_kit_created_timestamp'], 'integer'],
            [['study_kit_name', 'study_kit_description'], 'string', 'max' => 255],
        ];
    }
}