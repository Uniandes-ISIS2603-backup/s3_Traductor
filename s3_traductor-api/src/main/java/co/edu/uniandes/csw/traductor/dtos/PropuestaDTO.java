/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.traductor.dtos;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * PropuestaDTO Objeto de transferencia de datos de Editoriales. Los DTO
 * contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *		"idEmpleado": Long, 
 *		"estado" : "String", 
 *		"descripcion" : "String",
 *		"costo" : Integer, 
 *		"tiempoEstimado" : "String ISO 8601"
 *	 } 
 *
 * </pre>
 *
 * @author Geovanny Andrés Gonzalez
 */


public class PropuestaDTO {
    
    private Long propuestaId;
    private String descripcion;
    private String costo;
    private String estado;
    private Long idEmpleado;
    private String fecha;

	/**
	 * Constructor vacio para que sea llenado por JAX-RS
	 */
	
	public PropuestaDTO() {
	}

    /**
     * @return the propuestaId
     */
	
    public Long getPropuestaId() {
        return propuestaId;
    }

    /**
     * @param propuestaId the propuestaId to set
     */
    public void setPropuestaId(Long propuestaId) {
        this.propuestaId = propuestaId;
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

    /**
     * @return the costo
     */
    public String getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(String costo) {
        this.costo = costo;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
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
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }    
}
