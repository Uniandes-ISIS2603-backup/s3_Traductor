/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class EmpleadoEntity extends BaseEntity implements Serializable {
    //----------------------------------------------------
    //Atributos
    //----------------------------------------------------
    private String nombreEmpleado;
    private String correoElectronico;
    private String nombreUsuario;
    private String contrasenia;
    private Integer aniosExperiencia;
    private String trayectoria;
    private String HojaDeVida;
    private List<CalificacionEntity> calificaciones = new ArrayList<CalificacionEntity>();
    private List<InvitacionEntity> invitaciones = new ArrayList<InvitacionEntity>();
    private List<ReferenciaEntity> refLaborales = new ArrayList<ReferenciaEntity>();
    private List<ReferenciaEntity> refPersonales = new ArrayList<ReferenciaEntity>();
    private List<PropuestaEntity> propuestas = new ArrayList<PropuestaEntity>();
    private List<SolicitudEntity> solicitudes=new ArrayList<SolicitudEntity>();
    private List<AreaConocimientoEntity> areasDeConocimiento=new ArrayList<AreaConocimientoEntity>();
    private List<IdiomaEntity> idiomas=new ArrayList<IdiomaEntity>();
    //----------------------------------------------------
    //getters and setters
    //----------------------------------------------------
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

        public String getTrayectoria() {
        return trayectoria;
    }

    public void setTrayectoria(String trayectoria) {
        this.trayectoria = trayectoria;
    }

    public String getHojaDeVida() {
        return HojaDeVida;
    }

    public void setHojaDeVida(String HojaDeVida) {
        this.HojaDeVida = HojaDeVida;
    }
    
    public Integer getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(Integer aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }


    public List<CalificacionEntity> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<CalificacionEntity> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<ReferenciaEntity> getRefPersonales() {
        return refPersonales;
    }

    public void setRefPersonales(List<ReferenciaEntity> refPersonales) {
        this.refPersonales = refPersonales;
    }

    public List<InvitacionEntity> getInvitaciones() {
        return invitaciones;
    }

    public void setInvitaciones(List<InvitacionEntity> invitaciones) {
        this.invitaciones = invitaciones;
    }

    public List<ReferenciaEntity> getRefLaborales() {
        return refLaborales;
    }

    public void setRefLaborales(List<ReferenciaEntity> refLaborales) {
        this.refLaborales = refLaborales;
    }

    public List<PropuestaEntity> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(List<PropuestaEntity> propuestas) {
        this.propuestas = propuestas;
    }

    public List<SolicitudEntity> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudEntity> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public List<AreaConocimientoEntity> getAreasDeConocimiento() {
        return areasDeConocimiento;
    }

    public void setAreasDeConocimiento(List<AreaConocimientoEntity> areasDeConocimiento) {
        this.areasDeConocimiento = areasDeConocimiento;
    }

    public List<IdiomaEntity> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<IdiomaEntity> idiomas) {
        this.idiomas = idiomas;
    }

}
