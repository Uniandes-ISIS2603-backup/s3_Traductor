/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.CalificacionDTO;
import co.edu.uniandes.csw.traductor.ejb.CalificacionLogic;
import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.LinkedList;
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
 * Clase que implementa el recurso "calificaciones"
 *
 * @author Santiago Salazar
 */
@Produces("application/json")
@Consumes("application/json")
public class CalificacionResource {

    
    //Logger
    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());

    //Inyeccion de la logica
    @Inject
    private CalificacionLogic calificacionLogic;
    
    //Define la frase "no existe" en una constante para sustuirlo en los multiples lugares
    //donde se define un error, todo ello con el fin de evitar duplicados
    
    private static final String NO_EXISTE = " no existe.";
    
    //Define la frase "El recurso /empleados/" en una constante para sustuirlo en los multiples lugares
    //donde se define un error, todo ello con el fin de evitar duplicados
    
    private static final String RECURSO_EMPLEADO = "El recurso /empleados/";

    /**
     * Crea una nueva calificacion con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * Agosto 27 - 2018: Esta operacion solo esta puesta para retornar lo
     * recibido. Geovanny.
     *
     * @param empleadosId El id del empleado a mirar los recursos
     * @param nuevaCalificacion {@link CalificacionDTO} - La calificacion que se desea
     * guardar.
     * @return JSON {@link CalificacionDTO} - La calificacion recibida.
     */
    @POST
    public CalificacionDTO createCalificacion(@PathParam("empleadoId") Long empleadosId, CalificacionDTO nuevaCalificacion) throws BusinessLogicException {

        // TODO: [createCalificacion] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.		
        LOGGER.log(Level.INFO, "CalificacionResources createCalificacion: input: {0}", nuevaCalificacion);
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CalificacionEntity entidad = nuevaCalificacion.toEntity();
        // Invoca la lógica para crear la calificacion nueva. Ahi abajo debe ir la logica.	
        CalificacionEntity nuevaEntity = calificacionLogic.createCalificacion(empleadosId, entidad);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo.	
        CalificacionDTO respuestaDTO = new CalificacionDTO(nuevaEntity);
        LOGGER.log(Level.INFO, "CalificacionResources createCalificacion: output: {0}", respuestaDTO);
        return respuestaDTO;
    }

    /**
     * Actualiza la calificacion con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param empleadosId El empleado que tiene esas calificaciones.
     * @param calificacionId Identificador de la calificacion que se desea actualizar.
     * Este debe ser una cadena de dígitos.
     * @param calificacion {@link CalificacionDTO} La calificacion que se desea guardar.
     * @return JSON {@link CalificacionDTO} - La editorial guardada.
     * @throws BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion a
     * actualizar.
     */
    @PUT
    @Path("{calificacionId: \\d+}") //Es la forma como se va a reconocer lo contenido en la calificacion, en este caso es 1 o mas numeros.
    public CalificacionDTO updateCalificacion(@PathParam("empleadoId") Long empleadosId, @PathParam("calificacionId") Long calificacionId, CalificacionDTO calificacion) throws BusinessLogicException {
        // TODO: [updateCalificacion] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        LOGGER.log(Level.INFO, "calificacionResource updateCalificacion: input: empleadoId: {0} , calificacionId: {1} , calificacion:{2}", new Object[]{empleadosId, calificacionId, calificacion});
        calificacion.setId(calificacionId); //Cambiar el ID de la calificacion.        
        CalificacionEntity entity = calificacionLogic.getCalificacion(empleadosId, calificacionId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO_EMPLEADO + empleadosId + "/calificaciones/" + calificacionId + NO_EXISTE, 404);
        }
        CalificacionDTO calificacionDTO = new CalificacionDTO(calificacionLogic.updateCalificacion(empleadosId, calificacion.toEntity()));
        LOGGER.log(Level.INFO, "ReviewResource updateReview: output:{0}", calificacionDTO);
        return calificacionDTO;
    }

    /**
     * Busca y devuelve todas las calificacion que posee el empleado.
     *
     * @param empleadoId Identificacion del empleado.
     * @return JSONArray {@link CalificacionDTO} - Las calificaciones que posee el
     * empleado Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<CalificacionDTO> getAllCalificaciones(@PathParam("empleadoId") Long empleadoId) {
        // TODO: [getAllCalificaciones] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.

        LOGGER.info("CalificacionResources getAllCalificaciones: input: void");
        List<CalificacionDTO> listaCalificaciones = listEntity2DetailDTO(calificacionLogic.getAllCalificaciones(empleadoId)); //Se llama a la logica para que devuelva la lista !
        LOGGER.log(Level.INFO, "CalificacionResources getAllCalificaciones:: output: {0}", listaCalificaciones);
        return listaCalificaciones;
    }

    /**
     * Busca la calificacion con el id asociado recibido en la URL y la devuelve.
     *
     * @param empleadoId Identificacion del empleado
     * @param calificacionId Identificador de la calificacion que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link CalificacionDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("empleadoId") Long empleadoId, @PathParam("calificacionId") Long calificacionId) throws WebApplicationException {

        // TODO: [getCalificaciones] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        LOGGER.log(Level.INFO, "CalificacionResources getCalificacion: input: {0}", calificacionId);
        CalificacionEntity entity = calificacionLogic.getCalificacion(empleadoId, calificacionId); //DTO respuesta.	
        if (entity == null) {
            throw new WebApplicationException(RECURSO_EMPLEADO + empleadoId + "/calificaciones/" + calificacionId + NO_EXISTE, 404);
        }

        CalificacionDTO entityBuscada = new CalificacionDTO(entity);
        LOGGER.log(Level.INFO, "CalificacionResources getCalificacion: output: {0}", entityBuscada);
        return entityBuscada;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos CalificacionEntity a una lista de
     * objetos CalificacionDTO (json)
     *
     * @param calificacionList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<CalificacionDTO> listEntity2DetailDTO(List<CalificacionEntity> calificacionList) {
        List<CalificacionDTO> list = new LinkedList<>();
        for (CalificacionEntity entity : calificacionList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
}
