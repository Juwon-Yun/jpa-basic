package Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Proxy_Team {
	@Id
	@GeneratedValue
	@Column(name = "TEAM_ID")
	private Long id;

	@Column(name = "TEAM_NAME")
	private String name;

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
