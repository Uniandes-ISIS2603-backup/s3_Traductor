/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.TarjetaDeCreditoDTO;
import co.edu.uniandes.csw.traductor.entities.TarjetaDeCreditoEntity;
import java.util.LinkedList;
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
 *
 * @author ANDRES
 */
@Path("tarjetasDeCredito")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class TarjetaDeCreditoResources {

    //Logger
    private static final Logger LOGGER = Logger.getLogger(PropuestaResource.class.getName());

    /**
     * Crea una nueva tarjeta con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * Por ahora solo retorna lo que recibe.
     *
     * @param nuevaTarjeta
     * @return JSON {@link PropuestaDTO} - La propuesta recibida.
     */
    @POST
    public TarjetaDeCreditoDTO createTarjeta(TarjetaDeCreditoDTO nuevaTarjeta) {

        //Llamado al Logger
        LOGGER.log(Level.INFO, "TarjetaDeCreditoResources createTarjeta: input: {0}", nuevaTarjeta.toString());
        LOGGER.log(Level.INFO, "TarjetaDeCreditoResources createTarjeta: output: {0}", nuevaTarjeta.toString());

        return nuevaTarjeta;
    }

    /**
     * Actualiza la propuesta con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param idTarjeta id de la tarjeta
     * @param tarjeta tarjeta a actualizar
     * @return JSON {@link PropuestaDTO} - La editorial guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tarjeta a
     * actualizar.
     */
    @PUT
    @Path("{idTarjeta: \\d+}") //Es la forma como se va a reconocer la Tarjeta que en este caso va a ser con un numero decimal largo.
    public TarjetaDeCreditoDTO updateTarjeta(@PathParam("propuestaId") Long idTarjeta, TarjetaDeCreditoDTO tarjeta) throws WebApplicationException {

        return null;
    }

    /**
     * Busca y devuelve todas las tarjetas que posee el cliente.
     *
     * @return JSONArray {@link PropuestaDTO} - Las tarjetas que posee el
     * empleado Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<TarjetaDeCreditoDTO> getTarjetas() {
        return null;
    }

    /**
     * Busca la tarjeta con el id asociado recibido en la URL y la devuelve.
     *
     * @param idTarjeta id de la tarjeta a buscar
     * @return JSON {@link PropuestaDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{idTarjeta: \\d+}")
    public TarjetaDeCreditoDTO getTarjeta(@PathParam("idTarjeta") Long idTarjeta) throws WebApplicationException {

        return null;
    }

    /**
     * Borra la tarjeta con el id asociado recibido en la URL.
     *
     * @param idTarjeta id de la tarjeta que se quiere eliminar
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @DELETE
    @Path("{idTarjeta: \\d+}")
    public void deleteTarjeta(@PathParam("idTarjeta") Long idTarjeta) throws WebApplicationException {

        LOGGER.log(Level.INFO, "TarjetaDeCreditoResources deleteTarjeta: input: {0}", idTarjeta);
        LOGGER.info("TarjetaDeCreditoResources deleteTarjeta: output: void");
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
        for(TarjetaDeCreditoEntity entity : listaTarjetas) {
            tarjetas.add(new TarjetaDeCreditoDTO(entity));
        }
        return tarjetas;
    }
}
