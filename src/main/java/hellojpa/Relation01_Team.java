//package hellojpa;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
///*
//	* 연관관계의 주인과 mappedBy
//		- 객체와 테이블간에 연관관계를 맺는 차이를 이해해야 한다.
//	* 객체와 테이블이 관계를 맺는 차이
//		- 객체 연관관계 = 2개
//			=> 회원 -> 팀 연관관계 1개(단방향)
//			=> 팀 -> 회원 연관관계 1개(단방향)
//		- 테이블 연관관계 = 1개
//			=> 회원 <-> 팀의 연관관계 1개(양방향)
//
//	* 객체의 양방향 관계 (둘 중 하나로 외래 키를 관리해야 한다)
//		- 객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개다.
//		- 객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 한다
//		- A -> B (a.getB())
//		- B -> A (b.getA())
//
//	* 테이블의 양방향 연관 관계
//		- 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리
//		- MEMBER.TEAM_ID 외래 키 하나로 양방향 연관관계를 가짐(양쪽으로 조인할 수 있다)
//
//	* 연관관계의 주인(Owner)
//		- 양방향 매핑 규칙
//			=> 객체의 두 관계중 하나를 연관관계의 주인으로 지정한다.
//			=> 연관관계의 주인만이 외래 키를 관리(등록, 수정)
//			=> 주인이 아닌쪽은 읽기만 가능하다.
//			=> 주인은 mappedBy 속성을 사용하면 안된다.
//			=> 주인이 아니면 mappedBy 속성으로 주인을 지정한다.
//		- 누구를 주인으로 할까??
//			=> 외래 키가 있는 곳을 주인으로 정해라
//			=> 여기서는 Member.team이 연관관계의 주인 [진짜 매핑] 다
//			=> 주인의 반대편(Team.members) [가짜 매핑] 1, 읽기전용
//
//	* 양방향 매핑시 가장 많이하는 실수 (연관관계의 주인에 값을 입력하지 않음)
//		- Member member = new Member();
//		  member.setUsername("member1");
//		  em.persist(member);
//
//		  Team team = new Team();
//		  team.setName("TeamA");
//		  team.getMembers.add(member);
//		  em.persist(team);
//
//		  member.team의 값에는 아무것도 넣지 않았다.
//
// */
//@Entity
//public class Relation01_Team {
//	@Id @GeneratedValue
//	@Column(name = "TEAM_ID")
//	private Long id;
//	private String name;
//
//	// 관례로 new ArrayList로 초기화를 해준다
//	// Relation01_Member에 ManyToOne으로 걸려있는 필드값을 mappedBy에 선언해야한다.
//	@OneToMany(mappedBy = "team")
//	private List<Relation01_Member> members = new ArrayList<>();
//
//	public void addMember(Relation01_Member member) {
//		member.setTeam(this);
//		members.add(member);
//	}
//	public List<Relation01_Member> getMembers() {
//		return members;
//	}
//
//	public void setMembers(List<Relation01_Member> members) {
//		this.members = members;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//}
