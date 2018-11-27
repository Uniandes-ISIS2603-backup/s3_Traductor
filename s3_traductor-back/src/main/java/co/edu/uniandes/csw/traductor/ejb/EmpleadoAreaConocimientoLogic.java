/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.AreaConocimientoEntity;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.AreaConocimientoPersistence;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Alvaro
 */
public class EmpleadoAreaConocimientoLogic {
     private final static Logger LOGGER=Logger.getLogger(EmpleadoAreaConocimientoLogic.class.getName());
    
    @Inject
    private EmpleadoPersistence empleadoPersistence;
    @Inject
    private AreaConocimientoPersistence areaPersistence;
    /**
     * 
     * @param empleadoId identificador del empleado al cual se le asignara una area de conocimiento
     * @param areaConocimientoId identificador del id a asignar
     * @return la entidad encontrada luego de la persistencia
     */
    public   AreaConocimientoEntity  addArea(Long empleadoId,Long areaConocimientoId){
       LOGGER.log(Level.INFO, "Inicia proceso de agregarle una area de conocimiento al empleado con id = {0}", empleadoId);
        EmpleadoEntity empleadoEntity = empleadoPersistence.find(empleadoId);
        AreaConocimientoEntity areaEntity = areaPersistence.find(areaConocimientoId);
        empleadoEntity.getAreasDeConocimiento().add(areaEntity);
        areaEntity.getEmpleados().add(empleadoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una area de conocimiento al empleado con id = {0}", empleadoId);
        return areaEntity;
    }
    /**
     * Retorna todas las areas del empleado buscado
     *
     * @param empleadoId El ID de el empleado buscado
     * @return La lista de areas del empleado
     */
    public List<AreaConocimientoEntity> getAreasConocimiento(Long empleadoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las areas de conocimiento asociadas al empleado con id = {0}", empleadoId);
        return empleadoPersistence.find(empleadoId).getAreasDeConocimiento();
    }
    /**
     * Retorna una area asociada a un empleado
     *
     * @param empleadoId  El id del empleado a buscar.
     * @param areaConocimientoId  El id del area a buscar
     * @return El area encontrada dentro del empleado.
     * @throws BusinessLogicException si el area no se encuentra en el empleado
     */
    public AreaConocimientoEntity getAreaConocmiento(Long empleadoId,Long areaConocimientoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el area de conocimiento con id = {0} del empleado con id = {1}", new Object[]{empleadoId, areaConocimientoId});
        List<AreaConocimientoEntity> areas = empleadoPersistence.find(empleadoId).getAreasDeConocimiento();
        AreaConocimientoEntity areaEntity = areaPersistence.find(areaConocimientoId);
        int index = areas.indexOf(areaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el area de conocimiento con id = {0} del empleado con id = {1}", new Object[]{empleadoId, areaConocimientoId});
        if (index >= 0) {
            return areas.get(index);
        }
        throw new BusinessLogicException("El area no esta asociada al empleado");
    }
    /**
     * 
     * @param empleadoId empleado a buscar.
     * @param areaConocimientoId area a eliminar.
     * @throws BusinessLogicException 
     */

    public void deleteArea(Long empleadoId, Long areaConocimientoId) throws BusinessLogicException {
        
        LOGGER.log(Level.INFO, "EmpleadoAreaConocimientoResoruce deleteArea: input: empleadoId {0}, areaConocimientoId: {1}", new Object[]{empleadoId, areaConocimientoId});
        EmpleadoEntity empleado = empleadoPersistence.find(empleadoId);
        AreaConocimientoEntity area = areaPersistence.find(areaConocimientoId);
        int index = empleado.getAreasDeConocimiento().indexOf(area);
        if(index >= 0)
        {
          empleado.getAreasDeConocimiento().remove(index);
        }
        int index2 = area.getEmpleados().indexOf(empleado);
        if(index2 >= 0)
        {
          area.getEmpleados().remove(index2);
        }
        throw new BusinessLogicException("El area no esta asociada al empleado");
    }
}
