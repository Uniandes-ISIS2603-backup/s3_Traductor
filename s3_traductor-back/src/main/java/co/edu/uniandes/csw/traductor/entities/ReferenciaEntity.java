/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

/**
 *
 * @author estudiante
 */
public class ReferenciaEntity{
    private String nombre;
    private Long id;
    private Integer numeroDeTelefono;

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
     * @return the numeroDeTelefono
     */
    public Integer getNumeroDeTelefono() {
        return numeroDeTelefono;
    }

    /**
     * @param numeroDeTelefono the numeroDeTelefono to set
     */
    public void setNumeroDeTelefono(Integer numeroDeTelefono) {
        this.numeroDeTelefono = numeroDeTelefono;
    }
}
