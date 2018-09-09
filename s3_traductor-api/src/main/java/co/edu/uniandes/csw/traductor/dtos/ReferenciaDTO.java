/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.ReferenciaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 *
 * @author Juan Felipe Parra Camargo
 */
public class ReferenciaDTO implements Serializable{

   /**
    * nombre de la persona a referenciar
    */
    private String nombre;
    /**
     * id de la referencia
     */
    private Long id;
    /**
     * telefono de la persona referenciada
     */
    private Integer numeroDeTelefono;
    /**
     * 
     */
    public ReferenciaDTO(){
        
    }
    /**
     * Construye una ReferenciaDTO a partir de su Entity
     * @param referenciaEntity 
     */
    public ReferenciaDTO(ReferenciaEntity referenciaEntity){
        if(referenciaEntity!=null){
            this.nombre=referenciaEntity.getNombre();
            this.id=referenciaEntity.getId();
            this.numeroDeTelefono=referenciaEntity.getNumeroDeTelefono();
        }
    }
     /**
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param name el nombre a colocar en la referencia
     */
    public void setNombre(String name) {
        this.nombre = name;
    }

    /**
     * @return la id de la referencia
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id la id a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return el telefono
     */
    public Integer getNumeroDeTelefono() {
        return numeroDeTelefono;
    }

    /**
     * @param telephoneNumber el numeroDeTelefono a asignar
     */
    public void setNumeroDeTelefono(Integer telephoneNumber) {
        this.numeroDeTelefono = telephoneNumber;
    }
    /**
     * Convertir el DTO actual a entity
     * @return el entity correspondiente a el dto actual
     */
    public ReferenciaEntity toEntity(){
        ReferenciaEntity referencia=new ReferenciaEntity();
        referencia.setNombre(nombre);
        referencia.setId(id);
        referencia.setNumeroDeTelefono(numeroDeTelefono);
        return referencia;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
