/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.PagosDTO;
import co.edu.uniandes.csw.traductor.dtos.PropuestaDTO;
import co.edu.uniandes.csw.traductor.dtos.SolicitudDTO;
import co.edu.uniandes.csw.traductor.ejb.PagoPropuestaLogic;
import co.edu.uniandes.csw.traductor.ejb.PagosLogic;
import co.edu.uniandes.csw.traductor.ejb.PropuestaLogic;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ANDRES
 */
@Path("clientes/{idCliente: \\d+}/pagos/{idTransaccion: \\d+}/propuesta")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagoPropuestaResource {

    private static final Logger LOGGER = Logger.getLogger(PagoPropuestaResource.class.getName());

    @Inject
    private PagosLogic pagosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private PagoPropuestaLogic pagoPropuestaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    
    @Inject
    private PropuestaLogic propuestaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
     

    /**
     * Remplaza la instancia de Editorial asociada a un Book.
     *
     * @param idTransaccion Identificador del libro que se esta actualizando.
     * Este debe ser una cadena de dígitos.
     * @param propuesta La editorial que se será del libro.
     * @return JSON {@link BookDetailDTO} - El arreglo de libros guardado en la
     * editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial o el
     * libro.
     */
    @PUT
    public PagosDTO updateEstadoTerminado(@PathParam("idCliente") Long idCliente,@PathParam("idTransaccion") Long idTransaccion, PropuestaDTO propuesta) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PagoSolicitudResource updateEstadoTerminado: input: idTransaccion{0} , Solicitud:{1}", new Object[]{idTransaccion, propuesta.toString()});
        if (pagosLogic.getPago(idCliente,idTransaccion) == null) {
            throw new WebApplicationException("El recurso /clentes/" + idCliente+"/pagos/"+idTransaccion + " no existe.", 404);
        }
        
        if (propuestaLogic.getPropuesta(propuesta.getPropuestaId()) == null) {
            throw new WebApplicationException("El recurso /propuesta/" + propuesta.getPropuestaId()+ " no existe.", 404);
        }
        
        PagosDTO pagosDTO = new PagosDTO(pagoPropuestaLogic.replacePropuesta(idCliente,idTransaccion, propuesta.getPropuestaId()));
         
        LOGGER.log(Level.INFO, "PagoSolicitudResource updateEstadoTerminado: output: {0}", pagosDTO.toString());
        return pagosDTO;


    }
}