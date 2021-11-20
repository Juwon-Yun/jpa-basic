package JPQL;

import javax.persistence.*;

@Entity
public class JPQL_Member {

	@Id
	@GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(name = "MEMBER_NAME")
	private String userName;

	@Column(name = "MEMBER_AGE")
	private int age;

	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private JPQL_TEAM team;

	public JPQL_Member() {
	}

	public JPQL_Member(Long id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public JPQL_TEAM getTeam() {
		return team;
	}

	public void setTeam(JPQL_TEAM team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return "JPQL_Member{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", age=" + age +
				'}';
	}


}
