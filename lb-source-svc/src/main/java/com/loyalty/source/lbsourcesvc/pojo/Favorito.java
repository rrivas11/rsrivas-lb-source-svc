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
    private String idprod;
    @Column(name = "fav_fecha")
    private LocalDateTime fecha;
    @Column(name = "fav_correo")
    private String correo;

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

    public String getIdprod() {
        return idprod;
    }

    public void setIdprod(String idprod) {
        this.idprod = idprod;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getBeneficiario() {
        return Beneficiario;
    }

    public void setBeneficiario(Cliente beneficiario) {
        Beneficiario = beneficiario;
    }

    public Producto getProductoBen() {
        return productoBen;
    }

    public void setProductoBen(Producto productoBen) {
        this.productoBen = productoBen;
    }
}
