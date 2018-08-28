/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.ReferenciaEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 *
 * @author Juan Felipe Parra Camargo
 */
public class ReferenciaDTO {

   
    private String nombre;
    private Long id;
    private Integer numeroDeTelefono;
    public ReferenciaDTO(){
        
    }
    public ReferenciaDTO(ReferenciaEntity referenciaEntity){
        if(referenciaEntity!=null){
            this.nombre=referenciaEntity.getNombre();
            this.id=referenciaEntity.getId();
            this.numeroDeTelefono=referenciaEntity.getNumeroDeTelefono();
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
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the telephoneNumber
     */
    public Integer getNumeroDeTelefono() {
        return numeroDeTelefono;
    }

    /**
     * @param telephoneNumber the telephoneNumber to set
     */
    public void setNumeroDeTelefono(Integer telephoneNumber) {
        this.numeroDeTelefono = telephoneNumber;
    }
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
