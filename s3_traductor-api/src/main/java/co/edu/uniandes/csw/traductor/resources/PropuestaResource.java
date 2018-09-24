/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.PropuestaDTO;
import co.edu.uniandes.csw.traductor.ejb.PropuestaLogic;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
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
 * Clase que implementa el recurso propuesta
 *
 * @author Geovanny Andres Gonzalez
 */
@Produces("application/json")
@Consumes("application/json")
public class PropuestaResource {

    //Logger
    private static final Logger LOGGER = Logger.getLogger(PropuestaResource.class.getName());

    //Inyeccion de la logica
    @Inject
    private PropuestaLogic propuestaLogic;

    /**
     * Crea una nueva propuesta con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * Agosto 27 - 2018: Esta operacion solo esta puesta para retornar lo
     * recibido. Geovanny.
     *
     * @param empleadosId El id del empleado a mirar los recursos
     * @param nuevaPropuesta {@link PropuestaDTO} - La propuesta que se desea
     * guardar.
     * @return JSON {@link PropuestaDTO} - La propuesta recibida.
     */
    @POST
    public PropuestaDTO createPropuesta(@PathParam("empleadosId") Long empleadosId, PropuestaDTO nuevaPropuesta) throws BusinessLogicException {

        // TODO: [createPropuesta] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.		
        LOGGER.log(Level.INFO, "PropuestaResources createPropuesta: input: {0}", nuevaPropuesta.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        PropuestaEntity entidad = nuevaPropuesta.toEntity();
        // Invoca la lógica para crear la propuesta nueva. Ahi abajo debe ir la logica.	
        PropuestaEntity nuevaEntity = propuestaLogic.createPropuesta(empleadosId, entidad);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo.	
        PropuestaDTO respuestaDTO = new PropuestaDTO(nuevaEntity);
        LOGGER.log(Level.INFO, "PropuestaResources createPropuesta: output: {0}", respuestaDTO.toString());
        return respuestaDTO;
    }

    /**
     * Actualiza la propuesta con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param empleadosId El empleado que tiene esas propuestas.
     * @param propuestaId Identificador de la propuesta que se desea actualizar.
     * Este debe ser una cadena de dígitos.
     * @param propuesta {@link PropuestaDTO} La propuesta que se desea guardar.
     * @return JSON {@link PropuestaDTO} - La editorial guardada.
     * @throws BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la propuesta a
     * actualizar.
     */
    @PUT
    @Path("{propuestaId: \\d+}") //Es la forma como se va a reconocer lo contenido en la propuesta, en este caso es 1 o mas numeros.
    public PropuestaDTO updatePropuesta(@PathParam("empleadosId") Long empleadosId, @PathParam("propuestaId") Long propuestaId, PropuestaDTO propuesta) throws BusinessLogicException {
        // TODO: [updatePropuesta] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        LOGGER.log(Level.INFO, "propuestaResource updatePropuesta: input: empleadosId: {0} , propuestaId: {1} , review:{2}", new Object[]{empleadosId, propuestaId, propuesta.toString()});
        if (propuestaId.equals(propuesta.getPropuestaId())) {
            throw new BusinessLogicException("Los ids de la propuesta no coinciden.");
        }
        PropuestaEntity entity = propuestaLogic.getPropuesta(empleadosId, propuestaId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /empleado/" + empleadosId + "/propuestas/" + propuestaId + " no existe.", 404);

        }
        PropuestaDTO propuestaDTO = new PropuestaDTO(propuestaLogic.updatePropuesta(propuestaId, propuesta.toEntity()));
        LOGGER.log(Level.INFO, "ReviewResource updateReview: output:{0}", propuestaDTO.toString());
        return propuestaDTO;
    }

    /**
     * Busca y devuelve todas las propuesta que posee el empleado.
     *
     * @return JSONArray {@link PropuestaDTO} - Las propuestas que posee el
     * empleado Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<PropuestaDTO> getAllPropuestas() {
        // TODO: [getAllPropuestas] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.

        LOGGER.info("PropuestaResources getAllPropuestas: input: void");
        List<PropuestaDTO> listaPropuestas = listEntity2DetailDTO(propuestaLogic.getAllPropuestas()); //Se llama a la logica para que devuelva la lista !
        LOGGER.log(Level.INFO, "PropuestaResources getAllPropuestas:: output: {0}", listaPropuestas.toString());
        return listaPropuestas;
    }

    /**
     * Busca la propuesta con el id asociado recibido en la URL y la devuelve.
     *
     * @param propuestaId Identificador de la propuesta que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link PropuestaDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{propuestaId: \\d+}")
    public PropuestaDTO getPropuesta(@PathParam("propuestaId") Long propuestaId) throws WebApplicationException {

        // TODO: [getPropuestas] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        LOGGER.log(Level.INFO, "PropuestaResources getPropuesta: input: {0}", propuestaId);
        PropuestaDTO entityBuscada = null; //DTO respuesta.	

        try {
            entityBuscada = new PropuestaDTO(propuestaLogic.getPropuesta(propuestaId));
        } catch (BusinessLogicException e) {
            throw new WebApplicationException("El recurso /propuestas/" + propuestaId + " no existe.", 404);
        }

        LOGGER.log(Level.INFO, "PropuestaResources getPropuesta: output: {0}", entityBuscada.toString());
        return entityBuscada;
    }

    /**
     * Borra la propuesta con el id asociado recibido en la URL.
     *
     * @param propuestaId Identificador de la propuesta que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @DELETE
    @Path("{propuestaId: \\d+}")
    public void deletePropuesta(@PathParam("propuestaId") Long propuestaId) throws WebApplicationException {

        // TODO: [deletePropuesta] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        LOGGER.log(Level.INFO, "PropuestaResources deleteEditorial: input: {0}", propuestaId);

        try {
            propuestaLogic.getPropuesta(propuestaId); //Si no existe salta al catch y manda la excepcion.
            propuestaLogic.deletePropuesta(propuestaId);
        } catch (BusinessLogicException e) {
            throw new WebApplicationException("El recurso /propuestas/" + propuestaId + " no existe.", 404);
        }

        LOGGER.info("PropuestaResources deleteEditorial: output: void");
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
    private List<PropuestaDTO> listEntity2DetailDTO(List<PropuestaEntity> propuestaList) {
        List<PropuestaDTO> list = new LinkedList<>();
        for (PropuestaEntity entity : propuestaList) {
            list.add(new PropuestaDTO(entity));
        }
        return list;
    }
}
