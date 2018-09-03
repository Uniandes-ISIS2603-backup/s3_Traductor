/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistance;

import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
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
public class ClientePersistance {
    private static final Logger LOGGER = Logger.getLogger(ClientePersistance.class.getName());
        @PersistenceContext(unitName = "TraductorPU")
        protected EntityManager em;
        /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param calificacionEntity objeto calificacion que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ClienteEntity create(ClienteEntity clienteEntity) {
        LOGGER.log(Level.INFO, "Creando un cliente nuevo");
        em.persist(clienteEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un cliente nuevo");
        return clienteEntity;
    }
	
    /**
     * Devuelve todas calificaciones de la base de datos.
     *
     * @return una lista con todas las calificaciones que encuentre en la base de
     * datos, "select u from ClienteEntity u" es como un "select * from
     * ClienteEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ClienteEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos las calificaciones");
        
        TypedQuery query = em.createQuery("select u from CalificacionEntity u", ClienteEntity.class);
        return query.getResultList();
    }
	
    /**
     * Busca si hay alguna calificacion con el id enviado por parametro
     *
     * @param calId: id correspondiente a la calificacion buscada.
     * @return una editorial.
     */
    public ClienteEntity find(Long calId) {
        LOGGER.log(Level.INFO, "Consultando calificacion con id={0}", calId);
        return em.find(ClienteEntity.class, calId);
    }
    /**
     * Actualiza una cliente.
     *
     * @param authorEntity: la author que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return una author con los cambios aplicados.
     */
    public ClienteEntity update(ClienteEntity clienteEntity) {
        LOGGER.log(Level.INFO, "Actualizando el author con id={0}", clienteEntity.getId());
        
        return em.merge(clienteEntity);
    }
}
