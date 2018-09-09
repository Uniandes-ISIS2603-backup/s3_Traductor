/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistence;

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
public class SolicitudPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(SolicitudPersistence.class.getName());
        @PersistenceContext(unitName = "PrometeusPU")
        protected EntityManager em;
        /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param SolicitudEntity objeto solicitud que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public SolicitudPersistence create(SolicitudPersistence solicitudEntity) {
        LOGGER.log(Level.INFO, "Creando una solicitd nueva");
        em.persist(solicitudEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una solicitud nueva");
        return solicitudEntity;
    }
	
    /**
     * Devuelve todas las solicitudes de la base de datos.
     *
     * @return una lista con todas las solicitudes que encuentre en la base de
     * datos, "select u from SolicitudEntity u" es como un "select * from
     * SolicitudEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<SolicitudEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las solicitudes");
        
        TypedQuery query = em.createQuery("select u from SolicitudEntity u", SolicitudEntity.class);
        return query.getResultList();
    }
	
    /**
     * Busca si hay alguna solcitud con el id enviado por parametro
     *
     * @param calId: id correspondiente a la calificacion buscada.
     * @return una editorial.
     */
    public SolicitudEntity find(Long calId) {
        LOGGER.log(Level.INFO, "Consultando solicitud con id={0}", calId);
        return em.find(SolicitudEntity.class, calId);
    }
    
    
}
