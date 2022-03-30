<?php

namespace app\controllers;

use yii\filters\AccessControl;
use yii\web\Controller;

/**
 * Отвечает за управление данными пользователей
 */
class MainController extends Controller
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
                    \Yii::$app->response->redirect('/study-kit', 302)->send();;
                },

                'rules' => [
                    [
                        'allow' => true,
                        'actions' => $actionList,
                        'roles' => ['?'],
                    ],
                ],
            ],
        ];
    }

    public function actionIndex()
    {
        // отрисовка главной страницы
        return $this->render("index", []);
    }
}