from rest_framework import routers
from poll.views import PollViewSet, QuestionViewSet, AnswerViewSet, ChoiceViewSet, StatisticForAdminViewSet, StatisticForUserViewSet

router = routers.SimpleRouter()

router.register(r'poll', PollViewSet, 'poll')
router.register(r'question', QuestionViewSet, 'question')
router.register(r'answer', AnswerViewSet, 'answer')
router.register(r'choice', ChoiceViewSet, 'choice')
router.register(r'statistic-user', StatisticForUserViewSet, 'statistic-user')
router.register(r'statistic-admin', StatisticForAdminViewSet, 'statistic-admin')

urlpatterns = router.urls
