<?php

namespace app\models\EditStudy;

use app\models\db\ListToStudy;
use app\models\db\StudyKitList;
use app\models\helpers\UserAccess;
use Exception;
use yii\base\Model;

/**
 * модель для работы с формой регистрации и редактирования
 * названия и описания коллекции слов для изучения
 */
class KitToStudyForm extends Model
{
    const SCENARIO_CREATE = "create";
    const SCENARIO_DELETE = "delete";

    const REPETITION_KIT_NAME = "Для повторения";

    public $kitId; // id коллекции слов
    public $kitName = ""; // название коллекции слов
    public $kitDescription = ""; // описание коллекции слов

    public $errorText = null; // содежит текст ошибки // если = null - ошибок нет


    /**
     * Создает и возвращает id коллекции слов для изучения
     *
     * @return int
     * @throws Exception
     *
     */
    public function createNewKit()
    {
        $kitNew['user_id'] = \Yii::$app->user->id;
        $kitNew['study_kit_name'] = $this->kitName;
        $kitNew['study_kit_description'] = $this->kitDescription;
        $kitNew['study_kit_created_timestamp'] = time();

        return StudyKitList::createNewRecord($kitNew);
    }

    /**
     * Удаляет коллекцию слов для изучения
     */
    public function deleteKit()
    {
        StudyKitList::delStudyKit($this->kitId);
    }

    /**
     * Создание коллекции слов для повторения
     * Сейчас эта коллекция всегда с именем Для повторения + дата и время начала изучения коллекции
     * из которой создается коллекция для повторения
     * Если такая коллекция уже есть, возвращается id существующей коллекции
     *
     * @param $markUnique - метка для создания уникального имени коллекции Повторения
     * @return int - id существующей или созданной коллекции
     * @throws Exception
     */
    public static function createRepetitionKit($markUnique)
    {
        $userId = \Yii::$app->user->id;

        // проверить наличие коллекции у пользователя
        $kitName = self::REPETITION_KIT_NAME . " " . $markUnique;
        $kitId = StudyKitList::findOne(['user_id' => $userId, 'study_kit_name' => $kitName]);
        if ($kitId) {
            return $kitId->study_kit_id;
        }

        // создать коллекцию
        $kitNew['user_id'] = $userId;
        $kitNew['study_kit_name'] = $kitName;
        $kitNew['study_kit_description'] = "";
        $kitNew['study_kit_created_timestamp'] = time();

        return StudyKitList::createNewRecord($kitNew);
    }

    /**
     * проверяет принадлежность редактируемой коллекции для изучения
     * текущему пользователю
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
            [['kitName', 'kitDescription'], 'trim'],
            ['kitName', 'required', 'message' => 'Это поле нужно заполнить.'],
            [['kitName',], 'string', 'max' => 255, 'tooLong' => 'Поле Название коллекции не должно быть больше 255-ти символов'],
            [['kitDescription',], 'string', 'max' => 255, 'tooLong' => 'Поле Описание не должно быть больше 255-ти символов'],
            ['kitId', 'required'],
            ['kitId', 'validateKitId'],
        ];
    }

    public function scenarios()
    {
        return [
            self::SCENARIO_CREATE => ['kitName', 'kitDescription'],
            self::SCENARIO_DELETE => ['kitId'],
        ];
    }

    public function attributeLabels()
    {
        return [
            'kitName' => 'Название коллекции:',
            'kitDescription' => 'Описание:',
        ];
    }
}