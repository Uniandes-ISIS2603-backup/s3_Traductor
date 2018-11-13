/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.adapters.DateAdapter;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
 *              "fechaEntrega" : Date,
 *              "estado" : Integer,
 *   }
 * </pre>
 *
 * @author Jhonattan Fonseca
 */
public class SolicitudDTO implements Serializable {

    public final static Integer EN_ESPERA = 2;

    public final static Integer COMPLETADO = 1;

    public final static Integer CANCELADO = 0;

    public final static Integer CORECCION = 3;

    public final static Integer TRADUCCION = 4;

    protected Long id;
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Date fechaInicio;
    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Date fechaEntrega;
    protected Integer estado;
    protected Integer tipoSolicitud;
    protected IdiomaDTO idiomaEntrada;
    protected IdiomaDTO idiomaSalida;

    /**
     * Constructor vacio para que sea llenado por JAX-RS
     */
    public SolicitudDTO() {
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
    public SolicitudDTO(SolicitudEntity solicitudEntity) {
        if (solicitudEntity != null) {
            this.id = solicitudEntity.getId();
            this.fechaInicio = solicitudEntity.getFechaInicio();
            this.fechaEntrega = solicitudEntity.getFechaEntrega();
            this.estado = solicitudEntity.getEstado();
            this.tipoSolicitud = solicitudEntity.getTipoSolicitud();
            if (solicitudEntity.getIdiomaEntrada() != null) {
                this.idiomaEntrada = new IdiomaDTO(solicitudEntity.getIdiomaEntrada());
            }
            if (solicitudEntity.getIdiomaSalida() != null) {
                this.idiomaSalida = new IdiomaDTO(solicitudEntity.getIdiomaSalida());
            }
        }
    }

    /**
     * Conviertir DTO a Entity (Crea un nuevo Entity con los valores que posee
     * el DTO).
     *
     * @return Una PropuestaEntity con los valores que posee el DTO.
     */
    public SolicitudEntity toEntity() {
        SolicitudEntity solicitudEntity = new SolicitudEntity();
        solicitudEntity.setId(id);
        solicitudEntity.setEstado(this.estado);
        solicitudEntity.setTipoSolicitud(this.tipoSolicitud);
        solicitudEntity.setFechaInicio(this.fechaInicio);
        solicitudEntity.setFechaEntrega(this.fechaEntrega);
        if(this.idiomaEntrada != null)
        { 
            solicitudEntity.setIdiomaEntrada(this.idiomaEntrada.toEntity());
        }
        if(this.idiomaSalida != null)
        {
            solicitudEntity.setIdiomaSalida(this.idiomaSalida.toEntity());
        }
        return solicitudEntity;
    }

    //===================================================
    //Metodos
    //===================================================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public IdiomaDTO getIdiomaEntrada() {
        return idiomaEntrada;
    }

    public void setIdiomaEntrada(IdiomaDTO idiomaEntrada) {
        this.idiomaEntrada = idiomaEntrada;
    }

    public IdiomaDTO getIdiomaSalida() {
        return idiomaSalida;
    }

    public void setIdiomaSalida(IdiomaDTO idiomaSalida) {
        this.idiomaSalida = idiomaSalida;
    }
    
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
