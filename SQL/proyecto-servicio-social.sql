CREATE TYPE tiposUsuarios AS ENUM ('normal', 'administrador');
CREATE TYPE carreras AS ENUM ('matemáticas', 'ciencias de la computación',
    'física', 'actuaría', 'biología', 'ciencias de la tierra',
    'ciencias ambientales', 'física biomédica',
    'manejo sustentable de zonas costeras', 'matemáticas aplicadas', 'neurociencias'
);

CREATE TABLE Usuario (
    idUsuario SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(80) UNIQUE NOT NULL,
    tipo tiposUsuarios NOT NULL,
    carrera carreras NOT NULL,
    añoIngreso INTEGER NOT NULL,
    contraseña VARCHAR
);

-- Para agregar un cometario, se necesita el usuario que lo agrega, el 
-- Tema/Pregunta al que responde de donde se deriva: su categoria.
CREATE TABLE Comentario (	
	idComentario SERIAL PRIMARY KEY,
	comentario VARCHAR(255) NOT NULL,
	usuario VARCHAR(50) NOT NULL,
	fechaPublicacion DATE NOT NULL
);
