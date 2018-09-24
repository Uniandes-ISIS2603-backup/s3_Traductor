/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.ClientePersistence;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
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
	
	@Inject
	private ClientePersistence clientePersistence;
	
	@Inject
	private EmpleadoPersistence empleadoPersistence;
	
	/**
     * Crea una invitacion en la persistencia.
	 * @param clienteId Identificacion del cliente
     * @param invitacionEntity La entidad que representa la invitacion a persistir.
     * @return La entidad de la invitacion luego de persistirla.
     * @throws BusinessLogicException Si la invitacion a persistir ya existe o si no se cumple la integridad de los datos requeridos.
     */
	
	public InvitacionEntity createInvitacion(Long clienteId, InvitacionEntity invitacionEntity) throws BusinessLogicException
	{
		LOGGER.log(Level.INFO, "Inicia proceso de creación de la invitacion");
        
		// Verifica la regla de negocio. Verificacion de los datos del Entity.
		Long idEmpleado = invitacionEntity.getIdCliente();
		if (idEmpleado == null )
		{
			throw new BusinessLogicException("El id de empleado es un valor nulo"); 
		}
		
		else if (empleadoPersistence.find(idEmpleado) == null)
		{
			throw new BusinessLogicException("El empleado con id: " + idEmpleado + " para asociarle la propuesta no existe"); 
		}		
		
		else if (invitacionEntity.getDescripcion().length() == 0 || invitacionEntity.getDescripcion() == null)
		{
			throw new BusinessLogicException("La descripcion de la invitacion no puede ser nula o vacia");
		}
				
        // Invoca la persistencia para crear la invitacion pues se ha validado todas las reglas.
		ClienteEntity entidadPadre = clientePersistence.find(clienteId);
		invitacionEntity.setCliente(entidadPadre);
		LOGGER.log(Level.INFO, "Termina proceso de creación de la invitacion");
        return invitacionPersistence.create(invitacionEntity);       
	}
	
	/**
	 * Actualiza la invitacion con el id recibido en la URL con la informacion que se recibe en el cuerpo de la petición.	 	 
	 * @param invitacionEntity Entidad con la informacion a actualizar en la base de datos.
	 * @param clienteId Identificacion del cliente
	 * @return La invitacion con la información actualizada.	 
	 */
	
	public InvitacionEntity updateInvitacion(Long clienteId, InvitacionEntity invitacionEntity) 
	{
		LOGGER.log(Level.INFO, "Inicia proceso de actualizar la invitacion del cliente con el id = {0}", clienteId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.		
		ClienteEntity entidadPadre = clientePersistence.find(clienteId);
        invitacionEntity.setCliente(entidadPadre);
		invitacionPersistence.update(invitacionEntity);				
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la invitacion del cliente con el id = {0}", clienteId);
        return invitacionEntity;
	}
	
	/**
     * Obtener una invitacion por medio de su id.
     * @param invitacionId: id de la invitacion para ser buscada.
	 * @param clienteId Identificacion del cliente
     * @return la invitacion solicitada por medio de su id.	 
     */
	 
	public InvitacionEntity getInvitacion(Long clienteId,Long invitacionId)
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la invitacion con id = {0} del cliente: " + clienteId, invitacionId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
       return invitacionPersistence.find(clienteId, invitacionId);       
    }
	
	/**
     * Obtener todas las invitaciones existentes en la base de datos.
	 * @param clienteId Identificacion del cliente
     * @return Una lista de las invitaciones existentes.
     */
	
    public List<InvitacionEntity> getAllInvitaciones(Long clienteId)
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las invitaciones del cliente id ={0}", clienteId);
        // Note que, por medio de la inyección de dependencias se llama al método "getAll()" que se encuentra en la persistencia.
        ClienteEntity entidadPadre = clientePersistence.find(clienteId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las del cliente id ={0}", clienteId);
        return entidadPadre.getInvitaciones();
    }
	
	/**
     * Borrar un invitacion existente en la base de datos.     
	 * @param clienteId Identificacion del cliente
     * @param invitacionId: id de la invitacion a borrar     	 
	 * @throws BusinessLogicException si la invitacion no esta asociada al cliente.
     */
	
    public void deleteInvitacion(Long clienteId, Long invitacionId) throws BusinessLogicException 
	{
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la invitacion con id = {0}", invitacionId);
		InvitacionEntity entidadEliminar = getInvitacion(invitacionId, invitacionId);
		if (entidadEliminar == null){
			throw new BusinessLogicException("La invitacion con id = " + invitacionId + " no esta asociado a el cliente con id = " + clienteId);
		}
        invitacionPersistence.delete(invitacionId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la invitacion con id = {0} del cliente: " + clienteId, invitacionId);
    }
}
