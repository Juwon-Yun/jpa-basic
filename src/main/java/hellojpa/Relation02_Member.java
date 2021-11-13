//package hellojpa;
//
//import javax.persistence.*;
//
///*
//	* 연관관계 매핑시 고려사항 3가지
//	 - 다중성
//	 - 단방향, 양방향
//	 - 연관관계의 주인
//
//	* 다중성
//	 - 다대일 : @ManyToOne (가장 많이)
//	 - 일대다 : @OneToMany (필요할 때 가끔, 권장하지 않음)
//	 - 일대일 : @OneToOne (어쩌다 한번)
//	 - 다대다 : @ManyToMany (실무에서 안씀)
//
//	* 단방향, 양방향
//	 - 테이블
//	  => 외래 키 하나로 양쪽 조인 가능
//	  => 사실 방향이라는 개념이 없음
//	 - 객체
//	  => 참조용 필드가 있는 쪽으로만 참조 가능
//	  => 한쪽만 참조하면 단방향
//	  => 양쪽이 서로 참조하면 양방향
//
//	* 연관관계의 주인
//	 - 테이블은 외래 키 하나로 두 테이블이 연관관계를 맺음
//	 - 객체 양방향 관계는 A->B, B->A 처럼 참조가 2군데
//	 - 객체 양방향 관계는 참조가 2군데 있음. 둘중 테이블의 외래 키를 관리할 곳을 지정해야함
//	 - 연관관계의 주인 : 외래 키를 관리하는 참조
//	 - 주인의 반대편 : 외래 키에 영향을 주지 않음, 단순 조회만 가능
//
//
// */
//
//@Entity
//public class Relation02_Member {
//
//	@Id
//	@GeneratedValue
//	@Column(name = "MEMBER_ID")
//	private  Long id;
//
//	@Column(name = "USERNAME")
//	private  String username;
//
////	@ManyToOne(fetch = FetchType.LAZY)
////	@JoinColumn(name = "TEAM_ID")
////	private Relation02_Team team;
//
//	// 일대일 관계 @JoinColu mn 권장(필수)
//	@OneToOne
//	@JoinColumn(name = "LOCKER_ID")
//	private Relation02_Locker locker;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
////	public Relation02_Team getTeam() {
////		return team;
////	}
////
////	public void setTeam(Relation02_Team team) {
////		this.team = team;
////	}
//}
