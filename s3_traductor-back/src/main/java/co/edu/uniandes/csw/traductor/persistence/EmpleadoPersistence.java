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
        LOGGER.log(Level.INFO, "Creando un empleado nuevo");
        em.persist(empleado);
        LOGGER.log(Level.INFO, "empleado creado");
        return empleado;
    }
    /**
     * Devuelve todos los empleados de la base de datos.
     *
     * @return una lista con todas todos los empleados que encuentre en la base de
     * datos, "select u from EmpleadoEntory u" es como un "select * from
     * EmpleadoEntity;" - "SELECT * FROM table_name" en SQL.
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
     * Actualiza un empleado.
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
     * el empleado
     *
     * @param empId: id correspondiente al empleado a borrar.
     */
    public void delete(Long empId) {

        LOGGER.log(Level.INFO, "Borrando el empleado con id={0}", empId);
        EmpleadoEntity empleadoTmp = em.find(EmpleadoEntity.class, empId);
        em.remove(empleadoTmp);
    }
    
    /**
     * Busca si hay algun empleado con el nombre de usuario que se envía de argumento
     *
     * @param nombreUsuario: el nombre de usuario del empleado que se está buscando
     * @return null si no existe ningun empleado con la identificación del argumento. Si
     * existe alguno devuelve el primero.
     */
    public EmpleadoEntity findByNombreUsuario(String nombreUsuario) {
        LOGGER.log(Level.INFO, "Consultando empleados por nombre de usuario. Nombre = {0} ", nombreUsuario);
        // Se crea un query para buscar empleados con el nombre de usuario que recibe el método como argumento. ":nombreUsuario" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From EmpleadoEntity e where e.nombreUsuario = :nombreUsuario", EmpleadoEntity.class);
        // Se remplaza el placeholder ":nombreUsuario" con el valor del argumento 
        query = query.setParameter("nombreUsuario", nombreUsuario);
        // Se invoca el query se obtiene la lista resultado
        List<EmpleadoEntity> sameNombreUsuario = query.getResultList();
        EmpleadoEntity result;
        if (sameNombreUsuario == null) {
            result = null;
        } else if (sameNombreUsuario.isEmpty()) {
            result = null;
        } else {
            result = sameNombreUsuario.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar empleados por nombre de usuario. Nombre = {0} ", nombreUsuario);
        return result;
    }
    /**
     * Busca si hay algun Empleado con el correo que se envía de argumento
     *
     * @param correoElectronico: correo del Empleado que se está buscando
     * @return null si no existe ningun Empleado con el correo del argumento. Si
     * existe alguno devuelve el primero.
     */
    public EmpleadoEntity findByCorreo(String correoElectronico) {
        LOGGER.log(Level.INFO, "Consultando empleados por correo electronico. Correo = {0} ", correoElectronico);
        // Se crea un query para buscar empleados con el correo que recibe el método como argumento. ":correoElectronico" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From EmpleadoEntity e where e.correoElectronico = :correoElectronico", EmpleadoEntity.class);
        // Se remplaza el placeholder ":correoElectronico" con el valor del argumento 
        query = query.setParameter("correoElectronico", correoElectronico);
        // Se invoca el query se obtiene la lista resultado
        List<EmpleadoEntity> sameCorreoElectronico = query.getResultList();
        EmpleadoEntity result;
        if (sameCorreoElectronico == null) {
            result = null;
        } else if (sameCorreoElectronico.isEmpty()) {
            result = null;
        } else {
            result = sameCorreoElectronico.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar empleados por correo electronico. Correo = {0} ", correoElectronico);
        return result;
    }
}
