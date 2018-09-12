/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistance;

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
        LOGGER.log(Level.INFO, "Creando un libro nuevo");
        em.persist(tarjetaEntity);
        LOGGER.log(Level.INFO, "Libro creado");
        return tarjetaEntity;
    }

    /**
     * Devuelve todas las tarjetas de la base de datos.
     *
     * @return una lista con todas las tarjetas que encuentre en la base de datos,
     * "select u from TarjetaDeCreditoEntity u" es como un "select * from TarjetaDeCreditoEntity;" -
     * "SELECT * FROM table_name" en SQL.
     */
    public List<TarjetaDeCreditoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos las tarjetas");
        Query q = em.createQuery("select u from TarjetaDeCreditoEntity u");
        return q.getResultList();
    }

    /**
     * Busca si hay alguna tarjeta con el id que se envía de argumento
     *
     * @param idTarjeta: id correspondiente de la tarjeta buscada.
     * @return una tarjeta.
     */
    public TarjetaDeCreditoEntity find(Long idTarjeta) {
        LOGGER.log(Level.INFO, "Consultando la tarjeta con id={0}", idTarjeta);
        return em.find(TarjetaDeCreditoEntity.class, idTarjeta);
    }

    /**
     * Actualiza una tarjeta.
     *
     * @param tarjetaEntity: la tarjeta que viene con los nuevos cambios. Por ejemplo
     * el nombre pudo cambiar. En ese caso, se haria uso del método update.
     * @return una tarjeta con los cambios aplicados.
     */
    public TarjetaDeCreditoEntity update(TarjetaDeCreditoEntity tarjetaEntity) {
        LOGGER.log(Level.INFO, "Actualizando la tarjeta con id={0}", tarjetaEntity.getId());
        return em.merge(tarjetaEntity);
    }

    /**
     *
     * Borra un libro de la base de datos recibiendo como argumento el id del
     * libro
     *
     * @param idTarjeta: id correspondiente a la tarjeta a borrar.
     */
    public void delete(Long idTarjeta) {
        LOGGER.log(Level.INFO, "Borrando la tarjeta con id={0}", idTarjeta);
        TarjetaDeCreditoEntity bookEntity = em.find(TarjetaDeCreditoEntity.class, idTarjeta);
        em.remove(bookEntity);
    }

    
}
