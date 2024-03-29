/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.InvitacionDTO;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoInvitacionLogic;
import co.edu.uniandes.csw.traductor.ejb.InvitacionLogic;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Alvaro
 */
public class EmpleadoInvitacionResource {
    private static final Logger LOGGER = Logger.getLogger(EmpleadoInvitacionResource.class.getName());

    @Inject
    private EmpleadoInvitacionLogic empleadoInvitacionLogic;

    @Inject
    private InvitacionLogic invitacionLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda una invitacion dentro de un empleado con la informacion que recibe el
     * la URL. Se devuelve la invitacion que se guarda en el empleado.
     *
     * @param empleadoId Identificador del empleado que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param invitacionId Identificador de la invitacion que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BookDTO} - El libro guardado en la editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{invitacionId: \\d+}")
    public InvitacionDTO addInvitacion(@PathParam("empleadoId") Long empleadoId, @PathParam("invitacionId") Long invitacionId)
	{
        LOGGER.log(Level.INFO, "EmpleadoInvitacionResource addInvitacion: input: empleadoId: {0} , invitacionId: {1}", new Object[]{empleadoId, invitacionId});
		if (invitacionLogic.getInvitacionSoloId(invitacionId) == null){
				throw new WebApplicationException("El recurso /invitaciones/" + invitacionId + " no existe.", 404);
		}		
        
        InvitacionDTO respuesta = new InvitacionDTO(empleadoInvitacionLogic.addInvitacion(empleadoId, invitacionId));
        LOGGER.log(Level.INFO, "EmpleadoInvitacionResource addInvitacion: output: {0}", respuesta);
        return respuesta;
    }
	
	/**
     * Busca y devuelve todos las invitaciones que existen en un empleado.     *
     * @param empleadoId Identificador del empleado que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link InvitacionesDTO} - Las invitaciones encontradas en el
     * empleado. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<InvitacionDTO> getAllInvitaciones(@PathParam("empleadoId") Long empleadoId) {
        LOGGER.log(Level.INFO, "EmpleadoInvitacionResource getAllInvitaciones: input: {0}", empleadoId);
        List<InvitacionDTO> listaObjetos = invitacionesADTO(empleadoInvitacionLogic.getInvitaciones(empleadoId));		
        LOGGER.log(Level.INFO, "EditorialBooksResource getBooks: output: {0}", listaObjetos);
        return listaObjetos;
    }	
	
	/**
     * Busca la invitacion con el id asociado dentro del empleado con id asociado.
     *
     * @param empleadoId Identificador del empleado que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param invitacionId Identificador de la invitacion que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link InvitacionDTO} - La invitacion buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la invitacion.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la invitacion en el empleado.
     */
    @GET
    @Path("{invitacionId: \\d+}")
    public InvitacionDTO getInvitacion(@PathParam("empleadoId") Long empleadoId, @PathParam("invitacionId") Long invitacionId) throws BusinessLogicException
	{
        LOGGER.log(Level.INFO, "EmpleadoInvitacionResource getInvitacion: input: empleadoId: {0} , invitacionId: {1}", new Object[]{empleadoId, invitacionId});
        if (invitacionLogic.getInvitacionSoloId(invitacionId) == null) {
            throw new WebApplicationException("El recurso /empleados/" + empleadoId + "/invitaciones/" + invitacionId + " no existe.", 404);
        }
		
        InvitacionDTO respuesta = new InvitacionDTO(empleadoInvitacionLogic.getInvitacion(empleadoId, invitacionId));
        LOGGER.log(Level.INFO, "EmpleadoInvitacionResource getInvitacion: output: {0}", respuesta);
        return respuesta;
    }
    /**
     * Delete
     * @param empleadoId
     * @param invitacionId
     * @throws BusinessLogicException 
     */
    @DELETE
    @Path("{invitacionId: \\d+}")
    public void deleteInvitacion(@PathParam("empleadoId") Long empleadoId, @PathParam("invitacionId") Long invitacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EmpleadoInvitacionResource deleteInvitacion: input: {0}", empleadoId);
        if (invitacionLogic.getInvitacionSoloId(invitacionId) == null) {
            throw new WebApplicationException("El recurso /empleados/" + empleadoId + "/invitaciones/" + invitacionId + " no existe.", 404);
        }
        empleadoInvitacionLogic.deleteInvitacion(empleadoId, invitacionId);
        LOGGER.info("EmpleadoInvitacionResource deleteInvitacion: output: void");
    }
	/**
     * Convierte una lista de InvitacionEntity a una lista de InvitacionDTO.     *
     * @param entities Lista de InvitacionEntities a convertir.
     * @return Lista de InvitacionDTO convertida.
     */
    private List<InvitacionDTO> invitacionesADTO(List<InvitacionEntity> listaEntities) {
        List<InvitacionDTO> list = new ArrayList<>();
        for (InvitacionEntity entity : listaEntities) {
            list.add(new InvitacionDTO(entity));
        }
        return list;
    }
}
