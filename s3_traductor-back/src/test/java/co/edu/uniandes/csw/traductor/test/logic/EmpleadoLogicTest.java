 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;


import co.edu.uniandes.csw.traductor.ejb.EmpleadoLogic;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity;
import co.edu.uniandes.csw.traductor.entities.EmpleadoEntity.TipoEmpleado;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.EmpleadoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Clase que prueba la lógica de EmpleadoLogic
 * @author Alvaro
 */
@RunWith(Arquillian.class)
public class EmpleadoLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EmpleadoLogic empleadoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EmpleadoEntity> data = new ArrayList<>();

    private List<SolicitudEntity> solicitudesData = new ArrayList<>();
    
    private List<PropuestaEntity> propuestasData = new ArrayList<>();
    
    private List<InvitacionEntity> invitacionesData = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EmpleadoEntity.class.getPackage())
                .addPackage(EmpleadoLogic.class.getPackage())
                .addPackage(EmpleadoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from SolicitudEntity").executeUpdate();
        em.createQuery("delete from PropuestaEntity").executeUpdate();
        em.createQuery("delete from InvitacionEntity").executeUpdate();
        em.createQuery("delete from EmpleadoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        for (int i = 0; i < 4; i++) {
            EmpleadoEntity entity = factory.manufacturePojo(EmpleadoEntity.class);
            entity.setSolicitudes(new ArrayList<>());
            entity.setPropuestas(new ArrayList<>());
            entity.setInvitaciones(new ArrayList<>());
            entity.setTipoEmpleado(TipoEmpleado.TRADUCTOR);
            if(i == 0)
            {
                SolicitudEntity solicitud = factory.manufacturePojo(SolicitudEntity.class);
                em.persist(solicitud);
                entity.getSolicitudes().add(solicitud);
                solicitud.setEmpleado(entity);
                solicitudesData.add(solicitud);
            }
            else if (i == 1)
            {
                InvitacionEntity invitacion = factory.manufacturePojo(InvitacionEntity.class);
                em.persist(invitacion);
                entity.getInvitaciones().add(invitacion); 
                invitacion.setEmpleado(entity);
                invitacionesData.add(invitacion);
            }
            else if (i == 2){
                PropuestaEntity propuesta = factory.manufacturePojo(PropuestaEntity.class);
                em.persist(propuesta);
                entity.getPropuestas().add(propuesta);
                propuesta.setEmpleado(entity);
                propuestasData.add(propuesta);
            }
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para consultar la lista de Empleados.
     */
    @Test
    public void getEmpleadosTest() {
        List<EmpleadoEntity> list = empleadoLogic.getEmpleados();
        Assert.assertEquals("No tiene el mismo numero de empleados",data.size(), list.size());
        for (EmpleadoEntity entity : list) {
            boolean found = false;
            for (EmpleadoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue("Alguno de los empleados en data no se encontro en la persitencia",found);
        }
    }
    
    
    /**
     * Prueba para consultar un Empleado.
     */
    @Test
    public void getEmpleadoTest() {
        EmpleadoEntity entity = data.get(0);
        EmpleadoEntity resultEntity = empleadoLogic.getEmpleado(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombreEmpleado(), resultEntity.getNombreEmpleado());
        Assert.assertEquals(entity.getNombreUsuario(), resultEntity.getNombreUsuario());
    }
    
    /**
     * Prueba para crear un Empleado.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createEmpleadoTest() throws BusinessLogicException {
        EmpleadoEntity newEntity = factory.manufacturePojo(EmpleadoEntity.class);
        EmpleadoEntity result = empleadoLogic.createEmpleado(newEntity);
        Assert.assertNotNull(result);
        EmpleadoEntity entity = em.find(EmpleadoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombreEmpleado(), entity.getNombreEmpleado());
        Assert.assertEquals(newEntity.getNombreUsuario(), entity.getNombreUsuario());
    }

    /**
     * Prueba para crear un empleado con el mismo nombre de usuario de un empleado que ya
     * existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createEmpleadoConMismoNombreUsuarioTest() throws BusinessLogicException {
        EmpleadoEntity newEntity = factory.manufacturePojo(EmpleadoEntity.class);
        newEntity.setNombreUsuario(data.get(0).getNombreUsuario());
        empleadoLogic.createEmpleado(newEntity);
    }
    
    /**
     * Prueba para crear un empleado con el mismo correo de un empleado que ya
     * existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createEmpleadoConMismoCorreoTest() throws BusinessLogicException {
        EmpleadoEntity newEntity = factory.manufacturePojo(EmpleadoEntity.class);
        newEntity.setCorreoElectronico(data.get(0).getCorreoElectronico());
        empleadoLogic.createEmpleado(newEntity);
    }

    /**
     * Prueba para eliminar un Empleado.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void deleteEmpleadoTest() throws BusinessLogicException {
        EmpleadoEntity entity = data.get(3);
        empleadoLogic.deleteEmpleado(entity.getId());
        EmpleadoEntity deleted = em.find(EmpleadoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Empleado con solicitudes asociadas.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteEmpleadoConSolicitudesAsociadasTest() throws BusinessLogicException {
        EmpleadoEntity entity = data.get(0);
        empleadoLogic.deleteEmpleado(entity.getId());
    }
    
    /**
     * Prueba para eliminar un Empleado con propuestas asociadas.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteEmpleadoConPropuestasAsociadasTest() throws BusinessLogicException {
        EmpleadoEntity entity = data.get(2);
        empleadoLogic.deleteEmpleado(entity.getId());
    }
    
    /**
     * Prueba para eliminar un Empleado con invitaciones asociadas.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteEmpleadoConInvitacionesAsociadasTest() throws BusinessLogicException {
        EmpleadoEntity entity = data.get(1);
        empleadoLogic.deleteEmpleado(entity.getId());
    }
}