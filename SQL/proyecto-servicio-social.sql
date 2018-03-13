--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE TYPE disponible AS ENUM ('abierto', 'cerrado');

--creacion de tablas para temas y pregutas 
create table tema/pregunta (
  idTema INT PRIMARY KEY NOT NULL,
  fechaPublicacion CHAR(50) NOT NULL.
  disponibilidad disponible NOT NULL,
  Tema/Pregunta char(50) NOT NULL,
 );




