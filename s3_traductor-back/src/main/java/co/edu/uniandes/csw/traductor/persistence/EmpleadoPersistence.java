/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.persistence;

import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Empleado. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author Alvaro
 */
@Stateless
public class EmpleadoPersistence {
    private static final Logger LOGGER = Logger.getLogger(EmpleadoPersistence.class.getName());

    @PersistenceContext(unitName = "PrometeusPU")
    protected EntityManager em;
    /**
     * Crea un autor en la base de datos
     *
     * @param empleado que se desea persistir
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public EmpleadoEntity create(EmpleadoEntity empleado) {
        LOGGER.log(Level.INFO, "Creando un autor nuevo");
        em.persist(empleado);
        LOGGER.log(Level.INFO, "Docuemento creado");
        return empleado;
    }
    /**
     * Devuelve todos los empleados de la base de datos.
     *
     * @return una lista con todas todos los clientes que encuentre en la base de
     * datos, "select u from ClienteEntity u" es como un "select * from
     * ClienteEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<EmpleadoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los empleados");
        
        TypedQuery query = em.createQuery("select u from EmpleadoEntity u", EmpleadoEntity.class);
        return query.getResultList();
    }
    /**
     * Busca si hay algun empleado con el id que se envía de argumento
     *
     * @param emId: id correspondiente al empleado buscada.
     * @return un author.
     */
    public EmpleadoEntity find(Long emId) {
        LOGGER.log(Level.INFO, "Consultando el empleado con id={0}", emId);
        return em.find(EmpleadoEntity.class, emId);
    }
    /**
     * Actualiza un documento.
     *
     * @param empleadoEntity: el empleado que viene con los nuevos cambios. 
     * @return un empleado con los cambios aplicados.
     */
    public EmpleadoEntity update(EmpleadoEntity empleadoEntity) {
       LOGGER.log(Level.INFO, "Actualizando el empleado con id={0}", empleadoEntity.getId());
       return em.merge(empleadoEntity);
    }
    /**
     * Borra un empleado de la base de datos recibiendo como argumento el id de
     * el documento
     *
     * @param empId: id correspondiente al empleado a borrar.
     */
    public void delete(Long empId) {

        LOGGER.log(Level.INFO, "Borrando el documento con id={0}", empId);
        EmpleadoEntity empleadoTmp = em.find(EmpleadoEntity.class, empId);
        em.remove(empleadoTmp);
    }
}
