/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.*;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoLogic;
import co.edu.uniandes.csw.traductor.entities.*;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso Empleado
 *
 * @author Alvaro Javier Ayte
 */
@Path("empleados")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EmpleadoResource {

    //Logger
    private static final Logger LOGGER = Logger.getLogger(EmpleadoResource.class.getName());

    //Inyeccion de Logica
    @Inject
    private EmpleadoLogic empleadoLogic;

    /**
     * Crea un nuevo Empleado con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     *
     *
     * @param nuevoEmpleado {@link EmpleadoDTO} - La Empleado que se desea
     * guardar.
     * @return JSON {@link EmpleadoDTO} - La Empleado recibida.
     */
    @POST
    public EmpleadoDTO createEmpleado(EmpleadoDTO nuevoEmpleado) throws BusinessLogicException {

        //Llamado al Logger, no se para que sirve :(
        LOGGER.log(Level.INFO, "EmpleadoResources createEmpleado: input: {0}", nuevoEmpleado.toString());

        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        EmpleadoEntity empleadoEntity = nuevoEmpleado.toEntity();
        // Invoca la lógica para crear el Empleado nuevo. Ahi abajo debe ir la logica.
        EmpleadoEntity nuevaEntity = empleadoLogic.createEmpleado(empleadoEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo.		        
        EmpleadoDTO respuestaDTO = new EmpleadoDTO(nuevaEntity);
        LOGGER.log(Level.INFO, "EmpleadoResources createEmpleado: output: {0}", respuestaDTO.toString());
        return respuestaDTO;
    }

    /**
     * Busca y devuelve todos los Empleados
     *
     * @return JSONArray {@link EmpleadoDTO} - Las Empleados que posee el
     * empleado. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<EmpleadoDetailDTO> getEmpleados() {

        LOGGER.info("EmpleadoResources getAllEmpleados: input: void");
        List<EmpleadoDetailDTO> listaEmpleados = listEntity2DetailDTO(empleadoLogic.getEmpleados()); //Se llama a la logica para que devuelva la lista !
        LOGGER.log(Level.INFO, "EmpleadoResources getAllEmpleados: output: {0}", listaEmpleados.toString());
        return listaEmpleados;
    }

    /**
     * Busca El empleado con el id asociado recibido en la URL y la devuelve.
     *
     * @param empleadoId Identificador de la empleado que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link empleadoDTO} - La editorial buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{empleadoId: \\d+}")
    public EmpleadoDetailDTO getEmpleado(@PathParam("empleadoId") Long empleadoId) throws WebApplicationException {

        // TODO: [getEmpleado] Terminar el metodo cuando se tenga la conexion a la logica y persistencia.
        LOGGER.log(Level.INFO, "empleadoResources getempleado: input: {0}", empleadoId);
        EmpleadoDetailDTO entityBuscada = null; //DTO respuesta.	
        entityBuscada = new EmpleadoDetailDTO(empleadoLogic.getEmpleado(empleadoId));

        LOGGER.log(Level.INFO, "empleadoResources getempleado: output: {0}", entityBuscada.toString());
        return entityBuscada;
    }

    /**
     * Borra la empleado con el id asociado recibido en la URL.
     *
     * @param empleadoId Identificador de la empleado que se desea borrar. Este
     * debe ser una cadena de dígitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @DELETE
    @Path("{empleadoId: \\d+}")
    public void deleteEmpleado(@PathParam("empleadoId") Long empleadoId) throws WebApplicationException {

        LOGGER.log(Level.INFO, "empleadoResources deleteEmpleado: input: {0}", empleadoId);

        try {
            empleadoLogic.getEmpleado(empleadoId); //Si no existe salta al catch y manda la excepcion.
            empleadoLogic.deleteEmpleado(empleadoId);
        } catch (BusinessLogicException e) {
            throw new WebApplicationException("El recurso /empleados/" + empleadoId + " no existe.", 404);
        }

        LOGGER.info("empleadoResources deleteEmpleado: output: void");
    }
    
    /**
     * Conexión con el servicio propuestas para un empleado.
     * {@link PropuestaResource}
     *
     * Este método conecta la ruta de /empleados con las rutas de /propuestas que
     * dependen del empleado, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los pagos de un empleado.
     *
     * @param empleadoId El ID del empleado con respecto al  cual se
     * accede al servicio.
     * @return El servicio de propuestas para este empleado en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el empleado.
     */
    @Path("{empleadoId: \\d+}/propuestas")
    public Class<PropuestaResource> getPropuestaResource(@PathParam("empleadoId") Long empleadoId) 
    {
        if (empleadoLogic.getEmpleado(empleadoId) == null) {
            throw new WebApplicationException("El recurso /empleados/" + empleadoId + " no existe.", 404);
        }
        return PropuestaResource.class;
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EmpleadoEntity a una lista de
     * objetos EmpleadoDTO (json)
     *
     * @param EmpleadoList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<EmpleadoDetailDTO> listEntity2DetailDTO(List<EmpleadoEntity> emplList) {
        List<EmpleadoDetailDTO> list = new LinkedList<>();
        for (EmpleadoEntity entity : emplList) {
            list.add(new EmpleadoDetailDTO(entity));
        }
        return list;
    }
}