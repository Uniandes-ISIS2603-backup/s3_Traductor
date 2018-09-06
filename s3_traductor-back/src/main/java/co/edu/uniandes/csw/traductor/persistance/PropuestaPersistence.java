/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistance;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ejb.Stateless;

/**
 *
 * @author Geovanny Andres Gonzalez
 */

@Stateless
public class PropuestaPersistence 
{
	private static final Logger LOGGER = Logger.getLogger(PropuestaPersistence.class.getName());
	
	@PersistenceContext(unitName = "PrometeusPU") //Administrador Entity de la base de datos.
	protected EntityManager em;
	
	/**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param propuestaEntity objeto editorial que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
	
	public PropuestaEntity create(PropuestaEntity propuestaEntity) {
        LOGGER.log(Level.INFO, "Creando una editorial nueva");
        /* Note que hacemos uso de un método propio de EntityManager para persistir la editorial en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(propuestaEntity); 
        LOGGER.log(Level.INFO, "Saliendo de crear una editorial nueva");
        return propuestaEntity;
    }
}
