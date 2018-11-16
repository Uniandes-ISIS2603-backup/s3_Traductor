/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistence;

import co.edu.uniandes.csw.traductor.entities.PagosEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ANDRES
 */
@Stateless
public class PagosPersistence {
            private static final Logger LOGGER = Logger.getLogger(PagosPersistence.class.getName());
        @PersistenceContext(unitName = "PrometeusPU")
        protected EntityManager em;
        /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param pagosEntity objeto pago que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public PagosEntity create(PagosEntity pagosEntity) {
        LOGGER.log(Level.INFO, "Creando un pago nuevo");
        em.persist(pagosEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un pago nuevo");
        return pagosEntity;
    }
	
    /**
     * Devuelve todos los pagos de la base de datos.
     *
     * @return una lista con todos los pagos que encuentre en la base de
     * datos, "select u from PagosEntity u" es como un "select * from
     * PagosEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<PagosEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los pagos");
        
        TypedQuery query = em.createQuery("select u from PagosEntity u", PagosEntity.class);
        return query.getResultList();
    }
	
    /**
     * Busca si hay algun pago con el id enviado por parametro
     *
     * @param idTransaccion: id correspondiente al pago buscado.
     * @return una editorial.
     */
    public PagosEntity find(Long idCliente,Long idTransaccion) {
       LOGGER.log(Level.INFO, "Consultando el pago con id = {0} del cliente con id = " + idCliente, idTransaccion);
        TypedQuery<PagosEntity> q = em.createQuery("select p from PagosEntity p where (p.cliente.id = :idCliente) and (p.id = :idTransaccion)", PagosEntity.class);
        q.setParameter("idCliente", idCliente);
        q.setParameter("idTransaccion", idTransaccion);
        List<PagosEntity> results = q.getResultList();
        PagosEntity pago = null;
        if (results == null) {
            pago = null;
        } else if (results.isEmpty()) {
            pago = null;
        } else if (results.size() >= 1) {
            pago = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar el pago con id = {0} del cliente con id =" + idTransaccion, idCliente);
        return pago;
    }
    public PagosEntity update(PagosEntity pagosEntity) {
        LOGGER.log(Level.INFO, "Actualizando el pago con id={0}", pagosEntity.getId());
        return em.merge(pagosEntity);
    }
    
    
    /**
     *
     * Borra un pago de la base de datos recibiendo como argumento el id
     * del pago
     *
     * @param idPago: id correspondiente al pago a borrar.
     */
    public void delete(Long idPago) {
        LOGGER.log(Level.INFO, "Borrando pago con id = {0}", idPago);
        PagosEntity entity = em.find(PagosEntity.class, idPago);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el pago con id = {0}", idPago);
    }
	
}
