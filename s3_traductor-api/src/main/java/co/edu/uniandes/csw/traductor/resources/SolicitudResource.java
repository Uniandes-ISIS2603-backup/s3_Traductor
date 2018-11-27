/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.*;
import co.edu.uniandes.csw.traductor.ejb.SolicitudLogic;
import co.edu.uniandes.csw.traductor.entities.*;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.*;
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
 * Clase que implementa el recurso solicitud
 *
 * @author Alvaro Ayte
 */
@Path("solicitudes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SolicitudResource {

    //Logger
    private static final Logger LOGGER = Logger.getLogger(SolicitudResource.class.getName());

    //Inyeccion de Logica
    @Inject
    private SolicitudLogic solicitudLogic;
    
    //Define la frase "/solicitudes/" en una constante para sustuirlo en los multiples lugares
    //donde se define un error, todo ello con el fin de evitar duplicados
    
    private static final String SOLICITUDES = "/solicitudes/";

    //Define la frase "no existe" en una constante para sustuirlo en los multiples lugares
    //donde se define un error, todo ello con el fin de evitar duplicados
    
    private static final String NO_EXISTE = " no existe.";
    
    
    /**
     * Crea una nueva solicitud con la informacion que se recibe en el cuerpo
     * de la petición y se regresa un objeto identico con un id auto-generado
     * por la base de datos.
     *
     * Agosto 27 - 2018: Esta operacion solo esta puesta para retornar lo
     * recibido. Geovanny.
     *
     * @param clienteId Es el identificador del cliente a agregar la solicitud.
     * @param nuevaSolicitud {@link SolicitudDTO} - La solicitud que se desea
     * guardar.
     * @return JSON {@link SolicitudDTO} - La solicitud recibida.
     */
    @POST
    public SolicitudDTO createSolicitud(@PathParam("clientesId") Long clienteId, SolicitudDTO nuevaSolicitud) throws BusinessLogicException {

        // TODO:[createSolicitud] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        //Llamado al Logger, no se para que sirve :(
        LOGGER.log(Level.INFO, "SolicitudResources createSolicitud: input: {0}", nuevaSolicitud);

        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        SolicitudEntity solicitudEntity = nuevaSolicitud.toEntity();
        // Invoca la lógica para crear la solicitud nueva. Ahi abajo debe ir la logica.
        SolicitudEntity nuevaEntity = solicitudLogic.createSolicitud(clienteId, solicitudEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo.		        
        SolicitudDTO respuestaDTO = new SolicitudDTO(nuevaEntity);
        LOGGER.log(Level.INFO, "SolicitudResources createSolicitud: output: {0}", respuestaDTO);
        return respuestaDTO;
    }

    /**
     * Actualiza la solicitud con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param clienteId Identificacion del cliente.
     * @param idSolicitud Identificador de la solicitud que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param solicitud {@link SolicitudDTO} La solicitud que se desea
     * guardar.
     * @return JSON {@link SolicitudDTO} - La editorial guardada.
     * @throws co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException
     * Si hay errores en los datos de entrada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la solicitud a
     * actualizar.
     */
    @PUT
    @Path("{idSolicitud: \\d+}") //Es la forma como se va a reconocer lo contenido en la solicitud, en este caso es 1 o mas numeros.
    public SolicitudDTO updateSolicitud(@PathParam("clientesId") Long clienteId, @PathParam("idSolicitud") Long idSolicitud, SolicitudDTO solicitud) throws BusinessLogicException {
        // TODO: [updateSolicitud] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        LOGGER.log(Level.INFO, "SolicitudResource updateSolicitud: input: clienteId: {0} , solicitudId: {1} , review:{2}", new Object[]{clienteId, idSolicitud, solicitud});        
        SolicitudEntity entity = solicitudLogic.getSolicitud(clienteId, idSolicitud);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + SOLICITUDES + idSolicitud + NO_EXISTE, 404);
        }

        SolicitudDTO reviewDTO = new SolicitudDTO(solicitudLogic.updateSolicitud(clienteId, solicitud.toEntity()));
        LOGGER.log(Level.INFO, "SolicitudResource updateSolicitud: output:{0}", reviewDTO);
        return reviewDTO;
    }

    /**
     * Busca y devuelve todas las solicitudes que posee el cliente.
     *
     * @param clienteId Es el identificador del cliente a agregar la solicitud.
     * @return JSONArray {@link SolicitudDTO} - Las solicitudes que posee el
     * cliente. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<SolicitudDTO> getAllSolicitudes(@PathParam("clientesId") Long clienteId) {
        // TODO: [getAllSolicitudes] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.		
        LOGGER.info("SolicitudResources getAllSolicitudes: input: void");
        List<SolicitudDTO> listaSolicitudes = listEntity2DetailDTO(solicitudLogic.getAllSolicitudes(clienteId)); //Se llama a la logica para que devuelva la lista !
        LOGGER.log(Level.INFO, "SolicitudResources getAllSolicitudes: output: {0}", listaSolicitudes);
        return listaSolicitudes;
    }

    /**
     * Busca la solicitud con el id asociado recibido en la URL y la devuelve.
     *
     * @param clienteId Identificador del cliente.
     * @param solicitudId Identificador de la solicitud que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link SolicitudDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{idSolicitud: \\d+}")
    public SolicitudDTO getSolicitud(@PathParam("clientesId") Long clienteId, @PathParam("idSolicitud") Long solicitudId) throws WebApplicationException {

        // TODO: [getSolicitud] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        LOGGER.log(Level.INFO, "SolicitudResources getSolicitud: input: {0}", solicitudId);
        SolicitudEntity entity = solicitudLogic.getSolicitud(clienteId, solicitudId); //DTO respuesta.	
        if (entity == null) {
            throw new WebApplicationException("El recurso /cliente/" + clienteId + SOLICITUDES + solicitudId + NO_EXISTE, 404);
        }
        SolicitudDTO entityBuscada = new SolicitudDTO(entity);
        LOGGER.log(Level.INFO, "SolicitudResources getSolicitud: output: {0}", entityBuscada);
        return entityBuscada;
    }

    /**
     * Borra la solicitud con el id asociado recibido en la URL.
     *
     * @param clienteId Identificador del cliente.
     * @param solicitudId Identificador de la solicitud que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException Error de lógica que se genera cuando no se
     * encuentra la editorial.
     */
    @DELETE
    @Path("{idSolicitud: \\d+}")
    public void deleteSolicitud(@PathParam("clientesId") Long clienteId, @PathParam("idSolicitud") Long solicitudId) throws BusinessLogicException {

        // TODO: [deleteSolicitud] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.		
        LOGGER.log(Level.INFO, "SolicitudResources deleteSolicitud: input: {0}", solicitudId);

        SolicitudEntity entity = solicitudLogic.getSolicitud(clienteId, solicitudId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + SOLICITUDES + solicitudId + NO_EXISTE, 404);
        }

        solicitudLogic.deleteSolicitud(clienteId, solicitudId);
        LOGGER.info("SolicitudResources deleteSolicitud: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos SolicitudEntity a una lista
     * de objetos SolicitudDTO (json)
     *
     * @param solicitudList corresponde a la lista de editoriales de tipo
     * Entity que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<SolicitudDTO> listEntity2DetailDTO(List<SolicitudEntity> solicitudList) {
        List<SolicitudDTO> list = new LinkedList<>();
        for (SolicitudEntity entity : solicitudList) {
            list.add(new SolicitudDTO(entity));
        }
        return list;
    }
}
