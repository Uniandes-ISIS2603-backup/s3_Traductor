/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.PagosDTO;
import co.edu.uniandes.csw.traductor.ejb.PagosLogic;
import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
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
 *
 * @author ANDRES
 */
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PagosResource {
     //Logger
    private static final Logger LOGGER = Logger.getLogger(PropuestaResource.class.getName());

    @Inject
    private PagosLogic pagosLogic;
    /**
     * Crea una nueva tarjeta con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * Por ahora solo retorna lo que recibe.
     *
     * @param nuevoPago pago a agregar
     * @return JSON {@link PropuestaDTO} - La propuesta recibida.
     */
    @POST
    public PagosDTO createPago(@PathParam("idCliente") Long idCliente,PagosDTO nuevoPago) throws BusinessLogicException {

        //Llamado al Logger
        LOGGER.log(Level.INFO, "TarjetaDeCreditoResources createPago: input: {0}", nuevoPago.toString());
       PagosDTO nuevoPagoDTO = new PagosDTO(pagosLogic.createPago(idCliente, nuevoPago.getPropuestaDto().getPropuestaId(),nuevoPago.toEntity()));
        LOGGER.log(Level.INFO, "TarjetaDeCreditoResources createPago: output: {0}", nuevoPago.toString());

        return nuevoPagoDTO;
    }

    /**
     * Actualiza la propuesta con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param idTransaccion identificador del pago
     * @param pago informacion con la cual se va actualizar el pago
     * @return JSON {@link PropuestaDTO} - La editorial guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el pago a
     * actualizar.
     */
    @PUT
    @Path("{idTransaccion: \\d+}") //Es la forma como se va a reconocer la Tarjeta que en este caso va a ser con un numero decimal largo.
    public PagosDTO updatePago(@PathParam("idCliente") Long idCliente,@PathParam("idTransaccion") Long idTransaccion, PagosDTO pago) throws WebApplicationException, BusinessLogicException {

     LOGGER.log(Level.INFO, "PagosResource modificarTPago: input:(0)", idTransaccion);
        
     if (!idTransaccion.equals(pago.getId())) {
            throw new BusinessLogicException("Los ids del pago no coinciden.");
        }
     
    PagosEntity entity = pagosLogic.getPago(idCliente, idTransaccion);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + idCliente + "/pagos/" + idTransaccion + " no existe.", 404);

        }
        PagosDTO pagoDTO = new PagosDTO(pagosLogic.updatePago(idCliente, pago.toEntity()));
       
        
        LOGGER.log(Level.INFO,"PagosResource updatePago: output: (0)", pagoDTO.toString());
        
        return pagoDTO;
    }

    /**
     * Busca y devuelve todas los pagos que posee la tarjeta
     *
     * @param <error>
     * @param <error>
     * @return JSONArray {@link PropuestaDTO} - Las tarjetas que posee el
     * empleado Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<PagosDTO> getPagos(@PathParam("idCliente")Long idCliente) {
        LOGGER.log(Level.INFO, "PagosResource getPagos: input: {0}", idCliente);
        List<PagosDTO> listaDTOs = listEntity2DetailDTO(pagosLogic.getPagos(idCliente));
        LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }

    /**
     * Busca el pago con el id asociado recibido en la URL y lo devuelve.
     *
     * @param idTransaccion id del pago a buscar
     * @return JSON {@link PropuestaDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{idTransaccion: \\d+}")
    public PagosDTO getPago(@PathParam("idCliente") Long idCliente,@PathParam("idTransaccion") Long idTransaccion) throws WebApplicationException {
LOGGER.log(Level.INFO, "ReviewResource getReview: input: {0}", idTransaccion);
        PagosEntity entity = pagosLogic.getPago(idCliente, idTransaccion);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + idCliente + "/pagos/" + idTransaccion + " no existe.", 404);
        }
        PagosDTO pagosDTO = new PagosDTO(entity);
        LOGGER.log(Level.INFO, "ReviewResource getReview: output: {0}", pagosDTO.toString());
        return pagosDTO;
    }

    /**
     * Borra el pago con el id asociado recibido en la URL.
     *
     * @param idTransaccion id del pago que se quiere eliminar
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el pago.
     */
    @DELETE
    @Path("{idTransaccion: \\d+}")
    public void deletePago(@PathParam("idCliente") Long idCliente,@PathParam("idTransaccion") Long idTransaccion) throws WebApplicationException, BusinessLogicException {

         PagosEntity entity = pagosLogic.getPago(idCliente, idTransaccion);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + idCliente + "/pagos/" + idTransaccion + " no existe.", 404);
        }
        pagosLogic.deletePago(idCliente, idTransaccion);
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PropuestaEntity a una lista de
     * objetos PropuestaDTO (json)
     *
     * @param listaPagos corresponde a la lista de pagos de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de pagos en forma DTO (json)
     */
    private List<PagosDTO> listEntity2DetailDTO(List<PagosEntity> listaPagos) {
        List<PagosDTO> pagos = new LinkedList<>();
        for(PagosEntity entity : listaPagos) {
            pagos.add(new PagosDTO(entity));
        }
        return pagos;
    }
}
