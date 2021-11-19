package JPQL;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class JPQL_TEAM {

	@Id @Column(name = "TEAM_ID")
	@GeneratedValue
	private Long id;

	@Column(name = "TEAM_NAME")
	private String name;

	@OneToMany(mappedBy = "team")
	private List<JPQL_Member> members = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
