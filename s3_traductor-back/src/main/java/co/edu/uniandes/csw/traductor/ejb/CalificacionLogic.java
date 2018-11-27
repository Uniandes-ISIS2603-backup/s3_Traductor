package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.CalificacionPersistence;
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

    private final static Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());

    @Inject
    private CalificacionPersistence persistence;

    /**
     * encargado de persistir una calificacion en la base de datos
     *
     * @param calificacionEntity entidad que representa la informacion a
     * persistir en la base de datos
     * @return la misma entidad que es persistida en el metodo
     * @throws BusinessLogicException si ya existe una calificacion con ese
     * mismo id, o si la calificacion se pasa de su valor máximo
     */
    public CalificacionEntity createCalificacion(CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia el proceso de creacion de una calificacion");
        //Verifica regla de negocio de que la calificación deve tener su valor entre 0 y 10.
        if (calificacionEntity.getValorCalificacion() < 0 || calificacionEntity.getValorCalificacion() > 10) {
            throw new BusinessLogicException("La calificacion posee un valor inválido");
        }
        persistence.create(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina el proceso de creacion de la calificacion");
        return calificacionEntity;
    }

    /**
     * reotrna todas las calificaciones almacenadas en el sistema
     *
     * @return Lista de las calificaciones almacenadas en el sistes
     */
    public List<CalificacionEntity> getCalificaciones() {
        LOGGER.log(Level.INFO, "Imicia la consulta de todas las califiacaciones");
        List<CalificacionEntity> idiomas = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina la consulta");
        return idiomas;
    }

    /**
     * retorna la entidad que representa la informacion de la entidad buscada
     * por id
     *
     * @param calificacionID identifiacdor mediante el cual se va buscar la
     * informacion de la entidad a retornar
     * @return la entidad que representa la informacion de la calificacion
     * buscada
     */
    public CalificacionEntity getCalificacion(Long calificacionID) {
        LOGGER.log(Level.INFO, "Inicia la consulta de calificacion segun el id={0}", calificacionID);
        CalificacionEntity calificacionEntity = persistence.find(calificacionID);
        if (calificacionEntity == null) {
            LOGGER.log(Level.SEVERE, "La calificacion con el id = {0} no existe", calificacionID);
        }
        LOGGER.log(Level.INFO, "Termina el proceso de consulta de una calificacion con el id={0}", calificacionID);
        return calificacionEntity;
    }
    /**
     *
     * Actualizar un calificacion.
     *
     * @param calificacionesId: id del calificacion para buscarla en la base de
     * datos.
     * @param calificacionEntity: calificacion con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return El calificacion con los cambios actualizados en la base de datos.
     */
    public CalificacionEntity updateCalificacion(Long calificacionesId, CalificacionEntity calificacionEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la calificacion con id = {0}", calificacionesId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        CalificacionEntity newEntity = persistence.update(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la calificacion con id = {0}", calificacionEntity.getId());
        return newEntity;
    }
}
