package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Felipe Parra
 */
@Stateless
public class CalificacionLogic {

     private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());
    public final static String EN_PROCESO = "EN_PROCESO"; //Valor de estado para la calificacion.
    public final static String CULMINADA = "CULMINADA"; //Valor de estado para la calificacion.

    @Inject
    private CalificacionPersistence calificacionPersistence; //Invocación a la tabla de calificacion para trabajar en la base de datos.	

    @Inject
    private EmpleadoPersistence empleadoPersistence;

    /**
     * Crea una calificacion en la persistencia.
     *
     * @param empleadoId Identificacion del empleado.
     * @param calificacionEntity La entidad que representa la calificacion a
     * persistir.
     * @return La entidad de la calificacion luego de persistirla.
     * @throws BusinessLogicException Si la calificacion a persistir ya existe o si
     * no se cumple la integridad de los datos requeridos.
     */
    public CalificacionEntity createCalificacion(Long empleadoId, CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la calificacion");

        // Verifica la regla de negocio. Verificacion de los datos del Entity.
        if (calificacionEntity.getValorCalificacion()< 0) {
            throw new BusinessLogicException("El valor de la calificacion no puede ser un valor negativo, su valor fue: " + calificacionEntity.getValorCalificacion());
        } else if (calificacionEntity.getComentario().length() == 0 || calificacionEntity.getComentario()== null) {
            throw new BusinessLogicException("La descripcion de la calificacion no puede ser nula o vacia");
        }

        // Invoca la persistencia para crear la calificacion pues se ha validado todas las reglas.
        EmpleadoEntity entidadPadre = empleadoPersistence.find(empleadoId);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la calificacion");
        calificacionEntity.setEmpleado(entidadPadre);
        return calificacionPersistence.create(calificacionEntity);
    }

    /**
     * Actualiza la calificacion con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param empleadoId Id del empleado a actualizar su calificacion a actualizar
     * en la base de datos.
     * @param calificacionEntity Entidad con la informacion a actualizar en la base
     * de datos.
     * @return La calificacion con la información actualizada.
     */
    public CalificacionEntity updateCalificacion(Long empleadoId, CalificacionEntity calificacionEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la calificacion del empleado con el id = {0}", empleadoId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.		
        EmpleadoEntity entidadPadre = empleadoPersistence.find(empleadoId);
        calificacionEntity.setEmpleado(entidadPadre);
        calificacionPersistence.update(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la calificacion del empleado con el id = {0}", empleadoId);
        return calificacionEntity;
    }

    /**
     * Obtener una calificacion por medio de su id.
     *
     * @param empleadoId Identificacion del empleado
     * @param calificacionId: id de la calificacion para ser buscada.
     * @return la calificacion solicitada por medio de su id.
     */
    public CalificacionEntity getCalificacion(Long empleadoId, Long calificacionId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la calificacion con id = {0}", calificacionId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        return calificacionPersistence.find(empleadoId, calificacionId);
    }

    /**
     * Obtener una calificacion por medio de su id sin tener en cuente el empleado.
     *
     * @param calificacionId: id de la calificacion para ser buscada.
     * @return la calificacion solicitada por medio de su id.
     */
    public CalificacionEntity getCalificacionSoloId(Long calificacionId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la calificacion con id = {0}", calificacionId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        return calificacionPersistence.findSoloId(calificacionId);
    }

    /**
     * Obtener todas las calificaciones existentes en la base de datos.
     *
     * @param empleadoId Identificador del empleado
     * @return una lista de calificaciones.
     */
    public List<CalificacionEntity> getAllCalificaciones(Long empleadoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las calificaciones");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        EmpleadoEntity entidadPadre = empleadoPersistence.find(empleadoId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las calificaciones");
        return entidadPadre.getCalificaciones();
    }

    /**
     * Borrar un calificacion existente en la base de datos.
     *
     * @param empleadoId Identificacion del empleado
     * @param calificacionId: id de la calificacion a borrar
     * @throws BusinessLogicException Si la calificacion asociada posee una
     * invitación, debido a que esa labor es responsabilidad del cliente que la
     * interpuso.
     */
}
