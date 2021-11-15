package Proxy;

import hellojpa.Member;
import hellojpa.Relation01_Member;
import hellojpa.Relation01_Team;
import org.hibernate.Hibernate;
import org.hibernate.jpa.internal.PersistenceUnitUtilImpl;

import javax.persistence.*;

/*

	* 프록시
	* 즉시 로딩과 지연로딩
	* 지연 로딩 활용
	* 영속성 전이 : CASCADE
	* 고아 객체
	* 영속성 전이 + 고아 객체, 생명주기
	* 실전 예제 - 5.연관관계 관리
	
	Member 테이블만 조회할 경우 Team 정보도 조회 해야할까?

	* 프록시 기초
	 - em.find() vs em.getReference()
	 - em.find() : 데이터베이스를 통해서 실제 엔티티 객체 조회
	 - em.getReference() : 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회

	* 프록시 특징
	 - 실제 클래스를 상속 받아서 만들어짐
	 - 실제 클래스와 겉 모양이 같다.
	 - 사용하는 입장에서는 진짜 객체인지 프록시 객체인지 구분하지않고 사용해도 무방하다(이론상)

    * 프록시 특징 2
     - 프록시 객체는 실제 객체의 참조(target)를 보관
     - 프록시 객체를 호출하면 프록시 객체는 실제 객체의 메소드 호출

    * 프록시 객체의 초기화 방식(프록시의 매커니즘)
     - (가짜 프록시 객체생성) Member member = em.getReference(Member.class, "id1");
       member.getName();
     1. getName() 요청
     2. Member Proxy 테이블의 Member targer에는 값이 없다
     3. 영속성 컨텍스트에 초기화 요청함 실제 객체로 DB를 조회 한 후에
	 4. 조회한 DB 데이터를 바탕으로 실제 Entity 생성
	 5. 4번에서 가져온 진짜 getName()데이터를 가짜 프록시 객체인 target.getName()에 반환시킨다.
	 6. 한번더 요청하면 target.getName()에는 이미 가져온 객체가 있으니 그대로 출력한다.

	*** 중요) 프록시 객체의 특징 ***
	 - 프록시 객체는 처음 사용할 때 한번만 초기화
	 - 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는것은 아니다.
	   초기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
	 - 프록시 객체는 원본 엔티티를 상속받음, 따라서 타입 체크시 주의해야함(== 비교 실패, 대신 instance of를 사용해야한다)
	 - 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티를 반환한다. (반대의 상황에서도 똑같다)
	 - 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때는 프록시를 초기화하면 문제 발생
	   (하이버네이트는 org.hibernate.LazyInitializationException 예외를 터트림)

     JPA에서 같은 인스턴스(영속성컨텍스트, 트렉젝션)에서의 == 비교는 항상 true로 나오기 때문에 주의해야 한다.
     == 비교에서 pk가 같고 인스턴스가 같으면 항상 true를 반환한다. (동일성 보장?)

	 * 프록시 확인하는 방법들
	  - 프록시 인스턴스의 초기화 여부 확인
	    => PersistenceUnitUtil.isLoaded(Object entity)
	  - 프록시 클래스 확인 방법
	    => entity.getClass().getName() 출력(..javasist.. or HibernateProxy...)
	  - 프록시 강제 초기화
	    => org.hibernate.Hibernate.initialize(entity);
	  - 참고 : JPA 표준은 강제 초기화 없음
	    => 강제 호출 : member.getName()

 */
