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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Proxy_Team team;

	public Proxy_Member(){
		super();
	}

	public Proxy_Member(Long id) {
		this.id = id;
	}

	public Proxy_Member(String userName) {
		this.userName = userName;
	}

	public Proxy_Member(Long id, String userName) {
		this.id = id;
		this.userName = userName;
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
