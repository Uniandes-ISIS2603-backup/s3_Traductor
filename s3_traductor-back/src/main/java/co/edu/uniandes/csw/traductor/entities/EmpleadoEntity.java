/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;

/**
 *
 * @author estudiante
 */
public class EmpleadoEntity extends BaseEntity implements Serializable{

    private String nombreEmpleado;
    private String correoElectronico;
    private String nombreUsuario;
    private String contrasenia;
    private HojaDeVidaEntity hojaDeVida;
    private Integer aniosExperiencia;

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
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

    public Integer getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(Integer aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public HojaDeVidaEntity getHojaDeVida() {
        return hojaDeVida;
    }

    public void setHojaDeVida(HojaDeVidaEntity hojaDeVida) {
        this.hojaDeVida = hojaDeVida;
    }
    
}
