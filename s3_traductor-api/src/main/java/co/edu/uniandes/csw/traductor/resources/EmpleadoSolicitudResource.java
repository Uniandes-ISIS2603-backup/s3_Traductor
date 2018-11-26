/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.SolicitudDTO;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoLogic;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoSolicitudLogic;
import co.edu.uniandes.csw.traductor.ejb.SolicitudLogic;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Jhonattan
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmpleadoSolicitudResource {
    
    private static final Logger LOGGER = Logger.getLogger(EmpleadoCalificacionResource.class.getName());
    //@Inject
    //private EmpleadoLogic empleadoLogic
    @Inject
    private SolicitudLogic solicitudLogica;
    @Inject
    private EmpleadoLogic empleadoLogic;
    
    @Inject
    private EmpleadoSolicitudLogic empleadoSolicitudLogic;
    
    //Define la frase "no existe" en una constante para sustuirlo en los multiples lugares
    //donde se define un error, todo ello con el fin de evitar duplicados
    
    private static final String NO_EXISTE = " no existe.";

    /**
     * agregar una calificaicon corespondiente al empleado
     *
     * @param empleadoId identificador del empleado al que se le agregara la
     * calificacion
     * @param solicitudId identificador de la calificacion a asignar
     * @return el dto de la calificaion asignada
     */
    @POST
    @Path("(solicitudId: \\d+")
    
    public SolicitudDTO addSolicitud(@PathParam("solicitudId") Long empleadoId, @PathParam("solicitudId") Long solicitudId) {
        LOGGER.log(Level.INFO, "EmpleadoSolicitud addSolicitud: input: empleadoId {0}, solicitudId: {1}", new Object[]{empleadoId, solicitudId});
        if (solicitudLogica.getSolicitud(solicitudId) == null) {
            throw new WebApplicationException("El recurso /solicitudes/" + solicitudId + NO_EXISTE, 404);
        }
        SolicitudDTO solicitud = new SolicitudDTO(empleadoSolicitudLogic.addSolicitud(solicitudId, empleadoId));
        LOGGER.log(Level.INFO, "EmpleadoSolicitud addCalificaion: output: {0}", solicitud);
        return solicitud;
    }

    /**
     * Obtener las solicitudes asociadas a un empleado
     *
     * @return lista de los dto de las solicitudes asignadas a un empleado
     */
    @GET
    public List<SolicitudDTO> getSolicitudes(@PathParam("id") Long empleadoId) {
        LOGGER.log(Level.INFO, "EmpleadoCalificacionResource getCalificaciones: input: {0}", empleadoId);
        EmpleadoEntity empleada = empleadoLogic.getEmpleado(empleadoId);
        if (empleada == null) {
            throw new WebApplicationException("El empleado con id : " + empleadoId + NO_EXISTE, 404);
        }
        List<SolicitudDTO> solicitudesDTO = solicitudesListEntity2DTO(empleada.getSolicitudes());

        return solicitudesDTO;
    }
    
    @GET
    @Path("{solicitudId: \\d+}")
    public SolicitudDTO getSolicitud(@PathParam("EmpleadoId") Long empleadoId, @PathParam("solicitudId") Long solicitudId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EmpleadoSolicitudResoruce getSolicitud: input: empleadoId: {0} , solicitudId: {1}", new Object[]{empleadoId, solicitudId});
        if (solicitudLogica.getSolicitud(solicitudId) == null) {
            throw new WebApplicationException("El recurso /empleado/" + empleadoId + "/solicitudes/" + solicitudId + NO_EXISTE, 404);
        }
        SolicitudDTO solicitudDTO = new SolicitudDTO(empleadoSolicitudLogic.getSolicitud(empleadoId, solicitudId));
        LOGGER.log(Level.INFO, "EmpleadoSolicitudResoruce getSolicitud: output: {0}");
        return solicitudDTO;
    }
    
    /**
     * Elimina una solicitud de la lista de solicitudes de un empleado.
     * @param empleadoId empleado a buscar.
     * @param solicitudId solicitud a eliminar.
     * @throws BusinessLogicException 
     */
    @DELETE
    @Path("{solicitudId: \\d+}")
    public void deleteSolicitud(@PathParam("EmpleadoId") Long empleadoId, @PathParam("solicitudId") Long solicitudId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "EmpleadoSolicitudResoruce deleteSolicitud: input: empleadoId {0}, solidicutdId: {1}", new Object[]{empleadoId, solicitudId});
        EmpleadoEntity empleada = empleadoLogic.getEmpleado(empleadoId);
        if (empleada == null) {
            throw new WebApplicationException("El empleado con id: " + empleadoId + NO_EXISTE, 404);
            
        }
        SolicitudEntity soli = solicitudLogica.getSolicitud(solicitudId);
        int index = empleada.getSolicitudes().indexOf(soli);
        if(index >= 0)
        {
          empleada.getSolicitudes().remove(index);
        }
        solicitudLogica.deleteSolicitud(solicitudId);
    }

    /**
     * Convierte una lista de SolicitudEntity a una lista de SolciitudDetailDTO.
     *
     * @param solicitudEntity List, Lista de BookEntity a convertir.
     * @return Lista de BookDTO convertida.
     */
    private List<SolicitudDTO> solicitudesListEntity2DTO(List<SolicitudEntity> entityList) {
        List<SolicitudDTO> list = new ArrayList();
        for (SolicitudEntity entity : entityList) {
            list.add(new SolicitudDTO(entity));
        }
        return list;
    }
    
}
