<?php

namespace app\models\db;

use Yii;
use yii\db\ActiveRecord;

/**
 * This is the model class for table "study_kit".
 *
 * @property int $study_kit_id - id коллекции словарных статей составленной пользователем для изучения. Словарные статьи  (варианты перевода слова) хранятся в dic_entry
 * @property int $dic_entry_id - Id словарной статьи (вариантов перевода)
 * @property int $dic_entry_position - Порядок вывода словарной стать в коллекции для изучения
 */
class StudyKit extends ActiveRecord
{
    /**
     * Добавляет новую словарную статью  в коллекцию для изучения
     *
     * @param $kitId - коллекция для изучения
     * @param $dicEntryId - словарная статья
     */
    public static function addDicEntryToKit($kitId, $dicEntryId)
    {
        // принимает зачищенные переменные

        // получить порядковый номер под которым добавить
        // словарную статью  в коллекцию для изучения
        $dicEntryPosition = self::getPositionDicEntryToAdd($kitId);


        // добавляется новую словарную статью к коллекции изученя
        $kit = new StudyKit();

        $kit->study_kit_id = $kitId;
        $kit->dic_entry_id = $dicEntryId;
        $kit->dic_entry_position = $dicEntryPosition;

        $kit->save();
    }

    /**
     * Возвращает список словарных статей относящихся к
     * коллекции $kitId
     *
     * @param $kitId
     */
    public static function findDicEntryInKit($kitId)
    {
        $dicEntryList = self::find(['study_kit_id' => $kitId])
            ->asArray()
            ->all();

        return $dicEntryList;
    }

    /**
     * Удаляет словарную статью  из коллекции для изучения
     * @param $kitId - id коллекции для изучения
     * @param $dicEntryId - id словарной статьи
     * @param bool $isDeleteKit - признак что удаляется вся коллекция, а не одна словарная статья
     */
    public static function delDicEntryFromKit($kitId, $dicEntryId, $isDeleteKit = false)
    {
        self::deleteAll(['study_kit_id' => $kitId, 'dic_entry_id' => $dicEntryId]);

        // пересчитать порядковые номера словарных статей  в коллекции для изучения
        // если удаляется коллекция целиком, пересчитывать порядковые номера не нужно
        if (!$isDeleteKit) {
            self::recalcPositionDicEntry($kitId);
        }


        // если словарная статья  больше ни где не используется
        // удалить словарную статью из БД полностью
        DicEntry::delDicEntryIfNotUsed($dicEntryId);
    }

    /**
     * Возвращает true - если словарная статья есть в коллекциях для изучения
     * false - если словарной статьи нет
     *
     * @param $dicEntryId - id словарной статьи
     * @return bool
     */
    public static function isDicEntryInKit($dicEntryId)
    {
        $dicEntry = self::findOne(['dic_entry_id' => $dicEntryId]);

        if (is_null($dicEntry)) {
            return false;
        }

        return true;
    }

    /**
     * Пересчитывает порядковые номера словарных статей в коллекции для изучения
     *
     * @param $kitId - коллекция для изучения
     */
    private static function recalcPositionDicEntry($kitId)
    {
        // запросить список словарных статей из коллекции
        $entryList = self::selectDicEntryFromKit($kitId);

        // обновить порядковые номера
        $position = 1;
        foreach ($entryList AS $entry) {
            $dicEntryRow = self::findOne([
                'study_kit_id' => $kitId,
                'dic_entry_id' => $entry['dic_entry_id']]);

            if (is_null($dicEntryRow)) {
                // если за время операции кто то успел удалить запись
                continue;
            }

            $dicEntryRow->dic_entry_position = $position;
            $dicEntryRow->save();

            $position++;
        }
    }

    /**
     * Возвращает массив словарных статей из коллекции для изучения $kitId
     *
     * @param $kitId
     * @return StudyKit[]|array|ActiveRecord[]
     */
    private static function selectDicEntryFromKit($kitId)
    {
        $dicEntryList = self::find()
            ->where(['study_kit_id' => $kitId])
            ->orderBy('dic_entry_position ASC')
            ->asArray()
            ->all();

        return $dicEntryList;
    }

    /**
     * возвращает номер позиции под которой в коллекцию слов для изучения $kitId
     * должна быть добавлена словарная статья
     *
     * @param $kitId - id коллекции для изучения
     * @return int
     */
    private static function getPositionDicEntryToAdd($kitId)
    {
        $kit = self::find()
            ->select('MAX(dic_entry_position) AS psn')
            ->where(['study_kit_id' => $kitId])
            ->asArray()
            ->one();

        $dicEntryPosition = 1; // порядковый номер под которым будет добавлена статья

        if (!is_null($kit['psn'])) {
            $dicEntryPosition = (int)$kit['psn'] + 1;
        }

        return $dicEntryPosition;
    }

    public static function tableName()
    {
        return 'study_kit';
    }

    public function rules()
    {
        return [
            [['study_kit_id', 'dic_entry_id', 'dic_entry_position'], 'required'],
            [['study_kit_id', 'dic_entry_id', 'dic_entry_position'], 'integer'],
            [['study_kit_id', 'dic_entry_id'], 'unique', 'targetAttribute' => ['study_kit_id', 'dic_entry_id']],
        ];
    }
}
