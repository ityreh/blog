from .base import *

DEBUG = True

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.postgresql',
        'NAME': get_env_variable('DATABASE_DEFAULT_NAME'),
        'USER': get_env_variable('DATABASE_DEFAULT_USER'),
        'PASSWORD': get_env_variable('DATABASE_DEFAULT_PASSWORD'),
        'HOST': get_env_variable('DATABASE_DEFAULT_HOST'),
        'PORT': get_env_variable('DATABASE_DEFAULT_PORT'),
    }
}
