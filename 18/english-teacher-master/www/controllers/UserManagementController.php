<?php

namespace app\controllers;

use app\models\user\UserManagement;
use Yii;
use yii\filters\AccessControl;
use yii\web\Controller;

/**
 * Отвечает за управление данными пользователей
 */
class UserManagementController extends Controller
{
    private $modelUser; // ссылка на модель управления пользователями

    public function __construct($id, $module, $config = [])
    {
        parent::__construct($id, $module, $config);

        // активирует класс используемый во всех методах
        $this->modelUser = new UserManagement();
    }

    // управление доступом к действиям (action)
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::class,
                'only' => ['index', 'logout', 'registration'],
                'denyCallback' => function ($rule, $action) {
                    \Yii::$app->response->redirect('/', 302)->send();;
                },
                'rules' => [
                    [
                        'allow' => true,
                        'actions' => ['logout',],
                        'roles' => ['@'],
                    ],
                    [
                        'allow' => true,
                        'actions' => ['index', 'registration', ],
                        'roles' => ['?'],
                    ],
                ],
            ],
        ];
    }

    // производит аутентификацию пользователей
    public function actionIndex()
    {
        // определяется сценарий обработки данных формы
        $this->modelUser->scenario = UserManagement::SCENARIO_LOGIN;

        // проверка корректности заполнения формы
        if ($this->isFormNotValid($this->modelUser)) {
            return $this->render("user-registration", ["model" => $this->modelUser]);
        }

        // аутентификация прошла успешно
        // переадресация пользователя на страницу с коллекциями
        return $this->redirect('/study-kit', 302);
    }

    // производи разлогинивание пользователе
    public function actionLogout()
    {
        Yii::$app->user->logout();

        // переадресация пользователя на главную страницу
        return $this->redirect('/', 302);
    }

    // управляет регистрацией
    public function actionRegistration()
    {
        // определяется сценарий обработки данных формы
        $this->modelUser->scenario = UserManagement::SCENARIO_REGISTER;

        // проверка корректности заполнения формы
        if ($this->isFormNotValid($this->modelUser)) {
            return $this->render("user-registration", ["model" => $this->modelUser]);
        }

        // регистрация нового пользователя
        $this->modelUser->registration();

        if (is_null($this->modelUser->errorText)) {
            // регистрация прошла успешно
            // залогинить пользователя
            $this->modelUser->scenario = UserManagement::SCENARIO_LOGIN;
            $this->modelUser->validate();

            // переадресовать на страницу с коллекциями
            return $this->redirect('/study-kit', 302);
        }

        return $this->render("user-registration", ["model" => $this->modelUser]);
    }

    /**
     * Возвращает True - если данные переданные из формы ошибочны
     *
     * @param $model
     * @return bool
     */
    private function isFormNotValid($model)
    {
        return !($model->load(\Yii::$app->request->post()) && $model->validate());
    }
}