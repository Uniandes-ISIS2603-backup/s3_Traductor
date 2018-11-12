/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
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
    private List<InvitacionDTO> invitaciones;
    private List<PropuestaDTO> propuestas;

    public List<TarjetaDeCreditoDTO> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<TarjetaDeCreditoDTO> tarjetas) {
        this.tarjetas = tarjetas;
    }

    public List<InvitacionDTO> getInvitaciones() {
        return invitaciones;
    }

    public void setInvitaciones(List<InvitacionDTO> invitaciones) {
        this.invitaciones = invitaciones;
    }

    public List<PropuestaDTO> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(List<PropuestaDTO> propuestas) {
        this.propuestas = propuestas;
    }

    public List<PagosDTO> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagosDTO> pagos) {
        this.pagos = pagos;
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
        super();
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
            if (clienteEntity.getInvitaciones()!= null) {
                invitaciones = new ArrayList<>();
                for (InvitacionEntity entityInvitacion : clienteEntity.getInvitaciones()) {
                    invitaciones.add(new InvitacionDTO(entityInvitacion));
                }
            }
            if (clienteEntity.getPropuestas()!= null) {
                propuestas = new ArrayList<>();
                for (PropuestaEntity entityPropuesta : clienteEntity.getPropuestas()) {
                    propuestas.add(new PropuestaDTO(entityPropuesta));
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
    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = super.toEntity();
        if (pagos != null) {
            List<PagosEntity> pagosEntity = new ArrayList<>();
            for (PagosDTO dtoPago : getPagos()) {
                pagosEntity.add(dtoPago.toEntity());
            }
            clienteEntity.setPagos(pagosEntity);
        }
        if (solicitudes != null) {
            List<SolicitudEntity> solicitudesEntity = new ArrayList<>();
            for (SolicitudDTO dtoSolicitud : getSolicitudes()) {
                solicitudesEntity.add(dtoSolicitud.toEntity());
            }
            clienteEntity.setSolicitudes(solicitudesEntity);
        }
        if (tarjetas != null) {
            List<TarjetaDeCreditoEntity> tarjetasEntity = new ArrayList<>();
            for (TarjetaDeCreditoDTO dtoTarjeta : getTarjetas()) {
                tarjetasEntity.add(dtoTarjeta.toEntity());
            }
            clienteEntity.setTarjetas(tarjetasEntity);
        }
        if (invitaciones != null) {
            List<InvitacionEntity> invitacionesEntity = new ArrayList<>();
            for (InvitacionDTO dtoInvitacion : getInvitaciones()) {
                invitacionesEntity.add(dtoInvitacion.toEntity());
            }
            clienteEntity.setInvitaciones(invitacionesEntity);
        }
        if (propuestas != null) {
            List<PropuestaEntity> propuestasEntity = new ArrayList<>();
            for (PropuestaDTO dtoPropuesta : getPropuestas()) {
                propuestasEntity.add(dtoPropuesta.toEntity());
            }
            clienteEntity.setPropuestas(propuestasEntity);
        }
        return clienteEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
