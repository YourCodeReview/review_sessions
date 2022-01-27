from rest_framework import serializers
from poll.models import Answer, Poll, Question, Choice, Statistic
import datetime


class ChoiceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Choice
        fields = ('id', 'text', 'question')


class QuestionSerializer(serializers.ModelSerializer):
    choices = ChoiceSerializer(many=True, required=False)

    class Meta:
        model = Question
        fields = ('id', 'poll', 'text', 'type', 'choices')
        read_only_fields = ('id',)
        extra_kwargs = {
            'poll': {'write_only': True},
            'choices': {'read_only': True}
        }


class PollSerializer(serializers.ModelSerializer):
    class Meta:
        model = Poll
        fields = ('id', 'title', 'start_date', 'end_date', 'description')


class AnswerSerializer(serializers.ModelSerializer):
    choice = serializers.PrimaryKeyRelatedField(queryset=Choice.objects.all(), write_only=True)
    question = serializers.PrimaryKeyRelatedField(queryset=Question.objects.all(), write_only=True)

    class Meta:
        model = Answer
        fields = ('id', 'user', 'question', 'choice')
        extra_kwargs = {
            'user': {'write_only': True},
        }

    def to_representation(self, value):
        data = super().to_representation(value)
        questions = QuestionSerializer(value.question)
        choices = ChoiceSerializer(value.choice)

        data['question'] = questions.data
        data['choice'] = choices.data
        return data


class StatisticForUserSerializer(serializers.ModelSerializer):
    answers = serializers.SerializerMethodField()
    poll = serializers.PrimaryKeyRelatedField(
        queryset=Poll.objects.all(),
        write_only=True
    )

    class Meta:
        model = Statistic
        fields = ('id', 'poll', 'user', 'date', 'answers')
        read_only_fields = ('id', 'user', 'date', 'answers')

    @staticmethod
    def get_answers(self):
        instance = Answer.objects.filter(user=self.id)
        serializer = AnswerSerializer(instance=instance, many=True)
        return serializer.data


class StatisticForAdminSerializer(serializers.ModelSerializer):
    answers = serializers.SerializerMethodField()
    poll = serializers.PrimaryKeyRelatedField(
        queryset=Poll.objects.all(),
        write_only=True
    )

    class Meta:
        model = Statistic
        fields = ('id', 'poll', 'user', 'date', 'answers')
        read_only_fields = ('id', 'date', 'answers')

    @staticmethod
    def get_answers(self):
        instance = Answer.objects.filter(user=self.id)
        serializer = AnswerSerializer(instance=instance, many=True)
        return serializer.data