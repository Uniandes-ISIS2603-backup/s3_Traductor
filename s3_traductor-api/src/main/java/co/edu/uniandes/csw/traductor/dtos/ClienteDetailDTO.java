/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import java.io.Serializable;
import java.util.List;

/**
 * Clase de detalles del DTO Cliente.
 * @author Santiago Salazar
 */
public class ClienteDetailDTO extends ClienteDTO implements Serializable
{
    private List<TarjetaDeCreditoDTO> tarjetasDeCredito;
    private List<SolicitudDTO> solicitudes;

    public List<TarjetaDeCreditoDTO> getTarjetasDeCredito() {
        return tarjetasDeCredito;
    }

    public void setTarjetasDeCredito(List<TarjetaDeCreditoDTO> tarjetasDeCredito) {
        this.tarjetasDeCredito = tarjetasDeCredito;
    }

    public List<SolicitudDTO> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudDTO> solicitudes) {
        this.solicitudes = solicitudes;
    }
    
    public ClienteDetailDTO(){
        super();
    }
    
    public ClienteDetailDTO (ClienteEntity clienteEntity)
    {
        super(clienteEntity);
        //TODO: falta implementar la conversion de cliente Entity
    }
    
}
