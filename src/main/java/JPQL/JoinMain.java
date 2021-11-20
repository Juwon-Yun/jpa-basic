package JPQL;

import javax.persistence.*;
import java.util.List;

/*
	 * 조인 ( 엔티티 중심의 쿼리 )
	  - 내부 조인 ( m.team t <== 객체 위주의 쿼리 )
	   => SELECT m FROM Member m [INNER] JOIN m.team t
	  - 외부 조인
	   => SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
	  - 세타 조인 ( 객체 입장에서 연관 관계가 없는 조인 )
	   => SELECT COUNT(m) FROM Member m, Team t WHERE m.username = t.name

	 * 조인 - ON 절
	  - ON절을 활용한 조인(JPQ 2.1부터 지원)
	   => 1. 조인 대상 필터링
	   => 2. 연관관계 없는 엔티티 외부 조인 ( 하이버네이트 5.1 부터 )

	  1. 조인 대상 필터링
	    ex) 회원과 팀을 조인하면서, 팀 이른이 A인 팀만 조인
	      JPQL => SLECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'
	      SQL => SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.TEAM_ID = t.id and t.name = 'A '

	  2. 연관관계 없는 엔티티 외부 조인
	   ex) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
	      JPQL => SELECT m, t FROM Member m LEFT JOIN Team t on m.username = t.name
	      SQL  => SELECT m.*, t.* FROM Member m LEFT  JOIN Team t ON m.username = t.name

	  * 서브 쿼리
	   - 나이가 평균보다 많은 회원
	    => select m from Member m
	        where m.age > ( select avg(m2.age) from Member m2 )

	   - 나이가 평균보다 많은 회원
		=> select m from Member m
		    where ( select count(o) from Order o where m = o.member ) > 0

	  * 서브 쿼리 지원 함수
	   - [NOT] EXISTS (subquery) : 서브쿼리에 결과가 존재하면 참
	     -> {ALL | ANY | SOME} (subquery)
	     -> ALL 모두 만족하면 참
	     -> ANY, SOME : 같은 의미, 조건을 하나라도 만족하면 참
	   - [NOT] IN (subquery) : 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참

	  * 서브 쿼리 - 예제
	   - 팀 A 소속인 직원
	    => select m from Member m where exists ( select t from m.team t where t.name = 'teamA' )
       - 전체 상품 각각의 재고보다 주문량이 많은 주문들
        => select o from Order o where o.orderAmount > ALL ( select p.stockAmount from Product p )
       - 어떤 팀이든 팀에 소속된 회원
        => select m from Member m where m.team = ANY ( select t from Team t )

      * JPA 서브 쿼리 한계
       - JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
       - SELECT 절도 가능 ( 하이버네이트에서 지원 )
       - FROM 절의 서브 쿼리는 현재 JPQL에서 불가능
        => 조인으로 풀 수 있으면 풀어서 해결

      * JPQL 타입 표현
       - 문자 : 'HELLO', 'She''s'
       - 숫자 : 10L (Long), 10D (Double), 10F(Float)
       - Boolean : TRUE, FALSE ( 대소문자 구분 X )
       - ENUM : jpabook.MemberType.Admin ( 패키지명 포함 )
       - 엔티티 타입 : TYPE(m) = Member ( 상속 관계에서 사용 )

	  * JPQL 기타
	   - SQL과 문법이 같은 식
	   - EXISTS, IN
	   - AND, OR, NOT
	   - =, >, >=, <, <=, <>
	   - BETWEEN a AND b, LIKE, IS NULL

	  * 조건식 - CASE 식 ( JPA 표준 함수 )
	   - 기본 CASE 식
	      => query : select case when m.age <= 10 then '학생요금'
	                             when m.age >= 60 then '경로요금'
	                             else '일반요금'
	                        end
	                 from Member m
	   - 단순 CASE 식
	       => query : select case t.name when '팀A' then '인센티브110%'
	                                     when '팀B' then '인센티브120%'
	                                     else '인센티브105%'
							 end
					  from Team t
	   - COALESCE : 하나씩 조회해서 null 이 아니면 반환
	   - NULLIF : 두 값이 같으면 null 반환, 다르면 첫 번째 값 반환
	       ex) select coalesce(m.username, '이름 없는 회원') from Member m 
	            => 사용자 이름이 없으면 이름이 없는 회원을 반환
	           select NULLIF(m.username, '관리자') from Member m
	            => 사용자 이름이 '관리자'면 null을 반환하고 나머지는 본인의 이름을 반환

 */
public class JoinMain {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			JPQL_TEAM team = new JPQL_TEAM();
			team.setName("teamA");
			em.persist(team);

			JPQL_Member member = new JPQL_Member();
			member.setUserName("member1");
			member.setAge(10);

			member.setTeam(team);

			em.persist(member);

			em.flush();
			em.clear();

			// inner join JQPL_MEMBER의 @ManyToOne Fetch.LAZY 잊으면 안된다 inner는 생략 가능
			String query = "select m from JPQL_Member m inner join m.team t ";
			List<JPQL_Member> result = em.createQuery(query, JPQL_Member.class)
							.getResultList();
			System.out.println("result.size => " + result.size());

			// left outer join outer 생략 가능
			String query2 = "select m from JPQL_Member m left join m.team t ";
			List<JPQL_Member> result2 = em.createQuery(query, JPQL_Member.class)
							.getResultList();
			System.out.println("result2.size => " + result2.size());

			// 세타 조인
			String query3 = "select m from JPQL_Member m, Team t where m.username = t.name ";
			List<JPQL_Member> result3 = em.createQuery(query, JPQL_Member.class)
							.getResultList();
			System.out.println("result2.size => " + result2.size());



			tx.commit();
		}catch (Exception e){
			tx.rollback();
		}finally{

			em.close();
		}
		emf.close();


	}
}
