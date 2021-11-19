package JPQL;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/*
	JPQL(Java Persistence Query Language)
	
	* JPA는 다양한 쿼리 방법을 지원
	 - JPQL
	 - JPA Criteria
	 - QueryDSL
	 - 네이티브 SQL
	 - JDBC API 직접 사용, MyBatis, SpringJdbcTemplate 함께 사용

	* JPQL 소개
	 - 가당 단순한 조회 방법
	  => EntityManaget.find()
	  => 객체 그래프 탐색(a.getB().getC())
	 만약에 나이가 18살 이상인 회원을 모두 검색하고 싶다면??

	 * JPQL
	  - JPA를 사용하면 엔티티 객체를 중심으로 개발
	  - 문제는 검색 쿼리
	  - 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
	  - 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
	  - 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국
	        검색 조건이 포함된 SQL이 필요하다
	  - JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
	  - SQL과 문법 유사, SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 지원
	  - JPQL은 엔티티 객체를 대상으로 쿼리
	  - SQL은 데이터베이스 테이블을 대상으로 쿼리
	  - 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
	  - SQL을 추상화해서 특정 데이터베이스 SQL에 의존하지 않음
	  - JPQL을 한마디로 정의하면 객체 지향 SQL

	  주원이형 짱짱 맨 코딩 천재 멋져욤~~~~>_<!!b
	  
	 * Criteria 소개
	  - 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
	  - JPQL 빌더 역할
	  - JPA 공식 기능
	  - 단점 : 너무 복잡하고 실용성이 없다
	  - Criteria 대신에 QueryDSL 사용 권장
	  
	 * QueryDSL 소개
	  - 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
	  - JPQL 빌더 역할
	  - 컴파일 시점에 문법 오류를 찾을 수 있음
	  - 동적쿼리 작성 편리함
	  - 단순하고 쉬움
	  - 실무 사용 권장
	  
	 * 네이티브 SQL 소개
	  - JPA가 제공하는 SQL을 직접 사용하는 기능
	  - JPQL로 해결할 수 없는 특정 데이터베이스에 의존적인 기능
	  - 예) 오라클 CONNECT BY, 특정 DBa나 사용하는 SQL 힌트
	 
	 * JDBC 직접 사용, SpringJdbcTemplate 등
	  - JPA를 사용하면서 JDBC 커넥션을 직접 사용하거나, 스프링 JdbcTemplate, 마이바티스들을 함께 사용 가능
	  - 단 영속성 컨텍스트를 적절한 시점에 강제로 플러시 필요
	  - 예) JPA를 우회해서 SQL을 실행하기 직전에 영속성 컨텍스트 수동 플러시

 */
public class JPQLMain {
	public static void main(String[] args) {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction tx = em.getTransaction();

//		tx.begin();
//		try {
			// 엔티티를 대상으로 쿼리
//			List<JPQL_Member> result = em.createQuery("select m from JPQL_Member m where m.userName like '%kim%'"
//							, JPQL_Member.class
//					).getResultList();

//			for (JPQL_Member member : result) {
//				System.out.println("member => " + member);
//			}
			
			//Criteria 사용 준비
//			CriteriaBuilder cb = em.getCriteriaBuilder();
//			CriteriaQuery<JPQL_Member> query = cb.createQuery(JPQL_Member.class);

			// 루트 클래스 (조회를 시작할 클래스)
//			Root<JPQL_Member> m = query.from(JPQL_Member.class);

			// 쿼리 생성
//			CriteriaQuery<JPQL_Member> cq = query.select(m).where(cb.equal(m.get("userName"), "kim"));
//			List<JPQL_Member> resultList = em.createQuery(cq).getResultList();

			// flush -> commit, query
//			em.createNativeQuery("select MEMBER_ID from MEMBER")
//							.getResultList();

//			tx.commit();
//		}catch (Exception e){
//			tx.rollback();
//			e.printStackTrace();
//		}finally{
//			em.close();
//		}
//		emf.close();

	}
}
