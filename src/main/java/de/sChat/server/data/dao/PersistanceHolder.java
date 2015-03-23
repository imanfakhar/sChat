package de.sChat.server.data.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistanceHolder {
	
	private static PersistanceHolder holder = null;
	private EntityManagerFactory entityManagerFactory;
	
	private PersistanceHolder() {
		entityManagerFactory = Persistence.createEntityManagerFactory("schat");		
	}
	
	public static PersistanceHolder getInstance()
	{
		if(holder == null)
		{
			holder = new PersistanceHolder();
		}
		return holder;
	}
	
	public EntityManager getEntityManager()
	{
		return entityManagerFactory.createEntityManager();
	}

}
