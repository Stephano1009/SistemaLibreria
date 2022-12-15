USE MASTER
GO

IF(DB_ID('ROSVIL')IS NOT NULL)
DROP DATABASE ROSVIL
CREATE DATABASE ROSVIL
GO

USE ROSVIL
GO

CREATE TABLE categoria
(
id_categoria		INT IDENTITY(1,1)	NOT NULL,
nombre_categoria	VARCHAR(10)			NOT NULL,
estado				BIT					NOT NULL	
)
GO

ALTER TABLE categoria
	ADD CONSTRAINT pk_categoria PRIMARY KEY(id_categoria)
GO

ALTER TABLE categoria
	ADD CONSTRAINT uc_categoria_nombre UNIQUE(nombre_categoria)
GO

CREATE TABLE cargo
(
id_cargo			INT IDENTITY(1,1)	NOT NULL,
nombre_cargo		VARCHAR(20)			NOT NULL,
estado				BIT					NOT NULL
)
GO

ALTER TABLE cargo
	ADD CONSTRAINT pk_cargo PRIMARY KEY(id_cargo)
GO


CREATE TABLE proveedor
(
id_proveedor		INT IDENTITY (1,1)	NOT NULL,
ruc					VARCHAR(11)			NOT NULL,
nombre_proveedor	VARCHAR(20)			NOT NULL, 
telefono_proveedor	CHAR(9)				NOT NULL,
estado				BIT					NOT NULL,
)
GO

ALTER TABLE proveedor
	ADD CONSTRAINT pk_proveedor PRIMARY KEY(id_proveedor)
GO

CREATE TABLE producto
(
id_producto				INT IDENTITY (1,1)	NOT NULL,
nombre_producto			VARCHAR(12)			NOT NULL,
descripcion_producto	VARCHAR(500)		NOT NULL,
contenido				VARCHAR(20)			NOT NULL,
precio					DECIMAL (10,2)		NOT NULL,
estado					BIT					NOT NULL,
stock					INT					NOT NULL,
id_categoria			INT					,
id_proveedor			INT					,		
)
GO

ALTER TABLE producto
	ADD CONSTRAINT pk_producto PRIMARY KEY(id_producto)
GO

ALTER TABLE producto
	ADD CONSTRAINT fk_producto_categoria FOREIGN KEY(id_categoria) REFERENCES categoria
GO

ALTER TABLE producto
	ADD CONSTRAINT fk_producto_proveedor FOREIGN KEY(id_proveedor) REFERENCES proveedor
GO


CREATE TABLE usuario
(
id_usuario				INT IDENTITY(1,1)	NOT NULL,
usuario					VARCHAR (50)		NOT NULL,
clave					VARCHAR (20)		NOT NULL,
estado					BIT					NOT NULL,
id_cargo				INT					,
)
GO

ALTER TABLE usuario
	ADD CONSTRAINT pk_usuario PRIMARY KEY(id_usuario)
GO

ALTER TABLE usuario
	ADD CONSTRAINT fk_usuario_cargo FOREIGN KEY(id_cargo) REFERENCES cargo
GO

CREATE TABLE empleado
(
id_empleado				INT IDENTITY (1,1)		NOT NULL,
nombre_empleado			VARCHAR(20)				NOT NULL,
apellido_empleado		VARCHAR (20)			NOT NULL,
dni						CHAR(8)					NOT NULL,
direccion				VARCHAR(50)				NOT NULL,
telefono				CHAR(9)					NOT NULL,
estado					BIT						NOT NULL,
correo					VARCHAR(50)				NOT NULL,
id_usuario				INT						,
)
GO

ALTER TABLE empleado
	ADD CONSTRAINT PK_EMPLEADOS PRIMARY KEY(id_empleado)
GO

ALTER TABLE empleado
	ADD CONSTRAINT fk_empleados_usuario FOREIGN KEY(id_usuario) REFERENCES usuario
GO

