package Proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/*
	단순히 Member를 조회할 때 Team도 함께 조회해야 할까???

	 * 프록시와 즉시로딩 주의 ex) 조인 엔티티가 10개라면 즉시 로딩시에 10개 엔티티를 전부 조회한다.
       - 가급적 지연 로딩만 사용(특히 실무에서)
       - 즉시 로딩을 적용하면 에상하지 못한 SQL이 발생한다
       - 즉시 로딩은 JPQL에서 N+1문제를 일으킨다.
       - *** @ManyToOne, @OneToOne은 기본이 즉시 로딩이며 따로 LAZY로 설정해야 한다. ***
       - @OneToMany, @ManyToMany는 기본이 지연 로딩이다.

     * 해결책
       - 모든 엔티티를 LAZY로 설정
       - 패치 조인 "select m from Member m join fetch m.team", Member.class

	 * 지연 로딩 활용 ( 실무에서는 무조건 전부 지연 로딩 설정해야함 )
	   - Member와 Team은 자주 함께 사용 => 즉시 로딩
	   - Member와 Order는 가끔 사용 => 지연 로딩
	   - Order와 Product는 자주 함께 사용 => 즉시 로딩

	 * 지연 로딩 활용 => 실무
	   - 모든 연관관계에 지연 로딩을 사용하라
	   - 실무에서 즉시 로딩을 사용하지 마라
	   - JPQL fetch 조인이나, 엔티티 그래프 기능을 사용해라
	   - 즉시 로딩은 상상하지 못한 쿼리가 나간다.

 */
public class Proxy_EagerAndLazy {
	public static void main(String[] args) {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//
//		EntityManager em = emf.createEntityManager();
//
//		EntityTransaction tx = em.getTransaction();
//
//		tx.begin();
//		try {
//			Proxy_Team team = new Proxy_Team();
//			team.setName("teamA");
//			em.persist(team);
//
//			Proxy_Member member = new Proxy_Member();
//			member.setUserName("member1");
//			member.setTeam(team);
//			em.persist(member);
//
//			em.flush();
//			em.clear();

//			Proxy_Member findMember = em.find(Proxy_Member.class, member.getId());

			// JPQL예제 (EAGER시에 발생하는 문제)
			// 먼저 SQL을 해석함 => Member를 가져오고 => team이 즉시 로딩으로 되어있다? => 모든 Member의 컬럼값을
			//      가져오기 위해 모든 멤버의 Team값을 가져오는 쿼리를 즉시 로딩한다.

			// SQL : select * from member
			// SQL : select * from member where TEAM_ID = XXX
			// 대량의 쿼리 발생 ==> DB 성능 저하
			//List<Proxy_Member> members = em.createQuery("select  m from Proxy_Member m", Proxy_Member.class)
			//		.getResultList();

			// 패치 조인으로 인해서 모든 members의 요소가 채워져있는 상황이다.
//			List<Proxy_Member> members = em.createQuery("select m from Proxy_Member m join fetch m.team", Proxy_Member.class)
//					.getResultList();
//
//			System.out.println("findMember => " + findMember.getTeam().getClass());

			/*
				지연 로딩 LAZY를 이용해서 프록시로 조회
				Member ===(LAZY)==> Team
				Member member = em.find(Memeber.class, 1L)

				Member ===========> Team
				Team team = member.getTeam()
				team.getName() ==> 실제 team을 사용하는 시점에 초기화(DB 조회)

				Member와 Team을 자주 함께 사용한다면?
				
				즉시 로딩 EAGER를 이용해서 조회


			 */

			// 프록시 객체 내부의 데이터를 호출 하는 순간에 프록시 객체를 가져옴
//			System.out.println("======================");
//			System.out.println("teamName =>" + findMember.getTeam().getName());
//			System.out.println("======================");
//
//			tx.commit();
//		}catch (Exception e){
//			e.printStackTrace();
//			tx.rollback();
//		}finally {
//			em.close();
//		}
//		emf.close();

	}
}
