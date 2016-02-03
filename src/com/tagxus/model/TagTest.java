package com.tagxus.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.tagxus.dal.TagManager;

import junit.framework.TestCase;

public class TagTest extends TestCase {

	private EntityManagerFactory entityManagerFactory;

	@Override
	protected void setUp() throws Exception {
		// like discussed with regards to SessionFactory, an
		// EntityManagerFactory is set up once for an application
		// IMPORTANT: notice how the name here matches the name we gave the
		// persistence-unit in persistence.xml!
		entityManagerFactory = Persistence.createEntityManagerFactory("com.tagxus.jpa");
	}

	@Override
	protected void tearDown() throws Exception {
		entityManagerFactory.close();
	}

	public void testBasicUsage() {
		// create a couple of events...
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(new Tag("http://ggboom.com", "tag1", ""));
		entityManager.persist(new Tag("http://ggboom.com", "tag2", ""));
		entityManager.getTransaction().commit();
		entityManager.close();

		// now lets pull events from the database and list them
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		List<Tag> result = entityManager.createQuery("from Tag", Tag.class).getResultList();
		for (Tag t : result) {
			System.out.println("Tag (" + t.getName() + ") : " + t.getUri() + " | " + t.getXpath());
		}
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void testTagQuery() {
		try (TagManager manager = new TagManager()) {
			manager.addTag(new Tag("http://ggboom.com", "标签3", ""));
			manager.addTag(new Tag("http://ggboom.com", "标签4", "", "longhan"));
			manager.addTag(new Tag("http://ggboom.com", "标签5", ""));
			List<Tag> tags = manager.getTags("http://ggboom.com");
			TestCase.assertTrue(tags.size() > 0);
			TestCase.assertTrue(tags.get(0).getId() >0);
		}
	}
}
