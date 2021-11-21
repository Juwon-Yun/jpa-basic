package JPQL;

import javax.persistence.*;
import java.util.List;

/*
	 * 페치 조인 ( fetch join ) 
	  - SQL 조인 종류 X
	  - JPQL에서 성능 최적화를 위해 제공하는 기능
	  - 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회하는 기능 
	  - join fetch 명령어 사용
	  - 페치 조인 ::=[LEFT[OUTER]|INNER] JOIN FETCH 조인 경로

	 * 엔티티 페치 조인 ( 즉시 로딩과 상황이 비슷하다, 동적인 타이밍에 명시적으로 조인 가능 => 페치 조인 )
	  - 회원을 조회하면서 연관된 팀도 함께 조회( SQL 한번에 )
	  - SQL을 보면 회원 뿐만 아니라 팀(T.*)도 함께 SELECT
	  - [JPQL] select m from Member m join fetch m.team
	  - [SQL] SELECT M.*, T.* FROM MEMBER M INNER JOIN TEAM T ON M.TEAM_ID = T.ID

	 * 컬렉션 페치 조인
	  - 일대다 관계, 컬렉션 페치 조인
	    => [JPQL] select t from Team t join fetch t.members where t. name = '팀A'
		   [SQL]
 */
public class FetchJoinMain {
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

			String query = "select m From JPQL_Member m";
			// => 조인을 하는데 Fetch 해서 가져 온다, left join을 하면 outer 조인이 나간다
			// 프록시가 아니고 실제 엔티티가 영속성 컨텍스트에 올라가있는다
			// fetch => LAZY로 설정해도 항상 join fetch가 우선순위에 올라가있다
			query = "select m From JPQL_Member m join fetch m.team";
			query = "select t From JPQL_TEAM t join fetch t.members";

//			List<JPQL_Member> result = em.createQuery(query, JPQL_Member.class)
//							.getResultList();
//
//			for (JPQL_Member m : result) {
//				System.out.println("=======================================================");
//				System.out.println("m => " + m.getUserName() + " , " + m.getTeam().getName() );
//				System.out.println("=======================================================");
//			}
			List<JPQL_TEAM> teamResult = em.createQuery(query, JPQL_TEAM.class)
					.getResultList();

				System.out.println("=======================================================");
			for (JPQL_TEAM team : teamResult) {
				System.out.println("team => " + team.getName() + " , members => " + team.getMembers().size());
				System.out.println("=======================================================");
			}


			/*
				 회원1, 팀A (SQL)
				 회원2, 팀A (영속성 컨텍스트, 1차캐시)
				 회원3, 팀B (SQL)

				 ex) 회원이 100명일 경우 쿼리를 100번 조회함 => N + 1 문제
				        => ( 즉시 로딩이든, 지연 로딩이든 둘다 발생한다, 그래서 Fetch join을 사용함 )

				 페치 조인 사용시
				=======================================================
					m => 회원1 , 팀A
				=======================================================
				=======================================================
					m => 회원2 , 팀A
				=======================================================
				=======================================================
					m => 회원3 , 팀B
				=======================================================

				실제 엔티티를 영속성 컨텍스트에 담아서 쿼리 조회를 한번만 할 수 있다


				JPQL_TEAM 조회 결과 ( 일대다 조인으로 조회하면 디비 size가 뻥튀기 된다, 데이터는 정확히 가져옴 )

				=======================================================
				team => 팀A , members => 2
				=======================================================
				team => 팀A , members => 2
				=======================================================
				team => 팀B , members => 1
				=======================================================


			 */


			tx.commit();
		}catch (Exception e){
			e.printStackTrace();
			tx.rollback();
		}finally {
			em.close();
		}
		emf.close();
	}
}
