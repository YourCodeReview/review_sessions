<?php

namespace app\models\db;

use Yii;

/**
 * This is the model class for table "DicEntryList".
 *
 * @property int $dic_entry_id
 */
class DicEntryList extends \yii\db\ActiveRecord
{

    /**
     * Создает новую пустую словрную статью
     *
     * @return int
     */
    public static function getIdNewRecord()
    {
        $newRecord = new DicEntryList();
        $newRecord->save();

        return $newRecord->dic_entry_id;
    }

    public static function tableName()
    {
        return 'dic_entry_list';
    }

    public function rules()
    {
        return [];
    }
}
