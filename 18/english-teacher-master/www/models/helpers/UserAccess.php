<?php
// Хелпер сделан для тестирования прав доступа пользователя
// к операциям на сайте

namespace app\models\helpers;

use app\models\db\StudyKitList;

class UserAccess
{
    /**
     * Переадресует пользователя на главную страницу
     * если коллекция слов $kitId пользователю не принадлежит
     *
     * @param $kitId
     * @return bool
     */
    public static function redirectIfKitNotUser($kitId)
    {
        $userId = \Yii::$app->user->id;

        if (is_null($userId) || StudyKitList::isNotFindKitUser($userId, $kitId)) {
            // коллекция не принадлежит текущему пользователю
            \Yii::$app->response->redirect('/', 302)->send();
        }

        return true;
    }
}
