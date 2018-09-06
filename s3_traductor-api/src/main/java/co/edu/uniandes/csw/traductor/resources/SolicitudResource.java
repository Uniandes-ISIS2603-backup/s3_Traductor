    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.resources;

import co.edu.uniandes.csw.traductor.dtos.SolicitudDTO;
import co.edu.uniandes.csw.traductor.dtos.SolicitudDetailDTO;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


/**
 *
 * @author Jhonattan Fonseca
 */
@Path("solicitudes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SolicitudResource {
    
    
     private static final Logger LOGGER = Logger.getLogger(SolicitudResource.class.getName());
     
     
    @POST
    public SolicitudDTO createSolicitud(SolicitudDTO solicitud) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudResource createSolicitud: input: {0}", solicitud.toString());
        LOGGER.log(Level.INFO, "Solicitudesource createSolicitud: output: {0}", solicitud.toString());
        return solicitud;
    }
    
    @GET
    public List<SolicitudDTO> getAllSolicitudes()throws BusinessLogicException{ 
    List <SolicitudDTO> respuesta = new ArrayList<>() ;
        return respuesta ;  
    }
    
   @GET
    @Path("{SolicitudId: \\d+}")
    public SolicitudDetailDTO getSolicitud(@PathParam("SolicitudId") Long SolicitudId) {
        LOGGER.log(Level.INFO, "SolicitudResource getSolicitud: input: {0}", SolicitudId);
       
        return null;
    }
    
    @DELETE
    @Path("{SolicitudId: \\d+}")
    public void deleteSolicitud(@PathParam("SolicitudId") Long solicitudId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SolicitudResource deleteSolicitud: input: {0}", solicitudId);
        
        LOGGER.info("SolicitudResource deleteSolicitud: output: void");
    }
    
    
       
    
    
}
