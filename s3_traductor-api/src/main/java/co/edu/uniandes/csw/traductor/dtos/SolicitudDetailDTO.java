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
    
private IdiomaDTO idiomaEntrada;

private IdiomaDTO idiomaSalida;

private List<DocumentoDTO> documentos;

private ClienteDTO cliente;

//Falta la relación con empleado.

public SolicitudDetailDTO()
{
    
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

    public List<DocumentoDTO> getDocumentos() {
        return documentos;
    }
    
    public void setDocumentos(List<DocumentoDTO> documentos) {
        this.documentos = documentos;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
    

    
}
