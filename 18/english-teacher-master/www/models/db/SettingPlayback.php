<?php

namespace app\models\db;

use Yii;

/**
 * This is the model class for table "setting_playback".
 *
 * @property int $setting_playback_id Уникальный автоинкремент записи В 0 - м Id лежат данные по умолчанию
 * @property int $user_id id пользователя которому принадлежит запись
 * @property int|null $word_in_block Число слов в блоке. Вся коллекция разбивается на блоки воспроизведения.
 * @property int|null $word_translate_mil_sec Врнмя в мили секундах между словом и его переводом.
 * @property int|null $translate_option_mil_sec Время в милисекундах между вариантами перевода слова
 * @property int|null $word_pause_mil_sec Время между окончанием последнего варианта перевода предыдущего слова и следующим изучаемым словом.
 */
class SettingPlayback extends \yii\db\ActiveRecord
{
    public $userId; // введено что бы избежать при передачи из формы user_id и дополнительной проверки валидности

    public $errorText = null;

    /**
     * Возвращает установки воспроизведения по умолчанию или
     * личные настройки пользователя если они есть в БД
     *
     * @param $userId
     * @return SettingPlayback|array|\yii\db\ActiveRecord
     */
    public static function getSettingUser($userId)
    {
        // установки по умолчанию хранятся в записи с setting_playback_id = 0 и user_id = 0
        // т.о. при уворядочивании по user_id в обратном порядке, первой записью будут
        // личные настройки если они есть, а если их нет - настройки по умолчанию
        $userSetting = self::find()
            ->where(['user_id' => $userId])
            ->orWhere(['setting_playback_id' => 0])
            ->orderBy('user_id DESC')
            ->one();

        if (is_null($userSetting)) {
            throw \Exception(\Yii::$app->params['error']['site']);
        }

        return $userSetting;
    }

    /**
     * @return bool
     * @throws \Throwable
     */
    public function saveSettingUser()
    {
        // проверить отличаются ли данные от данных по умолчанию
        if ($this->isUserSettingDefault()) {
            // настройки совпадат с по умолчанию
            // удалить пользовательские настройки
            self::deleteAll(['user_id' => $this->userId]);

            return true;
        }

        // сохранить данные
        $model = self::findOne(['user_id' => $this->userId]);

        if (is_null($model)) {
            // у пользователя $this->userId нет записи о личных настройках
            $model = new SettingPlayback();
        }

        $model->user_id = $this->userId;
        $model->word_in_block = $this->word_in_block;
        $model->word_translate_mil_sec = $this->word_translate_mil_sec;
        $model->translate_option_mil_sec = $this->translate_option_mil_sec;
        $model->word_pause_mil_sec = $this->word_pause_mil_sec;

        $model->save();

        return true;
    }

    /**
     * Проверя совпадение сохраняемых пользователем настроек и
     * настроек по умоланию. true - совпадают
     *
     * @return bool
     */
    private function isUserSettingDefault()
    {
        $setting = self::find()
            ->where([
                'user_id' => 0,
                'word_in_block' => $this->word_in_block,
                'word_translate_mil_sec' => $this->word_translate_mil_sec,
                'translate_option_mil_sec' => $this->translate_option_mil_sec,
                'word_pause_mil_sec' => $this->word_pause_mil_sec,
            ])
            ->one();

        if (is_null($setting)) {
            return false;
        }

        return true;
    }


    public static function tableName()
    {
        return 'setting_playback';
    }


    public function rules()
    {
        // user_id - всегда задается, поэтому не нуждается в проверке

        return [
            [['word_in_block', 'word_translate_mil_sec', 'translate_option_mil_sec', 'word_pause_mil_sec'], 'required'],
            [['word_in_block', 'word_translate_mil_sec', 'translate_option_mil_sec', 'word_pause_mil_sec'], 'integer'],
        ];
    }


    public function attributeLabels()
    {
        return [
            'setting_playback_id' => 'Setting Playback ID',
            'user_id' => 'User ID',
            'word_in_block' => 'Число слов в блоке воспроизведения',
            'word_translate_mil_sec' => 'Пауза между словом и переводом Секунд',
            'translate_option_mil_sec' => 'Пауза между вариантами перевода Секунд',
            'word_pause_mil_sec' => 'Пауза между изучаемыми словами Секунд',
        ];
    }
}
