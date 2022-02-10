<?php

namespace app\models\db;

use \yii\db\ActiveRecord;

/**
 * This is the model class for table "word_list".
 *
 * @property int $word_id - id слова
 * @property string $word_spelling - Написание слова на языке оригинала
 * @property string|null $word_transcription - транскрипция
 * @property int $language_id - id - языка к которому относится слово
 * @property int $is_voiced - 0 - слово не озвучено; 1 - озвучено
 * @property string $word_description - Описание слова
 */
class WordList extends ActiveRecord
{
    /**
     * возвращает массив слов word[] = ["wodrId", "wordSpelling"].
     *
     * массив формируется из запроса к базе по призакам:
     * - для слова есть аудио
     * - слова начинаются с букв $firstLettersWord
     *
     * @param $firstLettersWord
     * @param int $languageId
     * @return WordList[]|array|ActiveRecord[]
     */
    public static function getWordsFoundByFirstLetters($firstLettersWord, $languageId = 2)
    {
        $wordList = self::find()
            ->select(['word_id', 'word_spelling'])
            ->where([
                'is_voiced' => 1,
                'language_id' => $languageId,

            ])
            ->andWhere(['like', 'word_spelling', $firstLettersWord . '%', false])
            ->orderBy(['word_spelling' => SORT_ASC])
            ->limit(10)
            ->asArray()
            ->all();

        return $wordList;
    }

    /**
     * Возвращает массив слов содержащих переданные для поиска буквы $lettersWord
     *
     * @param $lettersWord
     * @return WordList[]|array|ActiveRecord[]
     */
    public static function getWordsByLetters($lettersWord)
    {
        // поптыться найти точное соответствие
        $fullСompliance = self::find()
            ->select(['word_id', 'word_spelling', 'is_voiced'])
            ->andWhere(['like', 'word_spelling', $lettersWord, false])
            ->orderBy(['word_spelling' => SORT_ASC]);

        $partialСompliance = self::find()
            ->select(['word_id', 'word_spelling', 'is_voiced'])
            ->andWhere(['like', 'word_spelling', '%' . $lettersWord . '%', false])
            ->orderBy(['word_spelling' => SORT_ASC])
            ->limit(1000);

        return $query = $fullСompliance
            ->union($partialСompliance)
            ->asArray()
            ->all();
        /*
        return self::find()
            ->select(['word_id', 'word_spelling', 'is_voiced'])
            ->andWhere(['like', 'word_spelling', $lettersWord, false])
            ->orderBy(['word_spelling' => SORT_ASC])
            ->union()
            ->select(['word_id', 'word_spelling', 'is_voiced'])
            ->andWhere(['like', 'word_spelling', '%' . $lettersWord . '%', false])
            ->orderBy(['word_spelling' => SORT_ASC])
            ->limit(1000)
            ->asArray()
            ->all();
        */
    }

    /**
     * Проверяет существование слова $wordId
     * Если не найдено, возвращает true, если найдено False
     *
     * @param $wordId
     * @return bool
     */
    public static function isWordIdNotFound($wordId)
    {
        return !self::findOne(['word_id' => $wordId]);
    }

    public static function tableName()
    {
        return 'word_list';
    }

    public function rules()
    {
        return [
            [['word_spelling'], 'required'],
            [['language_id', 'is_voiced'], 'integer'],
            [['word_spelling'], 'string', 'max' => 100],
            [['word_transcription'], 'string', 'max' => 45],
            [['word_description'], 'string', 'max' => 255],
        ];
    }
}