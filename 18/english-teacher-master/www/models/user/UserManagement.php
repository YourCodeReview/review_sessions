<?php

namespace app\models\user;

use app\models\db\UserList;
use yii\base\Model;


/**
 * модель для Регистрации и Аутентификации пользователей
 *
 */
class UserManagement extends Model
{
    public $email;
    public $password;
    public $passwordRepeat;

    public $registrationSuccessful = false; // признак удачной регистрации

    public $errorText = null; // содежит текст ошибки // если = null - ошибок нет

    const SCENARIO_LOGIN = 'login';
    const SCENARIO_REGISTER = 'register';

    public function registration()
    {
        // массив введен что бы избежать пораждения взаимной зависимомти между моделями
        // и на случай расширения атрибутов авторизации
        $user['email'] = $this->email;
        $user['password'] = $this->password;

        try {
            UserList::registrationUserNew($user);
        } catch (\Exception $e) {
            $this->errorText = \Yii::$app->params['error']['site'];
            return false;
        }

        $this->registrationSuccessful = true;
        return true;
    }

    // определяет название полей в форме
    public function attributeLabels()
    {
        return [
            'email' => 'Ваш e-mail:',
            'password' => 'Пароль:',
            'passwordRepeat' => 'Повторите пароль:',
        ];
    }

    public function scenarios()
    {
        return [
            self::SCENARIO_LOGIN => ['email', 'password'],
            self::SCENARIO_REGISTER => ['email', 'password', 'passwordRepeat'],
        ];
    }

    public function rules()
    {
        return [
            [['email',], 'trim'],
            [['email', 'password', 'passwordRepeat',], 'required', 'message' => 'Это поле необходимо заполнить'],
            [['email',], 'string', 'max' => 60, 'tooLong' => 'Поле email не должно быть больше 60-ти символов'],
            [['email'], 'email', 'message' => 'В этом поле должен быть введен адрес электронной почты. Проверьте правильность ввода.'],
            [['email'], 'validateUniqueEmail', 'on' => [self::SCENARIO_REGISTER,]],
            ['passwordRepeat', 'compare', 'compareAttribute' => 'password', 'on' => [self::SCENARIO_REGISTER,], 'message' => 'Поля Пароль и Повторите пароль должны быть одинаковыми'],
            ['password', 'validateLogin', 'on' => [self::SCENARIO_LOGIN],],

        ];
    }


    /**
     * При регистрации нового пользователя проверяет email на уникальность
     *
     * @param $attribute
     * @param $params
     */
    public function validateUniqueEmail($attribute, $params)
    {
        try {
            $isEmailFound = UserList::isEmailFound($this->$attribute);
        } catch (\Exception $e) {
            $this->addError($attribute,  \Yii::$app->params['error']['site']);
            return;
        }

        if ($isEmailFound) {
            $this->addError($attribute, 'Такой e-mail уже зарегистрирован. 
                                                Воспользуйтесь формой напоминания пароля.');
        }
    }

    /**
     * Проводит аутентификацию пользоателя или возвращает ошибку
     *
     * @param $attribute
     * @param $params
     */
    public function validateLogin($attribute, $params)
    {
        try {
            $isLogin = UserList::isLoginPassed($this->email, $this->password);
        } catch (\Exception $e) {
            $this->addError($attribute, \Yii::$app->params['error']['site']);
            return;
        }

        if (!$isLogin) {
            $this->errorText = "Вы ввели не верную пару E-mail / Пароль. 
                                Попробуйте снова или воспользуйтесь страницей 
                                напоминания пароля.";

            // задать ошибку необходимо что бы валидатор данных формы возвращал ошибку
            $this->addError($attribute, ' ');
        }
    }
}