/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.AreaConocimientoDTO;
import co.edu.uniandes.csw.traductor.ejb.AreaConocimientoLogic;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoAreaConocimientoLogic;
import co.edu.uniandes.csw.traductor.entities.AreaConocimientoEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 *
 * @author Alvaro
 */
@Produces("application/json")
@Consumes("application/json")
public class EmpleadoAreaConocimientoResource {
    private static final Logger LOGGER = Logger.getLogger(EmpleadoAreaConocimientoResource.class.getName());
    @Inject
    private AreaConocimientoLogic areaLogic;
    @Inject
    private EmpleadoAreaConocimientoLogic empleadoAreaLogic;
    
    //Define la frase "no existe" en una constante para sustuirlo en los multiples lugares
    //donde se define un error, todo ello con el fin de evitar duplicados
    
    private static final String NO_EXISTE = " no existe.";

    /**
     * agregar una calificaicon corespondiente al empleado
     *
     * @param empleadoId identificador del empleado
     * @param areaConocimientoId identificador de la area a asignar
     * @return el dto del area asignada
     */
    @POST
    @Path("(areaConocimientoId: \\d+")

    public AreaConocimientoDTO addArea(@PathParam("empleadoId") Long empleadoId, @PathParam("areaConocimientoId") Long areaConocimientoId) {
        LOGGER.log(Level.INFO, "EmpleadoAreaConocimientoResource addArea: input: empleadoID: {0} , areaConocimientoId: {1}", new Object[]{empleadoId, areaConocimientoId});
        if (areaLogic.getArea(areaConocimientoId) == null) {
            throw new WebApplicationException("El recurso /areasConocimiento/" + areaConocimientoId + NO_EXISTE, 404);
        }
        AreaConocimientoDTO areaDTO = new AreaConocimientoDTO(empleadoAreaLogic.addArea(empleadoId, areaConocimientoId));
        LOGGER.log(Level.INFO, "EmpleadoAreaConocimientoResource addArea: output: {0}", areaDTO.toString());
        return areaDTO;
    }

    /**
     * Obtener las areas de conocimiento asociadas a un empleado
     *
     * @return lista de los dto de las areas de conocimiento asignadas a un empleado
     */
    @GET
    public List<AreaConocimientoDTO> getAreas(@PathParam("empleadoId") Long empleadoId) {
        LOGGER.log(Level.INFO, "EmpleadoAreaConocimientoResource getAreas: input: {0}", empleadoId);
        List<AreaConocimientoDTO> listaDTOs = areasEntityToDTO(empleadoAreaLogic.getAreasConocimiento(empleadoId));
        LOGGER.log(Level.INFO, "EmpleadoAreaConocimientoResource getAreas: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }
    @GET
    @Path("{areaConocimientoId: \\d+}")
    public AreaConocimientoDTO getArea(@PathParam("empleadoId") Long empleadoId, @PathParam("areaConocimientoId") Long areaConocimientoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EmpleadoAreaConocimientoResource getArea: input: empleadoId: {0} , areaConocimientoId: {1}", new Object[]{empleadoId, areaConocimientoId});
        if (areaLogic.getArea(areaConocimientoId) == null) {
            throw new WebApplicationException("El recurso /empleados/" + empleadoId + "/areasConocimiento/" + areaConocimientoId + NO_EXISTE, 404);
        }
        AreaConocimientoDTO areaDTO = new AreaConocimientoDTO(empleadoAreaLogic.getAreaConocmiento(empleadoId, areaConocimientoId));
        LOGGER.log(Level.INFO, "EmpleadoAreaConocimientoResource getArea: output: {0}", areaDTO.toString());
        return areaDTO;
    }
    /**
     * Delete
     * @param empleadoId
     * @param areaConocimientoId
     * @throws BusinessLogicException 
     */
    @DELETE
    @Path("{areaConocimientoId: \\d+}")
    public void deleteArea(@PathParam("empleadoId") Long empleadoId, @PathParam("areaConocimientoId") Long areaConocimientoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EmpleadoAreaConocimientoResource deleteArea: input: {0}", empleadoId);
        if (areaLogic.getArea(areaConocimientoId) == null) {
            throw new WebApplicationException("El recurso /areasConocimiento/" + areaConocimientoId + NO_EXISTE, 404);
        }
        empleadoAreaLogic.deleteArea(empleadoId, areaConocimientoId);
        LOGGER.info("EmpleadoAreaConocimientoResource deleteArea: output: void");
    }
    private List<AreaConocimientoDTO> areasEntityToDTO(List<AreaConocimientoEntity> listaEntities) {
        List<AreaConocimientoDTO> lista = new ArrayList<>();
        for (AreaConocimientoEntity a : listaEntities) {
            lista.add(new AreaConocimientoDTO(a));
        }
        return lista;
    }
}
