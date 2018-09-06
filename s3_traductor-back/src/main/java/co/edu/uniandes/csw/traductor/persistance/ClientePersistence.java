/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistance;

import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Cliente. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 * @author Santiago Salazar & Juan Felipe Parra
 */
@Stateless
public class ClientePersistence 
{
    private static final Logger LOGGER = Logger.getLogger(ClientePersistence.class.getName());
    
    @PersistenceContext(unitName = "PrometeusPU")
    protected EntityManager em;
    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param clienteEntity objeto cliente que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ClienteEntity create(ClienteEntity clienteEntity) {
        LOGGER.log(Level.INFO, "Creando un cliente nuevo");
        em.persist(clienteEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un cliente nuevo");
        return clienteEntity;
    }
	
    /**
     * Devuelve todos los clientes de la base de datos.
     *
     * @return una lista con todas todos los clientes que encuentre en la base de
     * datos, "select u from ClienteEntity u" es como un "select * from
     * ClienteEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ClienteEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los clientes");
        
        TypedQuery query = em.createQuery("select u from ClienteEntity u", ClienteEntity.class);
        return query.getResultList();
    }
	
    /**
     * Busca si hay algun cliente con el id enviado por parametro
     *
     * @param clientesId: id correspondiente del cliente buscado.
     * @return una editorial.
     */
    public ClienteEntity find(Long clientesId) {
        LOGGER.log(Level.INFO, "Consultando cliente con id={0}", clientesId);
        return em.find(ClienteEntity.class, clientesId);
    }
    /**
     * Actualiza una cliente.
     *
     * @param clienteEntity: la author que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return el cliente con los cambios aplicados.
     */
    public ClienteEntity update(ClienteEntity clienteEntity) {
        LOGGER.log(Level.INFO, "Actualizando el cliente con id={0}", clienteEntity.getId());
        
        return em.merge(clienteEntity);
    }
}
