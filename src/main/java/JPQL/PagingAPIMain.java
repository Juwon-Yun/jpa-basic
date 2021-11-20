package JPQL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/*
	 * 페이징 API
	  - setFirstResult(int startPosition) : 조회 시작 위치 ( 0부터 시작 )
	  - setMaxResult(int maxPosition) : 조회할 데이터 수

 */
public class PagingAPIMain {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			for(int i = 0; i < 100; i++){
				JPQL_Member member = new JPQL_Member();
				member.setUserName("member" + i);
				member.setAge(i);
				em.persist(member);
			}

			em.flush();
			em.clear();

			// <property name="hibernate.dialect" value="org.hibernate.dialect.Oracle12cDialect"/>
			List<JPQL_Member> result = em.createQuery("select m from JPQL_Member  m order by m.age desc ", JPQL_Member.class)
					.setFirstResult(1)
					.setMaxResults(10)
					.getResultList();

			System.out.println("result.size() => " + result.size());

			for (JPQL_Member member1 : result) {
				System.out.println("member1 => "  + member1);
			}

			em.flush();
			em.clear();
		}catch (Exception e){

			tx.rollback();
		}finally {

			em.close();
		}
		emf.close();

	}
}
