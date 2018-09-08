/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.InvitacionDTO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el subrecurso cliente/{idCliente}/invitacion
 * @author Geovanny Andres Gonzalez
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteInvitacionResource 
{
	private static final Logger LOGGER = Logger.getLogger(ClienteInvitacionResource.class.getName());
	
	/**
	 * Guarda una invitacion dentro de un cliente con la info que se recibe por la URL.
	 * Se devuelve la invitacion que se guarda en el cliente
	 * @param idCliente Identificador del cliente a quien se le asocia la invitacion
	 * @param idInvitacion Identificador de la invitacion que se esta asociando al cliente
	 * @return La invitacion DTO con la informacion recibida por URL.
	 */
	
	@POST
	@Path("{idInvitacion: \\d+}")
	public InvitacionDTO addInvitacion(@PathParam ("id") Long idCliente, @PathParam ("idInvitacion") Long idInvitacion)
	{
		LOGGER.log(Level.INFO, "ClienteInvitacionResource addInvitacion: input: id: {0} , idInvitacion: {1}", new Object[]{idCliente, idInvitacion});
		//Aqui va el llamado a la logica. Se usa un DTO Dummy, porfa reeemplzar cuando este la logica.
		InvitacionDTO invitacionDTO = new InvitacionDTO();
		LOGGER.log(Level.INFO, "ClienteInvitacionResource addInvitacion: output: {0}", invitacionDTO.toString());
		return invitacionDTO;
	}

	/**
	 * Busca la invitacion asociada con el id dentro del cliente con el id asociado.
	 * @param idCliente Identificador del cliente a quien se le esta buscando la invitacion
	 * @param idInvitacion Identificador de la invitacion que se esta buscando
	 * @return La informacion de la invitacion buscada en forma de DTO.
	 */
	
	@GET
	@Path("{idInvitacion: \\d+}")
	public InvitacionDTO getInvitacion(@PathParam ("id") Long idCliente, @PathParam ("idInvitacion") Long idInvitacion)
	{
		LOGGER.log(Level.INFO, "ClienteInvitacionResource getInvitacion: input: id: {0} , idInvitacion: {1}", new Object[]{idCliente, idInvitacion});
		//Aqui va el llamado a la logica. Se usa un DTO Dummy, porfa reeemplzar cuando este la logica.
		InvitacionDTO invitacionDTO = new InvitacionDTO();
		LOGGER.log(Level.INFO, "ClienteInvitacionResource getInvitacion: output: {0}", invitacionDTO.toString());
		return invitacionDTO;
	}

	/**
	 * Actualiza la descripcion de la invitacion asociada con el id dentro del cliente con el id asociado.
	 * @param idCliente Identificador del cliente a quien se le esta buscando la invitacion
	 * @param idInvitacion Identificador de la invitacion que se esta buscando
	 * @param invitacionDTO Es el DTO que contiene la nueva descripcion de la invitacion a asociar.
	 * @return La informacion de la invitacion buscada en forma de DTO.
	 */
	
	@PUT
	@Path("{idInvitacion: \\d+}")
	public InvitacionDTO updateInvitacion(@PathParam ("id") Long idCliente, @PathParam ("idInvitacion") Long idInvitacion, InvitacionDTO invitacion)
	{
		LOGGER.log(Level.INFO, "ClienteInvitacionResource updateInvitacion: input: id: {0} , idInvitacion: {1}", new Object[]{idCliente, idInvitacion});
		invitacion.setIdInvitacion(idInvitacion); //Se reemplaza el id para que reconozca la PK de la invitacion a actualizar en la BD.
		//Aqui va el llamado a la logica, para reeemplazar la descripcion del libro.
		InvitacionDTO invitacionDTO = new InvitacionDTO(); // Se usa un DTO Dummy, porfa reeemplzar cuando este la logica.
		LOGGER.log(Level.INFO, "ClienteInvitacionResource updateInvitacion: output: {0}", invitacionDTO.toString());
		return invitacionDTO;
	}
	
	@DELETE
	@Path("{idInvitacion: \\d+}")
	public void deleteInvitacion(@PathParam ("id") Long idCliente, @PathParam ("idInvitacion") Long idInvitacion)
	{
		LOGGER.log(Level.INFO, "ClienteInvitacionResource deleteInvitacion: input: id: {0} , idInvitacion: {1}", new Object[]{idCliente, idInvitacion});
		//Aqui va el llamado a la logica. Aqui deberia estar el llamado al borrado.		
		LOGGER.log(Level.INFO, "ClienteInvitacionResource deleteInvitacion: output: {0}");		
	}
}
