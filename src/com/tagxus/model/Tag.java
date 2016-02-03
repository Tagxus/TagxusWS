package com.tagxus.model;

import org.hibernate.annotations.GenericGenerator;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Tag")
public class Tag {
	private String name;
	private String uri;
	private String xpath;
	private Long id;
	private String createBy;

	public Tag() {
	}

	public Tag(String uri, String name, String xpath) {
		this.uri = uri;
		this.name = name;
		this.xpath = xpath;
	}

	public Tag(String uri, String name, String xpath, String username) {
		this(uri, name, xpath);
		this.createBy = username;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "uri")
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Column(name = "xpath")
	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public Long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	@Column(name = "createBy", nullable = true)
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("name", name);
		jsonObject.put("uri", uri);
		jsonObject.put("xpath", xpath);
		return jsonObject;
	}

	public static JSONArray toJSON(Iterable<Tag> list) {
		JSONArray array = new JSONArray();
		for (Tag t : list) {
			array.put(t.toJSON());
		}
		return array;
	}

}
