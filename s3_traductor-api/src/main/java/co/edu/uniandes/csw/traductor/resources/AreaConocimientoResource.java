/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.*;
import co.edu.uniandes.csw.traductor.ejb.AreaConocimientoLogic;
import co.edu.uniandes.csw.traductor.entities.*;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.*;
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
 * Clase que implementa el recurso area de conocimiento
 *
 * @author Geovanny Andres Gonzalez
 */
@Path("areasConocimiento")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class AreaConocimientoResource {

    //Logger
    private static final Logger LOGGER = Logger.getLogger(AreaConocimientoResource.class.getName());

    //Inyeccion de Logic.
    @Inject
    private AreaConocimientoLogic areaLogic;

    /**
     * Crea una nueva area de conocimiento con la informacion que se recibe en
     * el cuerpo de la petición y se regresa un objeto identico con un id
     * auto-generado por la base de datos.
     *
     * Agosto 27 - 2018: Esta operacion solo esta puesta para retornar lo
     * recibido. Geovanny.
     *
     * @param nuevaArea {@link AreaConocimientoDTO} - La area de conocimiento
     * que se desea guardar.
     * @return JSON {@link AreaConocimientoDTO} - La area de conocimiento
     * recibida.
     */
    @POST
    public AreaConocimientoDTO createArea(AreaConocimientoDTO nuevaArea) throws BusinessLogicException {

        // TODO:[createArea] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        //Llamado al Logger, no se para que sirve :(
        LOGGER.log(Level.INFO, "AreaConocimientoResources createArea: input: {0}", nuevaArea.toString());

        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        AreaConocimientoEntity areaEntity = nuevaArea.toEntity();
        // Invoca la lógica para crear la area de conocimiento nueva. Ahi abajo debe ir la logica.
        AreaConocimientoEntity nuevaEntity = areaLogic.createArea(areaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo.		        
        AreaConocimientoDTO respuestaDTO = new AreaConocimientoDTO(nuevaEntity);
        LOGGER.log(Level.INFO, "AreaConocimientoResources createArea: output: {0}", respuestaDTO.toString());
        return respuestaDTO;
    }

    /**
     * Busca y devuelve todas las area de conocimientos que existen
     *
     * @return JSONArray {@link AreaConocimientoDTO} - Las area de conocimientos
     * que existen Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<AreaConocimientoDTO> getAllAreas() {
        // TODO: [getAllAreas] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.		
        LOGGER.info("AreaConocimientoResources getAllAreas: input: void");
        List<AreaConocimientoDTO> listaAreas = listEntity2DetailDTO(areaLogic.getAllAreas()); //Se llama a la logica para que devuelva la lista !
        LOGGER.log(Level.INFO, "AreaConocimientoResources getAllAreas: output: {0}", listaAreas.toString());
        return listaAreas;
    }

    /**
     * Busca la area de conocimiento con el id asociado recibido en la URL y la
     * devuelve.
     *
     * @param id Identificador de la area de conocimiento que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link AreaConocimientoDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{id: \\d+}")
    public AreaConocimientoDTO getArea(@PathParam("id") Long id) throws WebApplicationException {
        LOGGER.log(Level.INFO, "AreaConocimientoResource getArea: input: {0}", id);
	
        AreaConocimientoEntity areaEntity = areaLogic.getArea(id);
        if(areaEntity == null) {
            throw new WebApplicationException("El recurso /areasConocimiento/" + id + " no existe.", 404);
        }
        AreaConocimientoDTO buscada = new AreaConocimientoDTO(areaEntity);
        LOGGER.log(Level.INFO, "AreaConocimientoResource getArea: output: {0}", buscada.toString());
        return buscada;
    }

    /**
     * Borra la area de conocimiento con el id asociado recibido en la URL.
     *
     * @param id Identificador de la area de conocimiento que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteArea(@PathParam("id") Long id) throws WebApplicationException {
        LOGGER.log(Level.INFO, "AreaConocimientoResource deleteArea: input: {0}", id);
        if(areaLogic.getArea(id) == null) {
            throw new WebApplicationException("El recurso /areasConocimiento/" + id + " no existe.", 404);
        }
        areaLogic.deleteArea(id);
        LOGGER.info("AreaConocimientoResource deleteArea: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos AreaConocimientoEntity a una
     * lista de objetos AreaConocimientoDTO (json)
     *
     * @param area de conocimientoList corresponde a la lista de editoriales de
     * tipo Entity que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<AreaConocimientoDTO> listEntity2DetailDTO(List<AreaConocimientoEntity> areasList) {
        List<AreaConocimientoDTO> list = new LinkedList<>();
        for (AreaConocimientoEntity entity : areasList) {
            list.add(new AreaConocimientoDTO(entity));
        }
        return list;
    }
}
