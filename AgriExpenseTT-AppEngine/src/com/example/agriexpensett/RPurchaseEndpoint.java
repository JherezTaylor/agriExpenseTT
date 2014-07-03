package com.example.agriexpensett;

import com.example.agriexpensett.EMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "rpurchaseendpoint", namespace = @ApiNamespace(ownerDomain = "example.com", ownerName = "example.com", packagePath="agriexpensett"))
public class RPurchaseEndpoint {

  /**
   * This method lists all the entities inserted in datastore.
   * It uses HTTP GET method and paging support.
   *
   * @return A CollectionResponse class containing the list of all entities
   * persisted and a cursor to the next page.
   */
  @SuppressWarnings({"unchecked", "unused"})
  @ApiMethod(name = "listRPurchase")
  public CollectionResponse<RPurchase> listRPurchase(
    @Nullable @Named("cursor") String cursorString,
    @Nullable @Named("limit") Integer limit) {

    EntityManager mgr = null;
    Cursor cursor = null;
    List<RPurchase> execute = null;

    try{
      mgr = getEntityManager();
      Query query = mgr.createQuery("select from RPurchase as RPurchase");
      if (cursorString != null && cursorString != "") {
        cursor = Cursor.fromWebSafeString(cursorString);
        query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
      }

      if (limit != null) {
        query.setFirstResult(0);
        query.setMaxResults(limit);
      }

      execute = (List<RPurchase>) query.getResultList();
      cursor = JPACursorHelper.getCursor(execute);
      if (cursor != null) cursorString = cursor.toWebSafeString();

      // Tight loop for fetching all entities from datastore and accomodate
      // for lazy fetch.
      for (RPurchase obj : execute);
    } finally {
      mgr.close();
    }

    return CollectionResponse.<RPurchase>builder()
      .setItems(execute)
      .setNextPageToken(cursorString)
      .build();
  }

  /**
   * This method gets the entity having primary key id. It uses HTTP GET method.
   *
   * @param id the primary key of the java bean.
   * @return The entity with primary key id.
   */
  @ApiMethod(name = "getRPurchase")
  public RPurchase getRPurchase(@Named("id") String id) {
	  DatastoreService datastore=DatastoreServiceFactory.getDatastoreService();
	  Key k=KeyFactory.stringToKey(id);
	  Entity et = null;
	  try {
		et=datastore.get(k);
	} catch (com.google.appengine.api.datastore.EntityNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 RPurchase p=new RPurchase();
	 if(et==null){
		 return null;
	 }
	 
	 p.setCost((Double) et.getProperty("cost"));
	 p.setQty((Double) et.getProperty("qty"));
	 p.setQuantifier((String) et.getProperty("quantifier"));
	 p.setResourceId((Integer) et.getProperty("resourceId"));
	 p.setQtyRemaining((Double) et.getProperty("qtyRemaining"));
	 p.setType((String) et.getProperty("type"));
	  return p;
	  /*
    EntityManager mgr = getEntityManager();
    RPurchase rpurchase  = null;
    try {
      rpurchase = mgr.find(RPurchase.class, id);
    } finally {
      mgr.close();
    }
    return rpurchase;*/
  }

  /**
   * This inserts a new entity into App Engine datastore. If the entity already
   * exists in the datastore, an exception is thrown.
   * It uses HTTP POST method.
   *
   * @param rpurchase the entity to be inserted.
   * @return The inserted entity.
   */
  @ApiMethod(name = "insertRPurchase")
  public RPurchase insertRPurchase(RPurchase rpurchase) {
	Key k=KeyFactory.createKey("RPurchase",rpurchase.getpId());
	rpurchase.setKey(k);
	rpurchase.setKeyrep(KeyFactory.keyToString(k));
    EntityManager mgr = getEntityManager();
    try {
      if(containsRPurchase(rpurchase)) {
        throw new EntityExistsException("Object already exists");
      }
      mgr.persist(rpurchase);
    } finally {
      mgr.close();
    }
    rpurchase.setAccount(KeyFactory.keyToString(k));
    return rpurchase;
  }

  /**
   * This method is used for updating an existing entity. If the entity does not
   * exist in the datastore, an exception is thrown.
   * It uses HTTP PUT method.
   *
   * @param rpurchase the entity to be updated.
   * @return The updated entity.
   */
  @ApiMethod(name = "updateRPurchase")
  public RPurchase updateRPurchase(RPurchase rpurchase) {
    EntityManager mgr = getEntityManager();
    try {
      if(!containsRPurchase(rpurchase)) {
        throw new EntityNotFoundException("Object does not exist");
      }
      mgr.persist(rpurchase);
    } finally {
      mgr.close();
    }
    return rpurchase;
  }

  /**
   * This method removes the entity with primary key id.
   * It uses HTTP DELETE method.
   *
   * @param id the primary key of the entity to be deleted.
   */
  @ApiMethod(name = "removeRPurchase")
  public void removeRPurchase(@Named("id") String id) {
	  DatastoreService datastore= DatastoreServiceFactory.getDatastoreService();
	  Key k=KeyFactory.stringToKey(id);
	  try{
		  datastore.delete(k);
	  }catch(Exception e){
		  return;
	  }
		  /*
    EntityManager mgr = getEntityManager();
    try {
      RPurchase rpurchase = mgr.find(RPurchase.class, id);
      mgr.remove(rpurchase);
    } finally {
      mgr.close();
    }*/
  }

  private boolean containsRPurchase(RPurchase rpurchase) {
    EntityManager mgr = getEntityManager();
    boolean contains = true;
    try {
      RPurchase item = mgr.find(RPurchase.class, rpurchase.getKey());
      if(item == null) {
        contains = false;
      }
    } finally {
      mgr.close();
    }
    return contains;
  }

  private static EntityManager getEntityManager() {
    return EMF.get().createEntityManager();
  }

}