/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.TarjetaDeCreditoDTO;
import co.edu.uniandes.csw.traductor.ejb.TarjetaDeCreditoLogic;
import co.edu.uniandes.csw.traductor.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TarjetaDeCreditoResource {

    //Logger
    private static final Logger LOGGER = Logger.getLogger(PropuestaResource.class.getName());
    
    @Inject
    private TarjetaDeCreditoLogic tarjetaLogic;
    
    //Define la frase "no existe" en una constante para sustuirlo en los multiples lugares
    //donde se define un error, todo ello con el fin de evitar duplicados
    
    private static final String NO_EXISTE = " no existe.";
   
   

    /**
     * Crea una nueva tarjeta con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * Por ahora solo retorna lo que recibe.
     *
     * @param idCliente El id del cliente a crearle la tarjeta
     * @param nuevaTarjeta El objeto DTO de la nueva tarjeta a crearle al cliente
     * @return JSON {@link PropuestaDTO} - La propuesta recibida.
     * @throws co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException
     */
    @POST
    public TarjetaDeCreditoDTO createTarjeta(@PathParam("clientesId") Long idCliente,TarjetaDeCreditoDTO nuevaTarjeta) throws BusinessLogicException {

        //Llamado al Logger
        LOGGER.log(Level.INFO, "TarjetaDeCreditoResources createTarjeta: input: {0}", nuevaTarjeta);
        TarjetaDeCreditoDTO nuevaTarjetaDTO = new TarjetaDeCreditoDTO(tarjetaLogic.createTarjeta(idCliente,nuevaTarjeta.toEntity()));
        LOGGER.log(Level.INFO, "TarjetaDeCreditoResources createTarjeta: output: {0}", nuevaTarjeta);

        return nuevaTarjetaDTO;
    }

    /**
     * Actualiza la tarjeta con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param idCliente id del cliente que tiene la tarjeta a actualizar
     * @param idTarjeta id de la tarjeta
     * @param tarjeta tarjeta a actualizar
     * @return JSON {@link TarjetaDeCreditoDTO} - La tarjeta actualizada.
     * @throws co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tarjeta a
     * actualizar.
     */
    @PUT
    @Path("{idTarjeta: \\d+}") //Es la forma como se va a reconocer la Tarjeta que en este caso va a ser con un numero decimal largo.
    public TarjetaDeCreditoDTO updateTarjeta(@PathParam("clientesId") Long idCliente, @PathParam("idTarjeta") Long idTarjeta, TarjetaDeCreditoDTO tarjeta) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "TarjetaDeCreditoResource updateTarjeta: input: id: {0} , tarjetaDeCredito: {1}", new Object[]{idTarjeta, tarjeta});
        if (!(idTarjeta.equals(tarjeta.getIdTarjeta()))) {
            throw new BusinessLogicException("Los ids de la tarjeta no coinciden.");
        }
        TarjetaDeCreditoEntity entity = tarjetaLogic.getTarjetaDeCredito(idCliente, idTarjeta);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + idCliente + "/tarjetasDeCredito/" + idTarjeta + NO_EXISTE, 404);
        }
        TarjetaDeCreditoDTO tarjetaDTO = new TarjetaDeCreditoDTO(tarjetaLogic.updateTarjeta(idCliente, tarjeta.toEntity()));
        LOGGER.log(Level.INFO, "TarjetaDeCreditoResource updateTarjeta: output: {0}", tarjetaDTO);
        return tarjetaDTO;

    }

    /**
     * Busca y devuelve todas las tarjetas que posee el cliente.
     *
     * @param idCliente - El id del cliente a buscar
     * @return JSONArray {@link TarjetaDeCreditoDTO} - Las tarjetas que posee el
     * empleado Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<TarjetaDeCreditoDTO> getTarjetas(@PathParam("clientesId") Long idCliente) {
       LOGGER.info("TarjetaDeCreditoResource getTarjetas: input: void");
        List<TarjetaDeCreditoDTO> listaTarjetas = listEntity2DetailDTO(tarjetaLogic.getTarjetas(idCliente));
        LOGGER.log(Level.INFO, "TarjetaDeCreditoResource getTarjetas: output: {0}", listaTarjetas);
        return listaTarjetas;
    }

    /**
     * Busca la tarjeta con el id asociado recibido en la URL y la devuelve.
     *
     * @param idCliente El id del cliente a dar la tarjeta.
     * @param idTarjeta id de la tarjeta a buscar
     * @return JSON {@link PropuestaDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{idTarjeta: \\d+}")
    public TarjetaDeCreditoDTO getTarjeta(@PathParam("clientesId") Long idCliente,@PathParam("idTarjeta") Long idTarjeta) throws WebApplicationException {
        LOGGER.log(Level.INFO, "TarjetaDeCreditoResource getTarjeta: input: {0}", idTarjeta);
        TarjetaDeCreditoEntity tarjetaEntity = tarjetaLogic.getTarjetaDeCredito(idCliente,idTarjeta);
        if (tarjetaEntity == null) {
            throw new WebApplicationException("El recurso /tarjetasDeCredito/" + idTarjeta + NO_EXISTE, 404);
        }
        TarjetaDeCreditoDTO tarjetaDTO = new TarjetaDeCreditoDTO(tarjetaEntity);
        LOGGER.log(Level.INFO, "TarjetaDeCreditoResource getTarjeta: output: {0}", tarjetaDTO);
        return tarjetaDTO;
    }

    /**
     * Borra la tarjeta con el id asociado recibido en la URL.
     *
     * @param idCliente El id del cliente a eliminarle la tarjeta dicha
     * @param idTarjeta id de la tarjeta que se quiere eliminar
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     * @throws co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException
     */
    @DELETE
    @Path("{idTarjeta: \\d+}")
    public void deleteTarjeta(@PathParam("clientesId") Long idCliente,@PathParam("idTarjeta") Long idTarjeta) throws WebApplicationException, BusinessLogicException {

        LOGGER.log(Level.INFO, "TarjetaDeCreditoResource deleteTarjeta: input: {0}", idTarjeta);
        TarjetaDeCreditoEntity entity = tarjetaLogic.getTarjetaDeCredito(idCliente,idTarjeta);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + idCliente + "/tarjetasDeCredito/" + idTarjeta + NO_EXISTE, 404);
       }        
        tarjetaLogic.deleteTarjeta(idCliente, idTarjeta);
        LOGGER.info("BookResource deleteTarjeta: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PropuestaEntity a una lista de
     * objetos PropuestaDTO (json)
     *
     * @param propuestaList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<TarjetaDeCreditoDTO> listEntity2DetailDTO(List<TarjetaDeCreditoEntity> listaTarjetas) {
        List<TarjetaDeCreditoDTO> tarjetas = new LinkedList<>();
        for (TarjetaDeCreditoEntity entity : listaTarjetas) {
            tarjetas.add(new TarjetaDeCreditoDTO(entity));
        }
        return tarjetas;
    }
}
