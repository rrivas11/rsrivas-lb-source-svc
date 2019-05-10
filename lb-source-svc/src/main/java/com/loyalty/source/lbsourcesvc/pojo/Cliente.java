package com.loyalty.source.lbsourcesvc.pojo;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "cl_idcliente")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idcli;

    @Column(name = "cl_nombre")
    private String nombre;

    @Column(name = "cl_apellido")
    private String apellido;

    @Column(name = "cl_correo")
    private String correo;

    @Column(name = "cl_usuario")
    private String usuario;

    @Column(name = "cl_pass")
    private String pass;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Producto> producto;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Favorito> favClie ;

    @OneToMany(mappedBy = "Beneficiario")
    @JsonIgnore
    private List<Favorito> favBen ;


    public int getIdcli() {
        return idcli;
    }

    public void setIdcli(int idcli) {
        this.idcli = idcli;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<Producto> getProducto() {
        return producto;
    }

    public void setProducto(List<Producto> producto) {
        this.producto = producto;
    }
}

