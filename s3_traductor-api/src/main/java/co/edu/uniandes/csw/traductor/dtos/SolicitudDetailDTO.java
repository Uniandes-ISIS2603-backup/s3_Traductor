/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.DocumentoEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jhonattanfonseca
 */
public class SolicitudDetailDTO extends SolicitudDTO implements Serializable {

    /**
     * Relacion de uno o muchos
     */
    private List<DocumentoDTO> documentos;

    public SolicitudDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     * @param solicitudEntity 
     */
    public SolicitudDetailDTO(SolicitudEntity solicitudEntity) {
        super(solicitudEntity);
        if (solicitudEntity != null) {
            if (solicitudEntity.getDocumentos() != null) {
                documentos = new ArrayList<>();
                for (DocumentoEntity docu : solicitudEntity.getDocumentos()) {
                    documentos.add(new DocumentoDTO(docu));
                }
            }
        }
    }

    
        /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el libro.
     */
    @Override
    public SolicitudEntity toEntity() {
        SolicitudEntity solicitudEnity = super.toEntity();
        if (documentos != null) {
            List<DocumentoEntity> documentosEntity = new ArrayList<>();
            for (DocumentoDTO docu : getDocumentos()) {
                documentosEntity.add(docu.toEntity());
            }
            solicitudEnity.setDocumentos((ArrayList<DocumentoEntity>) documentosEntity);
        }
      
        return solicitudEnity;
    }
    
    
    public List<DocumentoDTO> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoDTO> documentos) {
        this.documentos = documentos;
    }

}
