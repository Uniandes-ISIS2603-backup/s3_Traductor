/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.test.persistence;

import co.edu.uniandes.csw.traductor.entities.IdiomaEntity;
import co.edu.uniandes.csw.traductor.persistence.IdiomaPersistence;
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
 *
 * @author Juan Felipe Parra Camargo
 */
@RunWith(Arquillian.class)
public class IdiomaPersistenceTest {
    @Inject
    private IdiomaPersistence p;
    
    @Inject
    UserTransaction utx;

    @PersistenceContext
    private EntityManager em;
    
    private List<IdiomaEntity> data = new ArrayList<IdiomaEntity>();
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(IdiomaEntity.class.getPackage())
                .addPackage(IdiomaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
    }
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
     * dejar la tabla sin registros
     */
    private void clearData(){
        em.createQuery("delete from IdiomaEntity").executeUpdate();
    }
    /**
     * agregar registros para las pruebas
     */
    public void insertData(){
        PodamFactory f=new PodamFactoryImpl();
        for(int i=0;i<3;i++){
            IdiomaEntity en=f.manufacturePojo(IdiomaEntity.class);
            em.persist(en);
            data.add(en);
        }
    }
    
    @Test
    public void createIdiomaTest(){
        PodamFactory f=new PodamFactoryImpl();
        IdiomaEntity en=new IdiomaEntity();
        IdiomaEntity result=p.create(en);
        Assert.assertNotNull(result);
    }
    @Test
    public void getAllTest(){
        List<IdiomaEntity> result= p.findAll();
        for(IdiomaEntity i:result){
            Assert.assertTrue("El elemento consultado no esta en lo ingresado al sistema"
                    , data.contains(i));
        }
    }
    @Test
    public void findByIDTest(){
        IdiomaEntity e=data.get(0);
        Assert.assertEquals("El obejto deberia ser el mismo", e.getId(),(p.find(data.get(0).getId()).getId()));
    }
    @Test
    public void findByIdiomTest(){
        IdiomaEntity e=data.get(0);
        Assert.assertEquals("El obejto deberia ser el mismo", e.getIdioma(),(p.find(data.get(0).getId()).getIdioma()));
    }
    @Test
    public void deleteTest(){
        IdiomaEntity entity=p.find(data.get(1).getId());
        p.delete(entity.getId());
        Assert.assertNull("El idioma de respuesta deberia ser nulo",p.find(data.get(1).getId()));
    }
}
