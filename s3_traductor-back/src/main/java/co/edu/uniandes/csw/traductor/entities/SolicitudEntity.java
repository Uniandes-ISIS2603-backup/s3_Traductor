/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;
 
/**
 *
 * @author jhonattanfonseca
 */
//@Entity
public class SolicitudEntity extends BaseEntity implements Serializable {

    //Atributos
    private Long idSolicitud;
    private Date fechaInicio;
    private Date fechaEntrega;
    private Integer estado;
    private Integer tipoSolicitud;
    
    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;
    
    private EmpleadoEntity empleado;
    
    private IdiomaEntity idiomaSalida;
    
    private IdiomaEntity idiomaEntrada;
   

    public Long getIdSolicitud() {
        return idSolicitud;
    }

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

    public IdiomaEntity getIdiomaSalida() {
        return idiomaSalida;
    }

    public IdiomaEntity getIdiomaEntrada() {
        return idiomaEntrada;
    }

    
    
    
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
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

    public void setIdiomaSalida(IdiomaEntity idiomaSalida) {
        this.idiomaSalida = idiomaSalida;
    }

    public void setIdiomaEntrada(IdiomaEntity idiomaEntrada) {
        this.idiomaEntrada = idiomaEntrada;
    }

    
    
}
