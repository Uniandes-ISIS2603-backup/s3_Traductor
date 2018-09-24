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

/**
 *
 * @author estudiante
 */
@Entity
public class ReferenciaEntity extends BaseEntity implements Serializable{
    private String nombre;
    private Integer numeroDeTelefono;
    
    @PodamExclude
    @ManyToOne
    private EmpleadoEntity empleado;

    public EmpleadoEntity getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoEntity empleado) {
        this.empleado = empleado;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the numeroDeTelefono
     */
    public Integer getNumeroDeTelefono() {
        return numeroDeTelefono;
    }

    /**
     * @param numeroDeTelefono the numeroDeTelefono to set
     */
    public void setNumeroDeTelefono(Integer numeroDeTelefono) {
        this.numeroDeTelefono = numeroDeTelefono;
    }
}
