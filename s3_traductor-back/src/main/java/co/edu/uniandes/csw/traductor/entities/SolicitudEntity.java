/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author jhonattanfonseca
 */
@Entity
public class SolicitudEntity extends BaseEntity implements Serializable {

    public final static Integer EN_ESPERA = 2;

    public final static Integer COMPLETADO = 1;

    public final static Integer CANCELADO = 0;

    public final static Integer CORECCION = 3;

    public final static Integer TRADUCCION = 4;

    //Atributos
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    private Integer estado;
    private Integer tipoSolicitud;
    private String descripcion;
    private String archivo;
    private int longitud;


    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;

    @PodamExclude
    @ManyToOne
    private EmpleadoEntity empleado;

    
    private String idiomaSalida;


    private String idiomaEntrada;

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public Integer getEstado() {
        return estado;
    }

    public Integer getTipoSolicitud() {
        return tipoSolicitud;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public EmpleadoEntity getEmpleado() {
        return empleado;
    }

    public String getIdiomaSalida() {
        return idiomaSalida;
    }

    public String getIdiomaEntrada() {
        return idiomaEntrada;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public void setTipoSolicitud(Integer tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public void setEmpleado(EmpleadoEntity empleado) {
        this.empleado = empleado;
    }

    public void setIdiomaSalida(String idiomaSalida) {
        this.idiomaSalida = idiomaSalida;
    }

    public void setIdiomaEntrada(String idiomaEntrada) {
        this.idiomaEntrada = idiomaEntrada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }


}
