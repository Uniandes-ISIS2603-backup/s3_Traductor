/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.IdiomaDTO;
import co.edu.uniandes.csw.traductor.ejb.IdiomaLogic;
import co.edu.uniandes.csw.traductor.entities.IdiomaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "idiomas"
 *
 * @author Santiago Salazar
 */
@Path("idiomas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class IdiomaResource {

    private static final Logger LOGGER = Logger.getLogger(IdiomaResource.class.getName());

    //inyección de la dependencia de lógica: idiomaLogic
    private IdiomaLogic idiomaLogic;

    /**
     * Busca y devuelve todos los idiomas que existen en la aplicacion.
     *
     * @return JSONArray {@link IdiomaDTO} - Los Idiomas
     * encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<IdiomaDTO> getIdiomas() {
        LOGGER.info("IdiomaResource getIdiomas: input: void");
        List<IdiomaDTO> listaIdiomas = listEntity2DTO(idiomaLogic.getIdiomas());
        LOGGER.log(Level.INFO, "IdiomaResource getIdiomas: output: {0}", listaIdiomas.toString());
        return listaIdiomas;
    }

    /**
     * Busca el Idioma con el id en la URL y lo retorna
     *
     * @param idiomasId Identificador numerico del idioma que se esta buscando
     * @return JSON {@link IdiomaDTO} - El idioma buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el idioma.
     */
    @GET
    @Path("{idiomasId: \\d+}")
    public IdiomaDTO getIdioma(@PathParam("idiomasId") Long idiomasId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "IdiomaResource getIdioma: input: {0}", idiomasId);
        IdiomaEntity idiomaEntity = idiomaLogic.getIdioma(idiomasId);
        if (idiomaEntity == null) {
            throw new WebApplicationException("El recurso /idiomas/" + idiomasId + " no existe.", 404);
        }
        IdiomaDTO dtoIdioma = new IdiomaDTO(idiomaEntity);
        LOGGER.log(Level.INFO, "IdiomaResource getIdioma: output: {0}", dtoIdioma.toString());
        return dtoIdioma;
    }

    /**
     * Crea el idioma segun el objeto JSON recibido y retorna el mismo idioma
     * con el id generado por el sistema
     *
     * @param idioma {@link IdiomaDTO} - El idioma que se quiere crear
     * @return JSON {@link IdiomaDTO} - El idioma creado con el id autogenerado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe un idioma igual
     */
    @POST
    public IdiomaDTO createIdioma(IdiomaDTO idioma) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "IdiomaResource createIdioma: input: {0}", idioma.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        IdiomaEntity idiomaEntity = idioma.toEntity();
        // Invoca la lógica para crear el idioma nuevo
        IdiomaEntity nuevoIdiomaEntity = idiomaLogic.createIdioma(idiomaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        IdiomaDTO nuevoIdiomaDTO = new IdiomaDTO(nuevoIdiomaEntity);
        LOGGER.log(Level.INFO, "IdiomaResource createIdioma: output: {0}", nuevoIdiomaDTO.toString());
        return nuevoIdiomaDTO;
    }

    /**
     * Borra el idioma con el id asociado recibido en la URL.
     *
     * @param idiomasId Identificador del idioma que se desea
     * borrar. Este debe ser una cadena de dígitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el idioma.
     */
    @DELETE
    @Path("{idiomasId: \\d+}")
    public void deleteIdioma(@PathParam("idiomasId") Long idiomasId) {
        LOGGER.log(Level.INFO, "IdiomaResource deleteIdioma: input: {0}", idiomasId);
        if (idiomaLogic.getIdioma(idiomasId) == null) {
            throw new WebApplicationException("El recurso /idiomas/" + idiomasId + " no existe.", 404);
        }
        idiomaLogic.deleteIdioma(idiomasId);
        LOGGER.info("IdiomaResource deleteIdioma: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos IdiomaEntity a una lista de
     * objetos IdiomaDTO (json)
     *
     * @param entityList corresponde a la lista de idiomas de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de idiomas en forma DTO (json)
     */
    private List<IdiomaDTO> listEntity2DTO(List<IdiomaEntity> entityList) {
        List<IdiomaDTO> list = new ArrayList<>();
        for (IdiomaEntity entity : entityList) {
            list.add(new IdiomaDTO(entity));
        }
        return list;
    }
}
