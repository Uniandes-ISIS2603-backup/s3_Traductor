/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.traductor.presistence;

import co.edu.uniandes.csw.traductor.entities.DocumentoEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase que maneja la persistencia para Documento. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author Alvaro
 */
@Stateless
public class DocumentoPresistence {

    private static final Logger LOGGER = Logger.getLogger(DocumentoPresistence.class.getName());

    @PersistenceContext(unitName = "PrometeusPU")
    protected EntityManager em;

    /**
     * Crea un autor en la base de datos
     *
     * @param DocumentoEntity objeto author que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public DocumentoEntity create(DocumentoEntity DocumentoEntity) {
        LOGGER.log(Level.INFO, "Creando un autor nuevo");
        /* Note que hacemos uso de un método propio de EntityManager para persistir el documento en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(DocumentoEntity);
        LOGGER.log(Level.INFO, "Docuemento creado");
        return DocumentoEntity;
    }

    /**
     * Busca si hay algun documento con el id que se envía de argumento
     *
     * @param documentoId: id correspondiente a el documento buscada.
     * @return un author.
     */
    public DocumentoEntity find(Long documentoId) {
        LOGGER.log(Level.INFO, "Consultando el Documento con id={0}", documentoId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from DocumentoEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(DocumentoEntity.class, documentoId);
    }

    /**
     * Actualiza un documento.
     *
     * @param documentoEntity: el documento que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un documento con los cambios aplicados.
     */
    public DocumentoEntity update(DocumentoEntity documentoEntity) {
        LOGGER.log(Level.INFO, "Actualizando el author con id={0}", documentoEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        el documento con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        return em.merge(documentoEntity);
    }

    /**
     * Borra un documento de la base de datos recibiendo como argumento el id de
     * el documento
     *
     * @param documentoId: id correspondiente a el documento a borrar.
     */
    public void delete(Long documentoId) {

        LOGGER.log(Level.INFO, "Borrando el author con id={0}", documentoId);
        // Se hace uso de mismo método que esta explicado en public DocumentoEntity find(Long id) para obtener el documento a borrar.
        DocumentoEntity documentoEntity = em.find(DocumentoEntity.class, documentoId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
        EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
        Es similar a "delete from DocumentoEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(documentoEntity);
    }
}