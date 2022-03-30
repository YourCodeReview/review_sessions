<?php

namespace app\controllers;

use app\models\db\WordList;
use app\models\Study\StudyKit;
use yii\filters\AccessControl;
use yii\web\Controller;

/**
 * Отвечает за формирование списка слов для изучения
 */
class StudyController extends Controller
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
     * Выводит список, сформированных для обучения пользователем, коллекций слов
     *
     * @return string
     */
    public function actionIndex()
    {
        $modelStudyKit = new StudyKit();

        // выводится список доступных коллекций
        return $this->render("study-kit-list", ["model" => $modelStudyKit]);
    }

    /**
     * формирует страницу для проигрывания коллекции слов
     * непосредственно для заучивания
     */
    public function actionStudy()
    {
        $modelStudyKit = new StudyKit();
        $modelStudyKit->kitId = $this->getKitId();

        // выводится список доступных коллекций
        return $this->render("study", ["model" => $modelStudyKit]);
    }

     /**
      * возвращает переданный через GET id формируемой коллекции слов для изусения
      * если id коллекции не передан, происходит редирект на главную страницу
     *
     * @return int|void
     */
    private function getKitId()
    {
        $var = \Yii::$app->request->post();
        if (isset($var['kitId']) && is_array($var['kitId'])) {
            foreach ($var['kitId'] AS $id) {
                $kitId[] = (int)$id;
            }

            return $kitId;
        }

        // обязательная переменная не передана
        \Yii::$app->response->redirect('/', 302)->send();
    }
}