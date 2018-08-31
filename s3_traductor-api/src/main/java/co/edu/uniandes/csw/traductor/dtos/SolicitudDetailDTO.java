/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import java.util.List;

/**
 *
 * @author jhonattanfonseca
 */
public class SolicitudDetailDTO extends SolicitudDTO

{
    
private List<DocumentoDTO> documentos;



public SolicitudDetailDTO()
{
    
}



    public List<DocumentoDTO> getDocumentos() {
        return documentos;
    }
    
    public void setDocumentos(List<DocumentoDTO> documentos) {
        this.documentos = documentos;
    }


    
}
