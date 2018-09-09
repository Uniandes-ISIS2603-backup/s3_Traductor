/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.TarjetaDeCreditoDTO;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Clase que representa el recurso "clientes/{id}/solicitudes"
 * @author Santiago Salazar
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteTarjetasResource 
{
     private static final Logger LOGGER = Logger.getLogger(ClienteTarjetasResource.class.getName());

//    @Inject
//    private ClienteTarjetasLogic clienteTarjetasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
//
//    @Inject
//    private TarjetaDeCreditoLogic tarjetaDeCreditoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
     
     /**
     * Guarda una tarjeta dentro de un cliente con la informacion que recibe el
     * la URL. Se devuelve la tarjeta que se guarda en el cliente.
     *
     * @param clientesId Identificador del cliente que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param tarjetasId Identificador de las tarjetas que se desean guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link TarjetaDeCreditoDTO} - La tajerta guardada en el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tarjeta.
     */
     @POST
     @Path("{tarjetas: \\d+}")
     public TarjetaDeCreditoDTO addTarjeta(@PathParam("clientesId") Long clientesId, @PathParam("tarjetasId") Long tarjetasId)
     {
         //TODO: finalizar cascaron y REVISAR SI ASÏ SE HACE LA RELACION EN NUESTRO PROBLEMA
         return null;
     }
     
     /**
     * Busca y devuelve todos las tarjetas que existen en el cliente.
     *
     * @param clientesId Identificador del cliente que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link TarjetaDeCreditoDTO} - Las tarjetas encontradas en el
     * cliente. Si no hay ninguno retorna una lista vacía.
     */
     @GET
     public List<TarjetaDeCreditoDTO> getTarjetas(@PathParam("clientesId") Long clientesId)
     {
         //TODO: finalizar cascaron y REVISAR SI ASÏ SE HACE LA RELACION EN NUESTRO PROBLEMA
         return null;
     }
     
     /**
     * Busca la tarjeta con el id asociado dentro del cliente con id asociado.
     *
     * @param clientesId Identificador del cliente que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param tarjetasId Identificador de la tarjeta que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link TarjetaDeCreditoDTO} - La tarjeta buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tarjeta.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tarjeta en el
     * cliente.
     */
    @GET
    @Path("{tarjetasId: \\d+}")
    public TarjetaDeCreditoDTO getTarjeta(@PathParam("clientesId") Long clientesId, @PathParam("tarjetasId") Long tarjetasId) throws BusinessLogicException
    {
        //TODO: finalizar cascaron y REVISAR SI ASÏ SE HACE LA RELACION EN NUESTRO PROBLEMA
        return null;
    }
    
    //TODO: No se pone aun el de PUT porque no se sabe si sea ncesario implementar el actualizar la solicitud
    
    /**
     * Elimina la conexión entre la tarjeta y el cliente recibidos en la URL.
     *
     * @param clientesId El ID del cliente al cual se le va a desasociar la tarjeta
     * @param tarjetasId El ID de la tarjeta que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tarjeta.
     */
    @DELETE
    @Path("{tarjetasId: \\d+}")
    public void removeTarjeta(@PathParam("clientesId") Long clientesId, @PathParam("tarjetasId") Long tarjetasId) 
    {
        //TODO: finalizar cascaron y REVISAR SI ASÏ SE HACE LA RELACION EN NUESTRO PROBLEMA
    }
}
