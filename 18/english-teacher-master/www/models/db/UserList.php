<?php

namespace app\models\db;

use Exception;
use Yii;
use yii\web\IdentityInterface;

/**
 * This is the model class for table "user_list".
 *
 * @property int $id
 * @property string $user_email
 * @property string $user_password
 * @property int $user_emailI_confirm 0 - email не подтвержден, 1 - подтвержден
 * @property int $user_is_banned 0 - пользователю разрешен доступ на сайт, 1 - запрещен
 * @property string|null $auth_key
 * @property string|null $username
 */
class UserList extends \yii\db\ActiveRecord implements IdentityInterface
{
    /**
     * Добавляет нового пользователя в ситему
     *
     * @throws \yii\base\Exception
     * @throws \Exception
     */
    public static function registrationUserNew($userProperty)
    {
        // данные в функцию поступают уже проверенные

        // шифрование пароля // новая переменная введена что бы не дублировать массив в памяти
        $passwordEncode = self::encodePassword($userProperty['password']);

        $userNew = new UserList();
        $userNew->user_email = $userProperty['email'];
        $userNew->user_password = $passwordEncode;
        $userNew->save();

        if (!$userNew->save()) {
            throw new \Exception(print_r($userNew->errors, true));
        }

        return true;
    }

    /**
     * Возвращает true если $email - уже зарегистрирован
     *
     * @param $email
     * @return bool
     */
    public static function isEmailFound($email)
    {
        $found = self::find()
            ->select("id")
            ->where(['user_email' => $email])
            ->one();

        if (is_null($found)) {
            return false;
        }

        return true;
    }

    /**
     * Ищет и возвращает id пользователя по данным аутентификации
     *
     * @param $email
     * @param $password
     * @return bool
     */
    public static function isLoginPassed($email, $password)
    {
        // проверить аутентификацию пользователя
        $id = Yii::$app->user->id;
        if (!is_null($id)) {
            // пользователь уже аутентифицирован
            return true;
        }

        $foundUser = self::findOne(['user_email' => $email]);

        if (is_null($foundUser)) {
            return false;
        }

        $passwordHash = $foundUser->user_password;

        if (!Yii::$app->getSecurity()->validatePassword($password, $passwordHash)) {
            // неправильный пароль
            return false;
        }

        Yii::$app->user->login($foundUser);

        return true;
    }

    /**
     * Шифрует пароль
     * Функция добавлена для гарантии одинакового шифрования паролей в разных методах
     *
     * @param $password
     * @return string
     * @throws \yii\base\Exception
     */
    private static function encodePassword($password)
    {
        return Yii::$app->getSecurity()->generatePasswordHash($password);
    }

    public static function tableName()
    {
        return 'user_list';
    }

    public function rules()
    {
        return [
            [['user_email', 'user_password'], 'required'],
            [['user_emailI_confirm', 'user_is_banned'], 'integer'],
            [['user_email', 'user_password'], 'string', 'max' => 60],
            [['auth_key'], 'string', 'max' => 32],
            [['username'], 'string', 'max' => 45],
            [['user_email'], 'unique'],
        ];
    }





    ///////////////////////////////////////////////////////////////////////////////
    // Блок методов обеспечивающих работу с аутентификацие пользователя
    ///////////////////////////////////////////////////////////////////////////////

    //
    public static function findIdentity($id)
    {
        return static::findOne($id);
    }

    /**
     * Finds an identity by the given token.
     *
     * @param string $token the token to be looked for
     * @return IdentityInterface|null the identity object that matches the given token.
     */
    public static function findIdentityByAccessToken($token, $type = null)
    {
        return static::findOne(['access_token' => $token]);
    }

    /**
     * @return int|string current user ID
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @return string current user auth key
     */
    public function getAuthKey()
    {
        return $this->auth_key;
    }

    /**
     * @param string $authKey
     * @return bool if auth key is valid for current user
     */
    public function validateAuthKey($authKey)
    {
        return $this->getAuthKey() === $authKey;
    }

    public function beforeSave($insert)
    {
        if (parent::beforeSave($insert)) {
            if ($this->isNewRecord) {
                $this->auth_key = \Yii::$app->security->generateRandomString();
            }
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // окончание блока аутентификации
    ///////////////////////////////////////////////////////////////////////////////
}