package JPQL;

import javax.persistence.*;
import java.util.List;

/*
	* 프로젝션 
	 - SELECT 절에 조회할 대상을 지정하는 것
	 - 프로젝션 대상 : 엔티티, 임베디드 타입, 스칼라 타입( 숫자, 문자등 기본 데이터 타입 )
	 - SELECT m FROM Member m => 엔티티 프로젝션 
	 - SELECT m.team FROM Member m => 엔티티 프로젝션
	 - SELECT m.address FROM Member m => 임베디드 타입 프로젝션
	 - SELECT m.username, m.age FROM Member m => 스칼라 타입 프로젝션
	 - DISTINCT로 중복 제거

	 * 프로젝션 - 여러 값 조회 ex) 스칼라 프로젝션에서 여러가지 타입을 반환 받을 때 고려사항
      - SELECT m.username, m.age FROM Member m
	  - Query 타입으로 조회
	  - Object[] 타입 으로 조회
	  - new 명령어로 조회
	    => 단순 값을 DTO로 바로 조회
	       SELECT new jpabook.jpql.UserDTO(m.username, m.age) FROM Member m
	    => 패키지 명을 포함한 전체 클래스 명 입력
	    => 순서와 타입이 일치하는 생성자 필요

 */
public class JPQL_Main03 {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			JPQL_Member member = new JPQL_Member();
			member.setUserName("member1");
			member.setAge(10);
			em.persist(member);
			
			// 엔티티 프로젝션
			// 조회한 모든 데이터가 영속성 컨텍스트에 저장됨
			List<JPQL_Member> result = em.createQuery("select m from JPQL_Member m", JPQL_Member.class)
							.getResultList();

			JPQL_Member findmember = result.get(0);
			// 값이 바뀌면 영속성 컨텍스트에 저장되고 안바뀌면 저장되지 않음 
			findmember.setAge(20);
			
			// inner joing 을 실행함 join절을 명시하지 않아도 같은 결과를 가져오지만
			// 추후에 쿼리 튜닝에 문제가 생길 수 있음 ( 예측이 안됨 )
			List<JPQL_Member> result2 = em.createQuery("select m.team from JPQL_Member m join m.team t", JPQL_Member.class)
					.getResultList();

			// 임베디드 타입 프로젝션
			List<JPQL_Order> result3 = em.createQuery("select o.address from JPQL_Order o", JPQL_Order.class)
					.getResultList();

			// 스칼라 타입 프로젝션
			List result4 = em.createQuery("select distinct m.userName, m.age from JPQL_Member m", JPQL_Member.class)
					.getResultList();
			
			// 1. Object [] 타입으로 조회
			Object o = result4.get(0);
			Object[] resultObjArr = (Object[]) o;
			System.out.println("userName => " + resultObjArr[0]);
			System.out.println("age => " + resultObjArr[1]);

			// 2. TypeQuery로 조회
			List<Object[]> resultType = em.createQuery("select m.userName, m.age from JPQL_Member m")
					.getResultList();
			Object[] resultTypeQ = resultType.get(0);
			System.out.println("userName => " + resultTypeQ[0]);
			System.out.println("age => " + resultTypeQ[1]);

			// 제일 깔끔한 new 명령어로 조회 ( 패키지 명을 포함한 전체 이름 입력, 순서와 타입이 일치하는 생성자 필수 요건 )
			List<MemberDTO> resultList = em.createQuery("select new JPQL.MemberDTO(m.userName, m.age) from JPQL_Member m", MemberDTO.class)
					.getResultList();

			MemberDTO memberDTO =  resultList.get(0);
			System.out.println("memberDTO.username => " + memberDTO.getUserName());
			System.out.println("memberDTO.age => " + memberDTO.getAge());



			tx.commit();
		}catch (Exception e){

			tx.rollback();
		}finally{
			em.close();
		}
		emf.close();
	}
}
