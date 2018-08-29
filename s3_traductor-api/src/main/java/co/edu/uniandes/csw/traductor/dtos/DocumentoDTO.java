/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;
import co.edu.uniandes.csw.traductor.entities.DocumentoEntity;
import java.io.Serializable;
import java.io.File;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 *
 * @author Alvaro
 */
public class DocumentoDTO implements Serializable{
    private Long id;
    private String Descripcion;
    private Integer numeroPalabras;
    private File archivoAdjunto;
    //===================================================
    //Constructores
    //===================================================
    /**
    * Constructor vacio para que sea llenado por JAX-RS
     */
    public DocumentoDTO() {
    }
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param DocumentoEntity: Es la entidad que se va a convertir a DTO
     */

    public DocumentoDTO(DocumentoEntity entity){
        this.id=entity.getId();
        this.Descripcion=entity.getDescripcion();
        this.numeroPalabras=entity.getNumeroPalabras();
        this.archivoAdjunto=entity.getArchivoAdjunto();
                
    }
    //===================================================
    //Getters and setters
    //===================================================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descricion) {
        this.Descripcion = Descricion;
    }

    public Integer getNumeroPalabras() {
        return numeroPalabras;
    }

    public void setNumeroPalabras(Integer numeroPalabras) {
        this.numeroPalabras = numeroPalabras;
    }

    public File getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(File archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }
    //===================================================
    //Transformation
    //===================================================
    
    /**
     * Conviertir DTO a Entity (Crea un nuevo Entity con los valores que posee el DTO).
     * @return Un DocumentoEntity con los valores que posee el DTO.
     */
    public DocumentoEntity toEntity(){
        DocumentoEntity newEntity = new DocumentoEntity();
        newEntity.setId(this.id);
        newEntity.setDescripcion(this.Descripcion);
        newEntity.setNumeroPalabras(this.numeroPalabras);
        newEntity.setArchivoAdjunto(this.archivoAdjunto);
        return newEntity;
    }
    /**
    *Convierte la clase a string para el logger
    */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
   

}
