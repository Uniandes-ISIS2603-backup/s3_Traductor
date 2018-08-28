/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 * Entidad que representa al DTO de Idioma para persistencia.
 * @author Santi Salazar
 */
@Entity
public class IdiomaEntity extends BaseEntity implements Serializable 
{
    private String idioma;

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
}