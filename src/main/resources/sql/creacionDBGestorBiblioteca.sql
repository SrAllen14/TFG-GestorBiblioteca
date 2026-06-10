CREATE DATABASE IF NOT EXISTS DBGestorBiblioteca;

USE DBGestorBiblioteca;

CREATE USER "DBUserGestorBiblioteca"@"%" IDENTIFIED BY "paso";

GRANT ALL PRIVILEGES ON DBGestorBiblioteca.* TO "DBUserGestorBiblioteca"@"%";