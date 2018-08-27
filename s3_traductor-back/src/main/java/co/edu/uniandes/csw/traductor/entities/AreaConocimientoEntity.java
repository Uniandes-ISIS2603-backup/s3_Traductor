/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;
import java.io.Serializable;
import javax.persistence.Entity;

/**
 * Clase que representa un area de conocimiento en la persistencia y permite su
 * serialización.
 *
 * @author Geovanny Andres Gonzalez
 */

@Entity
public class AreaConocimientoEntity extends BaseEntity implements Serializable {

	private String area;
	
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
