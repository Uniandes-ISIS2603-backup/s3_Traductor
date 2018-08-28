/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.IdiomaEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que representa un DTO de la clase Idioma
 * @author Santi Salazar
 */
public class IdiomaDTO implements Serializable
{
    private Long id;
    private String idioma;

    /**
     * Da el id del Idioma.
     * @return el id
     */
    public Long getId() {
        return id;
    }

    /**
     * Cambia el id del Idioma.
     * @param id el nuevo id.
     */
    public void setId(Long id) {
        this.id = id;
    }

      /**
       * Obtiene el idioma
       * @return el idioma.
       */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Cambia el idioma.
     * @param idioma el nuevo idioma.
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    /**
     * Constructor por defecto
     */
    public IdiomaDTO() {
    }

    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param idiomaEntity: Es la entidad que se va a convertir a DTO
     */
    public IdiomaDTO(IdiomaEntity idiomaEntity) {
        if (idiomaEntity != null) {
            this.id = idiomaEntity.getId();
            this.idioma = idiomaEntity.getIdioma();
        }
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public IdiomaEntity toEntity() {
        IdiomaEntity idiomaEntity = new IdiomaEntity();
        idiomaEntity.setId(this.id);
        idiomaEntity.setIdioma(this.idioma);
        return idiomaEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
