/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistence;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ejb.Stateless;

/**
 * Clase que implementa la persistencia en la base de datos de la clase invitacion.
 * De igual modo, esta clase realiza operaciones CRUD para el manejo de operaciones transaccionales.
 * @author Geovanny Andres Gonzalez
 */

@Stateless
public class InvitacionPersistence 
{
	private static final Logger LOGGER = Logger.getLogger(InvitacionPersistence.class.getName());
	
	@PersistenceContext(unitName = "PrometeusPU") //Administrador Entity de la base de datos.
	protected EntityManager em;
	
	/**
     * Método para persisitir la entidad en la base de datos.     *
     * @param invitacionEntity objeto invitacion que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
	
	public InvitacionEntity create(InvitacionEntity invitacionEntity) {
        LOGGER.log(Level.INFO, "Creando una invitacion nueva");
        /* Note que hacemos uso de un método propio de EntityManager para persistir la propuesta en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(invitacionEntity); 
        LOGGER.log(Level.INFO, "Saliendo de crear una invitacion nueva");
        return invitacionEntity;
    }
	
	/**
     * Busca si hay alguna invitacion con el id que se envía de argumento     
     * @param invitacionId: id correspondiente a la invitacion buscada.
     * @return La invitacion con el id que se recibe por parametro.
     */
	
    public InvitacionEntity find(Long invitacionId) {
        LOGGER.log(Level.INFO, "Consultando invitacion con id={0}", invitacionId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from InvitacionEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
		
        return em.find(InvitacionEntity.class, invitacionId);
    }

	 /**
     * Actualiza una invitacion.    
     * @param invitacionEntity: la invitacion que viene con los nuevos cambios.
     * Por ejemplo el estado o el costo pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una invitacion con los cambios aplicados.
     */
	
    public InvitacionEntity update(InvitacionEntity invitacionEntity) {
        LOGGER.log(Level.INFO, "Actualizando invitacion con id = {0}", invitacionEntity.getId());
        
		/* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la propuesta con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
		
        LOGGER.log(Level.INFO, "Saliendo de actualizar la invitacion con id = {0}", invitacionEntity.getId());
        return em.merge(invitacionEntity);
    }
	
    /**
     *
     * Borra una invitacion de la base de datos recibiendo como argumento el id
     * de la propuesta
     * @param invitacionId: id correspondiente a la propuesta a borrar.
     */
	
    public void delete(Long invitacionId) {
        LOGGER.log(Level.INFO, "Borrando invitacion con id = {0}", invitacionId);
        
		// Se hace uso de mismo método que esta explicado en public InvitacionEntity find(Long id) para obtener la propuesta a borrar.
        InvitacionEntity entity = em.find(InvitacionEntity.class, invitacionId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from InvitacionEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la invitacion con id = {0}", invitacionId);
    }    
}
