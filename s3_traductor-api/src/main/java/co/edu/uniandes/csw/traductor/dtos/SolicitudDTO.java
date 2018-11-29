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
    protected String idiomaEntrada;
    protected String idiomaSalida;
    private String descripcion;
    private String archivo;
    private int longitud;

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
            this.idiomaEntrada =  solicitudEntity.getIdiomaEntrada();
            this.idiomaSalida = solicitudEntity.getIdiomaSalida();
            this.descripcion=solicitudEntity.getDescripcion();
            this.archivo=solicitudEntity.getArchivo();
            this.longitud=solicitudEntity.getLongitud();
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
        solicitudEntity.setDescripcion(this.descripcion);
        solicitudEntity.setArchivo(this.archivo);
        solicitudEntity.setLongitud(this.longitud);
            solicitudEntity.setIdiomaEntrada(this.idiomaEntrada);
            solicitudEntity.setIdiomaSalida(this.idiomaSalida);
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

    public String getIdiomaEntrada() {
        return idiomaEntrada;
    }

    public void setIdiomaEntrada(String idiomaEntrada) {
        this.idiomaEntrada = idiomaEntrada;
    }

    public String getIdiomaSalida() {
        return idiomaSalida;
    }

    public void setIdiomaSalida(String idiomaSalida) {
        this.idiomaSalida = idiomaSalida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }
    
    
    @Override
    public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
