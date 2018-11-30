package co.edu.uniandes.csw.traductor.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Alvaro
 */
@Entity
public class EmpleadoEntity extends BaseEntity implements Serializable {
    //Enum que se encarga de determinar el tipo de empleado.
    public enum TipoEmpleado {
        CORRECTOR,
        TRADUCTOR,
        CORRECTOR_TRADUCTOR
    }
    //----------------------------------------------------
    //Atributos
    //----------------------------------------------------
    private String nombreEmpleado;
    private String correoElectronico;
    private Integer aniosExperiencia;
    private String nombreUsuario;
    private String contrasenia;
    private String trayectoria;
    private String hojaDeVida;
    private TipoEmpleado tipoEmpleado;
    private String estudios;
    private String referencias;
    @PodamExclude
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CalificacionEntity> calificaciones;
    //Mapeos a mis Entity. Saludos. Geovanny
    //Alvaro recuerda que debe haber el OneToMany aqui que llame al un atributo en las clases destino de tipo EmpleadoEntity
    //Es como un "Callback" la relacion de las FK. Por ejemplo, el atributo empleado en mis entities. 
    @PodamExclude
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PropuestaEntity> propuestas;

    @PodamExclude
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<InvitacionEntity> invitaciones;

    @PodamExclude
    @ManyToMany
    private List<AreaConocimientoEntity> areasDeConocimiento;

    @PodamExclude
    @OneToMany(mappedBy = "empleado")
    private List<SolicitudEntity> solicitudes;

    @PodamExclude
    @ManyToMany
    private List<IdiomaEntity> idiomas;

    //----------------------------------------------------
    //getters and setters
    //----------------------------------------------------

    public String getEstudios() {
        return estudios;
    }

    public void setEstudios(String estudios) {
        this.estudios = estudios;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }
    
    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }
    
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
        return hojaDeVida;
    }

    public void setHojaDeVida(String HojaDeVida) {
        this.hojaDeVida = HojaDeVida;
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
