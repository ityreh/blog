from django.http import HttpResponse

from posts.models import Post


def list(request):
    latest_post_list = Post.objects.order_by('-pub_date')[:10]
    output = ', '.join([p.post_title for p in latest_post_list])
    return HttpResponse(output)

def read(request, post_id):
    return HttpResponse("You're looking at post %s." % post_id)
