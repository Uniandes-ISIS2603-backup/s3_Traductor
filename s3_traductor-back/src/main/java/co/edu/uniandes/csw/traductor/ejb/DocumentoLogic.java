/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.traductor.ejb;

import co.edu.uniandes.csw.traductor.entities.DocumentoEntity;
import co.edu.uniandes.csw.traductor.entities.IdiomaEntity;
import javax.ejb.Stateless;
import co.edu.uniandes.csw.traductor.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.traductor.persistence.DocumentoPresistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Alvaro Ayte
 */
@Stateless
public class DocumentoLogic {
    private final static Logger LOGGER = Logger.getLogger(IdiomaLogic.class.getName());
    
    /**
     * injeccion de persistencia para conectar con la base de datos
     */
    @Inject
    private DocumentoPresistence persistence; 
    
    /**
     * encargado de persistir un idioma en la base de datos
     * @param documentoEntity entidad que representa un Documento, DocumentoEntity !=null
     * @return El mismo idiomaEntity luego de persistirlo
     * @throws BusinessLogicException si ya existe un documento en el sistema con ese mismo id o nombre
     */
    public DocumentoEntity createDocumento(DocumentoEntity documentoEntity)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO,"Inicia el proceso de creacion de un Documento");
        
        if(persistence.find(documentoEntity.getId()) !=null)
            throw new BusinessLogicException("Este documento ya existe en el sistema");
        persistence.create(documentoEntity);
        LOGGER.log(Level.INFO,"Termina el proceso de creacion del documento");
        return documentoEntity;
    }
    /**
     * entrega una lista con todos los documentos almacenados en el sistema
     * @return List<DocumentoEntity> con los documentos almacenados en la persitencia de la aplicacion
     */
    public List<DocumentoEntity> getDocumentos(){
        LOGGER.log(Level.INFO,"Inicia la consulta de todos los documentos");
        List<DocumentoEntity> documentos=persistence.findAll();
        LOGGER.log(Level.INFO, "Termina la consulta");
        return documentos;
    }
    /**
     * obtiene un documento a partir de un id que llega por parametro
     * @param docID id que identifica el documento a representar
     * @return La entidad que representa al documento que llega por parametro
     */
    public DocumentoEntity getDocumento(Long docID){
        LOGGER.log(Level.INFO, "Inicia la consulta de idioma segun el id "+docID);
        DocumentoEntity documentoEntity=persistence.find(docID);
        if(documentoEntity==null)
            LOGGER.log(Level.SEVERE,"El idioma con el id = {0} no existe", docID);
        LOGGER.log(Level.INFO, "Termina el proceso de consulta de un idioma con el id={0}",docID);
        return documentoEntity;
    }
    /**
     * encargado de eliminar de la persistencia un documento 
     * @param docID id que identifica el documento a eliminar del sistema
     * @throws BusinessLogicException  si el documento identificado con el id del parametro no existe
     */
    public void deleteDocumento(Long docID)throws BusinessLogicException{
        if(this.getDocumento(docID)==null)
            throw new BusinessLogicException("El documento con id= "+docID+" no existe, por lo tanto no se puede borrar");
        persistence.delete(docID);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un Documento id={0}", docID);
    }
}