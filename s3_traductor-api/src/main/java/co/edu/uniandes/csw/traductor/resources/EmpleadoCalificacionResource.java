/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.CalificacionDTO;
import co.edu.uniandes.csw.traductor.ejb.CalificacionLogic;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * clase que representa el recurso empleado/{id}/calificacion
 * @author Juan Felipe Parra
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmpleadoCalificacionResource {
   
    //@Inject
    //private EmpleadoLogic empleadoLogic
    @Inject 
    private CalificacionLogic calificacionLogic;
    /**
     * agregar una calificaicon corespondiente al empleado
     * @param empleadoId identificador del empleado al que se le agregara la calificacion
     * @param calificacionId identificador de la calificacion a asignar
     * @return el dto de la calificaion asignada
     */
   @POST
   @Path("(calificacionId: \\d+")
   
   public CalificacionDTO addCalificacion(@PathParam("empleadoId") Long empleadoId, @PathParam("calificacionId") Long calificacionId){
       return null;
   }
   /**
    * Obtener las calificaciones asociadas a un empleado
    * @return lista de los dto de las calififcaciones asignadas a un empleado
    */
   @GET
   public List<CalificacionDTO> getCalificaciones(){
       return null;
   }
}
