from django.contrib.auth.models import User
from django.db import models
from model_utils.models import StatusField, TimeStampedModel, UUIDField


class TimeStampedImageModel(TimeStampedModel):
    pass


class Image(TimeStampedModel):
    label = models.CharField(max_length=200)
    model = models.ForeignKey(TimeStampedImageModel, on_delete=models.CASCADE)
    image = models.ImageField(upload_to='images/')
    default = models.BooleanField(default=False)
    width = models.FloatField(default=300)
    length = models.FloatField(default=300)

    def __str__(self):
        return self.label


class Post(TimeStampedImageModel):
    class Status(models.TextChoices):
        INITIALIZED = 'init', 'initialized'
        PUBLISHED = 'pub', 'published'
        ARCHIVED = 'arch', 'archived'

    STATUS = Status.choices

    title = models.CharField(max_length=200)
    description = models.CharField(max_length=500)
    text = models.TextField()
    status = StatusField()
    author = models.ForeignKey(User, on_delete=models.CASCADE)

    def __str__(self):
        return "%s : %s" % (self.id, self.title)


class Tag(models.Model):
    label = models.CharField(max_length=50)
    post = models.ManyToManyRel(Post, to='Post', related_name='tags')

    def __str__(self):
        return self.label
