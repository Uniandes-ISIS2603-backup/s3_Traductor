/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.InvitacionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que verifica las reglas del negocio de la clase invitacion.
 * @author Geovanny Andres Gonzalez
 */

@Stateless
public class InvitacionLogic
{
	private static final Logger LOGGER = Logger.getLogger(InvitacionLogic.class.getName());	
	
	@Inject
	private InvitacionPersistence invitacionPersistence; //Invocación a la tabla de invitacion para trabajar en la base de datos.
	
		
	/**
     * Crea una invitacion en la persistencia.
     * @param invitacionEntity La entidad que representa la invitacion a persistir.
     * @return La entidad de la invitacion luego de persistirla.
     * @throws BusinessLogicException Si la invitacion a persistir ya existe o si no se cumple la integridad de los datos requeridos.
     */
	
	public InvitacionEntity createInvitacion(InvitacionEntity invitacionEntity) throws BusinessLogicException
	{
		LOGGER.log(Level.INFO, "Inicia proceso de creación de la invitacion");
        
		// Verifica la regla de negocio. Verificacion de los datos del Entity.
		
		if (invitacionEntity.getIdEmpleado() == null)
		{
			throw new BusinessLogicException("El id de empleado es un valor nulo"); 
		}
		
		else if (invitacionEntity.getIdCliente() == null)
		{
			throw new BusinessLogicException("El id del cliente que crea la solicitud es un valor nulo"); 
		}
        
		else if (invitacionEntity.getSolicitudId() == null)
		{
			throw new BusinessLogicException("El id de la solicitud a quien se le asociar la invitacion es un valor nulo"); 
		}
		
		else if (invitacionEntity.getDescripcion().length() == 0 || invitacionEntity.getDescripcion() == null)
		{
			throw new BusinessLogicException("La descripcion de la invitacion no puede ser nula o vacia");
		}
		
        // Invoca la persistencia para crear la invitacion pues se ha validado todas las reglas.
        invitacionPersistence.create(invitacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la invitacion");
        return invitacionEntity;		
	}
	
	/**
	 * Actualiza la invitacion con el id recibido en la URL con la informacion que se recibe en el cuerpo de la petición.	 
	 * @param invitacionId Id de la invitacion a actualizar en la base de datos.
	 * @param invitacionEntity Entidad con la informacion a actualizar en la base de datos.
	 * @return La invitacion con la información actualizada.
	 * @throws BusinessLogicException en dado caso de que la invitacion a actualizar no exista !
	 */
	
	public InvitacionEntity updateInvitacion(Long invitacionId, InvitacionEntity invitacionEntity) throws BusinessLogicException
	{
		LOGGER.log(Level.INFO, "Inicia proceso de actualizar la invitacion con el id = {0}", invitacionId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.		
		getInvitacion(invitacionId); //Busca en primer lugar si la invitacion existe
        InvitacionEntity actualizado = invitacionPersistence.update(invitacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la invitacion con id = {0}", invitacionEntity.getId());
        return actualizado;
	}
	
	/**
     * Obtener una invitacion por medio de su id.
     * @param invitacionId: id de la invitacion para ser buscada.
     * @return la invitacion solicitada por medio de su id.
	 * @throws BusinessLogicException en caso de que la invitacion con el id no exista.
     */
	 
	public InvitacionEntity getInvitacion(Long invitacionId) throws BusinessLogicException
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la invitacion con id = {0}", invitacionId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        InvitacionEntity buscado = invitacionPersistence.find(invitacionId);
        if (buscado == null)
		{
            LOGGER.log(Level.SEVERE, "La invitacion con el id = {0} no existe", invitacionId);
			throw new BusinessLogicException("La invitacion con el id : " + invitacionId + " no existe");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la invitacion con id = {0}", invitacionId);
        return buscado;
    }
	
	/**
     * Obtener todas las invitaciones existentes en la base de datos.
     * @return Una lista de las invitaciones existentes.
     */
	
    public List<InvitacionEntity> getAllInvitaciones()
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las invitaciones");
        // Note que, por medio de la inyección de dependencias se llama al método "getAll()" que se encuentra en la persistencia.
        List<InvitacionEntity> entities = invitacionPersistence.getAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las invitaciones");
        return entities;
    }
	
	/**
     * Borrar un invitacion existente en la base de datos.     
     * @param invitacionId: id de la invitacion a borrar     
	 * @throws BusinessLogicException si la invitacion a borrar no existe.
     */
	
    public void deleteInvitacion(Long invitacionId) throws BusinessLogicException
	{
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la invitacion con id = {0}", invitacionId);
		getInvitacion(invitacionId); //Buscar la invitacion a borrar para mirar si existe
        invitacionPersistence.delete(invitacionId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la invitacion con id = {0}", invitacionId);
    }
}
