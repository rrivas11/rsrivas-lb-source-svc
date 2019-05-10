package com.loyalty.source.lbsourcesvc.pojo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "favorito")
public class Favorito {

    @Id
    @Column(name = "fav_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idfav;

    @Column(name = "fav_idcliente")
    private int idcliente;
    @Column(name = "fav_idbeneficiario")
    private int idbene;
    @Column(name = "fav_idproducto")
    private int idprod;
    @Column(name = "fav_fecha")
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fav_idcliente", referencedColumnName ="cl_idcliente", nullable = false, insertable = false, updatable = false )
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fav_idbeneficiario", referencedColumnName ="cl_idcliente", nullable = false, insertable = false, updatable = false )
    private Cliente Beneficiario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fav_idproducto", referencedColumnName ="prd_idproducto", nullable = false, insertable = false, updatable = false )
    private Producto productoBen;


    public int getIdfav() {
        return idfav;
    }

    public void setIdfav(int idfav) {
        this.idfav = idfav;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public int getIdbene() {
        return idbene;
    }

    public void setIdbene(int idbene) {
        this.idbene = idbene;
    }

    public int getIdprod() {
        return idprod;
    }

    public void setIdprod(int idprod) {
        this.idprod = idprod;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
