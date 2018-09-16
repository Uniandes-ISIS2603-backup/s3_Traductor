/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Entidad de DTO Cliente para la persistencia.
 * @author Santi Salazar
 */
@Entity
public class ClienteEntity extends BaseEntity implements Serializable{

    private String nombreUsuario;
    private String correoElectronico;
    private String contrasenia;
    private String nombre;
    
    @PodamExclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<TarjetaDeCreditoEntity> tarjetas;
    
    @PodamExclude
    @OneToMany(mappedBy = "cliente")
    private List<SolicitudEntity> solicitudes;
    
    @PodamExclude
    @OneToMany(mappedBy = "cliente")
    private List<PagosEntity> pagos;
    
    @PodamExclude
    @OneToMany(mappedBy = "cliente")
    private List<InvitacionEntity> invitaciones;
    
    @PodamExclude
    @OneToMany(mappedBy = "cliente")
    private List<PropuestaEntity> propuestas;

    public List<InvitacionEntity> getInvitaciones() {
        return invitaciones;
    }

    public void setInvitaciones(List<InvitacionEntity> invitaciones) {
        this.invitaciones = invitaciones;
    }

    public List<PropuestaEntity> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(List<PropuestaEntity> propuestas) {
        this.propuestas = propuestas;
    }
    
    

    public List<PagosEntity> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagosEntity> pagos) {
        this.pagos = pagos;
    }

    public List<SolicitudEntity> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudEntity> solicitudes) {
        this.solicitudes = solicitudes;
    }
    

    public List<TarjetaDeCreditoEntity> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<TarjetaDeCreditoEntity> tarjetas) {
        this.tarjetas = tarjetas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    private String identificacion;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }  
}
