package com.lifebank.source.lbsourcesvc.pojo.database;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "producto")
public class Producto {

    @Id
    @Column(name = "prd_idproducto")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String idproducto;

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

    @Column(name = "prd_tasa")
    private Double tasa;

    @Column(name = "prd_corte")
    private int corte;

    @Column(name = "prd_interes_acumulado")
    private Double interesAcumulado;

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

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public List<Favorito> getProdBen() {
        return prodBen;
    }

    public void setProdBen(List<Favorito> prodBen) {
        this.prodBen = prodBen;
    }

    public List<Transaccion> getTransaccionsOrigen() {
        return transaccionsOrigen;
    }

    public void setTransaccionsOrigen(List<Transaccion> transaccionsOrigen) {
        this.transaccionsOrigen = transaccionsOrigen;
    }

    public List<Transaccion> getTransaccionsDestino() {
        return transaccionsDestino;
    }

    public void setTransaccionsDestino(List<Transaccion> transaccionsDestino) {
        this.transaccionsDestino = transaccionsDestino;
    }

    public Double getTasa() {
        return tasa;
    }

    public void setTasa(Double tasa) {
        this.tasa = tasa;
    }

    public int getCorte() {
        return corte;
    }

    public void setCorte(int corte) {
        this.corte = corte;
    }

    public Double getInteresAcumulado() {
        return interesAcumulado;
    }

    public void setInteresAcumulado(Double interesAcumulado) {
        this.interesAcumulado = interesAcumulado;
    }
}
