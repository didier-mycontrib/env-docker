cd /d "%~dp0"
set POSTGRES_HOME=C:\Program Files\PostgreSQL\9.6
"%POSTGRES_HOME%/bin/psql" -U postgres -f customers-db.sql
pause