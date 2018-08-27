/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

/**
 * InvitacionDTO Objeto de transferencia de datos de Editoriales. Los DTO
 * contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *		"idCliente" : Long, 
 *		"solicitudId" : Long, 
 *		"descripcion" : String, 
 *		"idEmpleado" : Long
 *	 }
 * </pre>
 *
 * @author Geovanny Andrés Gonzalez
 */

public class InvitacionDTO {

	private Long idInvitacion;
	private Long idCliente;
	private Long idEmpleado;
	private Long solicitudId;
	private String descripcion;
	
	/**
	 * @return the idInvitacion
	 */
	public Long getIdInvitacion() {
		return idInvitacion;
	}

	/**
	 * @param idInvitacion the idInvitacion to set
	 */
	public void setIdInvitacion(Long idInvitacion) {
		this.idInvitacion = idInvitacion;
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
}
