<?php

namespace app\models\db;

use yii\db\ActiveRecord;

/**
 * This is the model class for table "DicEntry".
 *
 * @property int $dic_entry_id
 * @property int|null $word_couple_id
 * @property int|null $word_couple_position Позиция пары слов внутри словарной статьи
 */
class DicEntry extends ActiveRecord
{

    /**
     * Ищет в БД словарную статью точно совпалдающую с новой статьей
     * Если точной копии не найдено возвращается false
     * Если найдена возвращается id найденной статьи
     *
     * @param $dicEntryList
     * @return false|mixed
     */
    public static function findEntryByCouple($dicEntryList)
    {
        $dicEntryCount = count($dicEntryList);

        // получить id словарных статей похожих на добавляемую словарную статью
        $dicEntryId= self::getSimilarDicEntryId($dicEntryList, $dicEntryCount);

        if (count($dicEntryId) == 0) {
            // словарные статьи не найдены
            return false;
        }

        // найти словарную статью точно сопадающий с добавляемой
        $exactMatchId = self::getExactMatchdicEntryId($dicEntryId, $dicEntryCount);

        return $exactMatchId;
    }

    /**
     * удаляет из БД словарную статью если она ни где не используется
     *
     * @param $dicEntryId
     */
    public static function delDicEntryIfNotUsed($dicEntryId)
    {
        // проверить наличие словарной статьи в других коллекциях слов для изучения
        if (!StudyKit::isDicEntryInKit($dicEntryId)) {
            self::deleteAll(['dic_entry_id' => $dicEntryId]);
            DicEntryList::deleteAll(['dic_entry_id' => $dicEntryId]);
        }
    }

    /**
     * Возвращает id точной копии словарной статьи, если такая статья
     * есть в БД или false, если такой статьи в БД нет
     *
     * @param $dicEntryId
     * @param $dicEntryCount
     * @return false|mixed
     */
    private static function getExactMatchdicEntryId($dicEntryId, $dicEntryCount)
    {
        $dicEntryId = self::find()
            ->select(['dic_entry_id', 'COUNT(*) as cnt'])
            ->where(['in', 'dic_entry_id', $dicEntryId])
            ->asArray()
            ->groupBy(['dic_entry_id'])
            ->having(['cnt' => $dicEntryCount])
            ->all();

        if (count($dicEntryId) == 0) {
            // словарная статья не найдена
            return false;
        }

        return $dicEntryId[0]['dic_entry_id'];
    }

    /**
     * возвращает массив id существующих словарных статей похожих
     * на новую словарную статью $dicEntryList похожесть определяется по:
     * 1. наличию в словарной статье  тех же словарных пар
     * 2. в словарной статье  словарные пары стоят в том же порядке
     *
     * @param $dicEntryList - массив id словарных пар для новой словарной статьи
     */
    private static function getSimilarDicEntryId($dicEntryList, $dicEntryCount)
    {
        // сформировать запрос на выборку словарных статей  у которых
        // совпадают словарные пары и позиции размещения словарных пар
        $query = self::createQuerySelectCoupledIdInPosition($dicEntryList, $dicEntryCount);

        // получить массив id похожих словарных статей
        $dicEntryId = self::selectSimilardicEntryId($query);

        return $dicEntryId;
    }

    /**
     * возвращает запрос на выборку id словарных статей  у которых
     * совпадают позиции словарных пар с новой словарной статьей
     *
     * @param $dicEntryList - массив id словарных пар для новой словарной статьи
     * @param $dicEntryCount - число словарных пар в статье
     * @return string
     */
    private static function createQuerySelectCoupledIdInPosition($dicEntryList, $dicEntryCount)
    {
        $couplePosition = 1;
        foreach ($dicEntryList as $coupleId) {
            $coupleId = (int)$coupleId;
            $query[] =
                "(SELECT dic_entry_id, word_couple_position " .
                "FROM dic_entry " .
                "WHERE (word_couple_id = $coupleId AND word_couple_position = $couplePosition))";

            $couplePosition++;
        }

        for ($i = 1; $i < $dicEntryCount; $i++) {
            $query[0] .= " UNION " . $query[$i];
        }

        $queryRes = "SELECT dic_entry_id, count(*) as cnt " .
            "FROM ($query[0]) as UN " .
            "GROUP BY dic_entry_id " .
            "HAVING cnt = $dicEntryCount";

        return $queryRes;
    }

    /**
     * Делает выборку записей по запросу $query и из полученного результата
     * формирует массив из найденных id словарных статей
     *
     * @param $query
     * @return DicEntry[]|array|ActiveRecord[]
     */
    private static function selectSimilardicEntryId($query)
    {
        $idList = self::findBySql($query)
            ->asArray()
            ->all();

        if (count($idList) != 0) {
            $idList = array_column($idList, 'dic_entry_id');
        }

        return $idList;
    }

    /**
     * Добавляет вариант перевода (словарную пару $coupleId) в
     * словарную статью  $dicEntryId
     *
     * @param $dicEntryId - id словарной статьи  в которую добавляется словарная пара
     * @param $coupleId - id словарной пары
     * @param $couplePosition - позиция пары в статье
     */
    public static function addCoupleToDicEntry($dicEntryId, $coupleId, $couplePosition)
    {
        $entry = new DicEntry();

        $entry->dic_entry_id = $dicEntryId;
        $entry->word_couple_id = $coupleId;
        $entry->word_couple_position = $couplePosition;

        $entry->save();
    }

    public static function tableName()
    {
        return 'dic_entry';
    }

    public function rules()
    {
        return [
            [['dic_entry_id'], 'required'],
            [['dic_entry_id', 'word_couple_id', 'word_couple_position'], 'integer'],
        ];
    }
}
