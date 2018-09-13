/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa una invitacion en la persistencia y permite su
 * serialización.
 *
 * @author Geovanny Andres Gonzalez
 */

@Entity
public class InvitacionEntity extends BaseEntity implements Serializable {

	private Long idCliente;
	private Long idEmpleado;
	private Long solicitudId;
	private String descripcion;
	
	@PodamExclude
	@OneToOne
	private PropuestaEntity propuesta;
	
	//"Callback a Empleado - Relacion ManyToOne"
	@PodamExclude
	@ManyToOne
	private EmpleadoEntity empleado;
	
        //"Callback a Cliente - Relacion ManyToOne"
	@PodamExclude
	@ManyToOne
	private ClienteEntity cliente;
	
	/**
	 * Constructor
	 */
	
	public InvitacionEntity() {
	}

	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the idEmpleado
	 */
	public Long getIdEmpleado() {
		return idEmpleado;
	}

	/**
	 * @param idEmpleado the idEmpleado to set
	 */
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	/**
	 * @return the solicitudId
	 */
	public Long getSolicitudId() {
		return solicitudId;
	}

	/**
	 * @param solicitudId the solicitudId to set
	 */
	public void setSolicitudId(Long solicitudId) {
		this.solicitudId = solicitudId;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
}
