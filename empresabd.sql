use master 
go 

if(DB_ID('empresa')is not null) 
drop database empresa
create database empresa
go

USE empresa 
GO

CREATE TABLE CATEGORIAS 
(
ID_CATEGORIA int identity (1,1)primary key,
NOMBRE_CATEGORIA VARCHAR(10),
ESTADO BIT NOT NULL,
)
GO

INSERT INTO  CATEGORIAS VALUES('matematica',1)
INSERT INTO  CATEGORIAS VALUES('historia',1)
INSERT INTO  CATEGORIAS VALUES('religion',1)
INSERT INTO  CATEGORIAS VALUES('economia',1)
INSERT INTO  CATEGORIAS VALUES('Cuadernos',1)


CREATE TABLE CARGO 
(
ID_CARGO int identity (1,1)primary key,
NOMBRE_CARGO VARCHAR(20),
ESTADO BIT NOT NULL,
)
GO

INSERT INTO CARGO VALUES('Administrador',1)
INSERT INTO CARGO VALUES('Vendedor',1)
INSERT INTO CARGO VALUES('Cajero',1)
GO

create table PROVEEDORES
(
ID_PROVEEDOR  int identity (1,1)primary key,
RUC varchar(11) not null,
NOMBRE_PROVEEDOR varchar(20), 
TELEFONO_PROVEEDOR int not null,
ESTADO BIT NOT NULl,
)
GO

INSERT INTO PROVEEDORES values ('10733414713','alfredo',952314578,1)
INSERT INTO PROVEEDORES values ('10733445863','kati',965451468,1)
INSERT INTO PROVEEDORES values ('10733126243','karla',974136574,0)
INSERT INTO PROVEEDORES values ('10734518913','juanita',964614523,1)

create table Productos
(
ID_PRODUCTO  int identity (1,1)primary key,
NOMBRE_PRODUCTO varchar(12) not null,
DESCRIPCION_PRODUCTO varchar(10) not null,
CONTENIDO varchar(20) not null,
PRECIO DECIMAL (10,2)not null,
ESTADO BIT,
STOCK int ,
ID_CATEGORIA int FOREIGN KEY REFERENCES CATEGORIAS,
ID_PROVEEDOR int FOREIGN KEY REFERENCES PROVEEDORES

)
GO

INSERT INTO Productos values ('Cuaderno','rayado',100,7.20,1,20, 5, 1)
INSERT INTO Productos values ('libro','C.T.A',200,12.00,2,5, 1, 1)
INSERT INTO Productos values ('lapiz','mongol',10,1.00,3,4, 1, 1)
INSERT INTO Productos values ('goma','artesco',200,3.50,4,10, 1,1)	

SELECT*FROM Productos

create table usuario
(
id_usuario int identity(1,1) primary key,
usuario varchar (50) not null,
clave varchar (20) not null,
estado bit,
id_cargo int
)
go

insert into usuario values('Administrador', 'admin123456', 1, 1)
insert into usuario values('Invitado', 'admin123456', 1, 2)
insert into usuario values('AlexanderDel', 'admin123456', 1, 1)
go

create table Empleados
(
ID_EMPLEADO  int identity (1,1)primary key,
NOMBRE_EMPLEADO varchar(20) ,
APELLIDO_EMPLEADO varchar (20) not null,
DNI char(8) not null,
DIRECCION varchar(50) not null,
TELEFONO char(9) not null,
ESTADO bit,
CORREO VARCHAR(50) not null,
ID_USUARIO int foreign key references usuario
)
go
INSERT INTO Empleados values ('Robert','zuares',12345678,'las palmeras 584',985475365, 1, 'user@gmail.com', 1)
INSERT INTO Empleados values ('juan','velasques',14785236,'los laureel',965321468,1, 'user@gmail.com', 2)
INSERT INTO Empleados values ('Marko','Polo',12345665,'villas del norte',965136574,1, 'user@gmail.com',2)
INSERT INTO Empleados values ('Eduardo','Martinez',12314785,'JLO',964614563,1, 'user@gmail.com',2)	


create table CLIENTES
(
ID_CLIENTE  int identity(1,1)primary key,
NOMBRE_CLIENTE varchar (20) not null,
APELLIDO_CLIENTE varchar (10) not null,
NUMERODOC char(8) not null,
DIRECCION_CLIENTE varchar(15) not null
)
go

INSERT INTO CLIENTES values ('Rober','casas',65138462,'arboleda 140')
INSERT INTO CLIENTES values ('juana','velas',16352456,'san jose')
INSERT INTO CLIENTES values ('Andrea','flores',96521478,'villas del sur')
INSERT INTO CLIENTES values ('Ed','Guerrero',36521486,'calle enchs')		


create table TIPO_PAGO
(
ID_TIPO_PAGO  int identity(1,1)primary key,
NOMBRE_TIPO_PAGO varchar(11) not null,
ESTADO bit,
)
GO
INSERT INTO TIPO_PAGO values ('visa',200,1)
INSERT INTO TIPO_PAGO values ('mastercard',100,1)
INSERT INTO TIPO_PAGO values ('YAPE',200,1)
INSERT INTO TIPO_PAGO values ('efectivo',50,1)	


create table VENTAS
(
ID_VENTA INT IDENTITY (1,1) PRIMARY KEY,
SERIE VARCHAR(20) NOT NULL,
NUMERO SMALLINT NOT NULL,
TIPO_DOCUMENTO CHAR(1) NOT NULL,
ID_EMPLEADO INT FOREIGN KEY REFERENCES EMPLEADOS,
ID_CLIENTE INT FOREIGN KEY REFERENCES CLIENTES,
TIPO_PAGO INT FOREIGN KEY REFERENCES TIPO_PAGO,
FECHA DATE NOT NULL
)
GO

CREATE TABLE DETALLE_VENTA
(
ID_DETALLEVENTA INT IDENTITY(1,1) PRIMARY KEY,
ID_VENTA INT FOREIGN KEY REFERENCES VENTAS, 
ID_PRODUCTO INT FOREIGN KEY REFERENCES PRODUCTOS,
CANTIDAD TINYINT,
PRECIOVENTA MONEY,
)
GO 


SELECT*FROM PROVEEDORES
SELECT*FROM CATEGORIAS
SELECT*FROM DETALLE_VENTA
SELECT*FROM VENTAS
SELECT*FROM CLIENTES
SELECT*FROM TIPO_PAGO
SELECT*FROM Empleados
SELECT* FROM usuario






