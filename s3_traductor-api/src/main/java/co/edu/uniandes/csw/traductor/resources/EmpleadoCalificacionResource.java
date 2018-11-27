/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.CalificacionDTO;
import co.edu.uniandes.csw.traductor.ejb.CalificacionLogic;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoCalificacionLogic;
import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
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
 * clase que representa el recurso empleado/{id}/calificacion
 *
 * @author Juan Felipe Parra
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmpleadoCalificacionResource {

    private static final Logger LOGGER = Logger.getLogger(EmpleadoCalificacionResource.class.getName());
    //@Inject
    //private EmpleadoLogic empleadoLogic
    @Inject
    private CalificacionLogic calificacionLogic;
    @Inject
    private EmpleadoCalificacionLogic empleadoCalificacionLogic;
    
    //Define la frase "no existe" en una constante para sustuirlo en los multiples lugares
    //donde se define un error, todo ello con el fin de evitar duplicados
    
    private static final String NO_EXISTE = " no existe.";

    /**
     * agregar una calificaicon corespondiente al empleado
     *
     * @param empleadoId identificador del empleado al que se le agregara la
     * calificacion
     * @param calificacionId identificador de la calificacion a asignar
     * @return el dto de la calificaion asignada
     */
    @POST
    @Path("(calificacionId: \\d+")

    public CalificacionDTO addCalificacion(@PathParam("empleadoId") Long empleadoId, @PathParam("calificacionId") Long calificacionId) {
        LOGGER.log(Level.INFO, "EmpleadoCalificacionResource addCalificacion: input: empleadoID: {0} , calificacionID: {1}", new Object[]{empleadoId, calificacionId});
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionId + NO_EXISTE, 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(empleadoCalificacionLogic.addCalificacion(empleadoId, calificacionId));
        LOGGER.log(Level.INFO, "EEmpleadoCalificacionResource addCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }

    /**
     * Obtener las calificaciones asociadas a un empleado
     *
     * @return lista de los dto de las calififcaciones asignadas a un empleado
     */
    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("empleadoId") Long empleadoId) {
        LOGGER.log(Level.INFO, "EmpleadoCalificacionResource getCalificaciones: input: {0}", empleadoId);
        List<CalificacionDTO> listaDTOs = calificacionEntiryToDTO(empleadoCalificacionLogic.getCalificaciones(empleadoId));
        LOGGER.log(Level.INFO, "EmpleadoCalificacionResource getCalificaciones: output: {0}", listaDTOs);
        return listaDTOs;
    }
    @GET
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("empleadoId") Long empleadoId, @PathParam("calificacionId") Long calificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EmpleadoCalificacionResource getCalificacion: input: empleadoId: {0} , calificacionId: {1}", new Object[]{empleadoId, calificacionId});
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /empleados/" + empleadoId + "/calificaciones/" + calificacionId + NO_EXISTE, 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(empleadoCalificacionLogic.getCalificacion(empleadoId, calificacionId));
        LOGGER.log(Level.INFO, "EmpleadoCalificacionResource getBook: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
    /**
     * Delete
     * @param empleadoId
     * @param calificacionId
     * @throws BusinessLogicException 
     */
    @DELETE
    @Path("{calificacionId: \\d+}")
    public void deleteCalificacion(@PathParam("empleadoId") Long empleadoId, @PathParam("calificacionId") Long calificacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EmpleadoCalificacionResource deleteCalificacion: input: {0}", empleadoId);
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionId + NO_EXISTE, 404);
        }
        empleadoCalificacionLogic.deleteCalificacion(empleadoId, calificacionId);
        LOGGER.info("EmpleadoCalificacionResource deleteCalificacion: output: void");
    }
    private List<CalificacionDTO> calificacionEntiryToDTO(List<CalificacionEntity> listaEntities) {
        List<CalificacionDTO> lista = new ArrayList<>();
        for (CalificacionEntity a : listaEntities) {
            lista.add(new CalificacionDTO(a));
        }
        return lista;
    }

}
