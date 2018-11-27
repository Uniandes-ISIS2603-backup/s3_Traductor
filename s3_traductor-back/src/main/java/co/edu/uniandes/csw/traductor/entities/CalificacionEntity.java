package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

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
    private String nombreCalificador;
    @PodamExclude
    @ManyToOne
    private EmpleadoEntity empleado;

    public EmpleadoEntity getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoEntity empleado) {
        this.empleado = empleado;
    }
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

    public String getNombreCalificador() {
        return nombreCalificador;
    }

    public void setNombreCalificador(String nombreCalificador) {
        this.nombreCalificador = nombreCalificador;
    }
}
