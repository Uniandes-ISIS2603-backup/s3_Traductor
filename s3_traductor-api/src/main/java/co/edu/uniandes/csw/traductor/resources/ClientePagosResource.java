/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.PagosDTO;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Clase que representa el recurso "clientes/{id}/pagos"
 * @author Santiago Salazar
 */
public class ClientePagosResource 
{
    private static final Logger LOGGER = Logger.getLogger(ClientePagosResource.class.getName());

//    @Inject
//    private ClientePagosLogic clientePagosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
//
//    @Inject
//    private PagosLogic pagosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
     
    /**
     * Guarda un pago dentro de un cliente con la informacion que recibe el
     * la URL. Se devuelve el pago que se guarda en el cliente.
     *
     * @param clientesId Identificador del cliente que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param pagosId Identificador del pago que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link PagosDTO} - El pago guardado en el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el pago.
     */
     @POST
     @Path("{pagosId: \\d+}")
     public PagosDTO addPago(@PathParam("clientesId") Long clientesId, @PathParam("pagosId") Long pagosId)
     {
         //TODO: finalizar cascaron y REVISAR SI ASÏ SE HACE LA RELACION EN NUESTRO PROBLEMA
         return null;
     }
     
     /**
     * Busca y devuelve todos los pagos que existen en el cliente.
     *
     * @param clientesId Identificador del cliente que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link PagosDTO} - Los pagos encontrados en el
     * cliente. Si no hay ninguno retorna una lista vacía.
     */
     @GET
     public List<PagosDTO> getPagos(@PathParam("clientesId") Long clientesId)
     {
         //TODO: finalizar cascaron y REVISAR SI ASÏ SE HACE LA RELACION EN NUESTRO PROBLEMA
         return null;
     }
     
     /**
     * Busca el pago con el id asociado dentro del cliente con id asociado.
     *
     * @param clientesId Identificador del cliente que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param pagosId Identificador del pago que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link PagosDTO} - El pago buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el pago.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el pago en el
     * cliente.
     */
    @GET
    @Path("{pagosId: \\d+}")
    public PagosDTO getPago(@PathParam("clientesId") Long clientesId, @PathParam("pagosId") Long pagosId) throws BusinessLogicException
    {
        //TODO: finalizar cascaron y REVISAR SI ASÏ SE HACE LA RELACION EN NUESTRO PROBLEMA
        return null;
    }
    
    //TODO: No se pone aun el de PUT porque no se sabe si sea ncesario implementar el actualizar el pago
    
    /**
     * Elimina la conexión entre el pago y el cliente recibidos en la URL.
     *
     * @param clientesId El ID del cliente al cual se le va a desasociar el pago
     * @param pagosId El ID del pago que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el pago.
     */
    @DELETE
    @Path("{pagosId: \\d+}")
    public void removePago(@PathParam("clientesId") Long clientesId, @PathParam("pagosId") Long pagosId) 
    {
        //TODO: finalizar cascaron y REVISAR SI ASÏ SE HACE LA RELACION EN NUESTRO PROBLEMA
    }
}
