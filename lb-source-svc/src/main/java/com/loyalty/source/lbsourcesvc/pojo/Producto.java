package com.loyalty.source.lbsourcesvc.pojo;

import javax.persistence.*;

@Entity(name = "producto")
public class Producto {

    @Id
    @Column(name = "prd_idcuenta")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idcuenta;

    @Column(name = "prd_idtipoproducto")
    private int idtipoCuenta;

    @Column(name = "prd_idpropietario")
    private int idpropietario;

    @Column(name = "prd_nombre")
    private String nombre;

    @Column(name = "prd_saldo")
    private Double saldo;

    @Column(name = "prd_monto")
    private Double monto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prd_idpropietario", referencedColumnName ="cl_idcliente", nullable = false, insertable = false, updatable = false )
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prd_idtipoproducto", referencedColumnName ="tipo_id", nullable = false, insertable = false, updatable = false )
    private TipoProducto tipoProducto;


    public int getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(int idcuenta) {
        this.idcuenta = idcuenta;
    }

    public int getIdtipoCuenta() {
        return idtipoCuenta;
    }

    public void setIdtipoCuenta(int idtipoCuenta) {
        this.idtipoCuenta = idtipoCuenta;
    }

    public int getIdpropietario() {
        return idpropietario;
    }

    public void setIdpropietario(int idpropietario) {
        this.idpropietario = idpropietario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
