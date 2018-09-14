/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;
import co.edu.uniandes.csw.traductor.entities.AreaConocimientoEntity;
import co.edu.uniandes.csw.traductor.entities.AreaConocimientoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.AreaConocimientoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que verifica las reglas del negocio de la clase areaConocimiento.
 * @author Geovanny Andres Gonzalez
 */

@Stateless
public class AreaConocimientoLogic
{
	private static final Logger LOGGER = Logger.getLogger(AreaConocimientoLogic.class.getName());	
	
	@Inject
	private AreaConocimientoPersistence areaPersistence; //Invocación a la tabla de areaConocimiento para trabajar en la base de datos.
	
		
	/**
     * Crea una area de conocimiento en la persistencia.
     * @param areaEntity La entidad que representa el area de conocimiento a persistir.
     * @return La entidad del area luego de persistirla.
     * @throws BusinessLogicException Si la area de conocimiento a persistir ya existe o si no se cumple la integridad de los datos requeridos.
     */
	
	public AreaConocimientoEntity createArea(AreaConocimientoEntity areaEntity) throws BusinessLogicException
	{
		LOGGER.log(Level.INFO, "Inicia proceso de creación del areaConocimiento");
        
		// Verifica la regla de negocio. Verificacion de los datos del Entity.
		if (areaEntity.getArea() == null ||areaEntity.getArea().length() == 0)
		{
			throw new BusinessLogicException("La descripcion del area de conocimiento no puede ser nula o de longitud vacia");
		}

		else if (areaPersistence.findByArea(areaEntity.getArea()) != null)
		{
			throw new BusinessLogicException("La area de conocimiento ya existe");
		}
		
        // Invoca la persistencia para crear la area de conocimiento pues se ha validado todas las reglas.
        areaPersistence.create(areaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del areaConocimiento");
        return areaEntity;		
	}
	
	/**
	 * Actualiza la areaConocimiento con el id recibido en la URL con la informacion que se recibe en el cuerpo de la petición.	 
	 * @param areaId Id de la areaConocimiento a actualizar en la base de datos.
	 * @param areaEntity Entidad con la informacion a actualizar en la base de datos.
	 * @return La areaConocimiento con la información actualizada.
	 * @throws BusinessLogicException en dado caso de que la areaConocimiento a actualizar no exista !
	 */
	
	public AreaConocimientoEntity updateArea(Long areaId, AreaConocimientoEntity areaEntity) throws BusinessLogicException
	{
		LOGGER.log(Level.INFO, "Inicia proceso de actualizar la areaConocimiento con el id = {0}", areaId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.		
		getArea(areaId); //Busca en primer lugar si la areaConocimiento existe
        AreaConocimientoEntity actualizado = areaPersistence.update(areaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la areaConocimiento con id = {0}", areaEntity.getId());
        return actualizado;
	}
	
	/**
     * Obtener una areaConocimiento por medio de su id.
     * @param areaId: id de la areaConocimiento para ser buscada.
     * @return la areaConocimiento solicitada por medio de su id.
	 * @throws BusinessLogicException en caso de que la areaConocimiento con el id no exista.
     */
	 
	public AreaConocimientoEntity getArea(Long areaId) throws BusinessLogicException
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la areaConocimiento con id = {0}", areaId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        AreaConocimientoEntity buscado = areaPersistence.find(areaId);
        if (buscado == null)
		{
            LOGGER.log(Level.SEVERE, "La areaConocimiento con el id = {0} no existe", areaId);
			throw new BusinessLogicException("La areaConocimiento con el id : " + areaId + " no existe");
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la areaConocimiento con id = {0}", areaId);
        return buscado;
    }
	
	/**
     * Obtener todas las areaConocimiento existentes en la base de datos.
     * @return Una lista de las areas de conocimiento existentes.
     */
	
    public List<AreaConocimientoEntity> getAllAreas()
	{
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las areas de conocimiento");
        // Note que, por medio de la inyección de dependencias se llama al método "getAll()" que se encuentra en la persistencia.
        List<AreaConocimientoEntity> entities = areaPersistence.getAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las areas de conocimiento");
        return entities;
    }
	
	/**
     * Borrar un areaConocimiento existente en la base de datos.     
     * @param areaId: id de la areaConocimiento a borrar     
	 * @throws BusinessLogicException si la areaConocimiento a borrar no existe.
     */
	
    public void deleteArea(Long areaId) throws BusinessLogicException
	{
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la areaConocimiento con id = {0}", areaId);
		getArea(areaId); //Buscar la areaConocimiento a borrar para mirar si existe
        areaPersistence.delete(areaId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la areaConocimiento con id = {0}", areaId);
    }
}
