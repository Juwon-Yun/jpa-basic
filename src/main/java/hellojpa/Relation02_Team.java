package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Relation02_Team {

	@Id
	@GeneratedValue
	@Column(name = "TEAM_ID")
	private Long id;

	@Column(name = "TEAM_NAME")
	private String name;

	// 단순 읽기 기능의 양방향 매핑
//	@OneToMany(mappedBy = "team")
//	private List<Relation02_Member> members = new ArrayList<>();
//
	@OneToMany(mappedBy = "team")
	@JoinColumn(name = "TEAM_ID")
	private List<Relation02_Member> members = new ArrayList<>();

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

	public List<Relation02_Member> getMembers() {
		return members;
	}

	public void setMembers(List<Relation02_Member> members) {
		this.members = members;
	}
}
