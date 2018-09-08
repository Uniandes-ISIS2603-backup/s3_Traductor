/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro
 */
public class EmpleadoDTO implements Serializable{
    //----------------------------------------------------
    //Atributos
    //----------------------------------------------------
    private Long id;
    private String nombreEmpleado;
    private String correoElectronico;
    private Integer aniosExperiencia;
    private String nombreUsuario;
    private String contrasenia;
    private String trayectoria;
    private String hojaDeVida; 

    
    //----------------------------------------------------
    //constructores
    //----------------------------------------------------
    public EmpleadoDTO() {}
    public EmpleadoDTO(EmpleadoEntity empleado){
        this.id = empleado.getId();
        this.nombreEmpleado= empleado.getNombreEmpleado();
        this.correoElectronico=empleado.getCorreoElectronico();
        this.aniosExperiencia=empleado.getAniosExperiencia();
        this.nombreUsuario=empleado.getNombreUsuario();
        this.contrasenia=empleado.getContrasenia();
        this.trayectoria = empleado.getTrayectoria();
        this.hojaDeVida = empleado.getHojaDeVida();
        
    }
    //----------------------------------------------------
    //getters and setters
    //----------------------------------------------------
    public Long getId() {
        return id;
    }

    public String getTrayectoria() {
        return trayectoria;
    }

    public void setTrayectoria(String trayectoria) {
        this.trayectoria = trayectoria;
    }

    public String getHojaDeVida() {
        return hojaDeVida;
    }

    public void setHojaDeVida(String hojaDeVida) {
        this.hojaDeVida = hojaDeVida;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Integer getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(Integer aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    

    //----------------------------------------------------
    //Metodos adicionales
    //----------------------------------------------------
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    public EmpleadoEntity toEntity(){
        //nemen = newEmpleadoEntity
        EmpleadoEntity nemen = new EmpleadoEntity();
        nemen.setId(this.id);
        nemen.setNombreEmpleado(this.nombreEmpleado);
        nemen.setCorreoElectronico(this.correoElectronico);
        nemen.setAniosExperiencia(this.aniosExperiencia);
        nemen.setNombreUsuario(this.nombreUsuario);
        nemen.setContrasenia(this.contrasenia);
        nemen.setTrayectoria(this.trayectoria);
        nemen.setHojaDeVida(this.hojaDeVida);
      
        return nemen;
    }
}
