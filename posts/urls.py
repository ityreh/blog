from django.urls import path

from . import views

urlpatterns = [
    # /posts/
    path('', views.list, name='list'),
    # /posts/<id>/
    path('<int:post_id>/', views.read, name='read'),
]
