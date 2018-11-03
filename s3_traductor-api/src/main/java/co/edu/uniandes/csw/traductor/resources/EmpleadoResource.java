/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.EmpleadoDTO;
import co.edu.uniandes.csw.traductor.dtos.EmpleadoDetailDTO;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoLogic;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "empleados"
 * @author Santiago Salazar
 */
@Path("empleados")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EmpleadoResource 
{
    private static final Logger LOGGER = Logger.getLogger(EmpleadoResource.class.getName());
    
    //Inyección de la dependencia de lógica: empleadoLogic
    @Inject
    private EmpleadoLogic logic;
    
    /**
     * Crea un empleado con la informacion que se recibe en formato JSON
     * en el cuerpo de la peticion y recibe el mismo empleado ingresado
     * con un id generado automaticamente.
     * @param empleado {@link EmpleadoDTO} - El empleado que se quiere crear
     * @return JSON {@link EmpleadoDTO} - El empleado creado con el id autogenerado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el empleado
     */
    @POST
    public EmpleadoDTO createEmpleado(EmpleadoDTO empleado) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "EmpleadoResource createEmpleado: input: {0}", empleado.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        EmpleadoEntity empleadoEntity = empleado.toEntity();
        // Invoca la lógica para crear la editorial nueva
        EmpleadoEntity nuevoEmpleadoEntity = logic.createEmpleado(empleadoEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        EmpleadoDTO nuevoEmpleadoDTO = new EmpleadoDTO(nuevoEmpleadoEntity);
        LOGGER.log(Level.INFO, "EmpleadoResource createEmpleado: output: {0}", nuevoEmpleadoDTO.toString());
        return nuevoEmpleadoDTO;  
    }
    
    /**
     * Busca y devuelve todos los empleados que existen en la aplicacion.
     *
     * @return JSONArray {@link EmpleadoDetailDTO} - Los empleados
     * encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EmpleadoDetailDTO> getEmpleados()
    {
        LOGGER.info("EmpleadoResource getEmpleados: input: void");
        List<EmpleadoDetailDTO> listaEmpleados = listEntity2DetailDTO(logic.getEmpleados());
        LOGGER.log(Level.INFO, "EmpleadoResource getEmpleados: output: {0}", listaEmpleados.toString());
        return listaEmpleados;
    }
    
    /**
     * Busca el empleado con el id asociado recibido en la URL y la devuelve.
     *
     * @param empleadoId Identificador del empleado que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link EmpleadoDetailDTO} - El empleado buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el empleado.
     */
    @GET
    @Path("{empleadoId: \\d+}")
    public EmpleadoDetailDTO getEmpleado(@PathParam("empleadoId") Long empleadoId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "EmpleadoResource getEmpleado: input: {0}", empleadoId);
        EmpleadoEntity empleadoEntity = logic.getEmpleado(empleadoId);
        if (empleadoEntity == null) {
            throw new WebApplicationException("El recurso /empleados/" + empleadoId + " no existe.", 404);
        }
        EmpleadoDetailDTO detailDTO = new EmpleadoDetailDTO(empleadoEntity);
        LOGGER.log(Level.INFO, "EmpleadoResource getEmpleado: output: {0}", detailDTO.toString());
        return detailDTO; //Estaba retornando null, se te paso aqui Salo.
    }
    
    /**
     * Actualiza el empleado con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param empleadoId Identificador del empleado que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param empleado {@link EmpleadoDetailDTO} El empleado que se desea
     * guardar.
     * @return JSON {@link EmpleadoDetailDTO} - El empleado guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el empleado a
     * actualizar.
     */
    @PUT
    @Path("{empleadoId: \\d+}")
    public EmpleadoDetailDTO updateEmpleado(@PathParam("empleadoId") Long empleadoId, EmpleadoDetailDTO empleado) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "EmpleadoResouce updateEmpleado: input: id:{0} , empleado: {1}", new Object[]{empleadoId, empleado.toString()});
        empleado.setId(empleadoId);
        if (logic.getEmpleado(empleadoId) == null) {
            throw new WebApplicationException("El recurso /empleados/" + empleadoId + " no existe.", 404);
        }
        EmpleadoDetailDTO detailDTO = new EmpleadoDetailDTO(logic.updateEmpleado(empleadoId, empleado.toEntity()));
        LOGGER.log(Level.INFO, "empleadoResource updateEmpleado: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    /**
     * Borra el empleado con el id asociado recibido en la URL.
     *
     * @param empleadoId Identificador del empleado que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el empleado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el empleado.
     */
    @DELETE
    @Path("{empleadoId: \\d+}")
    public void deleteEmpleado(@PathParam("empleadoId") Long empleadoId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "EmpleadoResource deleteEmpleado: input: {0}", empleadoId);
        if (logic.getEmpleado(empleadoId) == null) {
            throw new WebApplicationException("El recurso /empleados/" + empleadoId + " no existe.", 404);
        }
        logic.deleteEmpleado(empleadoId);
        LOGGER.info("EmpleadoResource deleteEmpleado: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EmpleadoEntity a una lista de
     * objetos EmpleadoDetailDTO (json)
     *
     * @param entityList corresponde a la lista de Empleados de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de Emopleados en forma DTO (json)
     */
    private List<EmpleadoDetailDTO> listEntity2DetailDTO(List<EmpleadoEntity> entityList) {
        List<EmpleadoDetailDTO> list = new ArrayList<>();
        for (EmpleadoEntity entity : entityList) {
            list.add(new EmpleadoDetailDTO(entity));
        }
        return list;
    }
    
}
