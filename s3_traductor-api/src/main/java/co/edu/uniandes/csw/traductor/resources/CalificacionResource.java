/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.CalificacionDTO;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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
 * Clase que implementa el recurso "calificaciones"
 * @author Santiago Salazar
 */
@Path("calificaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CalificacionResource 
{
    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());
    
    //TODO: inyección de la dependencia de lógica: clienteLogic
    
    /**
     * Busca y retorna todas las calificaciones.
     * @return JSONAray {@link CalificacionDTO} - Las calificaciones
     */
    @GET
    public List<CalificacionDTO> getCalificaciones()
    {
        //TODO: Finalizar cascaron
        
        return null;
    }
    
    /**
     * Busca la calificacion con el id en la URL y lo retorna
     * @param calificacionesId Identificador numerico de la calificacion
     * que se esta buscando
     * @return JSON {@link ClienteDTO} - La calificacion buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("calificacionesId") Long calificacionesId) throws WebApplicationException
    {
        //TODO: Finalizar cascaron
        return null;
    }
    
    /**
     * Crea la calificacion segun el objeto JSON recibido
     * y retorna la misma calificacion con el id generado 
     * por el sistema
     * @param calificacion {@link CalificacionDTO} - La
     * calificacion que se quiere crear
     * @return JSON {@link CalificacionDTO} - la calificacion
     * creada con el id autogenerado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe una calificacion  
     */
    @POST
    public CalificacionDTO createCalificacion(CalificacionDTO calificacion) throws BusinessLogicException
    {
        //TODO: Finalizar cascaron
        return calificacion;
    }
    
    /**
     * Actualiza la calificacion con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param calificacionesId Identificador de la calificacion que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param calificacion {@link CalificacionDTO} La calificacion que se desea
     * guardar.
     * @return JSON {@link CalificacionDTO} - La calificacion guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion a
     * actualizar.
     */
    @PUT
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws WebApplicationException
    {
        //TODO: Finalizar cascaron.
        return null;
    }
    
    /**
     * Borra la calificacion con el id asociado recibido en la URL.
     *
     * @param calificacionesId Identificador de la calificacion que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la calificacion.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @DELETE
    @Path("{calificacionesId: \\d+}")
    public void deleteCalificacion(@PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException
    {
        //TODO: Finalizar cascaron
    }
}
