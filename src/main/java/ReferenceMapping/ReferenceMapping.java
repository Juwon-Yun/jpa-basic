package ReferenceMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*
	 * 상속관계 매핑
	  - 관계형 데이터베이스는 상속 관계 없음
	  - 슈퍼타입 서브타입 관계라는 모델링 기업이 객체 상속과 유사
	  - 상속관계 매핑 : 객체의 상속과 DB의 슈퍼타입 서브타입 관계를 매핑

	 * 주요 어노테이션
	  - @Inheritance(strategy = InheritanceType.XXX)
	    => JOINED : 조인 전략
	    => SINGLE_TABLE : 단일 테이블 전략
	    => TABLE_PER_CLASS : 구현 클래스마다 테이블 전략
	  - @DiscriminatorColumn(name="DTYPE")
	  - @DiscriminatorValue("XXX")


 */
public class ReferenceMapping {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();

		tx.begin();
		try {
			Movie movie = new Movie();
			movie.setDirector("aaaa");
			movie.setActor("bbbb");
			movie.setName("바람과함께사라지다");
			movie.setPrice(10000);

			em.persist(movie);

			em.flush();
			em.clear();

			System.out.println("==========================================");
			Movie findMovie = em.find(Movie.class, movie.getId());
			System.out.println("findMovie => " + findMovie);
			System.out.println("==========================================");

			tx.commit();
		}catch (Exception e){
			tx.rollback();
		}finally {
			em.close();
		}
		emf.close();

	}
}
