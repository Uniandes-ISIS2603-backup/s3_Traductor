/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistence;

import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
import co.edu.uniandes.csw.traductor.entities.IdiomaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Juan Felipe Parra
 */
@Stateless
public class CalificacionPersistence {
     private static final Logger LOGGER = Logger.getLogger(CalificacionPersistence.class.getName());
        @PersistenceContext(unitName = "PrometeusPU")
        protected EntityManager em;
        /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param calificacionEntity objeto calificacion que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public CalificacionEntity create(CalificacionEntity calificacionEntity) {
        LOGGER.log(Level.INFO, "Creando una calificacion nueva");
        em.persist(calificacionEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una calificaion nueva");
        return calificacionEntity;
    }
	
    /**
     * Devuelve todas las calificaciones de la base de datos.
     *
     * @return una lista con todas las calificaciones que encuentre en la base de
     * datos, "select u from CalificaionEntity u" es como un "select * from
     * CalificacionEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<CalificacionEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos las calificaciones");
        
        TypedQuery query = em.createQuery("select u from CalificacionEntity u", CalificacionEntity.class);
        return query.getResultList();
    }
	
    /**
     * Busca si hay alguna calificacion con el id que se envía de argumento
     *
     * @param calificacionId: id correspondiente a la calificacion buscada.
     * @return La calificacion con el id que se recibe por parametro.
     */
    public CalificacionEntity find(Long empleadoId, Long calificacionId) {
        LOGGER.log(Level.INFO, "Consultando calificacion con id={0}", calificacionId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from CalificacionEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */

        TypedQuery<CalificacionEntity> q = em.createQuery("select p from CalificacionEntity p where (p.empleado.id = :empleadoId) and (p.id = :calificacionId)", CalificacionEntity.class);
        q.setParameter("empleadoId", empleadoId);
        q.setParameter("calificacionId", calificacionId);
        List<CalificacionEntity> results = q.getResultList();
        CalificacionEntity cal = null;
        if (results != null && results.size() >= 1) {
            cal = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la propueta con id = {1} del empleado con id {0}", new Object[]{empleadoId, calificacionId});
        return cal;
    }

    /**
     * Busca si hay alguna calificacion con el id que se envía de argumento
     *
     * @param calificacionId: id correspondiente a la calificacion buscada.
     * @return La calificacion con el id que se recibe por parametro.
     */
    public CalificacionEntity findSoloId(Long calificacionId) {
        LOGGER.log(Level.INFO, "Consultando calificacion con id={0}", calificacionId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from CalificacionEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Finalizando consulta calificacion con id={0}", calificacionId);
        return em.find(CalificacionEntity.class, calificacionId);
    }
     /**
     * Actualiza una calificacion.
     *
     * @param areaEntity: la calificacion que viene con los nuevos cambios.
     * Por ejemplo el estado o el costo pudo cambiar. En ese caso, se haria uso
     * del método update.
     * @return una calificacion con los cambios aplicados.
     */
    public CalificacionEntity update(CalificacionEntity areaEntity) {
        LOGGER.log(Level.INFO, "Actualizando calificacion con id = {0}", areaEntity.getId());

        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        la calificacion con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(areaEntity);
    }
}
