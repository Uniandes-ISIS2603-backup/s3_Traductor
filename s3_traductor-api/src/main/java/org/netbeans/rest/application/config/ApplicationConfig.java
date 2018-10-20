
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author estudiante
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(co.edu.uniandes.csw.traductor.mappers.BusinessLogicExceptionMapper.class);
        resources.add(co.edu.uniandes.csw.traductor.mappers.WebApplicationExceptionMapper.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.AreaConocimientoResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.CalificacionResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.ClientePropuestaResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.ClienteResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.DocumentoResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.EmpleadoCalificacionResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.EmpleadoResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.EmpleadoSolicitudResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.IdiomaResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.InvitacionResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.PagosResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.PropuestaResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.SolicitudIdiomaResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.SolicitudResource.class);
        resources.add(co.edu.uniandes.csw.traductor.resources.TarjetaDeCreditoResource.class);
}
    
}


