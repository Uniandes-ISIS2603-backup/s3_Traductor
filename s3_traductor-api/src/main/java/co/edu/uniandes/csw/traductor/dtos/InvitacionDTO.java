package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * InvitacionDTO Objeto de transferencia de datos de Editoriales. Los DTO
 * contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   { 
 *		"descripcion" : String,
 *		"idEmpleado" : Long
 *	 }
 * </pre>
 *
 * @author Geovanny Andrés Gonzalez
 */

public class InvitacionDTO implements Serializable {

    private Long idInvitacion;    
    private Long idEmpleado;
    private Long solicitudId;
    private String descripcion;

    /**
     * Constructor vacio para que sea llenado por JAX-RS
     */
    public InvitacionDTO() {
    }

    //===================================================
    //Metodos de transformacion
    //===================================================
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param invitacionEntity: Es la entidad que se va a convertir a DTO
     */
    public InvitacionDTO(InvitacionEntity invitacionEntity) {
        if (invitacionEntity != null) {
            this.idInvitacion = invitacionEntity.getId();            
            this.descripcion = invitacionEntity.getDescripcion();
            this.idEmpleado = invitacionEntity.getIdEmpleado();
            this.solicitudId = invitacionEntity.getSolicitudId();
        }
    }

    /**
     * Conviertir DTO a Entity (Crea un nuevo Entity con los valores que posee
     * el DTO).
     *
     * @return Una InvitacionEntity con los valores que posee el DTO.
     */
    public InvitacionEntity toEntity() {
        InvitacionEntity invitacionEntity = new InvitacionEntity();
        if(idInvitacion != null && idInvitacion >= 0)
        {
            invitacionEntity.setId(idInvitacion);
        }
        invitacionEntity.setDescripcion(descripcion);
        invitacionEntity.setIdEmpleado(idEmpleado);
        invitacionEntity.setSolicitudId(solicitudId);
        return invitacionEntity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    //===================================================
    //Metodos
    //===================================================
    /**
     * @return the idInvitacion
     */
    public Long getIdInvitacion() {
        return idInvitacion;
    }

    /**
     * @param idInvitacion the idInvitacion to set
     */
    public void setIdInvitacion(Long idInvitacion) {
        this.idInvitacion = idInvitacion;
    }   

    /**
     * @return the idEmpleado
     */
    public Long getIdEmpleado() {
        return idEmpleado;
    }

    /**
     * @param idEmpleado the idEmpleado to set
     */
    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the solicitud id
     */
    public Long getSolicitudId() {
        return solicitudId;
    }

    /**
     * @param soliciutdId  the solicitudId to set
     */
    public void setSolicitudId(Long soliciutdId) {
        this.solicitudId = soliciutdId;
    }
}
