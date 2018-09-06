/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistance;


import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author jhonattanfonseca
 */
@Stateless
public class SolicitudPersitance 
{
    private static final Logger LOGGER = Logger.getLogger(SolicitudPersitance.class.getName());
        @PersistenceContext(unitName = "PrometeusPU")
        protected EntityManager em;
        /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param calificacionEntity objeto calificacion que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public SolicitudPersitance create(SolicitudPersitance calificacionEntity) {
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
    public List<SolicitudEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos las calificaciones");
        
        TypedQuery query = em.createQuery("select u from CalificacionEntity u", SolicitudEntity.class);
        return query.getResultList();
    }
	
    /**
     * Busca si hay alguna calificacion con el id enviado por parametro
     *
     * @param calId: id correspondiente a la calificacion buscada.
     * @return una editorial.
     */
    public SolicitudEntity find(Long calId) {
        LOGGER.log(Level.INFO, "Consultando calificacion con id={0}", calId);
        return em.find(SolicitudEntity.class, calId);
    }
    
    
}

