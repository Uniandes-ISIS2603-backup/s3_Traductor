/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.SolicitudDTO;
import co.edu.uniandes.csw.traductor.dtos.SolicitudDetailDTO;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
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
 * Clase que representa el recurso "clientes/{id}/solicitudes"
 * @author Santiago Salazar
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteSolicitudesResource 
{
     private static final Logger LOGGER = Logger.getLogger(ClienteSolicitudesResource.class.getName());

//    @Inject
//    private ClienteSolicitudesLogic clienteSolicitudesLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
//
//    @Inject
//    private SolicitudLogic solicitudLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
     
    /**
     * Guarda una solicitud dentro de un cliente con la informacion que recibe el
     * la URL. Se devuelve la solicitud que se guarda en el cliente.
     *
     * @param clientesId Identificador del cliente que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param solicitudesId Identificador de la solicitud que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SolicitudDTO} - La solicitud guardado en el cliente.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la solicitud.
     */
     @POST
     @Path("{solicitudesId: \\d+}")
     public SolicitudDTO addSolicitud(@PathParam("clientesId") Long clientesId, @PathParam("solicitudesId") Long solicitudesId)
     {
         //TODO: finalizar cascaron y REVISAR SI ASÏ SE HACE LA RELACION EN NUESTRO PROBLEMA
         return null;
     }
     
     /**
     * Busca y devuelve todos las solicitudes que existen en el cliente.
     *
     * @param clientesId Identificador del cliente que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link SolicitudesDetailDTO} - Las solicitudes encontradas en el
     * cliente. Si no hay ninguno retorna una lista vacía.
     */
     @GET
     public List<SolicitudDetailDTO> getSolicitudes(@PathParam("clientesId") Long clientesId)
     {
         //TODO: finalizar cascaron y REVISAR SI ASÏ SE HACE LA RELACION EN NUESTRO PROBLEMA
         return null;
     }
     
     /**
     * Busca la solicitud con el id asociado dentro del cliente con id asociado.
     *
     * @param clientesId Identificador del cliente que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param solicitudesId Identificador de la solicitud que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SolicitudDetailDTO} - La solicitud buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la solicitud.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la solicitud en el
     * cliente.
     */
    @GET
    @Path("{solicitudesId: \\d+}")
    public SolicitudDetailDTO getSolicitud(@PathParam("clientesId") Long clientesId, @PathParam("solicitudesId") Long solicitudesId) throws BusinessLogicException
    {
        //TODO: finalizar cascaron y REVISAR SI ASÏ SE HACE LA RELACION EN NUESTRO PROBLEMA
        return null;
    }
    
    //TODO: No se pone aun el de PUT porque no se sabe si sea ncesario implementar el actualizar la solicitud
    
    /**
     * Elimina la conexión entre la solicitud y el cliente recibidos en la URL.
     *
     * @param clientesId El ID del cliente al cual se le va a desasociar la solicitud
     * @param solicitudesId El ID de la solicitud que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la solicitud.
     */
    @DELETE
    @Path("{solicitudesId: \\d+}")
    public void removeBook(@PathParam("clientesId") Long clientesId, @PathParam("solicitudesId") Long solicitudesId) 
    {
        //TODO: finalizar cascaron y REVISAR SI ASÏ SE HACE LA RELACION EN NUESTRO PROBLEMA
    }
}
