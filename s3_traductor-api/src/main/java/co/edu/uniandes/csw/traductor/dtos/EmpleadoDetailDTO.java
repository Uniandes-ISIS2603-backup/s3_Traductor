/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import java.io.Serializable;

/**
 *
 * @author Alvaro
 */
public class EmpleadoDetailDTO extends EmpleadoDTO implements Serializable{
    //----------------------------------------------------
    //Atributos
    //----------------------------------------------------    
    
    //----------------------------------------------------
    //constructores
    //----------------------------------------------------
    public EmpleadoDetailDTO() {
        super();
    }

    public EmpleadoDetailDTO(EmpleadoEntity empleado) {
        super(empleado);
    }
    //----------------------------------------------------
    //getters and setters
    //----------------------------------------------------

    
}
