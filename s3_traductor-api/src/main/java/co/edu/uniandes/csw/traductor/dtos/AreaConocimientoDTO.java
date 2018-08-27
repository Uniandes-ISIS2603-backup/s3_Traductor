/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;
import co.edu.uniandes.csw.traductor.entities.AreaConocimientoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * AreaConocimientoDTO Objeto de transferencia de datos de Editoriales. Los DTO
 * contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *		"id" : Long, 
 *		"area" : String, 		
 *	 }
 * </pre>
 *
 * @author Geovanny Andrés Gonzalez
 */

public class AreaConocimientoDTO implements Serializable{

	private Long id;
	private String area;
	
	/**
	 * Constructor vacio para que sea llenado por JAX-RS
	 */
	
	public AreaConocimientoDTO() {
	}

	//===================================================
	//Metodos de transformacion
	//===================================================
	
	/**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param areaConocimientoEntity: Es la entidad que se va a convertir a DTO
     */
	
	public AreaConocimientoDTO (AreaConocimientoEntity areaConocimientoEntity)
	{
		if (areaConocimientoEntity != null)
		{
			this.area = areaConocimientoEntity.getArea();
		}
	}
	
	/**
     * Conviertir DTO a Entity (Crea un nuevo Entity con los valores que posee el DTO).
	 * @return Una PropuestaEntity con los valores que posee el DTO.
     */
	
	public AreaConocimientoEntity toEntity()
	{
		AreaConocimientoEntity areaConocimientoEntity = new AreaConocimientoEntity();
		areaConocimientoEntity.setArea(area);
		return areaConocimientoEntity;
	}	
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	
	//===================================================
	//Metodos
	//===================================================
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}	
}
