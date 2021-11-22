package JPQL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/*
	 * 페치 조인의 특징과 한계
	  - 페치 조인 대상에는 별칭을 줄 수 없다.
	    => 하이버네이트는 가능하지만 가급적 사용하지 않는 것을 추천
      - 둘 이상의 컬렉션은 페치 조인 할 수 없다.
      - 컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResults)를 사용할 수 없다.
        => 일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능 ( 데이터 뻥튀기가 되지 않기 때문 즉, 데이터 뻥튀기가 되는 조회는 쓰면 안된다)
        => 하이버네이트는 경고 로그를 남기고 메모리에서 페이징( 매우 위험 )
      - 연관된 엔티티들을 SQL 한 번으로 조회 - 성능 최적화
      - 엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선함 ( Fetch = LAZY 보다 우선 => 글로벌 로딩 전략 )
      - 실무에서 글로벌 로딩 전략은 모두 지연 로딩
      - 최적화가 필요한 곳은 페치 조인 적용

     * 페치 조인 - 정리
      - 모든 것을 페치 조인으로 해결할 수 는 없다
      - 페치 조인은 객체 그래프를 유지할 때 사용하면 효과적
      - 여러 테이블을 조인해서 엔티티가 가진 모양이 아닌 전혀 다른 결과를 내야하면, 페치 조인보다는 일반 조인을 사용하고
            필요한 데이터들만 조회해서 DTO로 반환하는 것이 효과적이다.


	 * 다형성 쿼리

	  * TYPE
	    - 조회 대상을 특정 자식으로 한정
	    - 예) Item 중에 Book, Movie를 조회해라
	    - [JPQL] select i from Item i where type(i) IN (Book, Movie)
	    - [SQL] select i from i where i.DTYPE in ('B', 'M')
	  
	  * TREAT( JPA 2.1 )
	   - 자바의 타입 캐스팅과 유사함
	   - 상속 구조에서 부모 타입을 특정 자식 타입으로 다룰 때 사용
	   - FROM, WHERE, SELECT( 하이버네이트 지원 ) 사용
	   - 예) 부모인 Item과 자식 Book이 있다.
	    => [JPQL] select i from item i where treat(i as Book).author = 'kim'
        => [SQL] select i.* from item i where i.DTYPE = 'B' and i.author = 'kim'

	 * 엔티티 직접 사용
	 
	  * 엔티티 직접 사용 - 기본 키 값 
	    - JPQL에서 엔티티를 직접 사용하면 SQL에서 해당 엔티티의 기본 키 값을 사용
	    - [JPQL] 
	       => select count(m.id) from Member m  => 엔티티의 아이디를 사용
	       => select count(m) from Member m => 엔티티를 직접 사용
	    - [SQL]
	       => select count(m.id) as cnt from Member m

		- 엔티티를 파라미터로 전달 ( member )
		  => String jpql = "select m from Member  m where m = :member"
		  => List resultList = em.createQuery(jpql)
		                         .setParameter("member", member)
		                         .getResultList();

		- 식별자를 직접 전달 ( memberId)
		  => String jpql = "select m from Member m where m.id = :memberId"
          => List resultList = em.createQuery(jpql)
                                 .setParameter("memberId", memberId)
                                 .getResultList();

		- 실행된 SQL
		  => select m.* from Member m where m.id = ?

	   * 엔티티 직접 사용 - 외래 키 값
	    - [JPQL]
	        => Team team = em.find(Team.class, 1L)
			=> String qlString = "select m from Member m where m.team = :team"
			=> List resultList = em.createQuery(qlString)
			                       .setParameter("team", team)
			                       .getResultList();

			=> String qlString = "select m from Member m where m.team.id = :teamId"
			=> List resultList = em.createQuery(qlString)
								   .setParameter("teamId", teamId)
								   .getResultList();

		- [SQL]
		  => select m.* from Member m where m.team_id = ?

	    * Named 쿼리 - 어노테이션
	     => @Entity
	        @NamedQuery(name = "Member.findByUsername"
	                    , query = "select m from Member m where m.username = :username")
	        public class Member{...}
		
		* Named 쿼리 - 정적 쿼리
		 - 미리 정의해서 이름을 부여해두고 사용하는 JPQL 
		 - 정적 쿼리
		 - 어노테이션, XML에 정의
		 - *** 애플리케이션 로딩 시점에 초기화 후 재사용 ***
		 - *** 애플리케이션 로딩 시점에 쿼리를 검증 ***

		* Named 쿼리 환경에 따른 설정
		 - XML이 항상 우선권을 가진다
		 - 애플리케이션 운영 환경에 따라 다른 XML을 배포 할 수 있다

		* 벌크 연산 ( 쿼리 한번으로 여러 엔티티의 row를 변경함 )
		 - 재고가 10개 미만인 모든 상품의 가격을 10% 상승하려면?
		 - JPA 변경 감지 기능으로 실행하려면 너무 많은 SQL 실행
		   => 1. 재고가 10개 미만인 상품을 리스트로 조회한다
		   => 2. 상품 엔티티의 가격을 10% 증가한다
		   => 3. 트랜잭션 커밋 시점에 변경 감지가 동작한다
		 - 변경된 데이터가 100건 이라면 100번의 UPDATE SQL 실행

		* 벌크 연산 예제
		 - 쿼리 한 번으로 여러 테이블 로우 변경(엔티티)
		 - executeUpdate()의 결과는 영향받은 엔티티 수 반환
		 - UPDATE, DELETE 지원
		 - INSERT(insert into ... select, 하이버네이트 지원)
		   => String qlString = "update Product p set p.price = p.price * 1.1 where p.stockAmount < :stockAmount"
		   => int resultCount = em.createQuery(qlString).setParameter("stockAmount", 10).executeUpdate();
		
		* 벌크 연산 주의
		 - 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리
		   => 방법1. 벌크 연산 먼저 실행
		   => 방법2. 벌크 연산 수행 후 영속성 컨텍스트 초기화

		* 
 */
