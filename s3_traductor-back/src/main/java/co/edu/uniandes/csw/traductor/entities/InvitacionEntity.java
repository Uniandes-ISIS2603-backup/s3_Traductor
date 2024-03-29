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

	private Long idEmpleado;	
	private String descripcion;	
	private Long solicitudId;
	
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
            
            /**
             * Se deja el constructor vacio debido a que el API JAX-RS necesita un constructor que
             * le permita crear el objeto sin necesidad de pasar parametros de primero
             * 
             * Luego el mismo API se encargara de asignar los valores con los metodos Setters
             * 
             * Geovanny Andres Gonzalez
             */
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

	//Atención: 14 de Septiembre 21:57 - Se elimino el atributo solicitudId debido a que según el modelo conceptual
	//solicitud no se relaciona con la invitacion.

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
	
	public Long getSolicitudId() {
		return solicitudId;
	}

	public void setSolicitudId(Long solicitudId) {
		this.solicitudId = solicitudId;
	}

	public PropuestaEntity getPropuesta() {
		return propuesta;
	}

	public void setPropuesta(PropuestaEntity propuesta) {
		this.propuesta = propuesta;
	}
}
