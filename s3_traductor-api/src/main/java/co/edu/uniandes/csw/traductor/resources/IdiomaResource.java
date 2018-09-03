/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.IdiomaDTO;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.List;
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
 * @author Santiago Salazar
 */
@Path("idiomas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class IdiomaResource 
{
    private static final Logger LOGGER = Logger.getLogger(IdiomaResource.class.getName());
    
    //TODO: inyección de la dependencia de lógica: idiomaLogic
     
    /**
     * Busca y retorna todos los idiomas
     * @return JSONAray {@link IdiomaDTO} - Los idiomas
     */
    @GET
    public List<IdiomaDTO> getIdiomas()
    {
        //TODO: Finalizar cascaron
        
        return null;
    }
    
    /**
     * Busca el Idioma con el id en la URL y lo retorna
     * @param idiomasId Identificador numerico del idioma
     * que se esta buscando
     * @return JSON {@link ClienteDTO} - El idioma buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el idioma.
     */
    @GET
    @Path("{idiomasId: \\d+}")
    public IdiomaDTO getIdioma(@PathParam("IdiomasId") Long idiomasId) throws WebApplicationException
    {
        //TODO: Finalizar cascaron
        return null;
    }
    
    /**
     * Crea el idioma segun el objeto JSON recibido
     * y retorna el mismo idioma con el id generado 
     * por el sistema
     * @param idioma {@link IdiomaDTO} - El 
     * idioma que se quiere crear
     * @return JSON {@link IdiomaDTO} - El idioma
     * creado con el id autogenerado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe un idioma igual  
     */
    @POST
    public IdiomaDTO createIdioma(IdiomaDTO calificacion) throws BusinessLogicException
    {
        //TODO: Finalizar cascaron
        return calificacion;
    }
    
    /**
     * Borra la calificacion con el id asociado recibido en la URL.
     *
     * @param calificacionesId Identificador de la calificacion que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la calificacion.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la calificacion.
     */
    @DELETE
    @Path("{idiomasId: \\d+}")
    public void deleteIdioma(@PathParam("idiomasId") Long idiomasId) throws BusinessLogicException
    {
        //TODO: Finalizar cascaron
    }
}