CREATE TABLE cliente
(
id_cliente				INT IDENTITY(1,1)	NOT NULL,
nombre_cliente			VARCHAR (100)		NOT NULL,
apellido_cliente		VARCHAR (100)		NOT NULL,
numerodoc				CHAR(8)				NOT NULL,
direccion_cliente		VARCHAR(500)		NOT NULL
)
GO

ALTER TABLE cliente
	ADD CONSTRAINT pk_clientes PRIMARY KEY(id_cliente)
GO


CREATE TABLE tipo_pago
(
id_tipo_pago			INT IDENTITY(1,1)	NOT NULL,
nombre_tipo_pago		VARCHAR(11)			NOT NULL,
estado					BIT					NOT NULL,
)
GO

ALTER TABLE tipo_pago
	ADD CONSTRAINT pk_tipo_pago PRIMARY KEY(id_tipo_pago)
GO

CREATE TABLE venta
(
id_venta				INT IDENTITY (1,1)	NOT NULL,
serie					VARCHAR(20)			NOT NULL,
numero					SMALLINT			NOT NULL,
tipo_documento			CHAR(1)				NOT NULL,
id_empleado				INT					,
id_cliente				INT					,
id_tipo_pago			INT					,
fecha					DATE				NOT NULL,
estado					bit					NOT NULL
)
GO

ALTER TABLE venta
	ADD CONSTRAINT pk_venta PRIMARY KEY(id_venta)
GO

ALTER TABLE venta
	ADD CONSTRAINT fk_venta_empleado FOREIGN KEY(id_empleado) REFERENCES empleado
GO

ALTER TABLE venta
	ADD CONSTRAINT fk_venta_cliente FOREIGN KEY(id_cliente) REFERENCES cliente
GO

ALTER TABLE venta
	ADD CONSTRAINT fk_venta_tipo_pago FOREIGN KEY(id_tipo_pago) REFERENCES tipo_pago
GO


CREATE TABLE detalle_venta
(
id_detalleventa			INT IDENTITY(1,1)		NOT NULL,
id_venta				INT						, 
id_producto				INT						,
cantidad				TINYINT,
precioventa				MONEY,
)
GO 

ALTER TABLE detalle_venta
	ADD CONSTRAINT pk_detalle_venta PRIMARY KEY(id_detalleventa)
GO

ALTER TABLE detalle_venta
	ADD CONSTRAINT fk_detalle_venta_venta FOREIGN KEY(id_venta) REFERENCES venta
GO

ALTER TABLE detalle_venta
	ADD CONSTRAINT fk_detalle_venta_producto FOREIGN KEY(id_producto) REFERENCES producto
GO

/*SELECT TOP 1 NUMERO FROM VENTAS WHERE TIPO_DOCUMENTO = 'B' ORDER BY ID_VENTA DESC;

select * from ventas;
SELECT * FROM DETALLE_VENTA;
SELECT * FROM PRODUCTOS
EXEC SP_COLUMNS PRODUCTOS

ALTER TABLE PRODUCTOS ALTER COLUMN DESCRIPCION_PRODUCTO VARCHAR(100) NOT NULL;

UPDATE VENTAS SET ESTADO = '1' WHERE ID_VENTA = 2;

ALTER TABLE VENTAS ADD ESTADO BIT NULL

select p.ID_PRODUCTO, p.NOMBRE_PRODUCTO, P.STOCK, P.ESTADO, P.DESCRIPCION_PRODUCTO,P.CONTENIDO, P.PRECIO,
c.NOMBRE_CATEGORIA , PRO.NOMBRE_PROVEEDOR From Productos P INNER JOIN CATEGORIAS C on p.ID_CATEGORIA = c.ID_CATEGORIA INNER JOIN PROVEEDORES PRO 
ON PRO.ID_PROVEEDOR = P.ID_PROVEEDOR 
order by p.NOMBRE_PRODUCTO;

delete from ventas where ID_VENTA = 2

select * from EMPLEADOS*/

