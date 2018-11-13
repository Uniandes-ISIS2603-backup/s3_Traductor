/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.SolicitudDTO;
import co.edu.uniandes.csw.traductor.ejb.SolicitudLogic;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Jhonattan Fonseca
 */
@Path("solicitudes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SolicitudResource {

    private static final Logger LOGGER = Logger.getLogger(SolicitudResource.class.getName());

    @Inject
    private SolicitudLogic solicitudLogic;

    @POST
    public SolicitudDTO createSolicitud(SolicitudDTO solicitud) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudResource createSolicitud: input: {0}", solicitud.toString());
        SolicitudEntity solicitudEntity = solicitud.toEntity();

        SolicitudEntity nuevaSolicitudEntity = solicitudLogic.createSolicitud(solicitudEntity);
        SolicitudDTO nuevaSolicitudDTO = new SolicitudDTO(nuevaSolicitudEntity);
        LOGGER.log(Level.INFO, "Solicitudesource createSolicitud: output: {0}", solicitud.toString());
        return nuevaSolicitudDTO;
    }

    @GET
    public List<SolicitudDTO> getSolicitudes() throws BusinessLogicException {
        LOGGER.info("SolicitudResource getSolicitudes: input: void");
        List<SolicitudDTO> respuesta = listEntity2DTO(solicitudLogic.getSolicitudes());
        LOGGER.log(Level.INFO, "SolicitudResource getSolicitudes: output: {0}", respuesta.toString());
        return respuesta;
    }

    @GET
    @Path("{SolicitudId: \\d+}")
    public SolicitudDTO getSolicitud(@PathParam("SolicitudId") Long SolicitudId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "SolicitudResource getSolicitud: input: {0}", SolicitudId);
        SolicitudEntity solicitudEntity = solicitudLogic.getSolicitud(SolicitudId);
        if (solicitudEntity == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + SolicitudId + "no existe.", 404);
        }
        SolicitudDTO obtenido = new SolicitudDTO(solicitudEntity);
        LOGGER.log(Level.INFO, "SolicitudResource getSolicitud: onput: {0}", SolicitudId);

        return obtenido;
    }

    @PUT
    @Path("{SolicitudId: \\d+}")
    public SolicitudDTO updateSolicitud(@PathParam("SolicitudId") Long id, SolicitudDTO nueva) {
        LOGGER.log(Level.INFO, "SolicitudResouce updateSolicitud: input --> solicitudId: {0}", id);
        solicitudLogic.getSolicitud(id);
        if (solicitudLogic.getSolicitud(id) == null) {
            throw new WebApplicationException("La solicitud /solicitudes/" + id + "no existe.", 404);
        }
        //COMENTARIO
        SolicitudDTO actualizada = new SolicitudDTO(solicitudLogic.updateSolicitud(id, nueva.toEntity()));
        LOGGER.log(Level.INFO, "clienteResource updateCliente: output: {0}", actualizada.toString());
        return actualizada;
    }

    @DELETE
    @Path("{SolicitudId: \\d+}")
    public void deleteSolicitud(@PathParam("SolicitudId") Long solicitudId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudResource deleteSolicitud: input: {0}", solicitudId);
        SolicitudEntity solicitudEntity = solicitudLogic.getSolicitud(solicitudId);
        if (solicitudEntity == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitudId + "no existe", 404);
        }

        solicitudLogic.deleteSolicitud(solicitudId);
        LOGGER.log(Level.INFO, "SolicitudResource deleteSolicitud: input: {0}", solicitudId);
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos SolicitudEntity a una lista de
     * objetos SolicitudDTO (json)
     *
     * @param entityList corresponde a la lista de solicitudes de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de solicitudes en forma DTO (json)
     */
    private List<SolicitudDTO> listEntity2DTO(List<SolicitudEntity> entityList) {
        List<SolicitudDTO> list = new ArrayList<>();
        for (SolicitudEntity entity : entityList) {
            list.add(new SolicitudDTO(entity));
        }
        return list;
    }
}
