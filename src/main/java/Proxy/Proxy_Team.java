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

	public Proxy_Team() {
		super();
	}

	public Proxy_Team(Long id) {
		this.id = id;
	}

	public Proxy_Team(String name) {
		this.name = name;
	}

	public Proxy_Team(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
