/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
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
	private EmpleadoPersistence empleadoPersistence;
	
	
	/**
     * Crea una propuesta en la persistencia.
	 * @param empleadoId Identificacion del empleado.
     * @param propuestaEntity La entidad que representa la propuesta a persistir.
     * @return La entidad de la propuesta luego de persistirla.
     * @throws BusinessLogicException Si la propuesta a persistir ya existe o si no se cumple la integridad de los datos requeridos.
     */
	
	public PropuestaEntity createPropuesta(Long empleadoId,PropuestaEntity propuestaEntity) throws BusinessLogicException
	{
		LOGGER.log(Level.INFO, "Inicia proceso de creación de la propuesta");
        
		// Verifica la regla de negocio. Verificacion de los datos del Entity.
		if (propuestaEntity.getCosto() < 0)
		{
			throw new BusinessLogicException("El costo de la propuesta no puede ser un valor negativo, su valor fue: " + propuestaEntity.getCosto()); 
		}
			
		else if (propuestaEntity.getDescripcion().length() == 0 || propuestaEntity.getDescripcion() == null)
		{
			throw new BusinessLogicException("La descripcion de la propuesta no puede ser nula o vacia");
		}
		
        // Invoca la persistencia para crear la propuesta pues se ha validado todas las reglas.
		propuestaEntity.setEstado(EN_PROCESO);
        EmpleadoEntity entidadPadre = empleadoPersistence.find(empleadoId);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la propuesta");
        propuestaEntity.setEmpleado(entidadPadre);
		return propuestaPersistence.create(propuestaEntity);
	}
	
	/**
	 * Actualiza la propuesta con el id recibido en la URL con la informacion que se recibe en el cuerpo de la petición.	 
	 * @param empleadoId Id del empleado a actualizar su propuesta a actualizar en la base de datos.
	 * @param propuestaEntity Entidad con la informacion a actualizar en la base de datos.
	 * @return La propuesta con la información actualizada.	 
	 */
	
	public PropuestaEntity updatePropuesta(Long empleadoId, PropuestaEntity propuestaEntity)
	{
		LOGGER.log(Level.INFO, "Inicia proceso de actualizar la propuesta del empleado con el id = {0}", empleadoId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.		
		EmpleadoEntity entidadPadre = empleadoPersistence.find(empleadoId);
        propuestaEntity.setEmpleado(entidadPadre);
		propuestaPersistence.update(propuestaEntity);				
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la propuesta del empleado con el id = {0}", empleadoId);
        return propuestaEntity;
	}
	
	/**
     * Obtener una propuesta por medio de su id.
	 * @param empleadoId Identificacion del empleado
     * @param propuestaId: id de la propuesta para ser buscada.
     * @return la propuesta solicitada por medio de su id.	 
     */
	 
	public PropuestaEntity getPropuesta(Long empleadoId ,Long propuestaId)
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la propuesta con id = {0}", propuestaId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        return propuestaPersistence.find(empleadoId, propuestaId);               
    }
	
	/**
     * Obtener una propuesta por medio de su id sin tener en cuente el empleado.	 
     * @param propuestaId: id de la propuesta para ser buscada.
     * @return la propuesta solicitada por medio de su id.	 
     */
	 
	public PropuestaEntity getPropuestaSoloId(Long propuestaId)
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la propuesta con id = {0}", propuestaId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        return propuestaPersistence.findSoloId(propuestaId);               
    }
	
	/**
     * Obtener todas las propuestas existentes en la base de datos.
	 * @param empleadoId Identificador del empleado
     * @return una lista de propuestas.
     */
	
    public List<PropuestaEntity> getAllPropuestas(Long empleadoId)
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las propuestas");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        EmpleadoEntity entidadPadre = empleadoPersistence.find(empleadoId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las propuestas");
        return entidadPadre.getPropuestas();
    }
	
	/**
     * Borrar un propuesta existente en la base de datos.     
	 * @param empleadoId Identificacion del empleado
     * @param propuestaId: id de la propuesta a borrar
     * @throws BusinessLogicException Si la propuesta asociada posee una invitación, debido a que esa labor es responsabilidad del cliente que la interpuso.
     */
	
    public void deletePropuesta(Long empleadoId, Long propuestaId) throws BusinessLogicException
	{
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la propuesta con id = {0}", propuestaId);        
		// Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        PropuestaEntity entidadEliminar = getPropuesta(empleadoId, propuestaId);			
        if (entidadEliminar == null){
            throw new BusinessLogicException("La propuesta con id = " + propuestaId + " no esta asociado a el empleado con id = " + empleadoId);
        }		
        propuestaPersistence.delete(propuestaId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la propuesta con id = {0}", propuestaId);
    }
}
