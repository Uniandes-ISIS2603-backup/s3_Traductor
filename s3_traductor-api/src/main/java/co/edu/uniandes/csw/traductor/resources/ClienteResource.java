/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.ClienteDTO;
import co.edu.uniandes.csw.traductor.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
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
 * Clase que implementa el recurso "clientes"
 * @author Santiago Salazar
 */
@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteResource 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());
    
    //TODO: inyección de la dependencia de lógica: clienteLogic
    
    /**
     * Crea un cliente con la informacion que se recibe en formato JSON
     * en el cuerpo de la peticion y recibe el mismo cliente ingresado
     * con un id generado automaticamente.
     * @param cliente {@link ClienteDTO} - El cliente que se quiere crear
     * @return JSON {@link ClienteDTO} - El cliente creado con el id autogenerado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el cliente
     */
    @POST
    public ClienteDTO createCliente(ClienteDTO cliente) throws BusinessLogicException
    {
        //TODO: Completar cascaron.
         LOGGER.log(Level.INFO, "ClienteResource createCliente: input: {0}", cliente.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        //ClienteEntity clienteEntity = cliente.toEntity();
        // Invoca la lógica para crear la editorial nueva
        //ClienteEntity nuevoClienteEntity = clienteLogic.createCliente(clienteEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        //ClienteDTO nuevoClienteDTO = new ClienteDTO(nuevoClienteEntity);
        //LOGGER.log(Level.INFO, "EditorialResource createEditorial: output: {0}", nuevoClienteDTO.toString());
        //return nuevoClienteDTO;
        return cliente;
    }
    
    /**
     * Busca y devuelve todos los clientes que existen en la aplicacion.
     *
     * @return JSONArray {@link ClienteDetailDTO} - Los clientes
     * encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ClienteDetailDTO> getClientes()
    {
        //TODO: Finalizar cascaron
        LOGGER.info("ClienteResource getClientes: input: void");
        //List<ClienteDetailDTO> listaClientes = listEntity2DetailDTO(clienteLogic.getEditorials());
        //LOGGER.log(Level.INFO, "EditorialResource getEditorials: output: {0}", listaClientes.toString());
        //return listaClientes;
        return null;
    }
    
    /**
     * Busca el cliente con el id asociado recibido en la URL y la devuelve.
     *
     * @param clientesId Identificador del cliente que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ClienteDetailDTO} - El cliente buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente.
     */
    @GET
    @Path("{clientesId: \\d+}")
    public ClienteDetailDTO getCliente(@PathParam("clientesId") Long clientesId) throws WebApplicationException
    {
        //TODO: finalizar cascaron.
        LOGGER.log(Level.INFO, "ClienteResource getCliente: input: {0}", clientesId);
        //ClienteEntity clienteEntity = clienteLogic.getCliente(clientesId);
        //if (clienteEntity == null) {
        //    throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        //}
        //ClienteDetailDTO detailDTO = new ClienteDetailDTO(clienteEntity);
        //LOGGER.log(Level.INFO, "ClienteResource getCliente: output: {0}", detailDTO.toString());
        return null;
    }
    
    /**
     * Actualiza el cliente con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param clientesId Identificador del cliente que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param cliente {@link ClienteDetailDTO} El cliente que se desea
     * guardar.
     * @return JSON {@link ClienteDetailDTO} - El cliente guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente a
     * actualizar.
     */
    @PUT
    @Path("{clientesId: \\d+}")
    public ClienteDetailDTO updateCliente(@PathParam("clientesId") Long clientesId, ClienteDetailDTO cliente) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "ClienteResouce updateCliente: input: id:{0} , cliente: {1}", new Object[]{clientesId, cliente.toString()});
        //cliente.setId(clientesId);
        //if (clienteLogic.getCliente(clientesId) == null) {
        //    throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
        //}
        //ClienteDetailDTO detailDTO = new ClienteDetailDTO(clienteLogic.updateCliente(clientesId, cliente.toEntity()));
        //LOGGER.log(Level.INFO, "clienteResource updateCliente: output: {0}", detailDTO.toString());
        //return detailDTO;
        return null;
    }
    
    /**
     * Borra el cliente con el id asociado recibido en la URL.
     *
     * @param clientesId Identificador del cliente que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente.
     */
    @DELETE
    @Path("{clientesId: \\d+}")
    public void deleteCliente(@PathParam("clientesId") Long clientesId) throws BusinessLogicException 
    {
        //TODO: Finalizar cascaron
        LOGGER.log(Level.INFO, "ClienteResource deleteCliente: input: {0}", clientesId);
//        if (clienteLogic.getCliente(clientesId) == null) {
//            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
//        }
//        clienteLogic.deleteCliente(clientesId);
//        LOGGER.info("ClienteResource deleteCliente: output: void");
    }
    
    /**
     * Conexión con el servicio solicitudes para un cliente.
     * {@link ClienteSolicitudesResource}
     *
     * Este método conecta la ruta de /clientes con las rutas de /solicitudes que
     * dependen del cliente, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las solicitudes de un cliente.
     *
     * @param clientesId El ID del cliente con respecto al  cual se
     * accede al servicio.
     * @return El servicio de solicitudes para este cliente en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el cliente.
     */
    @Path("{clientesId: \\d+}/solicitudes")
    public Class<ClienteSolicitudesResource> getClienteSolicitudesResource(@PathParam("clientesId") Long clientesId) 
    {
//        if (clienteLogic.getCliente(clientesId) == null) {
//            throw new WebApplicationException("El recurso /clientes/" + clientesId + " no existe.", 404);
//        }
        return ClienteSolicitudesResource.class;
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos ClienteEntity a una lista de
     * objetos ClienteDetailDTO (json)
     *
     * @param entityList corresponde a la lista de clientes de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de clientes en forma DTO (json)
     */
    private List<ClienteDetailDTO> listEntity2DetailDTO(List<ClienteEntity> entityList) {
        List<ClienteDetailDTO> list = new ArrayList<>();
        for (ClienteEntity entity : entityList) {
            list.add(new ClienteDetailDTO(entity));
        }
        return list;
    }
    
}
