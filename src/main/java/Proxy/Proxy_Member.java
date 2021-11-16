package Proxy;

import javax.persistence.*;

@Entity
public class Proxy_Member {
	@Id @GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(name = "USERNAME")
	private String userName;

	// 지연로딩 설정 FetchType.LAZY을 하면 프록시 객체를 준다
	// 즉시로딩 설정 FetchType.EAGER를 하면 한번에 진짜 엔티티(Team)까지 가져온다
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Proxy_Team team;

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setTeam(Proxy_Team team) {
		this.team = team;
	}


	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public Proxy_Team getTeam() {
		return team;
	}
}
