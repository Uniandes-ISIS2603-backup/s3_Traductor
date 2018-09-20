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

    private List<DocumentoDTO> documentos;

    public SolicitudDetailDTO() {
    }

    public SolicitudDetailDTO(SolicitudEntity solicitudEntity) {
        super();
        if(solicitudEntity != null)
        {
            if(solicitudEntity.getDocumentos() != null)
            {
                documentos = new ArrayList<>();
                for(DocumentoEntity docu : solicitudEntity.getDocumentos())
                {
                    documentos.add(new DocumentoDTO(docu));
                }
            }
        }
    }

    public List<DocumentoDTO> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoDTO> documentos) {
        this.documentos = documentos;
    }

}
