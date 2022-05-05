from django.http import HttpResponse
from django.shortcuts import render

from posts.models import Post


def list(request):
    latest_post_list = Post.objects.order_by('-created')[:10]
    context = {'latest_question_list': latest_post_list}
    return render(request, 'posts/list.html', context)


def read(request, post_id):
    return HttpResponse("You're looking at post %s." % post_id)