public class FetchJoinMain02 {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();
		try{
			JPQL_TEAM teamA = new JPQL_TEAM();
			teamA.setName("팀A");
			em.persist(teamA);

			JPQL_TEAM teamB = new JPQL_TEAM();
			teamB.setName("팀B");
			em.persist(teamB);

			JPQL_Member member1 = new JPQL_Member();
			member1.setUserName("회원1");
			member1.setAge(0);
			member1.setTeam(teamA);
			em.persist(member1);

			JPQL_Member member2 = new JPQL_Member();
			member2.setUserName("회원2");
			member2.setAge(0);
			member2.setTeam(teamA);
			em.persist(member2);

			JPQL_Member member3 = new JPQL_Member();
			member3.setUserName("회원3");
			member3.setAge(0);
			member3.setTeam(teamB);
			em.persist(member3);

			em.flush();
			em.clear();

			// 조회하면 안된다 ( JPA가 의도한 조회 결과가 아님 ) 
			// 같은 엔티티를 전부, 하나는 3개만 조회했을 때 영속성 컨텍스트는 혼란해 하고, 데이터를 보장하지않는다
			// A -> B Fetch join하고 A-> B -> C를 Fetch join할 때나 alias를 씀
//			String query = "select distinct t From JPQL_TEAM t join fetch t.members as m where m.username = '쓰면 안됨' ";
//
//			query = "select m from JPQL_Member m where m.id = :memberId ";
//			JPQL_Member findMember = em.createQuery(query, JPQL_Member.class)
//					.setParameter("memberId", member1.getId())
//					.getSingleResult();
//
//			System.out.println("findMember => " + findMember);

			// @NamedQuery
//			List<JPQL_Member> resultList = em.createNamedQuery("Member.findByUsername")
//					.setParameter("username", "회원1")
//					.getResultList();
//
//			for (JPQL_Member member : resultList) {
//				System.out.println("member = " + member);
//			}
			
			// flush 자동 호출 ( 영속성 컨텍스트를 거치지 않고 바로 DB에 쿼리 입력 )
			int resultCount = em.createQuery("update JPQL_Member m set m.age = 20")
					.executeUpdate();

			System.out.println("resultCount => " + resultCount);

			// 그래서 DB에 넣고 영속성 컨텍스트를 초기화 해야함
			em.clear();

			// 엔티티 매니저를 다시 가져와도 0 임
			JPQL_Member findMember = em.find(JPQL_Member.class, member1.getAge());
			System.out.println( "findMember" + findMember );
			
			// 영속성 컨텍스트에 있는걸 바로 가져온다 ( 0으로 나옴 )
			System.out.println("member1.getAge() => " + member1.getAge());
			System.out.println("member2.getAge() => " + member2.getAge());
			System.out.println("member3.getAge() => " + member3.getAge());



			tx.commit();
		}catch (Exception e){
			tx.rollback();
		}finally {
			em.close();
		}
		emf.close();
	}
}
