/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jhonattanfonseca
 */
public class SolicitudEntity extends BaseEntity implements Serializable {

    //Atributos
    private Long idSolicitud;
    private Date fechaInicio;
    private Date fechaEntrega;
    private Integer estado;
    private Integer tipoSolicitud;

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

    
    
}