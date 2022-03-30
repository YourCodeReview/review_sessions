<?php

namespace app\models\db;

use Yii;

/**
 * This is the model class for VIEW "list_to_study".
 *
 * @property int $study_kit_id - id коллекции слов составленной пользователем для изучения. Состав словарных статей хранится в dic_entry
 * @property int $dic_entry_id - Id - словарных статей
 * @property int $dic_entry_position - Порядок вывода словарных статей в коллекции для изучения
 * @property int $user_id id - владельца коллекции
 * @property string|null $study_kit_name - Название коллекции слов. Для того что бы пользователь мог отличить одну коллекцию от другой
 * @property string|null $study_kit_description - Описание коллекции
 * @property int $study_kit_created_timestamp - Тайм стамп UTC времени создания коллекции. Сделано для независимости времени от часового пояса установленного в MySql и PHP.
 * @property int|null $word_couple_id - id пары слов (одного варианта перевода) который  включен  в словарную статью
 * @property int|null $word_couple_position - Позиция пары слов внутри словарной статьи
 * @property int $wodr_id_1 - id слова
 * @property int $word_linked_id - id связанного слова (перевода)
 * @property string $word_spelling_1 - Написание слова на языке оригинала
 * @property string|null $word_transcription_1
 * @property int $language_id_1 id - языка к которому относится слово
 * @property int $is_voiced_1 0 - слово не озвучено; 1 - озвучено
 * @property int $word_id_2
 * @property string $word_spelling_2 - Написание слова на языке оригинала
 * @property string|null $word_transcription_2
 * @property int $language_id_2 id - языка к которому относится слово
 * @property int $is_voiced_2 0 - слово не озвучено; 1 - озвучено
 */

/**
 * Модель построена над Видом, а не таблицей
 */

class ListToStudy extends \yii\db\ActiveRecord
{
    /**
     * Возвращает массив из состава словарной статьи
     * принадлежащих коллекции для изучения $kitId
     *
     * @param $kitId
     * @param $userId
     * @return array
     */
    public static function getDicEntryContent($kitId, $userId)
    {
        $kitId = (int)$kitId; // лучше перебдеть чем недобдеть
        $userId = (int)$userId; // лучше перебдеть чем недобдеть

        $kit = self::findBySql("
SELECT 
    study_kit.dic_entry_id,
    wl_1.word_spelling AS word_spelling_1,
    wl_2.word_spelling AS word_spelling_2
FROM
    study_kit_list
LEFT JOIN
	study_kit
		ON (study_kit_list.study_kit_id = study_kit.study_kit_id)
LEFT JOIN
	dic_entry
		ON (study_kit.dic_entry_id = dic_entry.dic_entry_id)
LEFT JOIN
	word_couple_list
		ON (dic_entry.word_couple_id = word_couple_list.word_couple_id)
LEFT JOIN
	word_list AS wl_1
		ON (word_couple_list.word_id = wl_1.word_id)
LEFT JOIN
	word_list AS wl_2
		ON (word_couple_list.word_linked_id = wl_2.word_id)
WHERE
	study_kit_list.study_kit_id = $kitId
    AND study_kit_list.user_id = $userId
    AND study_kit.dic_entry_id IS NOT NULL
ORDER BY
	study_kit_list.study_kit_created_timestamp DESC,
    study_kit.dic_entry_position ASC,
    dic_entry.word_couple_position
")
            ->asArray()
            ->all();

        // преобразовать массив в каталог
        $kit = self::convertArrayKitForStudy($kit);

        return $kit;
    }

    /**
     * Возвращает массив из состава словарной статьи
     * принадлежащих коллекции для изучения $kitId для вывода
     * на странице воспроизведения
     *
     * @param $kitId
     * @return array
     */
    public static function getDicEntryForStudy($kitId)
    {
        $kitId = (int)$kitId; // лучше перебдеть чем недобдеть

        $couplerKitList = self::findBySql("
SELECT 
    study_kit.dic_entry_id,
    wl_1.word_id AS word_id_1,
    wl_1.word_spelling AS word_spelling_1,
    wl_2.word_id AS word_id_2,
    wl_2.word_spelling AS word_spelling_2
FROM
    study_kit_list
LEFT JOIN
	study_kit
		ON (study_kit_list.study_kit_id = study_kit.study_kit_id)
LEFT JOIN
	dic_entry
		ON (study_kit.dic_entry_id = dic_entry.dic_entry_id)
LEFT JOIN
	word_couple_list
		ON (dic_entry.word_couple_id = word_couple_list.word_couple_id)
LEFT JOIN
	word_list AS wl_1
		ON (word_couple_list.word_id = wl_1.word_id)
LEFT JOIN
	word_list AS wl_2
		ON (word_couple_list.word_linked_id = wl_2.word_id)
WHERE
	study_kit_list.study_kit_id = $kitId
    AND study_kit.dic_entry_id IS NOT NULL -- отрезает пустые коллекции
ORDER BY
	study_kit_list.study_kit_created_timestamp DESC,
    study_kit.dic_entry_position ASC,
    dic_entry.word_couple_position
")
            ->asArray()
            ->all();

        return $couplerKitList;
    }

    /**
     * Преобразует линейный массив в многомерный.
     * Верхний уровень id словарной статьи , вложенные Написание слова,
     * написание перевода слова
     *
     * @param $entryList
     * @return array
     */
    private static function convertArrayKitForStudy($entryList)
    {
        if (count($entryList) == 0) {
            return $entryList;
        }

        foreach ($entryList AS $entry) {
            $kit[$entry['dic_entry_id']][$entry['word_spelling_1']][] = $entry['word_spelling_2'];
        }

        return $kit;
    }

    public static function tableName()
    {
        return 'list_to_study';
    }
}
