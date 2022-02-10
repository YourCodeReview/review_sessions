<?php

namespace app\controllers;

use app\models\db\SettingPlayback;
use yii\filters\AccessControl;
use yii\web\Controller;

/**
 * Отвечает за настройку параметров воспроизведения
 */
class SettingPlaybackController extends Controller
{
    // управление доступом к действиям (action)
    public function behaviors()
    {
        $actionList = [
            'index',
        ];

        return [
            'access' => [
                'class' => AccessControl::class,
                'only' => $actionList,
                'denyCallback' => function ($rule, $action) {
                    \Yii::$app->response->redirect('/login', 302)->send();;
                },

                'rules' => [
                    [
                        'allow' => true,
                        'actions' => $actionList,
                        'roles' => ['@'],
                    ],
                ],
            ],
        ];
    }

    /**
     * вывод, проверка и сохранение параметров воспроизведения
     *
     * @return string
     * @throws \Throwable
     */
    public function actionIndex()
    {
        $userId = \Yii::$app->user->id;

        // форма активируется с настройками пользователя или
        // настройками по умолчанию
        $model = SettingPlayback::getSettingUser($userId);
        $model->userId = $userId;

        // проверка данных формы
        if ($this->isFormNotValid($model)) {
            // форма заполнена с ошибкой
            return $this->render("setting-playback", ["model" => $model]);
        }

        // сохранить настройки и переадресовать на страницу
        // со списком коллеций для изучения
        $model->saveSettingUser();

        return $this->redirect("/study-kit", 302);
    }

    private function isFormNotValid($model)
    {
        return !($model->load(\Yii::$app->request->post()) && $model->validate());
    }
}