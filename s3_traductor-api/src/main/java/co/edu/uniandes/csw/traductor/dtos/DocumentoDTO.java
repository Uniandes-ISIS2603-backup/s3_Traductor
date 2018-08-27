/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;
import java.io.Serializable;
/**
 *
 * @author estudiante
 */
public class DocumentoDTO implements Serializable{
    private Long id;
    private String Descricion;
    private Integer numeroPalabras;
    /**
     * Constructor por defecto.
     */
    public DocumentoDTO() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricion() {
        return Descricion;
    }

    public void setDescricion(String Descricion) {
        this.Descricion = Descricion;
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
    private File archivoAdjunto;

}
