/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.entities.TarjetaDeCreditoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase de detalles del DTO Cliente.
 * @author Santiago Salazar
 */
public class ClienteDetailDTO extends ClienteDTO implements Serializable
{
    private List<TarjetaDeCreditoDTO> tarjetas;
    private List<SolicitudDTO> solicitudes;
    private List<PagosDTO> pagos;

    public List<PagosDTO> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagosDTO> pagos) {
        this.pagos = pagos;
    }

    public List<TarjetaDeCreditoDTO> getTarjetasDeCredito() {
        return tarjetas;
    }

    public void setTarjetasDeCredito(List<TarjetaDeCreditoDTO> tarjetasDeCredito) {
        this.tarjetas = tarjetasDeCredito;
    }

    public List<SolicitudDTO> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudDTO> solicitudes) {
        this.solicitudes = solicitudes;
    }
    
    /**
     * Constructor por defecto
     */
    public ClienteDetailDTO(){
    }
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param clienteEntity La entidad del cliente para transformar a DTO.
     */
    public ClienteDetailDTO (ClienteEntity clienteEntity)
    {
        super(clienteEntity);

        if (clienteEntity != null) {
            if (clienteEntity.getTarjetas() != null) {
                tarjetas = new ArrayList<>();
                for (TarjetaDeCreditoEntity entityTarjeta : clienteEntity.getTarjetas()) {
                    tarjetas.add(new TarjetaDeCreditoDTO(entityTarjeta));
                }
            }
            if (clienteEntity.getSolicitudes() != null) {
                solicitudes = new ArrayList<>();
                for (SolicitudEntity entitySolicitudes : clienteEntity.getSolicitudes()) {
                    solicitudes.add(new SolicitudDTO(entitySolicitudes));
                }
            }
            if (clienteEntity.getPagos() != null) {
                pagos = new ArrayList<>();
                for (PagosEntity entityPagos : clienteEntity.getPagos()) {
                    pagos.add(new PagosDTO(entityPagos));
                }
            }
        }
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
