package de.sChat.server.httpServer;

import javax.persistence.EntityManager;

public class EntityManagerHolder {
	
	private static EntityManager em;

	public static EntityManager getEntityManager() {
		return em;
	}

	public static void setEntityManager(EntityManager bus) {
		em = bus;
	}

}
