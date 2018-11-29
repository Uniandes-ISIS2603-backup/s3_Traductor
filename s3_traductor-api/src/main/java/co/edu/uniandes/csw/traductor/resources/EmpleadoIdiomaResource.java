/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.IdiomaDTO;
import co.edu.uniandes.csw.traductor.ejb.EmpleadoIdiomaLogic;
import co.edu.uniandes.csw.traductor.ejb.IdiomaLogic;
import co.edu.uniandes.csw.traductor.entities.IdiomaEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Alvaro
 */
public class EmpleadoIdiomaResource {
     private static final Logger LOGGER = Logger.getLogger(EmpleadoIdiomaResource.class.getName());
    @Inject
    private IdiomaLogic idiomaLogic;
    @Inject
    private EmpleadoIdiomaLogic empleadoIdiomaLogic;

    /**
     * agregar una calificaicon corespondiente al empleado
     *
     * @param empleadoId identificador del empleado
     * @param idiomaId identificador del idioma a asignar
     * @return el dto del idioma asignado
     */
    @POST
    @Path("{idiomaId: \\d+}")

    public IdiomaDTO addIdioma(@PathParam("empleadoId") Long empleadoId, @PathParam("idiomaId") Long idiomaId) {
        LOGGER.log(Level.INFO, "EmpleadoIdiomaResource addIdioma: input: empleadoID: {0} , idiomaId: {1}", new Object[]{empleadoId, idiomaId});
        if (idiomaLogic.getIdioma(idiomaId) == null) {
            throw new WebApplicationException("El recurso /idiomas/" + idiomaId + " no existe.", 404);
        }
        IdiomaDTO idiomaDTO = new IdiomaDTO(empleadoIdiomaLogic.addIdioma(empleadoId, idiomaId));
        LOGGER.log(Level.INFO, "EmpleadoIdiomaResource addIdioma: output: {0}", idiomaDTO.toString());
        return idiomaDTO;
    }

    /**
     * Obtener los idiomas asociados a un empleado
     *
     * @return lista de los dto de los idiomas asignados a un empleado
     */
    @GET
    public List<IdiomaDTO> getIdiomas(@PathParam("empleadoId") Long empleadoId) {
        LOGGER.log(Level.INFO, "EmpleadoIdiomaResource getIdiomas: input: {0}", empleadoId);
        List<IdiomaDTO> listaDTOs = idiomasEntityToDTO(empleadoIdiomaLogic.getIdiomas(empleadoId));
        LOGGER.log(Level.INFO, "EmpleadoIdiomaResource getIdiomas: output: {0}", listaDTOs.toString());
        return listaDTOs;
    }
    @GET
    @Path("{idiomaId: \\d+}")
    public IdiomaDTO getIdioma(@PathParam("empleadoId") Long empleadoId, @PathParam("idiomaId") Long idiomaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EmpleadoIdiomaResource getIdioma: input: empleadoId: {0} , idiomaId: {1}", new Object[]{empleadoId, idiomaId});
        if (idiomaLogic.getIdioma(idiomaId) == null) {
            throw new WebApplicationException("El recurso /empleados/" + empleadoId + "/idiomas/" + idiomaId + " no existe.", 404);
        }
        IdiomaDTO idiomaDTO = new IdiomaDTO(empleadoIdiomaLogic.getIdioma(empleadoId, idiomaId));
        LOGGER.log(Level.INFO, "EmpleadoIdiomaResource getIdioma: output: {0}", idiomaDTO.toString());
        return idiomaDTO;
    }
    /**
     * Delete
     * @param empleadoId
     * @param idiomaId
     * @throws BusinessLogicException 
     */
    @DELETE
    @Path("{idiomaId: \\d+}")
    public void deleteIdioma(@PathParam("empleadoId") Long empleadoId, @PathParam("idiomaId") Long idiomaId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EmpleadoIdiomaResource deleteIdioma: input: {0}", empleadoId);
        if (idiomaLogic.getIdioma(idiomaId) == null) {
            throw new WebApplicationException("El recurso /idiomas/" + idiomaId + " no existe.", 404);
        }
        empleadoIdiomaLogic.deleteIdioma(empleadoId, idiomaId);
        LOGGER.info("EmpleadoIdiomaResource deleteIdioma: output: void");
    }
    private List<IdiomaDTO> idiomasEntityToDTO(List<IdiomaEntity> listaEntities) {
        List<IdiomaDTO> lista = new ArrayList<>();
        for (IdiomaEntity a : listaEntities) {
            lista.add(new IdiomaDTO(a));
        }
        return lista;
    }
}
