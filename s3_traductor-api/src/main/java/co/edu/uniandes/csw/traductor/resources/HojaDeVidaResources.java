/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.HojaDeVidaDTO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
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
@Path("hojaDeVida")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class HojaDeVidaResources {
    private static final Logger LOGGER = Logger.getLogger(PropuestaResources.class.getName());

    /**
     * Crea una nueva hoja de vida con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * Por ahora solo retorna lo que recibe.
     *
     * @param nuevaHoja
     * @return JSON {@link PropuestaDTO} - La propuesta recibida.
     */
    @POST
    public HojaDeVidaDTO createHojaDeVida(HojaDeVidaDTO nuevaHoja) {

        //Llamado al Logger
        LOGGER.log(Level.INFO, "TarjetaDeCreditoResources createTarjeta: input: {0}", nuevaHoja.toString());
        LOGGER.log(Level.INFO, "TarjetaDeCreditoResources createTarjeta: output: {0}", nuevaHoja.toString());

        return nuevaHoja;
    }
     /**
     *Devuelve todas la unica hoja de vida que posee el cliente.
     *
     * @return JSONArray {@link PropuestaDTO} - Las propuestas que posee el
     * empleado Si no hay ninguna retorna null.
     */
    @GET
    public HojaDeVidaDTO getHojaDeVida() {
        return null;
    }
    
    /**
     * Actualiza la propuesta con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param nuevaHoja la nueva de vida con la que va a actualizar la anterior
     * @return JSON {@link PropuestaDTO} - La editorial guardada.
     */
    @PUT
    public HojaDeVidaDTO updateHojaDeVida(HojaDeVidaDTO nuevaHoja) {

        return nuevaHoja;
    }
}
