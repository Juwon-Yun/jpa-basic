package JPQL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
			member1.setTeam(teamA);
			em.persist(member1);

			JPQL_Member member2 = new JPQL_Member();
			member2.setUserName("회원2");
			member2.setTeam(teamA);
			em.persist(member2);

			JPQL_Member member3 = new JPQL_Member();
			member3.setUserName("회원3");
			member3.setTeam(teamB);
			em.persist(member3);

			em.flush();
			em.clear();

			// 조회하면 안된다 ( JPA가 의도한 조회 결과가 아님 ) 
			// 같은 엔티티를 전부, 하나는 3개만 조회했을 때 영속성 컨텍스트는 혼란해 하고, 데이터를 보장하지않는다
			// A -> B Fetch join하고 A-> B -> C를 Fetch join할 때나 alias를 씀
			String query = "select distinct t From JPQL_TEAM t join fetch t.members as m where m.username = '쓰면 안됨' ";

			



			
			tx.commit();
		}catch (Exception e){
			tx.rollback();
		}finally {
			em.close();
		}
		emf.close();
	}
}
