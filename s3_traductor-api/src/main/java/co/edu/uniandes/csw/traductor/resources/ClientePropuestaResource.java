package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.PropuestaDTO;
import co.edu.uniandes.csw.traductor.ejb.ClientePropuestaLogic;
import co.edu.uniandes.csw.traductor.ejb.PropuestaLogic;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
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
 * Clase que implementa el recurso clientes/{id}/propuestas Permite crear la
 * asociacion entre un cliente existente y una propuesta.
 *
 * @author Geovanny Andres Gonzalez
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientePropuestaResource {

    private static final Logger LOGGER = Logger.getLogger(ClientePropuestaResource.class.getName());

    @Inject
    private ClientePropuestaLogic clientePropuestaLogic;

    @Inject
    private PropuestaLogic propuestaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda una propuesta dentro de un cliente con la informacion que recibe
     * el la URL. Se devuelve la propuesta que se guarda en el cliente.
     *
     * @param clienteId Identificador del cliente que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param propuestaId Identificador de la propuesta que se desea guardar.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link BookDTO} - El libro guardado en la editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{propuestaId: \\d+}")
    public PropuestaDTO addPropuesta(@PathParam("clientesId") Long clienteId, @PathParam("propuestaId") Long propuestaId) {
        LOGGER.log(Level.INFO, "ClientePropuestaResource addPropuesta: input: clienteId: {0} , propuestaId: {1}", new Object[]{clienteId, propuestaId});
        if (propuestaLogic.getPropuestaSoloId(propuestaId) == null) {
            throw new WebApplicationException("El recurso /propuestas/" + propuestaId + " no existe.", 404);
        }

        PropuestaDTO respuesta = new PropuestaDTO(clientePropuestaLogic.addPropuesta(clienteId, propuestaId));
        LOGGER.log(Level.INFO, "ClientePropuestaResource addPropuesta: output: {0}", respuesta);
        return respuesta;
    }

    /**
     * Busca y devuelve todos las propuestas que existen en un cliente.
     *
     *
     * @param clienteId Identificador del cliente que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSONArray {@link PropuestasDTO} - Las propuestas encontradas en
     * el cliente. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<PropuestaDTO> getAllPropuestas(@PathParam("clientesId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClientePropuestaResource getAllPropuestas: input: {0}", clienteId);
        List<PropuestaDTO> listaObjetos = propuestasADTO(clientePropuestaLogic.getPropuestas(clienteId));
        LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: output: {0}", listaObjetos);
        return listaObjetos;
    }

    /**
     * Busca la propuesta con el id asociado dentro del cliente con id asociado.
     *
     * @param clienteId Identificador del cliente que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param propuestaId Identificador de la propuesta que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link PropuestaDTO} - La propuesta buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la propuesta.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la propuesta en el
     * cliente.
     */
    @GET
    @Path("{propuestaId: \\d+}")
    public PropuestaDTO getPropuesta(@PathParam("clientesId") Long clienteId, @PathParam("propuestaId") Long propuestaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClientePropuestaResource getPropuesta: input: clienteId: {0} , propuestaId: {1}", new Object[]{clienteId, propuestaId});
        if (propuestaLogic.getPropuestaSoloId(propuestaId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + "/propuestas/" + propuestaId + " no existe.", 404);
        }

        PropuestaDTO respuesta = new PropuestaDTO(clientePropuestaLogic.getPropuesta(clienteId, propuestaId));
        LOGGER.log(Level.INFO, "ClientePropuestaResource getPropuesta: output: {0}", respuesta);
        return respuesta;
    }

    /**
     * Delete
     *
     * @param clienteId
     * @param propuestaId
     * @throws BusinessLogicException
     */
    @DELETE
    @Path("{propuestaId: \\d+}")
    public void deletePropuesta(@PathParam("clientesId") Long clienteId, @PathParam("propuestaId") Long propuestaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClientePropuestaResource deletePropuesta: input: {0}", clienteId);
        if (propuestaLogic.getPropuestaSoloId(propuestaId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + "/propuestas/" + propuestaId + " no existe.", 404);
        }
        clientePropuestaLogic.deletePropuesta(clienteId, propuestaId);
        LOGGER.info("ClientePropuestaResource deletePropuesta: output: void");
    }

    /**
     * Convierte una lista de PropuestaEntity a una lista de PropuestaDTO.
     *
     *
     * @param entities Lista de PropuestaEntities a convertir.
     * @return Lista de PropuestaDTO convertida.
     */
    private List<PropuestaDTO> propuestasADTO(List<PropuestaEntity> listaEntities) {
        List<PropuestaDTO> list = new ArrayList<>();
        for (PropuestaEntity entity : listaEntities) {
            list.add(new PropuestaDTO(entity));
        }
        return list;
    }
}
