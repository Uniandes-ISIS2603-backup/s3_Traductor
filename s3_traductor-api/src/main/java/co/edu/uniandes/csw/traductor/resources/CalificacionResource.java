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
 *
 * @author Santiago Salazar
 */
@Path("calificaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CalificacionResource {

    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());

    //inyección de la dependencia de lógica: calificacionLogic
    private CalificacionLogic calificacionLogic;

    /**
     * Busca y devuelve todas las calificaciones que existen en la aplicacion.
     *
     * @return JSONArray {@link CalificacionDTO} - Las calificaciones
     * encontrados en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<CalificacionDTO> getCalificaciones() {
        LOGGER.info("CalificacionResource getCalificaciones: input: void");
        List<CalificacionDTO> listaCalificaciones = listEntity2DTO(calificacionLogic.getCalificaciones());
        LOGGER.log(Level.INFO, "CalificacionResource getCalificaciones: output: {0}", listaCalificaciones.toString());
        return listaCalificaciones;
    }

    /**
     * Busca la calificacion con el id en la URL y lo retorna
     *
     * @param calificacionesId Identificador numerico de la calificacion que se
     * esta buscando
     * @return JSON {@link CalificacionDTO} - La calificacion buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @GET
    @Path("{calificacionesId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("calificacionesId") Long calificacionesId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: input: {0}", calificacionesId);
        CalificacionEntity calificacionEntity = calificacionLogic.getCalificacion(calificacionesId);
        if (calificacionEntity == null) {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionesId + " no existe.", 404);
        }
        CalificacionDTO dtoCalificacion = new CalificacionDTO(calificacionEntity);
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", dtoCalificacion.toString());
        return dtoCalificacion;
    }

    /**
     * Crea la calificacion segun el objeto JSON recibido y retorna la misma
     * calificacion con el id generado por el sistema
     *
     * @param calificacion {@link CalificacionDTO} - La calificacion que se
     * quiere crear
     * @return JSON {@link CalificacionDTO} - la calificacion creada con el id
     * autogenerado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe una calificacion o el
     * valor de la calificacion es invalido
     */
    @POST
    public CalificacionDTO createCalificacion(CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: input: {0}", calificacion.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        CalificacionEntity calificacionEntity = calificacion.toEntity();
        // Invoca la lógica para crear la calificacion nueva
        CalificacionEntity nuevaCalificacionEntity = calificacionLogic.createCalificacion(calificacionEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        CalificacionDTO nuevaCalificacionDTO = new CalificacionDTO(nuevaCalificacionEntity);
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: output: {0}", nuevaCalificacionDTO.toString());
        return nuevaCalificacionDTO;
    }

//    /**
//     * Actualiza la calificacion con el id recibido en la URL con la informacion
//     * que se recibe en el cuerpo de la petición.
//     *
//     * @param calificacionesId Identificador de la calificacion que se desea
//     * actualizar. Este debe ser una cadena de dígitos.
//     * @param calificacion {@link CalificacionDTO} La calificacion que se desea
//     * guardar.
//     * @return JSON {@link CalificacionDTO} - La calificacion guardada.
//     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
//     * Error de lógica que se genera cuando no se encuentra la calificacion a
//     * actualizar.
//     */
//    @PUT
//    @Path("{calificacionesId: \\d+}")
//    public CalificacionDTO updateCalificacion(@PathParam("calificacionesId") Long calificacionesId, CalificacionDTO calificacion) throws WebApplicationException {
//        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input: id:{0} , calificacion: {1}", new Object[]{calificacionesId, calificacion.toString()});
//        if (calificacionLogic.getCalificacion(calificacionesId) == null) {
//            throw new WebApplicationException("El recurso /clientes/" + calificacionesId + " no existe.", 404);
//        }
//        CalificacionDTO detailDTO = new CalificacionDTO(calificacionLogic.updateCalificacion(calificacionesId, calificacion.toEntity()));
//        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: output: {0}", detailDTO.toString());
//        return detailDTO;
//    }

//    /**
//     * Borra la calificacion con el id asociado recibido en la URL.
//     *
//     * @param calificacionesId Identificador de la calificacion que se desea
//     * borrar. Este debe ser una cadena de dígitos.
//     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
//     * Error de lógica que se genera cuando no se puede eliminar la
//     * calificacion.
//     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
//     * Error de lógica que se genera cuando no se encuentra la calificacion.
//     */
//    @DELETE
//    @Path("{calificacionesId: \\d+}")
//    public void deleteCalificacion(@PathParam("calificacionesId") Long calificacionesId) throws BusinessLogicException {
//        LOGGER.log(Level.INFO, "CalificacionResource deleteCalificacion: input: {0}", calificacionesId);
//        if (calificacionLogic.getCalificacion(calificacionesId) == null) {
//            throw new WebApplicationException("El recurso /calificaciones/" + calificacionesId + " no existe.", 404);
//        }
//        calificacionLogic.deleteCalificacion(calificacionesId);
//        LOGGER.info("CalificacionResource deleteCalificacion: output: void");
//    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos CalificacionEntity a una lista
     * de objetos CalificacionDTO (json)
     *
     * @param entityList corresponde a la lista de calificaciones de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de calificaciones en forma DTO (json)
     */
    private List<CalificacionDTO> listEntity2DTO(List<CalificacionEntity> entityList) {
        List<CalificacionDTO> list = new ArrayList<>();
        for (CalificacionEntity entity : entityList) {
            list.add(new CalificacionDTO(entity));
        }
        return list;
    }
}
