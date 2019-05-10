-- Drop table

-- DROP TABLE lm_lifebank.cliente;

CREATE TABLE lm_lifebank.cliente (
	cl_idcliente int4 NOT NULL,
	cl_nombre bpchar(50) NULL,
	cl_apellido bpchar(50) NULL,
	cl_correo bpchar(20) NULL,
	cl_usuario bpchar(20) NULL,
	cl_pass bpchar(20) NULL,
	CONSTRAINT pk_cliente PRIMARY KEY (cl_idcliente)
);

-- Drop table

-- DROP TABLE lm_lifebank.producto;

CREATE TABLE lm_lifebank.producto (
	prd_idcuenta int4 NOT NULL,
	prd_idtipoproducto int4 NOT NULL,
	prd_idpropietario int4 NOT NULL,
	prd_nombre bpchar(50) NULL,
	prd_saldo numeric(8,2) NOT NULL,
	prd_monto numeric(8,2) NULL,
	CONSTRAINT pk_cuentas PRIMARY KEY (prd_idcuenta),
	CONSTRAINT fk_cliente_producto FOREIGN KEY (prd_idtipoproducto) REFERENCES cliente(cl_idcliente),
	CONSTRAINT fk_tipoproductoe_producto FOREIGN KEY (prd_idpropietario) REFERENCES tipoproducto(tipo_id)
);

-- Drop table

-- DROP TABLE lm_lifebank.tipoproducto;

CREATE TABLE lm_lifebank.tipoproducto (
	tipo_id int4 NOT NULL,
	tipo_nombre bpchar(50) NOT NULL,
	CONSTRAINT pk_tipoproducto PRIMARY KEY (tipo_id)
);
