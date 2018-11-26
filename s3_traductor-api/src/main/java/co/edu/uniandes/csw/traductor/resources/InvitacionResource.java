/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.*;
import co.edu.uniandes.csw.traductor.ejb.InvitacionLogic;
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
 * Clase que implementa el recurso invitacion
 *
 * @author Geovanny Andres Gonzalez
 */
@Path("invitaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class InvitacionResource {

    //Logger
    private static final Logger LOGGER = Logger.getLogger(InvitacionResource.class.getName());

    //Inyeccion de Logica
    @Inject
    private InvitacionLogic invitacionLogic;
    
    //Define la frase "/invitaciones/" en una constante para sustuirlo en los multiples lugares
    //donde se define un error, todo ello con el fin de evitar duplicados
    
    private static final String INVITACIONES = "/invitaciones/";

    //Define la frase "no existe" en una constante para sustuirlo en los multiples lugares
    //donde se define un error, todo ello con el fin de evitar duplicados
    
    private static final String NO_EXISTE = " no existe.";
    
    
    /**
     * Crea una nueva invitacion con la informacion que se recibe en el cuerpo
     * de la petición y se regresa un objeto identico con un id auto-generado
     * por la base de datos.
     *
     * Agosto 27 - 2018: Esta operacion solo esta puesta para retornar lo
     * recibido. Geovanny.
     *
     * @param clienteId Es el identificador del cliente a agregar la invitacion.
     * @param nuevaInvitacion {@link InvitacionDTO} - La invitacion que se desea
     * guardar.
     * @return JSON {@link InvitacionDTO} - La invitacion recibida.
     */
    @POST
    public InvitacionDTO createInvitacion(@PathParam("clientesId") Long clienteId, InvitacionDTO nuevaInvitacion) throws BusinessLogicException {

        // TODO:[createInvitacion] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        //Llamado al Logger, no se para que sirve :(
        LOGGER.log(Level.INFO, "InvitacionResources createInvitacion: input: {0}", nuevaInvitacion);

        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        InvitacionEntity invitacionEntity = nuevaInvitacion.toEntity();
        // Invoca la lógica para crear la invitacion nueva. Ahi abajo debe ir la logica.
        InvitacionEntity nuevaEntity = invitacionLogic.createInvitacion(clienteId, invitacionEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo.		        
        InvitacionDTO respuestaDTO = new InvitacionDTO(nuevaEntity);
        LOGGER.log(Level.INFO, "InvitacionResources createInvitacion: output: {0}", respuestaDTO);
        return respuestaDTO;
    }

    /**
     * Actualiza la invitacion con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param clienteId Identificacion del cliente.
     * @param idInvitacion Identificador de la invitacion que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param invitacion {@link InvitacionDTO} La invitacion que se desea
     * guardar.
     * @return JSON {@link InvitacionDTO} - La editorial guardada.
     * @throws co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException
     * Si hay errores en los datos de entrada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la invitacion a
     * actualizar.
     */
    @PUT
    @Path("{idInvitacion: \\d+}") //Es la forma como se va a reconocer lo contenido en la invitacion, en este caso es 1 o mas numeros.
    public InvitacionDTO updateInvitacion(@PathParam("clientesId") Long clienteId, @PathParam("idInvitacion") Long idInvitacion, InvitacionDTO invitacion) throws BusinessLogicException {
        // TODO: [updateInvitacion] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        LOGGER.log(Level.INFO, "InvitacionResource updateInvitacion: input: clienteId: {0} , invitacionId: {1} , review:{2}", new Object[]{clienteId, idInvitacion, invitacion});

        if (!idInvitacion.equals(invitacion.getIdInvitacion())) {
            throw new BusinessLogicException("Los ids de la invitacion no coinciden");
        }

        InvitacionEntity entity = invitacionLogic.getInvitacion(clienteId, idInvitacion);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + INVITACIONES + idInvitacion + NO_EXISTE, 404);
        }

        InvitacionDTO reviewDTO = new InvitacionDTO(invitacionLogic.updateInvitacion(clienteId, invitacion.toEntity()));
        LOGGER.log(Level.INFO, "InvitacionResource updateInvitacion: output:{0}", reviewDTO);
        return reviewDTO;
    }

    /**
     * Busca y devuelve todas las invitaciones que posee el empleado.
     *
     * @param clienteId Es el identificador del cliente a agregar la invitacion.
     * @return JSONArray {@link InvitacionDTO} - Las invitaciones que posee el
     * empleado. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<InvitacionDTO> getAllInvitaciones(@PathParam("clientesId") Long clienteId) {
        // TODO: [getAllInvitaciones] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.		
        LOGGER.info("InvitacionResources getAllInvitaciones: input: void");
        List<InvitacionDTO> listaInvitaciones = listEntity2DetailDTO(invitacionLogic.getAllInvitaciones(clienteId)); //Se llama a la logica para que devuelva la lista !
        LOGGER.log(Level.INFO, "InvitacionResources getAllInvitaciones: output: {0}", listaInvitaciones);
        return listaInvitaciones;
    }

    /**
     * Busca la invitacion con el id asociado recibido en la URL y la devuelve.
     *
     * @param clienteId Identificador del cliente.
     * @param invitacionId Identificador de la invitacion que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link InvitacionDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{idInvitacion: \\d+}")
    public InvitacionDTO getInvitacion(@PathParam("clientesId") Long clienteId, @PathParam("idInvitacion") Long invitacionId) throws WebApplicationException {

        // TODO: [getInvitacion] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        LOGGER.log(Level.INFO, "InvitacionResources getInvitacion: input: {0}", invitacionId);
        InvitacionEntity entity = invitacionLogic.getInvitacion(clienteId, invitacionId); //DTO respuesta.	
        if (entity == null) {
            throw new WebApplicationException("El recurso /cliente/" + clienteId + INVITACIONES + invitacionId + NO_EXISTE, 404);
        }
        InvitacionDTO entityBuscada = new InvitacionDTO(entity);
        LOGGER.log(Level.INFO, "InvitacionResources getInvitacion: output: {0}", entityBuscada);
        return entityBuscada;
    }

    /**
     * Borra la invitacion con el id asociado recibido en la URL.
     *
     * @param clienteId Identificador del cliente.
     * @param invitacionId Identificador de la invitacion que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException Error de lógica que se genera cuando no se
     * encuentra la editorial.
     */
    @DELETE
    @Path("{idInvitacion: \\d+}")
    public void deleteInvitacion(@PathParam("clientesId") Long clienteId, @PathParam("idInvitacion") Long invitacionId) throws BusinessLogicException {

        // TODO: [deleteInvitacion] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.		
        LOGGER.log(Level.INFO, "InvitacionResources deleteInvitacion: input: {0}", invitacionId);

        InvitacionEntity entity = invitacionLogic.getInvitacion(clienteId, invitacionId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + INVITACIONES + invitacionId + NO_EXISTE, 404);
        }

        invitacionLogic.deleteInvitacion(clienteId, invitacionId);
        LOGGER.info("InvitacionResources deleteInvitacion: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos InvitacionEntity a una lista
     * de objetos InvitacionDTO (json)
     *
     * @param invitacionList corresponde a la lista de editoriales de tipo
     * Entity que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<InvitacionDTO> listEntity2DetailDTO(List<InvitacionEntity> invitacionList) {
        List<InvitacionDTO> list = new LinkedList<>();
        for (InvitacionEntity entity : invitacionList) {
            list.add(new InvitacionDTO(entity));
        }
        return list;
    }
}
