/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.File;
import java.util.Date;

/**
 *
 * @author estudiante
 */
public class PersonaNaturalEntity extends ClienteEntity{
    private String nombre;
    private Date fechaDeNacimiento;
    private File fotoPerfil;
    private Integer numeroDeIdentificacion;

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
     * @return the fechaDeNacimiento
     */
    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    /**
     * @param fechaDeNacimiento the fechaDeNacimiento to set
     */
    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    /**
     * @return the fotoPerfil
     */
    public File getFotoPerfil() {
        return fotoPerfil;
    }

    /**
     * @param fotoPerfil the fotoPerfil to set
     */
    public void setFotoPerfil(File fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    /**
     * @return the numeroDeIdentificacion
     */
    public Integer getNumeroDeIdentificacion() {
        return numeroDeIdentificacion;
    }

    /**
     * @param numeroDeIdentificacion the numeroDeIdentificacion to set
     */
    public void setNumeroDeIdentificacion(Integer numeroDeIdentificacion) {
        this.numeroDeIdentificacion = numeroDeIdentificacion;
    }
}
