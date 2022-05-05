from core.utils import get_env_variable
from django.contrib import admin
from django.urls import include, path

ADMIN_PATH = get_env_variable('URLS_ADMIN_PATH')

urlpatterns = [
    path('', include('posts.urls')),
    path('posts/', include('posts.urls')),
    path(ADMIN_PATH, admin.site.urls),
    path('grappelli/', include('grappelli.urls')),
    #path('admin/', include('admin_honeypot.urls', namespace='admin_honeypot')),
]
