<?php

namespace app\controllers;

use app\models\admin\CoupleEditForm;
use app\models\admin\WordAddEditForm;
use app\models\admin\WordSearchForm;
use app\models\admin\WordAddList;

use yii\filters\AccessControl;
use yii\web\Controller;
use yii\web\UploadedFile;

/**
 * Отвечает за формирование списка слов для изучения
 */
class AdminController extends Controller
{
    /**
     * Осуществляет контроль доступа к действиям класса
     */
    public function init()
    {
        parent::init();

        if (isset(\Yii::$app->user->identity->user_role)) {
            $userName = \Yii::$app->user->identity->user_role;
        } else {
            \Yii::$app->response->redirect('/login', 302)->send();
            return false;
        }


        if ($userName != "admin") {
            \Yii::$app->response->redirect('/login', 302)->send();
            return false;
        }
    }

    /**
     * Выводит форму поиска слов в БД
     *
     */
    public function actionIndex()
    {
        $model = new WordSearchForm();

        if ($this->isFormNotValid($model)) {
            return $this->render("word-search", ["model" => $model]);
        }

        $model->findWord();

        return $this->render("word-search", ["model" => $model]);
    }

    /**
     * Добавляет на сайт слова списком
     */
    public function actionWordListAdd()
    {
        $folderWord = "../word_add_list/";
        $fileWordList = $folderWord . "_word_list.txt";

        /**
         * Определяет нужно ли проводить переозвучку ранее озвученных слов
         */
        $reSound = false;

        // проверить переданные GET параметры
        $getVar = \Yii::$app->request->get();
        if (isset($getVar['reSound'])) {
            $reSound = true;
        }

        $model = new WordAddList();
        try {
            $model->addWordList($folderWord, $fileWordList, $reSound);
        } catch (\Exception $e) {
            return $e->getMessage();
        }

        return "Операция выполнена";
    }

    /**
     * Добавляет новое слово в БД
     *
     * @return string
     */
    public function actionWordAddEdit()
    {
        $model = new WordAddEditForm();
        // $model = new Test();

        // две следующие инструкции разделять нельзя
        // иначе нарушается подгрузка файла из АктивФорм
        if ($model->load(\Yii::$app->request->post())) {
            $model->mp3File = UploadedFile::getInstance($model, 'mp3File');

            if (!$model->validate()) {
                return $this->render("word-add-edit", ["model" => $model]);
            }
        } else {
            return $this->render("word-add-edit", ["model" => $model]);
        }


        try {
            if ($model->wordId == 0) {
                // добавляется новое слово
                $wordId = $model->addWord();
            } else {
                // обновляется уже существующее слово
                $wordId = $model->editWord();
            }


        } catch (\Exception $e) {
            $model->errorText = \Yii::$app->params['error']['site'];
            return $this->render("word-add-edit", ["model" => $model]);
        }

        if ($model->languageId == 2) {
            return $this->redirect("/admin/couple?wordId=$wordId", 302);
        }

        return $this->redirect("/admin", 302);

    }

    /**
     * Редактирует и создает новые связи слова (слово - перевод)
     *
     * @return void|\yii\web\Response
     */
    public function actionCouple()
    {
        $get = \Yii::$app->request->get();
        if (!isset($get["wordId"])) {
            return $this->redirect('/', 302);
        }

        $wordId = (int)$get['wordId'];

        $model = new CoupleEditForm();
        $model->wordId = $wordId;

        $a = \Yii::$app->request->post();

        if ($this->isFormNotValid($model)) {
            return $this->render("word-couple", ["model" => $model]);
        }

        // добавляется вариант перевода
        $model->addNewCouple();

        return $this->render("word-couple", ["model" => $model]);
    }


    private function isFormNotValid($model)
    {
        return !($model->load(\Yii::$app->request->post()) && $model->validate());
    }
}
