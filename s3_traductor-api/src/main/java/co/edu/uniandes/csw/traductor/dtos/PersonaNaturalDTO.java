package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.PersonaNaturalEntity;
import java.io.File;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan Felipe Parra Camargo
 */
public class PersonaNaturalDTO extends ClienteDTO {
    private String nombre;
    private Date fechaDeNacimiento;
    private File fotoPerfil;
    private Integer numeroDeIdentificacion;
    /**
     * 
     */
    public PersonaNaturalDTO(){
        
    }
    /**
     * 
     * @param personaNaturalEntity 
     */
    public PersonaNaturalDTO(PersonaNaturalEntity personaNaturalEntity){
        super(personaNaturalEntity);
        if(personaNaturalEntity!=null){
            this.nombre=personaNaturalEntity.getNombre();
            this.fechaDeNacimiento=personaNaturalEntity.getFechaDeNacimiento();
            this.fotoPerfil=personaNaturalEntity.getFotoPerfil();
            this.numeroDeIdentificacion=personaNaturalEntity.getNumeroDeIdentificacion();
        }
    }
    /**
     * @return the name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param name the name to set
     */
    public void setNombre(String name) {
        this.nombre = name;
    }

    /**
     * @return the bornDate
     */
    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    /**
     * @param bornDate the bornDate to set
     */
    public void setFechaDeNacimiento(Date bornDate) {
        this.fechaDeNacimiento = bornDate;
    }

    /**
     * @return the profilePhoto
     */
    public File getFotoDePerfil() {
        return fotoPerfil;
    }

    /**
     * @param profilePhoto the profilePhoto to set
     */
    public void setFotoDePerfil(File profilePhoto) {
        this.fotoPerfil = profilePhoto;
    }

    /**
     * @return the identificationNumber
     */
    public Integer getNumeroDeIdentificacion() {
        return numeroDeIdentificacion;
    }

    /**
     * @param identificationNumber the identificationNumber to set
     */
    public void setNumeroDeIdentificacion(Integer identificationNumber) {
        this.numeroDeIdentificacion = identificationNumber;
    }
    public PersonaNaturalEntity toEntity(){
        super.toEntity();
        PersonaNaturalEntity persona=new PersonaNaturalEntity();
        persona.setNombre(nombre);
        persona.setFechaDeNacimiento(fechaDeNacimiento);
        persona.setFotoPerfil(fotoPerfil);
        persona.setNumeroDeIdentificacion(numeroDeIdentificacion);
        return persona;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
