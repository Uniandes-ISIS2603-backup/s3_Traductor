/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.Date;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que representa una propuesta en la persistencia y permite su
 * serialización.
 *
 * @author Geovanny Andres Gonzalez
 */
@Entity
public class PropuestaEntity extends BaseEntity implements Serializable {

    private String descripcion;
    private Integer costo;
    private String estado;  

    @Temporal(TemporalType.DATE)
    private Date tiempoEstimado;

    //Asociacion de cardinalidad 1 con Invitacion
    @PodamExclude
    @OneToOne(mappedBy = "propuesta", fetch = FetchType.LAZY) //Asociacion 1-1. Se pone de dueña a propuesta. Por ello tiene el mapeo.
    private InvitacionEntity invitacion;

    @PodamExclude
    @OneToOne(mappedBy = "propuesta", fetch = FetchType.LAZY, optional = true) //Asociacion 1-1. Se pone de dueña a propuesta. Por ello tiene el mapeo.
    private PagosEntity pago;

    //"Callback a Empleado - Relacion ManyToOne"
    @PodamExclude
    @ManyToOne
    private EmpleadoEntity empleado;

    //"Callback a Cliente - Relacion ManyToOne"
    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;

    /**
     * Descripcion de la propuesta
     *
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Cambia la descripcion de la propuesta
     *
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Costo de la realizacion del trabajo
     *
     * @return the costo
     */
    public Integer getCosto() {
        return costo;
    }

    /**
     * Cambia el costo del trabajo
     *
     * @param costo the costo to set
     */
    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    /**
     * Estado de progreso del trabajo
     *
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Cambia el estado del trabajo a medida que se va progresando
     *
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }    

    /**
     * Retorna la fecha estimada de entrega
     *
     * @return the fecha
     */
    public Date getTiempoEstimado() {
        return tiempoEstimado;
    }

    /**
     * Cambia la fecha estimada de entrega
     *
     * @param tiempoEstimado the fecha to set
     */
    public void setTiempoEstimado(Date tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    /**
     * @return the invitacion
     */
    public InvitacionEntity getInvitacion() {
        return invitacion;
    }

    /**
     * @param invitacion the invitacion to set
     */
    public void setInvitacion(InvitacionEntity invitacion) {
        this.invitacion = invitacion;
    }

    public EmpleadoEntity getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoEntity empleado) {
        this.empleado = empleado;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public PagosEntity getPago() {
        return pago;
    }

    public void setPago(PagosEntity pago) {
        this.pago = pago;
    }
}
