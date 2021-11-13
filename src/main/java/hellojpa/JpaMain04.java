package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*
	* 일대다 단방향 정리
	 - 일대다 단방향은 일대다(1:N)에서 일(1)이 연관관계의 주인
	 - 테이블 일대다 관계는 항상 다(N) 쪽에 외래 키가 있음
	 - 객체와 테이블의 차이 때문에 반대편 테이블의 외래 키를 관리하는 특이한 구조
	 - @JoinColumn을 꼭 사용해야 함. 그렇지 않으면 조인 테이블 방식을 사용함(중간에 테이블을 하나 추가함)

	* 일대다 양방향 정리
	 - 이런 매핑은 공식적으로 존재X
	 - @JoinColumn(insertable = false, updatable = false)
	 - 읽기 전용 필드를 사용해서 양방향 처럼 사용하는 방법
	 - 다대일 양방향을 사용하자
	
	* 일대일 관계(주 테이블에 외래 키는 단방향)
	 - 일대일 관계는 그 반대도 일대일
	 - 주 테이블이나 대상 테이블 중에 외래 키 선택 가능
	   => 주 테이블에 외래 키
	   => 대상 테이블에 외래 키
	 - 외래 키에 데이터베이스 유니크(UNI) 제약조건 추가
	
	% 일대일 대상 테이블에 외래 키 단방향 정리  %
	% 단방향 관계는 JPA 지원하지 않음 XXXXXX  %
	% 양방향 관계는 지원함 OOOOOOOOOOOOOOOO  %

	*  일대일 : 주 테이블에 외래 키 양방향 정리
	 - 다대일 양방향 매핑 처럼 외래 키가 있는 곳이 연관관계의 주인
	 - 반대편은 mappedBy 적용

	* 일대일 정리
	 - 주 객체가 대상 객체의 참조를 가지는 것 처럼 주 테이블에 외래 키를 두고 대상 테이블을 찾음
	 - 객체지향 개발자 선호
	 - JPA 매핑이 편리함
	 - 장점 : 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능
	 - 단점 : 값이 없으면 외래 키에 null 허용

	* 대상 테이블에 외래 키
	 - 대상 테이블에 외래 키가 존재
	 - 전통적인 데이터베이스 개발자 선호
	 - 장점 : 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지
	 - 단점 : 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩됨(프록시는 뒤에서 설명)

	* 다대다
	 - 관계형 데이터베이스는 정규화된 테이블 2개로 다대다 관계를 표현할 수 없음
	 - 연결 테이블을 추가해서 일대다, 다대일 관계로 풀어내야함
	
	* 다대다 매핑의 한계
	 - 편리해 보이지만 실무에서 사용하지 않음
	 - 연결 테이블이 단순히 연결만 하고 끝나지 않음
	 - 주문시간, 수량 같은 데이터가 들어올 수 있음

	* 다대다 한계 극복
	 - 연결 테이블용 엔티티 추가(연결 테이블을 엔티티로 승격)
	 - @ManyToMany => @OneToMany, @ManyToOne

 */
public class JpaMain04 {
	public static void main(String[] args) {

//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

//		EntityManager em = emf.createEntityManager();

//		EntityTransaction tx = em.getTransaction();

//		tx.begin();
//		try {
			// alt + enter => extract method
//			Relation02_Member member = getMember(em);

//			Relation02_Team team = new Relation02_Team();
//			team.setName("teamA");

			// 일대다 매핑(일이 주인) team entity에 손을 댓지만 member에 update 쿼리가 나감
			// update 쿼리가 추가되어 나감 테이블이 많을 수록 위험함
//			team.getMembers().add(member);

//			em.persist(team);

//			tx.commit();
//		}catch (Exception e){
//			tx.rollback();
//		}finally {
//			em.close();
//		}
//		emf.close();

	}

//	private static Relation02_Member getMember(EntityManager em) {
//		Relation02_Member member = new Relation02_Member();
//		member.setUsername("member1");
//		em.persist(member);
//		return member;
//	}
}
