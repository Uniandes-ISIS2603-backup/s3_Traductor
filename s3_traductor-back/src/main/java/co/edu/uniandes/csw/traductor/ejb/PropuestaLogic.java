/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.PropuestaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que verifica las reglas del negocio de la clase propuesta.
 * @author Geovanny Andres Gonzalez
 */

@Stateless
public class PropuestaLogic
{
	private static final Logger LOGGER = Logger.getLogger(PropuestaLogic.class.getName());
	public final static String EN_PROCESO = "EN_PROCESO"; //Valor de estado para la propuesta.
	public final static String CULMINADA = "CULMINADA"; //Valor de estado para la propuesta.
	
	@Inject
	private PropuestaPersistence propuestaPersistence; //Invocación a la tabla de propuesta para trabajar en la base de datos.	
	
	@Inject
	private InvitacionLogic invitacionLogic; //Invocación a la tabla de invitaciones para trabajar en la base de datos.	
	
	/**
     * Crea una propuesta en la persistencia.
     * @param propuestaEntity La entidad que representa la propuesta a persistir.
     * @return La entidad de la propuesta luego de persistirla.
     * @throws BusinessLogicException Si la propuesta a persistir ya existe o si no se cumple la integridad de los datos requeridos.
     */
	
	public PropuestaEntity createPropuesta(PropuestaEntity propuestaEntity) throws BusinessLogicException
	{
		LOGGER.log(Level.INFO, "Inicia proceso de creación de la propuesta");
        
		// Verifica la regla de negocio. Verificacion de los datos del Entity.
		
		if (propuestaEntity.getIdEmpleado() == null)
		{
			throw new BusinessLogicException("El id de empleado es un valor nulo"); 
		}
		
		else if (propuestaEntity.getCosto() < 0)
		{
			throw new BusinessLogicException("El costo de la propuesta no puede ser un valor negativo, su valor fue: " + propuestaEntity.getCosto()); 
		}
			
		else if (propuestaEntity.getDescripcion().length() == 0 || propuestaEntity.getDescripcion() == null)
		{
			throw new BusinessLogicException("La descripcion de la propuesta no puede ser nula o vacia");
		}
		
        // Invoca la persistencia para crear la propuesta pues se ha validado todas las reglas.
		propuestaEntity.setEstado(EN_PROCESO);
        propuestaPersistence.create(propuestaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la propuesta");
        return propuestaEntity;		
	}
	
	/**
	 * Actualiza la propuesta con el id recibido en la URL con la informacion que se recibe en el cuerpo de la petición.	 
	 * @param propuestaId Id de la propuesta a actualizar en la base de datos.
	 * @param propuestaEntity Entidad con la informacion a actualizar en la base de datos.
	 * @return La propuesta con la información actualizada.
	 * @throws BusinessLogicException en dado caso de que la propuesta a actualizar no exista !
	 */
	
	public PropuestaEntity updatePropuesta(Long propuestaId, PropuestaEntity propuestaEntity) throws BusinessLogicException
	{
		LOGGER.log(Level.INFO, "Inicia proceso de actualizar la propuesta con el id = {0}", propuestaId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.		
		getPropuesta(propuestaId); //Busca en primer lugar si la propuesta existe
        PropuestaEntity actualizado = propuestaPersistence.update(propuestaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la propuesta con id = {0}", propuestaEntity.getId());
        return actualizado;
	}
	
	/**
     * Obtener una propuesta por medio de su id.
     * @param propuestaId: id de la propuesta para ser buscada.
     * @return la propuesta solicitada por medio de su id.
	 * @throws BusinessLogicException en caso de que la propuesta con el id no exista.
     */
	 
	public PropuestaEntity getPropuesta(Long propuestaId) throws BusinessLogicException
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la propuesta con id = {0}", propuestaId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        PropuestaEntity buscado = propuestaPersistence.find(propuestaId);
        if (buscado == null)
		{
            LOGGER.log(Level.SEVERE, "La propuesta con el id = {0} no existe", propuestaId);
			throw new BusinessLogicException("La propuesta con el id : " + propuestaId + " no existe");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la propuesta con id = {0}", propuestaId);
        return buscado;
    }
	
	/**
     * Obtener todas las propuestas existentes en la base de datos.
     * @return una lista de propuestas.
     */
	
    public List<PropuestaEntity> getAllPropuestas()
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las propuestas");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<PropuestaEntity> propuestas = propuestaPersistence.getAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las propuestas");
        return propuestas;
    }
	
	/**
     * Borrar un propuesta existente en la base de datos.     
     * @param propuestaId: id de la propuesta a borrar
     * @throws BusinessLogicException Si la propuesta asociada posee una invitación, debido a que esa labor es responsabilidad del cliente que la interpuso.
     */
	
    public void deletePropuesta(Long propuestaId) throws BusinessLogicException
	{
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la propuesta con id = {0}", propuestaId);        
		// Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        InvitacionEntity invitacionPropuesta = getPropuesta(propuestaId).getInvitacion();				
        if (invitacionPropuesta != null)
		{
            throw new BusinessLogicException("No se puede borrar la propuesta con id = " + propuestaId + " debido a que posee una invitacion asociada");
        }		
        propuestaPersistence.delete(propuestaId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la propuesta con id = {0}", propuestaId);
    }
}
