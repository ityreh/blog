# blog

A highly configurable blog platform implemented on Django.

## Installation

TODO:

## Usage

TODO:

## Support

If you have any problems using this tool or feature reqeusts, please feel free to [open an issue](https://github.com/ityreh/ecom/issues/new).

## Roadmap

### MVP

- [ ] todo

## Contributing

You are welcome to contribute and make pull requests. If you want to introduce a new bigger feature you can [open an issue](https://github.com/ityreh/blog/issues/new) to discuss it.

### Install requirements locally

Install the required dependencies for the project:

    pip install -r requirements/local.txt

### Run locally

You can run the application locally and access it under `localhost:8000`:

    python manage.py runserver --settings=config.settings.local

Make sure you have a DB instance running locally and well configured.

### Development

Write project dependencies to `requirements.txt`:

    pip freeze > requirements.txt

Add a new app:

    python manage.py startapp <app_name>

Create DB migrations for new models:

    python manage.py makemigrations <app_name> --settings=config.settings.local

Migrate model changes:

    python manage.py migrate --settings=config.settings.local

Bring migrations to heel:

    python manage.py squashmigrations <app_name> <migrations_index> --settings=config.settings.local

Create an admin user:

    python manage.py createsuperuser --settings=config.settings.local

Collect media files:

    python manage.py collectstatic --settings=config.settings.local

## Authors and acknowledgement

- [Ityreh](https://github.com/ityreh)

## License

[GPL-3.0-or-later](./LICENSE)

## Project status

Working on MVP.
