/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author estudiante
 */
public class DocumentoEntity extends BaseEntity implements Serializable{
    
    private String Descripcion;
    private Integer numeroPalabras;
    private File archivoAdjunto;

   
    
    public DocumentoEntity() {
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
