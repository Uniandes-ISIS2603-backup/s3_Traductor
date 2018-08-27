/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;
import java.io.Serializable;
import javax.persistence.Entity;

/**
 * Clase que representa una propuesta en la persistencia y permite su
 * serialización.
 *
 * @author Geovanny Andres Gonzalez
 */

@Entity
public class PropuestaEntity extends BaseEntity implements Serializable {

	private String descripcion;
    private String costo;
    private String estado;
    private Long idEmpleado;
    private String fecha;
	
	/*
		Constructor
	*/
	
	public PropuestaEntity() {
	}	
	
	/**
	 * Descripcion de la propuesta
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Cambia la descripcion de la propuesta
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Costo de la realizacion del trabajo
	 * @return the costo
	 */
	public String getCosto() {
		return costo;
	}

	/**
	 * Cambia el costo del trabajo
	 * @param costo the costo to set
	 */
	public void setCosto(String costo) {
		this.costo = costo;
	}

	/**
	 * Estado de progreso del trabajo
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Cambia el estado del trabajo a medida que se va progresando
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Retorna el ID del empleado que va a realizar el trabajo.
	 * @return the idEmpleado
	 */
	public Long getIdEmpleado() {
		return idEmpleado;
	}

	/**
	 * Cambia el ID del empleado que va a realizar el trabajo.
	 * @param idEmpleado the idEmpleado to set
	 */
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	/**
	 * Retorna la fecha estimada de entrega
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Cambia la fecha estimada de entrega
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	
}
