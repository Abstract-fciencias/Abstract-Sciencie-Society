/*
    Abstract Science Society
*/

drop extension if exists pgcrypto;
create extension pgcrypto;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE TYPE disponible AS ENUM ('abierto', 'cerrado');
CREATE TYPE tiposUsuarios AS ENUM ('normal', 'administrador');
CREATE TYPE carreras AS ENUM ('matemáticas', 'ciencias de la computación',
    'física', 'actuaría', 'biología', 'ciencias de la tierra',
    'ciencias ambientales', 'física biomédica',
    'manejo sustentable de zonas costeras', 'matemáticas aplicadas',
    'neurociencias', 'trabajador'
);

CREATE TABLE Usuario (
    idUsuario SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    correo VARCHAR(80) UNIQUE NOT NULL,
    contraseña TEXT NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    carrera VARCHAR(100) NOT NULL,
    anioingreso VARCHAR(4) NOT NULL
);

comment on table Usuario
is
'El usuario USUARIO tiene la contraseña PASS después de aplicarle un hash';

create or replace function hash() returns trigger as $$
  begin
    if TG_OP = 'INSERT' then
       new.contraseña = crypt(new.contraseña, gen_salt('bf', 8)::text);
    end if;
    if TG_OP = 'UPDATE' then
       new.contraseña = crypt(new.contraseña, gen_salt('bf', 8)::text);
    end if;
    return new;
  end;
$$ language plpgsql;

comment on function hash()
is
'Cifra la contraseña del usuario al guardarla en la base de datos.';

create trigger cifra
before insert on Usuario
for each row execute procedure hash();


create or replace function login(correo VARCHAR(80), contraseñaN text) returns boolean as $$
  select exists(select 1
                  from Usuario
                 where correo = correo and
                       contraseña = crypt(contraseñaN, contraseña));
$$ language sql stable;

-- Para agregar un cometario, se necesita el usuario que lo agrega, el 
-- Tema/Pregunta al que responde de donde se deriva: su categoria.
CREATE TABLE Comentario (	
    idComentario SERIAL PRIMARY KEY,
    comentario VARCHAR(255) NOT NULL,
    idUsuario INTEGER,
    fechaPublicacion TIMESTAMP NOT NULL
);

create table Tema (
    idTema SERIAL PRIMARY KEY NOT NULL,
    Contenido VARCHAR(240),
    idUsuario INTEGER,
    fechaPublicacion TIMESTAMP NOT NULL,
    disponibilidad VARCHAR(30) NOT NULL,
    idCategoria INTEGER NOT NULL
);


ALTER TABLE Comentario ADD CONSTRAINT FKCOMENTARIO
FOREIGN KEY (idUsuario)
REFERENCES Usuario (idUsuario) ON DELETE CASCADE;

ALTER TABLE Comentario ADD COLUMN idTema INTEGER;

ALTER TABLE Comentario ADD CONSTRAINT FKCOMENTARIOIDTEMA
FOREIGN KEY (idTema)
REFERENCES Tema (idTema) ON DELETE CASCADE;

--creacion de tablas para temas y pregutas 
ALTER TABLE Tema ADD CONSTRAINT FKUSUARIO
FOREIGN KEY (idUsuario)
REFERENCES Usuario (idUsuario) ON DELETE CASCADE;

CREATE TABLE Categoria(
    idCategoria SERIAL PRIMARY KEY,		
    nombre VARCHAR(100) NOT NULL, 
    descripcion VARCHAR(255) NOT NULL 
);

ALTER TABLE Tema ADD CONSTRAINT FKCATEGORIA
FOREIGN KEY (idCategoria)
REFERENCES Categoria (idCategoria) ON DELETE CASCADE;

ALTER TABLE Usuario ADD COLUMN imagen boolean NOT NULL DEFAULT FALSE;

ALTER TABLE Usuario ADD COLUMN confirmado boolean NOT NULL DEFAULT FALSE;
