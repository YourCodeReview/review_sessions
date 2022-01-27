from rest_framework import viewsets
from poll.serializers import ChoiceSerializer, QuestionSerializer, PollSerializer, AnswerSerializer, \
    StatisticForUserSerializer, StatisticForAdminSerializer
from poll.models import Answer, Poll, Question, Choice, Statistic
from rest_framework import permissions


class PollViewSet(viewsets.ModelViewSet):
    queryset = Poll.objects.all()
    serializer_class = PollSerializer
    permission_classes = [permissions.IsAdminUser]


class QuestionViewSet(viewsets.ModelViewSet):
    queryset = Question.objects.all()
    serializer_class = QuestionSerializer
    permission_classes = [permissions.IsAdminUser]


class ChoiceViewSet(viewsets.ModelViewSet):
    queryset = Choice.objects.all()
    serializer_class = ChoiceSerializer
    permission_classes = [permissions.IsAdminUser]


class AnswerViewSet(viewsets.ModelViewSet):
    queryset = Answer.objects.all()
    serializer_class = AnswerSerializer
    permission_classes = [permissions.IsAuthenticated]


class StatisticForUserViewSet(viewsets.ModelViewSet):
    queryset = Statistic.objects.all()
    serializer_class = StatisticForUserSerializer
    permission_classes = [permissions.IsAuthenticated]

    def perform_create(self, serializer):
        if self.request.user.is_authenticated:
            return serializer.save(user=self.request.user)

        return super().perform_create(serializer)


class StatisticForAdminViewSet(viewsets.ModelViewSet):
    queryset = Statistic.objects.all()
    serializer_class = StatisticForAdminSerializer
    permission_classes = [permissions.IsAdminUser]

    def perform_create(self, serializer):
        if self.request.user.is_authenticated:
            return serializer.save(user=self.request.user)

        return super().perform_create(serializer)