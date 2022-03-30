<?php

namespace app\controllers;

use app\models\db\WordList;
use app\models\EditStudy\WordDeleteFromKit;
use app\models\EditStudy\WordSelectToStudyForm;
use app\models\EditStudy\KitToStudyForm;
use app\models\EditStudy\WordCoupleSelectForm;
use app\models\Study\StudyKit;
use yii\filters\AccessControl;
use yii\helpers\Html;
use yii\web\Controller;
use yii\web\Response;

/**
 * Отвечает за формирование списка слов для изучения
 */
class EditStudyController extends Controller
{


    // управление доступом к действиям (action)
    public function behaviors()
    {
        $actionList = [
            'index',
            'delete-word-from-kit',
            'delete-study-kit',
            'select-word-couple',
            'create-study-kit',
            'select-word',
            'add-to-repetition-kit'
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
     * Добавляет словарную пару в коллекцию Повторение
     * если коллекция Повторение отсутствует создает ее
     * используется при проигрывании других коллекций
     */
    public function actionAddToRepetitionKit()
    {
        $varSend = \Yii::$app->request->get();
        if (!isset($varSend['dicEntryId']) || !isset($varSend['timestampStar'])) {
            return "";
        }

        $dicEntryId = (int)$varSend['dicEntryId'];
        $timestampStar = (int)$varSend['timestampStar'];

        try {
            $markRepetition = date("Y/m/d H:i:s", $timestampStar);
            $kitRepetitionId = KitToStudyForm::createRepetitionKit($markRepetition);
            WordSelectToStudyForm::addDicEntryToKitSt($kitRepetitionId, $dicEntryId);
        } catch (\Exception $e) {
            $a = 1;
        }

        return "";
    }

    /**
     * Наполняет коллекцию для изучения словами с вариантами перевода
     *
     * @return string
     */
    public function actionIndex()
    {
        // модель формы поиска слов
        $modelWordSelect = new WordSelectToStudyForm($this->getKitId());

        // в модели сейчас специально задается параметр говорящий что найдено
        // одно слово. Сделано это по причине изменения формы выбора слова
        $modelWordSelect->selectedOneWord = "1";

        // проверка данных формы
        if ($this->isFormNotValid($modelWordSelect)) {
            // если слов не найдено или произошла ошибка выполнения
            return $this->render("select-word-to-study", ["model" => $modelWordSelect]);
        }

        // проверка количества найденных слов
        //если слов не найдено совсем, ошибка уже сформирована на уровне проверки данных
        if ($modelWordSelect->getWordsFoundCount() > 1) {
            // выводится форма выбора одного слова из найденных
            return $this->render("select-word-to-study-from-list", ["model" => $modelWordSelect]);
        }

        // на этом этапе гарантированно найдено одно слово. Ситуации 0 и > 1 отсечены выше

        // подключить модель для отрисовки формы выбора списка переводов выбранного слова
        $modelWordCouple = $this->getModelWordCouple($modelWordSelect);


        // выводится форма выбора переводов для выбранного слова
        return $this->render("select-word-couple", [
                "model" => $modelWordCouple,
                "wordSpelling" => $modelWordSelect->getSpellingFindWord()
            ]
        );
    }

    /**
     * Формирует словарные статьи
     *
     * @return string|Response
     */
    public function actionSelectWordCouple()
    {
        // подключение модели по работе со словарными статьями
        $model = new WordCoupleSelectForm();

        // проверка данных формы
        if ($this->isFormNotValid($model)) {
            // если варианты перевода не переданы или произошла ошибка выполнения
            return $this->render("select-word-couple", [
                    "model" => $model,
                    "wordSpelling" => ''
                ]
            );
        }

        try {
            // добавить выбранные пары в словарную статью для заучивания
            $dicEntryId = $model->addWordCoupleToKit();

            // добавить словарную статью в коллекцию для заучивания
            $modelWordSelect = new WordSelectToStudyForm($model->kitId);
            $modelWordSelect->addDicEntryToKit($dicEntryId);

        } catch (\Exception $e) {
            $model->errorText = \Yii::$app->params['error']['site'];
            // echo "<pre>"; var_dump($e); exit;
            return $this->render("select-word-couple", [
                    "model" => $model,
                    "wordSpelling" => ''
                ]
            );
        }

        // словарная статья успешно добавлена
        // переадресация на страницу добавления слов к изучению
        return $this->redirect("/kit-fill?kitId={$model->kitId}", 302);
    }


    /**
     * Создает новую коллекцию слов для заучивания
     * т.е. список слов с выбранными вариантами перевода
     *
     * @return string|Response
     */
    public function actionCreateStudyKit()
    {
        $model = new KitToStudyForm();
        $model->scenario = KitToStudyForm::SCENARIO_CREATE;

        // проверка данных формы
        if ($this->isFormNotValid($model)) {
            // форма заполнена с ошибкой
            return $this->render("kit-to-study", ["model" => $model]);
        }

        // сохраняет новую словарную статью
        try {
            $newKitId = $model->createNewKit();
        } catch (\Exception $e) {
            $model->errorText = \Yii::$app->params['error']['site'];
            return $this->render("kit-to-study", ["model" => $model]);
        }

        // переадресует на форму наполнения новой словарной статьи словами
        // переадресация пользователя на главную страницу
        return $this->redirect("/kit-fill?kitId=$newKitId", 302);
    }

    /**
     * Удаляет коллекцию слов
     */
    public function actionDeleteStudyKit()
    {
        $model = new KitToStudyForm();
        $model->scenario = KitToStudyForm::SCENARIO_DELETE;

        // проверка данных формы
        // возможна только ошибка связанная с не санкционированным
        // доступом. Она приведет к переодресации на главную страницу
        // из валидатора модели
        $this->isFormNotValid($model);

        try {
            $model->deleteKit();
        } catch (\Exception $e) {
            // подключить модель для отображения списка коллекций
            $model = new StudyKit();
            $model->errorText = \Yii::$app->params['error']['site'];

            return $this->render("../study/study-kit-list", ["model" => $model]);
        }

        // операция выполнена успешно
        // переадресовать на сраницу со списком коллекций
        return $this->redirect("/study-kit", 302);
    }


    /**
     * Удаляет из коллекции для изучения словарную статью
     */
    public function actionDeleteWordFromKit()
    {
        $model = new WordDeleteFromKit();
        $errorText = \Yii::$app->params['error']['site'];

        // проверка данных формы
        if ($this->isFormNotValid($model)) {
            // вернуться к форме выбора слов для изучения
            $errorText = "Непредвиденная ошибка. Попробуйте снова";
            if (isset($model->kitId)) {
                $kitId = (int)$model->kitId;
                return $this->renderWordsToStudy($kitId, $errorText);
            }

            // попытка не санкционированного доступа
            return $this->redirect("/", 302);
        }

        try {
            $model->delDicEntryFromStudy();
            $errorText = null;
        } catch (\Exception $e) {
            // ни каких действий не предпринимается
            // будет отрисована форма выбора слов с ошибкой
        }

        return $this->renderWordsToStudy($model->kitId, $errorText);
    }

    /**
     * Возвращает найденные по первым буквам слова для элемента
     * управления на
     * view/edit-study/_form-word-select
     *
     * @return array
     */
    public function actionSelectWord()
    {
        \Yii::$app->response->format = \yii\web\Response::FORMAT_JSON;
        $wordList = [];
        $get = \Yii::$app->request->get();
        if (isset($get['word']) && $get['word'] != "") {
            $firstLettersWord = $get['word'];
        } else {
            return [];
        }

        $languageId = 2;
        if (isset($get['language'])) {
            $languageId = (int)$get['language'];
        }

        $wordList = WordList::getWordsFoundByFirstLetters($firstLettersWord, $languageId);

        $response = []; // на случай отсутствия вариантов слова
        if (isset($get['id'])) {
            // в ответ нужно добавить id слов
            $i = 0;
            foreach ($wordList as $word) {
                $response[$i]['word_spelling'] = Html::encode($word['word_spelling']);
                $response[$i]['word_id'] = $word['word_id'];
                $i++;
            }
        } else {
            foreach ($wordList as $word) {
                $response[] = Html::encode($word['word_spelling']);
            }
        }


        return $response;
    }

    /**
     * Отрисовывает форму выбора слов для заучвания
     *
     * @param $kitId
     * @param $errorText
     * @return string
     */
    private function renderWordsToStudy($kitId, $errorText)
    {
        $model = new WordSelectToStudyForm($kitId);

        $model->errorText = $errorText;

        return $this->render("select-word-to-study", ["model" => $model]);
    }

    /**
     * активирует модель для отрисовки выбора вариантов перевода
     *
     * @param $modelWordSelect
     * @return WordCoupleSelectForm
     */
    private function getModelWordCouple($modelWordSelect)
    {
        /** @var $modelWordSelect WordSelectToStudyForm */

        $wordToStudyId = $modelWordSelect->getWordToStudyId();

        $modelWordCouple = new WordCoupleSelectForm();
        $modelWordCouple->wordForStudyId = $wordToStudyId;
        $modelWordCouple->kitId = $modelWordSelect->kitId;

        return $modelWordCouple;
    }

    private function isFormNotValid($model)
    {
        return !($model->load(\Yii::$app->request->post()) && $model->validate());
    }

    /**
     * возвращает переданный через GET id формируемой коллекции слов для изусения
     * если id коллекции не передан, происходит редирект на главную страницу
     *
     * @return int|void
     */
    private function getKitId()
    {
        $getVar = \Yii::$app->request->get();
        if (isset($getVar['kitId'])) {
            return (int)$getVar['kitId'];
        }

        // обязательная переменная не передана
        \Yii::$app->response->redirect('/', 302)->send();
    }

}