package JPQL;

import javax.persistence.*;
import java.util.List;

/*
	* JPQL 소개
	  - JPQL은 객체 지향 쿼리 언어다. 따라서 테이블을 대상으로 쿼리하는 것이 아니라 엔티티 객체를 대상으로 쿼리한다.
	  - JPQL은 SQL을 추상화해서 특정 데이터 베이스 SQL에 의존하지 않는다
	  - JPQL은 결국 SQL로 변환된다

	* JPQL 문법
	  - select m from member as m where m.age > 18
	  - 엔티티와 속성은 대소문자 구분 O (Member, age)
	  - JPQL 키워드는 대소문자 구분 X ( SELECT, FROM, where)
  	  - 엔티티 이름 사용, 테이블 이름이 아님(Member)
  	  - 별칭은 필수(m) (as는 생략가능)

  	* 집합과 정렬
  	 - GROUP BY, HAVING
  	 - ORDER BY
  	
  	* TypeQuery, Query
  	 - TypeQuery : 반환 타입이 명확할 때 사용
  	   => TypeQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class)
  	 - Query : 반환 타입이 명확하지 않을 때 사용
  	   => Query query = em.createQuery("SELECT m.username, m.age from member m")
	
	* 결과 조회 API
	 - query.getResultList() : 결과가 하나 이상일 때, 리스트 반환
	    => 결과가 없으면 빈 리스트 반환
	 - query.getSingleResult() : 결과가 정확히 하나, 단일 객체 반환
	    => 결과가 없으면 : javax.persistence.NoResultException
	    => 둘 이상이면 : javax.persistence.NonUniqueResultException

	* 파라미터 바인딩 - 이름 기준, 위치 기준
	 - SELECT m FROM Member m where m.username =: username
	   => query.setParameter("username", usernameParam)
	 - SELECT m FROM Member m where m.username = ?1
	   => query.setParameter(1, usernameParam)
 */
public class JPQLMain02 {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();
		try{
			JPQL_Member member = new JPQL_Member();
			member.setUserName("MEMBER1");
			member.setAge(10);
			em.persist(member);

			TypedQuery<JPQL_Member> query = em.createQuery("select m from JPQL_Member m where m.id = 10", JPQL_Member.class);

			List<JPQL_Member> resultList = query.getResultList();
			for (JPQL_Member mem: resultList) {
				System.out.println("member =>  " + mem.getUserName());
			}
			
			// javax.persistence.NoResultException => error 발생함
			JPQL_Member singleMember = query.getSingleResult();
			// Spring Data JPA =>
			System.out.println("SingleResult => " + singleMember);

			TypedQuery<String> queryStr = em.createQuery("select m.userName from JPQL_Member m", String.class);

			// 타입 정보를 받을 수 없을 때 ( 반환 값이 명확하지 않을 떄)
			Query query3 = em.createQuery("select m.userName, m.age from JPQL_Member m");

			// Parameter를 바인딩 할 수 있다. ( 이름, 문자 기준 파라미터 바인딩  )
			JPQL_Member queryStr2 = em.createQuery("select m.userName from JPQL_Member m where m.userName = :username", JPQL_Member.class)
					.setParameter("username", "MEMBER1")
					.getSingleResult();

			// 위치 기준 바인딩은 추천 하지않음 ex) m.id = ?1




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
