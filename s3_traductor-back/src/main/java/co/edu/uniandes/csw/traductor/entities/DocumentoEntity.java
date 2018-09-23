/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.File;
import java.io.Serializable;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Alvaro
 */
public class DocumentoEntity extends BaseEntity implements Serializable{
    
    private String Descripcion;
    private Integer numeroPalabras;
    private File archivoAdjunto;
    @PodamExclude
    @ManyToOne
    private SolicitudEntity solicitud;
   
    
    public DocumentoEntity() {
    }

    public SolicitudEntity getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudEntity solicitud) {
        this.solicitud = solicitud;
    }
    
    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
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
}
