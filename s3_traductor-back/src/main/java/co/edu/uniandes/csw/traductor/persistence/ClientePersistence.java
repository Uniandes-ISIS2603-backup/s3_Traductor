/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistence;

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
    
    /**
     *
     * Borra un cliente de la base de datos recibiendo como argumento el id
     * del cliente
     *
     * @param clientesId: id correspondiente al cliente a borrar.
     */
    public void delete(Long clientesId) {
        LOGGER.log(Level.INFO, "Borrando cliente con id = {0}", clientesId);
        
        ClienteEntity entity = em.find(ClienteEntity.class, clientesId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from ClienteEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el cliente con id = {0}", clientesId);
    }
    
    /**
     * Busca si hay algun cliente con la identificacion que se envía de argumento
     *
     * @param identificacion: Identificación del cliente que se está buscando
     * @return null si no existe ningun cliente con la identificación del argumento. Si
     * existe alguno devuelve el primero.
     */
    public ClienteEntity findByIdentificacion(String identificacion) {
        LOGGER.log(Level.INFO, "Consultando clientes por identificacion ", identificacion);
        // Se crea un query para buscar clientes con la identificacion que recibe el método como argumento. ":identificacion" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From ClienteEntity e where e.identificacion = :identificacion", ClienteEntity.class);
        // Se remplaza el placeholder ":identificacion" con el valor del argumento 
        query = query.setParameter("identificacion", identificacion);
        // Se invoca el query se obtiene la lista resultado
        List<ClienteEntity> sameIdentificacion = query.getResultList();
        ClienteEntity result;
        if (sameIdentificacion == null) {
            result = null;
        } else if (sameIdentificacion.isEmpty()) {
            result = null;
        } else {
            result = sameIdentificacion.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar clientes por identificacion ", identificacion);
        return result;
    }
    /**
     * Busca si hay algun cliente con el nombre de usuario que se envía de argumento
     *
     * @param nombreUsuario: el nombre de usuario del cliente que se está buscando
     * @return null si no existe ningun cliente con la identificación del argumento. Si
     * existe alguno devuelve el primero.
     */
    public ClienteEntity findByNombreUsuario(String nombreUsuario) {
        LOGGER.log(Level.INFO, "Consultando clientes por nombre de usuario ", nombreUsuario);
        // Se crea un query para buscar clientes con el nombre de usuario que recibe el método como argumento. ":nombreUsuario" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From ClienteEntity e where e.nombreUsuario = :nombreUsuario", ClienteEntity.class);
        // Se remplaza el placeholder ":nombreUsuario" con el valor del argumento 
        query = query.setParameter("nombreUsuario", nombreUsuario);
        // Se invoca el query se obtiene la lista resultado
        List<ClienteEntity> sameNombreUsuario = query.getResultList();
        ClienteEntity result;
        if (sameNombreUsuario == null) {
            result = null;
        } else if (sameNombreUsuario.isEmpty()) {
            result = null;
        } else {
            result = sameNombreUsuario.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar clientes por nombre de usuario ", nombreUsuario);
        return result;
    }
    /**
     * Busca si hay algun cliente con el correo que se envía de argumento
     *
     * @param correoElectronico: correo del cliente que se está buscando
     * @return null si no existe ningun cliente con el correo del argumento. Si
     * existe alguno devuelve el primero.
     */
    public ClienteEntity findByCorreo(String correoElectronico) {
        LOGGER.log(Level.INFO, "Consultando clientes por correo electronico ", correoElectronico);
        // Se crea un query para buscar clientes con el correo que recibe el método como argumento. ":correoElectronico" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From ClienteEntity e where e.correoElectronico = :correoElectronico", ClienteEntity.class);
        // Se remplaza el placeholder ":correoElectronico" con el valor del argumento 
        query = query.setParameter("correoElectronico", correoElectronico);
        // Se invoca el query se obtiene la lista resultado
        List<ClienteEntity> sameCorreoElectronico = query.getResultList();
        ClienteEntity result;
        if (sameCorreoElectronico == null) {
            result = null;
        } else if (sameCorreoElectronico.isEmpty()) {
            result = null;
        } else {
            result = sameCorreoElectronico.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar clientes por correo electronico ", correoElectronico);
        return result;
    }
}
