/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.dtos;
import co.edu.uniandes.csw.traductor.entities.AreaConocimientoEntity;
import co.edu.uniandes.csw.traductor.entities.CalificacionEntity;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.IdiomaEntity;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro
 */
public class EmpleadoDetailDTO extends EmpleadoDTO implements Serializable{
    //----------------------------------------------------
    //Atributos
    //----------------------------------------------------    
    private List<CalificacionDTO> calificaciones ;
    private List<InvitacionDTO> invitaciones ;
    private List<PropuestaDTO> propuestas;
    private List<SolicitudDetailDTO> solicitudes;
    private List<AreaConocimientoDTO> areasDeConocimiento;
    private List<IdiomaDTO> idiomas;
    //----------------------------------------------------
    //constructores
    //----------------------------------------------------
    public EmpleadoDetailDTO() {
        super();
    }

    public EmpleadoDetailDTO(EmpleadoEntity empleado) {
        super(empleado);
        calificaciones = new ArrayList<>();
        if(empleado.getCalificaciones()!=null){
            for (CalificacionEntity calEntity : empleado.getCalificaciones()) {
                CalificacionDTO tmpCal=new CalificacionDTO(calEntity);
                this.calificaciones.add(tmpCal);
            }
        }
        invitaciones = new ArrayList<>();
        if(empleado.getInvitaciones()!=null){
            for (InvitacionEntity inviEntity : empleado.getInvitaciones()) {
                InvitacionDTO tmpInv=new InvitacionDTO(inviEntity);
                this.invitaciones.add(tmpInv);
            }
        }

        propuestas = new ArrayList<>();
        if (empleado.getPropuestas()!=null) {
            for (PropuestaEntity entPro : empleado.getPropuestas()) {
                PropuestaDTO tmpPro=new PropuestaDTO(entPro);
                this.propuestas.add(tmpPro);
            }
        }
        areasDeConocimiento=new ArrayList<>();
        if (empleado.getAreasDeConocimiento()!=null) {
            for(AreaConocimientoEntity enArea: empleado.getAreasDeConocimiento()){
                AreaConocimientoDTO tmpArea = new AreaConocimientoDTO(enArea);
                this.areasDeConocimiento.add(tmpArea);
            }
        }
        idiomas=new ArrayList<>();
        if (empleado.getIdiomas()!=null){
        for(IdiomaEntity entIdi:empleado.getIdiomas()){
            IdiomaDTO tmpIdio= new IdiomaDTO(entIdi);
            this.idiomas.add(tmpIdio);
            }
        }
        solicitudes=new ArrayList<>();
        if(empleado.getSolicitudes()!=null){
            //for(SolicitudEntity solen:empleado.getSolicitudes()){
                //SolicitudDetailDTO tmpsoli = new SolicitudDetailDTO(solen);
                //break; //TODO
            //}
        }
    }
    
    //----------------------------------------------------
    //getters and setters
    //----------------------------------------------------

    public List<CalificacionDTO> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionDTO> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<InvitacionDTO> getInvitaciones() {
        return invitaciones;
    }

    public void setInvitaciones(List<InvitacionDTO> invitaciones) {
        this.invitaciones = invitaciones;
    }

    public List<PropuestaDTO> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(List<PropuestaDTO> propuestas) {
        this.propuestas = propuestas;
    }

    public List<SolicitudDTO> getSolicitudes() {
        return null;//TODO
    }

    public void setSolicitudes(List<SolicitudDTO> solicitudes) {
        //this.solicitudes = solicitudes;
        //TODO
    }

    public List<AreaConocimientoDTO> getAreasDeConocimiento() {
        return areasDeConocimiento;
    }

    public void setAreasDeConocimiento(List<AreaConocimientoDTO> areasDeConocimiento) {
        this.areasDeConocimiento = areasDeConocimiento;
    }

    public List<IdiomaDTO> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<IdiomaDTO> idiomas) {
        this.idiomas = idiomas;
    }

    //----------------------------------------------------
    //Metodos adicionales
    //----------------------------------------------------

    /**
     * convierte un objeto empleado a estring para generar el log
     * @return string informacion del objeto
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    /**
     * convuierte un EmpleadoDetailDTO a un objeto entity correspondiente para la persistencia
     * @return nuevo entity correspondiente
     */
    @Override
    public EmpleadoEntity toEntity(){
        //nemen = newEmpleadoEntity
        EmpleadoEntity nemen = new EmpleadoEntity();
        nemen.setId(this.id);
        nemen.setNombreEmpleado(this.nombreEmpleado);
        nemen.setCorreoElectronico(this.correoElectronico);
        nemen.setAniosExperiencia(this.aniosExperiencia);
        nemen.setNombreUsuario(this.nombreUsuario);
        nemen.setContrasenia(this.contrasenia);
        nemen.setTrayectoria(this.trayectoria);
        nemen.setTipoEmpleado(this.tipoEmpleado);
        if(this.calificaciones!=null){
            List<CalificacionEntity> listCal=new ArrayList<>();
            for(CalificacionDTO tmpcal: this.calificaciones){
                listCal.add(tmpcal.toEntity());
            }
            nemen.setCalificaciones(listCal);
        }
       
        if(this.invitaciones!=null){
            List<InvitacionEntity> listInv=new ArrayList<>();
            for(InvitacionDTO tmpinv: this.invitaciones){
                listInv.add(tmpinv.toEntity());
            }
            nemen.setInvitaciones(listInv);
        }

         if(this.propuestas!=null){
            List<PropuestaEntity> listProp=new ArrayList<>();
            for(PropuestaDTO tmpprop: this.propuestas){
                listProp.add(tmpprop.toEntity());
            }
            nemen.setPropuestas(listProp);
        }
        if(this.areasDeConocimiento!=null){
            List<AreaConocimientoEntity> listAreas=new ArrayList<>();
            for(AreaConocimientoDTO tmparea: this.areasDeConocimiento){
                listAreas.add(tmparea.toEntity());
            }
            nemen.setAreasDeConocimiento(listAreas);
        }
        if(this.idiomas!=null){
            List<IdiomaEntity> listIdiomas=new ArrayList<>();
            for(IdiomaDTO tmpIdio: this.idiomas){
                listIdiomas.add(tmpIdio.toEntity());
            }
            nemen.setIdiomas(listIdiomas);
        }
        if(this.solicitudes!=null){
            List<SolicitudEntity> listSoli=new ArrayList<>();
            for(SolicitudDetailDTO tmpSol: this.solicitudes){
                listSoli.add(tmpSol.toEntity());
            }
            nemen.setSolicitudes(listSoli);
        }
        return nemen;
    }
}