public class Proxy {
	public static void main(String[] args) {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

//		EntityManager em = emf.createEntityManager();

//		EntityTransaction tx = em.getTransaction();

//		tx.begin();
//		try {

//			Relation01_Member member = em.find(Relation01_Member.class, 1L);
//			printMemberAndTeam(member);
//			printMember(member);

//			Relation01_Member member = new Relation01_Member();
//			member.setUsername("MemberA");
//
//			em.persist(member);
//
//			em.flush();
//			em.clear();

//			Relation01_Member findMember = em.find(Relation01_Member.class, member.getId());

			// em.getReference 할 시점에 getId는 이미 있는 데이터를 끌어오고 
//			Relation01_Member findMember = em.getReference(Relation01_Member.class, member.getId());
			// findMember Class =>class hellojpa.Relation01_Member$HibernateProxy$bFtJhdfU
			//          => $HibernateProxy$bFtJhdfU라는 프록시(가짜) 클래스 생성

//			System.out.println("findMember Class =>" + findMember.getClass() );
//			System.out.println("findMember Id =>" + findMember.getId() );
			// getUserName은 데이터가 없으니 쿼리를 날려서 가져온다
//			System.out.println("findMember UserName =>" + findMember.getUsername() );

			// 프록시 == 비교 예제
//			Relation01_Member member = new Relation01_Member();
//			member.setUsername("member1");
//			em.persist(member);

//			em.flush();
//			em.clear();

//			Relation01_Member m1 = em.getReference(Relation01_Member.class, member.getId());
//			System.out.println("m1 => " + m1.getClass());
			/*
				* 둘다 em.getReference()일때
				m1 => class hellojpa.Relation01_Member$HibernateProxy$0JpXRyPg
				Reference => class hellojpa.Relation01_Member$HibernateProxy$0JpXRyPg
				== 비교를 true로 맞추기 위해 둘다 프록시에서 가져옴

				m1은 getReference(), reference는 find()일때 ==이 성립 할까?
					=> JPA 에서는 무조건 true를 보장해야 하기 때문에 프록시에서 가져온다.
			 */
//			Relation01_Member reference = em.find(Relation01_Member.class, member.getId());
//			System.out.println("Reference => " + reference.getClass());

			// 같은 인스턴스 안에서라면 무조건 true를 반환
//			System.out.println("m1 == reference => " + (m1 == reference));

//			영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때는 프록시를 초기화하면 문제 발생
//			Relation01_Member member = new Relation01_Member();
//			member.setUsername("member1");
//			em.persist(member);

//			em.flush();
//			em.clear();

//			Relation01_Member refMember = em.getReference(Relation01_Member.class, member.getId());
//			System.out.println("m1 => " + refMember.getClass());

			// 프록시 초기화 확인하기
			// System.out.println("isLoaded? => " + emf.getPersistenceUnitUtil().isLoaded(refMember));
			// false (초기화 되지 않았음)
			
			// 강제 초기화 (JPA 표준 X)
//			refMember.getUsername();
//			System.out.println("isLoaded? => " + emf.getPersistenceUnitUtil().isLoaded(refMember));
			// true (초기화 됨)

			// 강제 초기화 ( Hibernate의 표준 )
//			Hibernate.initialize(refMember);



			// 영속성 컨텍스트 close() or Detach()
			// em.close();
			// em.clear();
//			em.detach(refMember);

			// 여기서 에러 발생함 ( *** 실무에서 정말 많이 일어나는 에러 *** )
			// org.hibernate.LazyInitializationException: could not initialize proxy [hellojpa.Relation01_Member#1] - no Session
			// 결론 : 영속성 컨텍스트가 없다
//			System.out.println("refMember => " + refMember.getUsername());

//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//		}finally {
//			em.close();
//		}
//		emf.close();

	}

	private static void printMember(Relation01_Member member) {
		System.out.println("member => " + member.getUsername());
	}

	private static void printMemberAndTeam(Relation01_Member member) {
		String username = member.getUsername();
		System.out.println("username => " + username);

		Relation01_Team team = member.getTeam();
		System.out.println("team => " + team.getName());
	}

	// 타입 비교는 instanceof로 (프록시에 데이터가 언제든지 추가로 넘어올 수 있기 때문)
	private static void logic(Member m1, Member m2){
//		System.out.println("m1 == m2 => " + (m1.getClass() == m2.getClass()));
		System.out.println("m1 instanceof Member => " + (m1 instanceof Member));
		System.out.println("m2 instanceof Member => " + (m2 instanceof Member));
	}
}
