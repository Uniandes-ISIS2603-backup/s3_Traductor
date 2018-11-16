/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistence;

import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ejb.Stateless;

/**
 * Clase que implementa la persistencia en la base de datos de la clase
 * propuesta. De igual modo, esta clase realiza operaciones CRUD para el manejo
 * de operaciones transaccionales.
 *
 * @author Geovanny Andres Gonzalez
 */
@Stateless
public class PropuestaPersistence {

    private static final Logger LOGGER = Logger.getLogger(PropuestaPersistence.class.getName());

    @PersistenceContext(unitName = "PrometeusPU") //Administrador Entity de la base de datos.
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param propuestaEntity objeto propuesta que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public PropuestaEntity create(PropuestaEntity propuestaEntity) {
        LOGGER.log(Level.INFO, "Creando una propuesta nueva");
        /* Note que hacemos uso de un método propio de EntityManager para persistir la propuesta en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(propuestaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una propuesta nueva");
        return propuestaEntity;
    }

    /**
     * Busca si hay alguna propuesta con el id que se envía de argumento
     *
     * @param propuestaId: id correspondiente a la propuesta buscada.
     * @return La propuesta con el id que se recibe por parametro.
     */
    public PropuestaEntity find(Long empleadoId, Long propuestaId) {
        LOGGER.log(Level.INFO, "Consultando propuesta con id={0}", propuestaId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from PropuestaEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */

        TypedQuery<PropuestaEntity> q = em.createQuery("select p from PropuestaEntity p where (p.empleado.id = :empleadoId) and (p.id = :propuestaId)", PropuestaEntity.class);
        q.setParameter("empleadoId", empleadoId);
        q.setParameter("propuestaId", propuestaId);
        List<PropuestaEntity> results = q.getResultList();
        PropuestaEntity review = null;
        if (results == null) {
            review = null;
        } else if (results.isEmpty()) {
            review = null;
        } else if (results.size() >= 1) {
            review = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la propueta con id = {0} del empleado con id =" + empleadoId, propuestaId);
        return review;
    }

    /**
     * Busca si hay alguna propuesta con el id que se envía de argumento
     *
     * @param propuestaId: id correspondiente a la propuesta buscada.
     * @return La propuesta con el id que se recibe por parametro.
     */
    public PropuestaEntity findSoloId(Long propuestaId) {
        LOGGER.log(Level.INFO, "Consultando propuesta con id={0}", propuestaId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from PropuestaEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Finalizando consulta propuesta con id={0}", propuestaId);
        return em.find(PropuestaEntity.class, propuestaId);
    }

    /**
     * Actualiza una propuesta.
     *
     * @param propuestaEntity: la propuesta que viene con los nuevos cambios.
     * Por ejemplo el estado o el costo pudo cambiar. En ese caso, se haria uso
     * del método update.
     * @return una propuesta con los cambios aplicados.
     */
    public PropuestaEntity update(PropuestaEntity propuestaEntity) {
        LOGGER.log(Level.INFO, "Actualizando propuesta con id = {0}", propuestaEntity.getId());

        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la propuesta con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar la propuesta con id = {0}", propuestaEntity.getId());
        return em.merge(propuestaEntity);
    }

    /**
     *
     * Borra una propuesta de la base de datos recibiendo como argumento el id
     * de la propuesta
     *
     * @param propuestasId: id correspondiente a la propuesta a borrar.
     */
    public void delete(Long propuestasId) {
        LOGGER.log(Level.INFO, "Borrando propuesta con id = {0}", propuestasId);

        // Se hace uso de mismo método que esta explicado en public PropuestaEntity find(Long id) para obtener la propuesta a borrar.
        PropuestaEntity entity = em.find(PropuestaEntity.class, propuestasId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from PropuestaEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la propuesta con id = {0}", propuestasId);
    }

    /**
     * Busca las propuestas que existen en la base de datos que posean el costo
     * dado por parametro.
     *
     * @param costo: Es el costo de las propuestas que se estan buscando
     * @return null si no existe ninguna propuesta con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    public List<PropuestaEntity> findAllByCosto(Integer costo) {
        LOGGER.log(Level.INFO, "Consultando propuestas por costo ", costo);
        // Se crea un query para buscar propuestaes con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From PropuestaEntity e where e.costo = :costo", PropuestaEntity.class); //Quey tipeado para consulta
        // Se remplaza el placeholder ":name" con el valor del argumento. Igual que el ? en el PreparedStatement
        query = query.setParameter("costo", costo); //Parecido al Set de parametros del PreparedStatement
        // Se invoca el query se obtiene la lista resultado
        List<PropuestaEntity> propuestasCosto = query.getResultList(); //Retorna una lista con las tuplas resultados.        
        LOGGER.log(Level.INFO, "Saliendo de las propuestas por costo ", costo);
        return propuestasCosto;
    }

    /**
     * Busca las propuestas que existen en la base de datos.
     *
     * @return Todas las propuestas existentes en la base de datos.
     */
    public List<PropuestaEntity> getAll() {
        LOGGER.log(Level.INFO, "Consultando todas las propuestas");
        // Se crea un query para buscar propuestaes con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From PropuestaEntity e", PropuestaEntity.class); //Quey tipeado para consulta       
        // Se invoca el query se obtiene la lista resultado
        List<PropuestaEntity> propuestas = query.getResultList(); //Retorna una lista con las tuplas resultados.        
        LOGGER.log(Level.INFO, "Retornando todas las propuestas");
        return propuestas;
    }
}
