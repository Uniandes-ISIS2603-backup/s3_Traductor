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
        LOGGER.log(Level.INFO, "EmpleadoCalificacion addCalificacion: input: empleadoId {0}, calificaionId: {1}", new Object[]{empleadoId, calificacionId});
//        if(empleadoCalificacionLogic.) {
//            
//        }
        CalificacionDTO calififacion = new CalificacionDTO(empleadoCalificacionLogic.addCalificacion(empleadoId, calificacionId));
        LOGGER.log(Level.INFO, "EmpleadoCalificacion addCalificaion: output: {0}", calififacion.toString());
        return calififacion;
    }

    /**
     * Obtener las calificaciones asociadas a un empleado
     *
     * @return lista de los dto de las calififcaciones asignadas a un empleado
     */
    @GET
    public List<CalificacionDTO> getCalificaciones(@PathParam("id") Long empleadoId) {
        LOGGER.log(Level.INFO, "EmpleadoCalificacionResource getCalificaciones: input: {0}", empleadoId);
        //List<CalificacionDTO> calificaciones= calificaciones(empleadoLogic.getCalificaciones(empleadoId));
        //LOGGER.log(Level.INFO,"EmpleadoCalificacionResource getCalificaciones: output: {0}",calififcaciones.toString());
        //return calificaciones;

        return null;
    }

    private List<CalificacionDTO> calificaciones(List<CalificacionEntity> listaEntities) {
        List<CalificacionDTO> lista = new ArrayList<>();
        for (CalificacionEntity a : listaEntities) {
            lista.add(new CalificacionDTO(a));
        }
        return lista;
    }

}
