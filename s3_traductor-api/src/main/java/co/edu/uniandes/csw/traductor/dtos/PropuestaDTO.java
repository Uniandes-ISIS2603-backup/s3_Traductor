/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.adapters.DateAdapter;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * PropuestaDTO Objeto de transferencia de datos de Editoriales. Los DTO
 * contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *		"idEmpleado": Long,
 *		"estado" : "String",
 *		"descripcion" : "String",
 *		"costo" : Integer,
 *		"tiempoEstimado" : "Date"
 *	 }
 *
 * </pre>
 *
 * @author Geovanny Andres Gonzalez
 */
public class PropuestaDTO implements Serializable {

    private Long id;
    private String descripcion;
    private Integer costo;
    private String estado;
    //Noviembre 13 - Se borra el atributo idEmpleado y se devolvera el del Entity del Empleado
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date tiempoEstimado;

    /**
     * Asociacion de cardinalidad 1 con invitacion
     */
    private InvitacionDTO invitacion;

    private PagosDTO pago;

    /**
     * Constructor vacio para que sea llenado por JAX-RS
     */
    public PropuestaDTO() {
    }

    //===================================================
    //Metodos de transformacion
    //===================================================
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param propuestaEntity: Es la entidad que se va a convertir a DTO
     */
    public PropuestaDTO(PropuestaEntity propuestaEntity) {
        if (propuestaEntity != null) {
            this.id = propuestaEntity.getId();
            this.descripcion = propuestaEntity.getDescripcion();
            this.costo = propuestaEntity.getCosto();
            this.estado = propuestaEntity.getEstado();            
            this.tiempoEstimado = propuestaEntity.getTiempoEstimado();
            this.invitacion = (propuestaEntity.getInvitacion() != null) ? new InvitacionDTO(propuestaEntity.getInvitacion()) : null;
        }
    }

    /**
     * Conviertir DTO a Entity (Crea un nuevo Entity con los valores que posee
     * el DTO).
     *
     * @return Una PropuestaEntity con los valores que posee el DTO.
     */
    public PropuestaEntity toEntity() {
        PropuestaEntity propuestaEntity = new PropuestaEntity();
        propuestaEntity.setCosto(costo);
        propuestaEntity.setDescripcion(descripcion);
        propuestaEntity.setEstado(estado);        
        propuestaEntity.setTiempoEstimado(tiempoEstimado);
        if (invitacion != null) {
            propuestaEntity.setInvitacion(invitacion.toEntity());
        }

        return propuestaEntity;
    }

    //===================================================
    //Metodos
    //===================================================
    /**
     * ID de la propuesta.
     *
     * @return the propuestaId
     */
    public Long getId() {
        return id;
    }

    /**
     * Cambia el ID de la propuesta.
     *
     * @param id the propuestaId to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Descripcion de la propuesta
     *
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Cambia la descripcion de la propuesta
     *
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Costo de la realizacion del trabajo
     *
     * @return the costo
     */
    public Integer getCosto() {
        return costo;
    }

    /**
     * Cambia el costo del trabajo
     *
     * @param costo the costo to set
     */
    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    /**
     * Estado de progreso del trabajo
     *
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Cambia el estado del trabajo a medida que se va progresando
     *
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }    

    /**
     * Retorna la fecha estimada de entrega
     *
     * @return the fecha
     */
    public Date getTiempoEstimado() {
        return tiempoEstimado;
    }

    /**
     * Cambia la fecha estimada de entrega
     *
     * @param fecha the fecha to set
     */
    public void setTiempoEstimado(Date fecha) {
        this.tiempoEstimado = fecha;
    }

    public InvitacionDTO getInvitacion() {
        return invitacion;
    }

    public void setInvitacion(InvitacionDTO invitacion) {
        this.invitacion = invitacion;
    }

    public PagosDTO getPago() {
        return pago;
    }

    public void setPago(PagosDTO pago) {
        this.pago = pago;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
