/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistence;

import co.edu.uniandes.csw.traductor.entities.TarjetaDeCreditoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ANDRES
 */
@Stateless
public class TarjetaDeCreditoPersistence {

    private static final Logger LOGGER = Logger.getLogger(TarjetaDeCreditoPersistence.class.getName());

    @PersistenceContext(unitName = "PrometeusPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param tarjetaEntity objeto tarjeta que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public TarjetaDeCreditoEntity create(TarjetaDeCreditoEntity tarjetaEntity) {
        LOGGER.log(Level.INFO, "Creando una tarjeta nueva");
        em.persist(tarjetaEntity);
        LOGGER.log(Level.INFO, "Tarjeta creado");
        return tarjetaEntity;
    }

    /**
     * Devuelve todas las tarjetas de la base de datos.
     *
     * @return una lista con todas las tarjetas que encuentre en la base de
     * datos, "select u from TarjetaDeCreditoEntity u" es como un "select * from
     * TarjetaDeCreditoEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<TarjetaDeCreditoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las tarjetas");
        Query q = em.createQuery("select u from TarjetaDeCreditoEntity u");
        return q.getResultList();
    }

    /**
     * Busca si hay alguna tarjeta con el id que se envía de argumento
     *
     * @param idTarjetaDeCredito: id correspondiente de la tarjeta buscada.
     * @return una tarjeta.
     */
    public TarjetaDeCreditoEntity find(Long idCliente,Long idTarjetaDeCredito) {
        LOGGER.log(Level.INFO, "Consultando la tarjeta con id={0}", idTarjetaDeCredito);
       TypedQuery<TarjetaDeCreditoEntity> q = em.createQuery("select p from TarjetaDeCreditoEntity p where (p.cliente.id = :idCliente) and (p.id = :idTarjeta)", TarjetaDeCreditoEntity.class);
        q.setParameter("idCliente", idCliente);
        q.setParameter("idTarjeta", idTarjetaDeCredito);
        List<TarjetaDeCreditoEntity> results = q.getResultList();
        TarjetaDeCreditoEntity review = null;
        if (results == null) {
            review = null;
        } else if (results.isEmpty()) {
            review = null;
        } else if (results.size() >= 1) {
            review = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la tarjeta con id = {0} del cliente con id =" + idCliente, idTarjetaDeCredito);
        return review;
    }

    public TarjetaDeCreditoEntity findByNumeroTarjeta(Long numeroTarjetaCredito) {
        LOGGER.log(Level.INFO, "Consultando tarjetas por numero ", numeroTarjetaCredito);
        // Se crea un query para buscar libros con el isbn que recibe el método como argumento. ":isbn" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From TarjetaDeCreditoEntity e where e.numeroTarjetaCredito = :numeroTarjetaCredito", TarjetaDeCreditoEntity.class);
        // Se remplaza el placeholder ":isbn" con el valor del argumento 
        query = query.setParameter("numeroTarjetaCredito", numeroTarjetaCredito);
        // Se invoca el query se obtiene la lista resultado
        List<TarjetaDeCreditoEntity> mismoNumero = query.getResultList();
        TarjetaDeCreditoEntity result;
        if (mismoNumero == null) {
            result = null;
        } else if (mismoNumero.isEmpty()) {
            result = null;
        } else {
            result = mismoNumero.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar tarjetas por numero ", numeroTarjetaCredito);
        return result;
    }

    /**
     * Actualiza una tarjeta.
     *
     * @param tarjetaEntity: la tarjeta que viene con los nuevos cambios. Por
     * ejemplo el nombre del titular pudo cambiar(corregir error). En ese caso,
     * se haria uso del método update.
     * @return una tarjeta con los cambios aplicados.
     */
    public TarjetaDeCreditoEntity update(TarjetaDeCreditoEntity tarjetaEntity) {
        LOGGER.log(Level.INFO, "Actualizando la tarjeta con id={0}", tarjetaEntity.getId());
        return em.merge(tarjetaEntity);
    }

    /**
     * Borra un libro de la base de datos recibiendo como argumento el id de la
     * tarjeta
     *
     * @param idTarjeta: id correspondiente a la tarjeta a borrar.
     */
    public void delete(Long idTarjeta) {
        LOGGER.log(Level.INFO, "Borrando la tarjeta con id={0}", idTarjeta);
        TarjetaDeCreditoEntity bookEntity = em.find(TarjetaDeCreditoEntity.class, idTarjeta);
        em.remove(bookEntity);
    }

}
