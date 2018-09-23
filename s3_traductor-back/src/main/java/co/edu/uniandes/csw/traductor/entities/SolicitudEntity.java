/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
    private Long idSolicitud;
    private Date fechaInicio;
    private Date fechaEntrega;
    private Integer estado;
    private Integer tipoSolicitud;
   
    
    
    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;
    
    @PodamExclude
    @ManyToOne
    private EmpleadoEntity empleado;
    
    @PodamExclude
    @OneToOne(mappedBy = "solicitud", fetch = FetchType.LAZY)
    private IdiomaEntity idiomaSalida;
    
    @PodamExclude
    @OneToOne(mappedBy = "solicitud", fetch = FetchType.LAZY)
    private IdiomaEntity idiomaEntrada;
    
    private ArrayList<DocumentoEntity> documentos = new ArrayList<>();

    public ArrayList<DocumentoEntity> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(ArrayList<DocumentoEntity> documentos) {
        this.documentos = documentos;
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
