/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.PropuestaPersistence;
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
	
	/**
     * Crea una propuesta en la persistencia.
     *
     * @param propuestaEntity La entidad que representa la propuesta a
     * persistir.
     * @return La entidad de la propuesta luego de persistirla.
     * @throws BusinessLogicException Si la editorial a persistir ya existe o si no se cumple la integridad de los datos requeridos.
     */
	
	public PropuestaEntity createPropuesta(PropuestaEntity propuestaEntity) throws BusinessLogicException
	{
		LOGGER.log(Level.INFO, "[LOGICA] Inicia proceso de creación de la propuesta");
        
		// Verifica la regla de negocio. Verificacion de los datos del Entity.
		
		if (propuestaEntity.getIdEmpleado() == null)
		{
			throw new BusinessLogicException("El id de empleado es un valor nulo"); 
		}
		
		else if (propuestaEntity.getCosto() < 0)
		{
			throw new BusinessLogicException("El costo de la propuesta no puede ser un valor negativo, su valor fue: " + propuestaEntity.getCosto()); 
		}
        
		else if (!propuestaEntity.getEstado().equals(CULMINADA) || !propuestaEntity.getEstado().equals(EN_PROCESO))
		{
			throw new BusinessLogicException("El estado de la propuesta no es correcto se esperaba EN_PROCESO o CULMINADA y se obtuvo: " + propuestaEntity.getEstado()); 
		}
		
		else if (propuestaEntity.getDescripcion().length() == 0 || propuestaEntity.getDescripcion() == null)
		{
			throw new BusinessLogicException("La descripcion de la propuesta no puede ser nula o vacia");
		}
		
        // Invoca la persistencia para crear la propuesta pues se ha validado todas las reglas.
        propuestaPersistence.create(propuestaEntity);
        LOGGER.log(Level.INFO, "[LOGICA] Termina proceso de creación de la propuesta");
        return propuestaEntity;		
	}
}
