package com.tagxus.dal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.tagxus.model.Tag;

public class TagManager implements AutoCloseable {

	private static EntityManagerFactory entityManagerFactory;;
	private EntityManager entityManager;

	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("com.tagxus.jpa");
	}

	public TagManager() {
		entityManager = entityManagerFactory.createEntityManager();
	}

	public Tag addTag(Tag tag) {
		entityManager.getTransaction().begin();
		entityManager.persist(tag);
		entityManager.getTransaction().commit();
		return tag;
	}

	public List<Tag> getTags(String uri) {
		entityManager.getTransaction().begin();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
		Root<Tag> tagRoot = criteria.from(Tag.class);
		criteria.select(tagRoot);
		criteria.where(builder.equal(tagRoot.get("uri"), uri));
		List<Tag> tags = entityManager.createQuery(criteria).getResultList();
		entityManager.getTransaction().commit();
		return tags;
	}
	
	public List<Tag> getTags()
	{
		entityManager.getTransaction().begin();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
		Root<Tag> tagRoot = criteria.from(Tag.class);
		criteria.select(tagRoot);
		List<Tag> tags = entityManager.createQuery(criteria).getResultList();
		entityManager.getTransaction().commit();
		return tags;
	}

	public void close() {
		entityManager.close();
	}

}
