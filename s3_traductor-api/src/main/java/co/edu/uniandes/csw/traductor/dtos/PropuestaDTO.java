/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.traductor.dtos;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
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
 * @author Geovanny Andres Gonzalez
 */

public class PropuestaDTO implements Serializable{
    
    private Long propuestaId;
    private String descripcion;
    private String costo;
    private String estado;
    private Long idEmpleado;
    private String tiempoEstimado;

	/**
	 * Constructor vacio para que sea llenado por JAX-RS
	 */
	
	public PropuestaDTO() {
	}

	//===================================================
	//Metodos de transformacion
	//===================================================
	
	/**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param propuestaEntity: Es la entidad que se va a convertir a DTO
     */
	
	public PropuestaDTO (PropuestaEntity propuestaEntity)
	{
		if (propuestaEntity != null)
		{
			this.descripcion = propuestaEntity.getDescripcion();
			this.costo = propuestaEntity.getCosto();
			this.estado = propuestaEntity.getEstado();
			this.idEmpleado = propuestaEntity.getIdEmpleado();
			this.tiempoEstimado = propuestaEntity.getTiempoEstimado();
		}
	}
	
	/**
     * Conviertir DTO a Entity (Crea un nuevo Entity con los valores que posee el DTO).
	 * @return Una PropuestaEntity con los valores que posee el DTO.
     */
	
	public PropuestaEntity toEntity()
	{
		PropuestaEntity propuestaEntity = new PropuestaEntity();
		propuestaEntity.setCosto(costo);
		propuestaEntity.setDescripcion(descripcion);
		propuestaEntity.setEstado(estado);
		propuestaEntity.setIdEmpleado(idEmpleado);
		propuestaEntity.setTiempoEstimado(tiempoEstimado);
		return propuestaEntity;
	}
	
	//===================================================
	//Metodos
	//===================================================
	
    /**
	 * ID de la propuesta.
     * @return the propuestaId
     */
	
    public Long getPropuestaId() {
        return propuestaId;
    }

    /**
	 * Cambia el ID de la propuesta.
     * @param propuestaId the propuestaId to set
     */
    public void setPropuestaId(Long propuestaId) {
        this.propuestaId = propuestaId;
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
	public String getTiempoEstimado() {
		return tiempoEstimado;
	}

	/**
	 * Cambia la fecha estimada de entrega
	 * @param fecha the fecha to set
	 */
	public void setTiempoEstimado(String fecha) {
		this.tiempoEstimado = fecha;
	}  
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
