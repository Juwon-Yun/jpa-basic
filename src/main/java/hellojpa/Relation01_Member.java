package hellojpa;

import javax.persistence.*;

@Entity
public class Relation01_Member {

	@Id @GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(name = "USERNAME")
	private String username;

//	@Column(name = "TEAM_ID")
//	private Long teamId;

	// @ManyToOne => 무슨 관계인지 설명
	// @JoinColumn => 위 관계를 나타낼때 조인하는 컬럼을 나타냄
	// fetch = FetchType.LAZY => 지연 로딩 전략
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_ID")
	private Relation01_Team team;

	public Relation01_Team getTeam() {
		return team;
	}

	public void setTeam(Relation01_Team team){
		this.team = team;
	}

	// 연관관계 메소드를 추가했으니 이름도 변경한다(Setter 관례 때문)
//	public void changeTeam(Relation01_Team team) {
//		this.team = team;
//		// 연관관계 편의메소드 ( this => Relation01_Member)
//		team.getMembers().add(this);
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
