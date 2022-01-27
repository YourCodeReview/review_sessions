from django.db import models
from django.db.models import TextChoices
from django.contrib.auth import get_user_model
from django.utils import timezone


class Question(models.Model):
    class TYPE(TextChoices):
        TEXT = 'Текстом'
        ONE_CHOICE = 'Выбрать только один вариант'
        MANY_CHOICE = 'Выбрать много вариантов'

    text = models.CharField(max_length=255)
    type = models.CharField(max_length=50, choices=TYPE.choices, default=TYPE.ONE_CHOICE)
    poll = models.ForeignKey('Poll', on_delete=models.PROTECT)
    visible = models.BooleanField(default=True)

    def __str__(self):
        return self.text


class Poll(models.Model):
    title = models.CharField(max_length=256)
    visible = models.BooleanField(default=True)
    start_date = models.DateField()
    end_date = models.DateField()
    description = models.TextField()

    def __str__(self):
        return self.title


class Choice(models.Model):
    question = models.ForeignKey('Question', related_name='question_choice', on_delete=models.PROTECT)
    text = models.CharField(max_length=255)

    def __str__(self):
        return self.text


class Answer(models.Model):
    user = models.ForeignKey(get_user_model(), on_delete=models.PROTECT)
    question = models.ForeignKey('Question', on_delete=models.PROTECT)
    choice = models.ForeignKey('Choice', on_delete=models.PROTECT)
    statistic = models.ForeignKey('Statistic', related_name='answers', on_delete=models.PROTECT, null=True, blank=True)
    created = models.DateField(auto_now_add=True)

    def __str__(self):
        return self.question.text


class Statistic(models.Model):
    poll = models.ForeignKey(Poll, on_delete=models.CASCADE)
    user = models.ForeignKey(get_user_model(), on_delete=models.CASCADE, blank=True, null=True)
    date = models.DateTimeField(default=timezone.now(), editable=False)
