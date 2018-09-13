/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistence;
import co.edu.uniandes.csw.traductor.entities.AreaConocimientoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ejb.Stateless;

/**
 * Clase que implementa la persistencia en la base de datos de la clase areaConocimiento.
 * De igual modo, esta clase realiza operaciones CRUD para el manejo de operaciones transaccionales.
 * @author Geovanny Andres Gonzalez
 */

@Stateless
public class AreaConocimientoPersistence 
{
	private static final Logger LOGGER = Logger.getLogger(AreaConocimientoPersistence.class.getName());
	
	@PersistenceContext(unitName = "PrometeusPU") //Administrador Entity de la base de datos.
	protected EntityManager em;
	
	/**
     * Método para persisitir la entidad en la base de datos.     *
     * @param areaEntity objeto areaConocimiento que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
	
	public AreaConocimientoEntity create(AreaConocimientoEntity areaEntity) {
        LOGGER.log(Level.INFO, "Creando una areaConocimiento nueva");
        /* Note que hacemos uso de un método propio de EntityManager para persistir la propuesta en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(areaEntity); 
        LOGGER.log(Level.INFO, "Saliendo de crear una areaConocimiento nueva");
        return areaEntity;
    }
	
	/**
     * Busca si hay alguna areaConocimiento con el id que se envía de argumento     
     * @param areaId: id correspondiente a la areaConocimiento buscada.
     * @return La areaConocimiento con el id que se recibe por parametro.
     */
	
    public AreaConocimientoEntity find(Long areaId) {
        LOGGER.log(Level.INFO, "Consultando areaConocimiento con id={0}", areaId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from AreaConocimientoEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
		
        return em.find(AreaConocimientoEntity.class, areaId);
    }

	 /**
     * Actualiza una areaConocimiento.    
     * @param areaEntity: la areaConocimiento que viene con los nuevos cambios.
     * Por ejemplo el estado o el costo pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una areaConocimiento con los cambios aplicados.
     */
	
    public AreaConocimientoEntity update(AreaConocimientoEntity areaEntity) {
        LOGGER.log(Level.INFO, "Actualizando areaConocimiento con id = {0}", areaEntity.getId());
        
		/* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la propuesta con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
		
        LOGGER.log(Level.INFO, "Saliendo de actualizar la areaConocimiento con id = {0}", areaEntity.getId());
        return em.merge(areaEntity);
    }
	
    /**
     *
     * Borra una areaConocimiento de la base de datos recibiendo como argumento el id
     * de la propuesta
     * @param areaId: id correspondiente a la propuesta a borrar.
     */
	
    public void delete(Long areaId) {
        LOGGER.log(Level.INFO, "Borrando areaConocimiento con id = {0}", areaId);
        
		// Se hace uso de mismo método que esta explicado en public AreaConocimientoEntity find(Long id) para obtener la propuesta a borrar.
        AreaConocimientoEntity entity = em.find(AreaConocimientoEntity.class, areaId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from AreaConocimientoEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la areaConocimiento con id = {0}", areaId);
    }
	
	/**
     * Busca las areas de conocimiento que existen en la base de datos.          
     * @return Todas las areas de conocimiento existentes en la base de datos.     
     */
	
    public List<AreaConocimientoEntity> getAll() {
        LOGGER.log(Level.INFO, "Consultando todas las areas");
        // Se crea un query para buscar las invitaciones con el nombre que recibe el método como argumento
        TypedQuery query = em.createQuery("Select e From AreaConocimientoEntity e", AreaConocimientoEntity.class); //Query tipeado para consulta       
        // Se invoca el query se obtiene la lista resultado
        List<AreaConocimientoEntity> entidades = query.getResultList(); //Retorna una lista con las tuplas resultados.        
        LOGGER.log(Level.INFO, "Retornando todas las areas");
        return entidades;
    }
}
