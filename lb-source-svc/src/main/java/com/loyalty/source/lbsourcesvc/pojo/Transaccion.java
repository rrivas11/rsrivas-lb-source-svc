package com.loyalty.source.lbsourcesvc.pojo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "transaccion")
public class Transaccion {

    @Id
    @Column(name = "tra_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int tra_id;
    @Column(name = "tra_id_origen")
    private  String id_origen;
    @Column(name = "tra_id_destino")
    private  String id_destino;
    @Column(name = "tra_descripcion")
    private  String descripcion;
    @Column(name = "tra_monto")
    private  Double monto;
    @Column(name = "tra_fecha")
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tra_id_origen", referencedColumnName ="prd_idproducto", nullable = false, insertable = false, updatable = false )
    private Producto origen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tra_id_destino", referencedColumnName ="prd_idproducto", nullable = false, insertable = false, updatable = false )
    private Producto destino;


    public int getTra_id() {
        return tra_id;
    }

    public void setTra_id(int tra_id) {
        this.tra_id = tra_id;
    }

    public String getId_origen() {
        return id_origen;
    }

    public void setId_origen(String id_origen) {
        this.id_origen = id_origen;
    }

    public String getId_destino() {
        return id_destino;
    }

    public void setId_destino(String id_destino) {
        this.id_destino = id_destino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Producto getOrigen() {
        return origen;
    }

    public void setOrigen(Producto origen) {
        this.origen = origen;
    }

    public Producto getDestino() {
        return destino;
    }

    public void setDestino(Producto destino) {
        this.destino = destino;
    }

}
