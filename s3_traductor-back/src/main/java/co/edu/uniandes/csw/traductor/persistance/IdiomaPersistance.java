/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistance;

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
public class IdiomaPersistance {
        private static final Logger LOGGER = Logger.getLogger(IdiomaPersistance.class.getName());
        @PersistenceContext(unitName = "TraductorPU")
        protected EntityManager em;
        /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param idiomaEntity objeto idioma que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public IdiomaEntity create(IdiomaEntity idiomaEntity) {
        LOGGER.log(Level.INFO, "Creando un idioma nuevo");
        em.persist(idiomaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un idioma nuevo");
        return idiomaEntity;
    }
	
    /**
     * Devuelve todos los idiomas de la base de datos.
     *
     * @return una lista con todos los idiomas que encuentre en la base de
     * datos, "select u from IdiomaEntity u" es como un "select * from
     * IdiomaEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<IdiomaEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los idiomas");
        
        TypedQuery query = em.createQuery("select u from IdiomaEntity u", IdiomaEntity.class);
        return query.getResultList();
    }
	
    /**
     * Busca si hay algun idioma con el id enviado por parametro
     *
     * @param idiomsId: id correspondiente al idioma buscada.
     * @return una editorial.
     */
    public IdiomaEntity find(Long idiomsId) {
        LOGGER.log(Level.INFO, "Consultando idioma con id={0}", idiomsId);
        return em.find(IdiomaEntity.class, idiomsId);
    }
    
    
    /**
     *
     * Borra un idoma de la base de datos recibiendo como argumento el id
     * del idioma
     *
     * @param idiomsID: id correspondiente al idioma a borrar.
     */
    public void delete(Long idiomsID) {
        LOGGER.log(Level.INFO, "Borrando idioma con id = {0}", idiomsID);
        IdiomaEntity entity = em.find(IdiomaEntity.class, idiomsID);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el idioma con id = {0}", idiomsID);
    }
	
   
}
