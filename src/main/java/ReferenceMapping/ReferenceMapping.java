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
	
	 * 조인 전략
	  - 장점
	   => 테이블 정규화
	   => 외래 키 참조 무결성 제약조건 활용 가능 
	   => 저장 공간 효율화
	  - 단점
	   => 조회시 조인을 많이 사용, 성능 저하
	   => 조회 쿼리가 복잡함
	   => 데이터 저장시 INSERT SQL 2번 호출

	 * 단일 테이블 전략
	  - 장점
	   => 조인이 필요 없으므로 일반적으로 조회 성능이 빠름
	   => 조회 쿼리가 단순함
	  - 단점
	   => 자식 엔티티가 매핑한 컬럼은 모두 null 허용
	   => 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다.
	   => 그러므로 상황에 따라서 조회 성능이 오히려 느려질 수 있다.
	  
	 * 구현 클래스마다 테이블 전략(쓰면 안되는 전략, DBA와 개발자 둘 다 권장하지않음)
	 % 이 전략은 데이터베이스 설계자와 ORM 전문가 둘다 추천하지 않는 전략이다. %
	  - 장점 
	   => 서브 타입을 명확하게 구분해서 처리할 때 효과적
	   => not null 제약 조건 사용 가능
	  - 단점
	   => 여러 자식 테이블을 함께 조회할 때 성능이 느림(UNION SQL)
	   => 자식 테이블을 통합해서 쿼리하기 어려움


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
