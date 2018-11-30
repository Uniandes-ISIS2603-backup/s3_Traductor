/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un area de conocimiento en la persistencia y permite su
 * serialización.
 *
 * @author Geovanny Andres Gonzalez
 *
 */
@Entity
public class AreaConocimientoEntity extends BaseEntity implements Serializable {

    private String area;

    //"Callback a Empleado - Relacion ManyToMany"
    @PodamExclude
    @ManyToMany(mappedBy = "areasDeConocimiento")
    private List<EmpleadoEntity> empleados;

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

    public List<EmpleadoEntity> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoEntity> empleados) {
        this.empleados = empleados;
    }

}
