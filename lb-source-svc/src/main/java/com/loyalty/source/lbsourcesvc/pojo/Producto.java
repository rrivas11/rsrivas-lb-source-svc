package com.loyalty.source.lbsourcesvc.pojo;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "producto")
public class Producto {

    @Id
    @Column(name = "prd_idproducto")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idproducto;

    @Column(name = "prd_idtipoproducto")
    private int idtipoProducto;

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

    @OneToMany(mappedBy = "productoBen")
    @JsonIgnore
    private List<Favorito> prodBen ;

    @OneToMany(mappedBy = "origen")
    @JsonIgnore
    private List<Transaccion> transaccionsOrigen ;

    @OneToMany(mappedBy = "destino")
    @JsonIgnore
    private List<Transaccion> transaccionsDestino ;

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public int getIdtipoProducto() {
        return idtipoProducto;
    }

    public void setIdtipoProducto(int idtipoProducto) {
        this.idtipoProducto = idtipoProducto;
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
