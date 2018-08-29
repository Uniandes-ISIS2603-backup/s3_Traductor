/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import java.io.Serializable;
import java.util.Date;


/**
 * SolicitudDTO Objeto de transferencia de datos de Editoriales. Los DTO
 * contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *		"idSolicitud" : Long, 
 *		"fechaInicio" : Date,
 * "fechaEntrega" : Date,
 * "estado" : Integer,
 *	 }
 * </pre>
 *
 * @author Jhonattan Fonseca
 */

public class SolicitudDTO implements Serializable{

    
    public final static Integer EN_ESPERA = 2;
    
    public final static Integer COMPLETADO = 1;
    
    public final static Integer CANCELADO = 0;
    
    public final static Integer CORECCION = 01;
    
    public final static Integer TRADUCCION = 02;
    
    
    
	private Long idSolicitud;
	private Date fechaInicio;
        private Date fechaEntrega;
        private Integer estado;
        private Integer tipoSolicitud;
	
	/**
	 * Constructor vacio para que sea llenado por JAX-RS
	 */
	
	public SolicitudDTO() 
        {
	}

	//===================================================
	//Metodos de transformacion
	//===================================================
	
	/**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param solicitudEntity es la entidad que se va a convertir en DTO
     * 
     */
	
	public SolicitudDTO (SolicitudEntity solicitudEntity)
	{
		if (solicitudEntity != null)
		{
			this.idSolicitud = solicitudEntity.getIdSolicitud();
                        this.fechaInicio = solicitudEntity.getFechaInicio();
                        this.fechaEntrega = solicitudEntity.getFechaEntrega();
                        this.estado = solicitudEntity.getEstado();  
                        this.tipoSolicitud = solicitudEntity.getTipoSolicitud();
		}
	}
	
	/**
     * Conviertir DTO a Entity (Crea un nuevo Entity con los valores que posee el DTO).
	 * @return Una PropuestaEntity con los valores que posee el DTO.
     */
	
	public SolicitudEntity toEntity()
	{
		SolicitudEntity solicitudEntity = new SolicitudEntity();
		solicitudEntity.setId(idSolicitud);
		return solicitudEntity;
	}	
	
	//@Override
    //public String toString() {
      //  return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    //}
	
	//===================================================
	//Metodos
	//===================================================

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(Integer tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }
	
	
		
}
