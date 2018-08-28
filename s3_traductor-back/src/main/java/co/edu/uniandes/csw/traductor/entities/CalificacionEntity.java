/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 * Clase del DTO de calificacion para la persistencia.
 * @author Santi Salazar
 */
@Entity
public class CalificacionEntity extends BaseEntity implements Serializable
{
    private Long idEmpleado;
    private String comentario;
    private Integer valorCalificacion;

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getValorCalificacion() {
        return valorCalificacion;
    }

    public void setValorCalificacion(Integer valorCalificacion) {
        this.valorCalificacion = valorCalificacion;
    }
}
