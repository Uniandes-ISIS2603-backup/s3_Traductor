/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.*;
import co.edu.uniandes.csw.traductor.entities.*;
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
 * Clase que implementa el recurso invitacion
 * @author Geovanny Andres Gonzalez
 */

@Path("invitaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class InvitacionResources
{

	//Logger
	private static final Logger LOGGER = Logger.getLogger(InvitacionResources.class.getName());

	/**
	 * Crea una nueva invitacion con la informacion que se recibe en el cuerpo de la petición y se regresa un objeto identico con un id auto-generado por la base de datos.
	 *
	 * Agosto 27 - 2018: Esta operacion solo esta puesta para retornar lo recibido. Geovanny.
	 *
	 * @param nuevaInvitacion {@link InvitacionDTO} - La invitacion que se desea guardar.
	 * @return JSON {@link InvitacionDTO} - La invitacion recibida.
	 */
	
	@POST
	public InvitacionDTO createInvitacion(InvitacionDTO nuevaInvitacion) {
		
		// TODO:[createInvitacion] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
	
		//Llamado al Logger, no se para que sirve :(
		LOGGER.log(Level.INFO, "InvitacionResources createInvitacion: input: {0}", nuevaInvitacion.toString());

		// Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
		//InvitacionEntity invitacionEntity = nuevaInvitacion.toEntity();
		// Invoca la lógica para crear la invitacion nueva. Ahi abajo debe ir la logica.
		// Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo.		        
		//InvitacionDTO respuestaDTO = new InvitacionDTO(nuevoEditorialEntity);
		LOGGER.log(Level.INFO, "InvitacionResources createInvitacion: output: {0}", nuevaInvitacion.toString());
		return nuevaInvitacion;
	}

	/**
	 * Actualiza la invitacion con el id recibido en la URL con la informacion que se recibe en el cuerpo de la petición.
	 *
	 * @param idInvitacion Identificador de la invitacion que se desea actualizar. Este debe ser una cadena de dígitos.
	 * @param invitacion {@link InvitacionDTO} La invitacion que se desea guardar.
	 * @return JSON {@link InvitacionDTO} - La editorial guardada.
	 * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error de lógica que se genera cuando no se encuentra la invitacion a actualizar.
	 */
	
	@PUT
	@Path("{idInvitacion: \\d+}") //Es la forma como se va a reconocer lo contenido en la invitacion, en este caso es 1 o mas numeros.
	public InvitacionDTO updateInvitacion(@PathParam("idInvitacion") Long idInvitacion, InvitacionDTO invitacion) throws WebApplicationException {
		// TODO: [updateInvitacion] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
		return null;
	}

	/**
	 * Busca y devuelve todas las invitaciones que posee el empleado.
	 * @return JSONArray {@link InvitacionDTO} - Las invitaciones que posee el empleado. Si no hay ninguna retorna una lista vacía.
	 */
	
	@GET
	public List<InvitacionDTO> getAllInvitaciones() {
		// TODO: [getAllInvitaciones] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
		
		//LOGGER.info("InvitacionResource getAllInvitaciones: input: void");
		//List<InvitacionDTO> listaPropuestas = listEntity2DetailDTO(invitacionLogic.getInvitacions()); Se llama a la logica para que devuelva la lista !
		//LOGGER.log(Level.INFO, "InvitacionResource getAllInvitaciones: output: {0}", listaPropuestas.toString());
		return null;
	}

	/**
	 * Busca la invitacion con el id asociado recibido en la URL y la devuelve.
	 * @param invitacionId Identificador de la invitacion que se esta buscando. Este debe ser una cadena de dígitos.
	 * @return JSON {@link InvitacionDTO} - La editorial buscada
	 * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error de lógica que se genera cuando no se encuentra la editorial.
	 */
	
	@GET
	@Path("{invitacionId: \\d+}")
	public InvitacionDTO getInvitacion(@PathParam("invitacionId") Long invitacionId) throws WebApplicationException {
		
		// TODO: [getInvitacion] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
		//LOGGER.log(Level.INFO, "InvitacionResource getInvitacion: input: {0}", editorialsId);
		
		/**
		 * Verificacion de que existe
		InvitacionEntity invitacionEntity = invitacionLogic.getInvitacion(editorialsId);
		if (invitacionEntity == null) {
			throw new WebApplicationException("El recurso /invitaciones/" + invitacionId + " no existe.", 404);
		}
		*/
		
		//InvitacionDTO detailDTO = new InvitacionDTO(invitacionEntity);
		//LOGGER.log(Level.INFO, "EditorialResource getInvitacion: output: {0}", detailDTO.toString());
		return null;
	}
	
	/**
     * Borra la invitacion con el id asociado recibido en la URL.
     *
     * @param invitacionId Identificador de la invitacion que se desea borrar.
     * Este debe ser una cadena de dígitos.     
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
	
    @DELETE
    @Path("{invitacionId: \\d+}")
    public void deleteInvitacion(@PathParam("invitacionId") Long invitacionId) throws WebApplicationException {
        
		// TODO: [deleteInvitacion] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
		
		LOGGER.log(Level.INFO, "InvitacionResource deleteInvitacion: input: {0}", invitacionId);
        
		/**
		if (invitacionLogic.getInvitacion(invitacionId) == null) {
            throw new WebApplicationException("El recurso /invitaciones/" + invitacionId + " no existe.", 404);
        }
        invitacionLogic.deleteInvitacion(invitacionId);
		*/
		
        LOGGER.info("InvitacionResource deleteInvitacion: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos InvitacionEntity a una lista de
     * objetos InvitacionDTO (json)
     *
     * @param invitacionList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
	
    private List<InvitacionDTO> listEntity2DetailDTO(List<InvitacionEntity> invitacionList) {
        List<InvitacionDTO> list = new LinkedList<>();
        for (InvitacionEntity entity : invitacionList) {
            list.add(new InvitacionDTO(entity));
        }
        return list;
    }
}
