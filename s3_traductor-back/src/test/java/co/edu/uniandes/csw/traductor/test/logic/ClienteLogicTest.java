 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.logic;

import co.edu.uniandes.csw.traductor.ejb.ClienteLogic;
import co.edu.uniandes.csw.traductor.entities.ClienteEntity;
import co.edu.uniandes.csw.traductor.entities.InvitacionEntity;
import co.edu.uniandes.csw.traductor.entities.PropuestaEntity;
import co.edu.uniandes.csw.traductor.entities.SolicitudEntity;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.ClientePersistence;
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
 * Clase que prueba la lógica de ClienteLogic
 * @author Santiago Salazar
 */
@RunWith(Arquillian.class)
public class ClienteLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ClienteLogic clienteLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<>();

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
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
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
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        for (int i = 0; i < 4; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            entity.setSolicitudes(new ArrayList<>());
            entity.setPropuestas(new ArrayList<>());
            entity.setInvitaciones(new ArrayList<>());
            if(i == 0)
            {
                SolicitudEntity solicitud = factory.manufacturePojo(SolicitudEntity.class);
                em.persist(solicitud);
                entity.getSolicitudes().add(solicitud);
                solicitud.setCliente(entity);
                solicitudesData.add(solicitud);
            }
            else if (i == 1)
            {
                InvitacionEntity invitacion = factory.manufacturePojo(InvitacionEntity.class);
                em.persist(invitacion);
                entity.getInvitaciones().add(invitacion); 
                invitacion.setCliente(entity);
                invitacionesData.add(invitacion);
            }
            else if (i == 3){
                PropuestaEntity propuesta = factory.manufacturePojo(PropuestaEntity.class);
                em.persist(propuesta);
                entity.getPropuestas().add(propuesta);
                propuesta.setCliente(entity);
                propuestasData.add(propuesta);
            }
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para consultar la lista de Clientes.
     */
    @Test
    public void getClientesTest() {
        List<ClienteEntity> list = clienteLogic.getClientes();
        Assert.assertEquals("No tiene el mismo numero de clientes",data.size(), list.size());
        for (ClienteEntity entity : list) {
            boolean found = false;
            for (ClienteEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue("Alguno de los clientes en data no se encontro en la persitencia",found);
        }
    }
    
    /**
     * Prueba para consultar un Cliente.
     */
    @Test
    public void getClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity resultEntity = clienteLogic.getCliente(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getIdentificacion(), resultEntity.getIdentificacion());
        Assert.assertEquals(entity.getNombreUsuario(), resultEntity.getNombreUsuario());
    }
    
    /**
     * Prueba para crear un Cliente.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createClienteTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
        Assert.assertNotNull(result);
        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getIdentificacion(), entity.getIdentificacion());
        Assert.assertEquals(newEntity.getNombreUsuario(), entity.getNombreUsuario());
    }

    /**
     * Prueba para crear un cliente con el mismo nombre de usuario de un cliente que ya
     * existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteConMismoNombreUsuarioTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setNombreUsuario(data.get(0).getNombreUsuario());
        clienteLogic.createCliente(newEntity);
    }
    
    /**
     * Prueba para crear un cliente con la misma identificacion de un cliente que ya
     * existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteConMismaIdentificacionTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setIdentificacion(data.get(0).getIdentificacion());
        clienteLogic.createCliente(newEntity);
    }
    
    /**
     * Prueba para crear un cliente con el mismo correo de un cliente que ya
     * existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createClienteConMismoCorreoTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCorreoElectronico(data.get(0).getCorreoElectronico());
        clienteLogic.createCliente(newEntity);
    }

    /**
     * Prueba para actualizar un Cliente.
     */
    @Test
    public void updateClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity pojoEntity = factory.manufacturePojo(ClienteEntity.class);
        pojoEntity.setId(entity.getId());
        clienteLogic.updateCliente(pojoEntity.getId(), pojoEntity);
        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getIdentificacion(), resp.getIdentificacion());
        Assert.assertEquals(pojoEntity.getNombreUsuario(), resp.getNombreUsuario());
    }

    /**
     * Prueba para eliminar un Cliente.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void deleteClienteTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(3);
        clienteLogic.deleteCliente(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Cliente con solicitudes asociadas.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteClienteConSolicitudesAsociadasTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(0);
        clienteLogic.deleteCliente(entity.getId());
    }
    
    /**
     * Prueba para eliminar un Cliente con propuestas asociadas.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteClienteConPropuestasAsociadasTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(2);
        clienteLogic.deleteCliente(entity.getId());
    }
    
    /**
     * Prueba para eliminar un Cliente con invitaciones asociadas.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void deleteClienteConInvitacionesAsociadasTest() throws BusinessLogicException {
        ClienteEntity entity = data.get(1);
        clienteLogic.deleteCliente(entity.getId());
    }
}
