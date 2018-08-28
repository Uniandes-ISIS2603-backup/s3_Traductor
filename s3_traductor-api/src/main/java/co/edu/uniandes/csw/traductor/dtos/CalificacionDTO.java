/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que representa el DTO de Calificacion
 * @author estudiante
 */
public class CalificacionDTO implements Serializable
{
    private Long id;
    private Long idEmpleado;
    private String comentario;
    private Integer valorCalificacion;
    
    /**
     * Constructor por defecto
     */
    public CalificacionDTO(){
    }

    /**
     * Da el id de la Calificacion
     * @return el id
     */
    public Long getId() {
        return id;
    }

    /**
     * Cambia el id de la calificacion
     * @param id el nuevo id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Da el id del empleado que se califico
     * @return el id del empleado calificado.
     */
    public Long getIdEmpleado() {
        return idEmpleado;
    }

    /**
     * Cambia el id del empleado que se califico
     * @param idEmpleado el nuevo id de empleado calificado
     */
    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    /**
     * Da el comentario de la calificacion
     * @return el comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Cambia el comentario de la calificacion
     * @param comentario el nuevo comentario
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Da el valor numerico de la calificacion
     * @return el valor.
     */
    public Integer getValorCalificacion() {
        return valorCalificacion;
    }

    /**
     * Cambia el valor de la calificacion
     * @param valorCalificacion el nuevo valor
     */
    public void setValorCalificacion(Integer valorCalificacion) {
        this.valorCalificacion = valorCalificacion;
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param calificacionEntity: Es la entidad que se va a convertir a DTO
     */
    public CalificacionDTO(CalificacionEntity calificacionEntity) {
        if (calificacionEntity != null) {
            this.id = calificacionEntity.getId();
            this.idEmpleado = calificacionEntity.getIdEmpleado();
            this.comentario = calificacionEntity.getComentario();
            this.valorCalificacion = calificacionEntity.getValorCalificacion();
        }
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public CalificacionEntity toEntity() {
        CalificacionEntity calificacionEntity = new CalificacionEntity();
        calificacionEntity.setId(this.id);
        calificacionEntity.setIdEmpleado(this.idEmpleado);
        calificacionEntity.setComentario(this.comentario);
        calificacionEntity.setValorCalificacion(this.valorCalificacion);
        return calificacionEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
