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
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Entidad que representa al DTO de Idioma para persistencia.
 * @author Santi Salazar
 */
@Entity
public class IdiomaEntity extends BaseEntity implements Serializable 
{
    private String idioma;
    
    @PodamExclude
    @ManyToMany(mappedBy = "idiomas")
    private List<EmpleadoEntity> empleados;
    public List<EmpleadoEntity> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoEntity> empleados) {
        this.empleados = empleados;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    public void addEmpleado(EmpleadoEntity empleado){
        this.empleados.add(empleado);
    }
}