package Proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*
	단순히 Member를 조회할 때 Team도 함께 조회해야 할까???
 */
public class Proxy_EagerAndLazy {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();

		tx.begin();
		try {
			Proxy_Team team = new Proxy_Team("teamA");
			em.persist(team);

			Proxy_Member member = new Proxy_Member("member1");
			em.persist(member);

			em.flush();
			em.clear();

			Proxy_Member findMember = em.find(Proxy_Member.class, member.getId());

			System.out.println("findMember => " + findMember.getTeam().getClass());

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
