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
    private  int id_origen;
    @Column(name = "tra_id_destino")
    private  int id_destino;
    @Column(name = "tra_id_tipo")
    private  int id_tipo;
    @Column(name = "tra_monto")
    private  int monto;
    @Column(name = "tra_fecha")
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tra_id_origen", referencedColumnName ="prd_idproducto", nullable = false, insertable = false, updatable = false )
    private Producto origen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tra_id_destino", referencedColumnName ="prd_idproducto", nullable = false, insertable = false, updatable = false )
    private Producto destino;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tra_id_tipo", referencedColumnName ="tip_id", nullable = false, insertable = false, updatable = false )
    private TipoTransaccion tipoTransaccion;

    public int getTra_id() {
        return tra_id;
    }

    public void setTra_id(int tra_id) {
        this.tra_id = tra_id;
    }

    public int getId_origen() {
        return id_origen;
    }

    public void setId_origen(int id_origen) {
        this.id_origen = id_origen;
    }

    public int getId_destino() {
        return id_destino;
    }

    public void setId_destino(int id_destino) {
        this.id_destino = id_destino;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
