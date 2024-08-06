CREATE TABLE Agentes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(255)
);

CREATE TABLE Clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(255)
);

CREATE TABLE Propiedades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(255) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    descripcion TEXT,
    id_agente INT,
    FOREIGN KEY (id_agente) REFERENCES Agentes(id)
);