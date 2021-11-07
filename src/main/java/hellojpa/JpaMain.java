package hellojpa;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/*
    엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
    
    엔티티 매니저는 쓰레드간에 공유하면 절대 안됨(사용하고 버려야 한다)
    
    JPA의 모든 데이터 변경은 트렉젝션 안에서 실행(조회도 왠만하면 안에서)

    가장 단순한 조회 =>
    EntityManager.find(), 객체 그래프 탐색( .get() )

    JPA를 쓸때 JPQL로 자세한 내용을 조회할 수 있다.

    JPQL의 특징
    JPA를 사용하면 엔티티 객체를 중심으로 개발.
    문제는 검색 쿼리,
    검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색,
    모든 DB데이터를 객체로 변환해서 검색하는 것을 불가능,
    애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된
    SQL이 필요하다.
    
    JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
    SQL과 문법 유사, SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 지원
    JPQL은 엔티티 객체를 대상으로 쿼리
    SQL은 데이터베이스 테이블을 대상으로 쿼리

    테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
    SQL을 추상화해서 특정 데이터베이스 SQL에 의존X
    JPQL을 한마디로 정의하면 객체 지향 SQL

 */
public class JpaMain {
    public static void main(String[] args) {
        // 데이터베이스 1개당 1개만 
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        
        // 엔티티 매니저 생성
//        EntityManager em = emf.createEntityManager();
        
        // 트렌젝션(트렌젝션 안에서만 모든 데이터 변경이 있어야한다, 조회는 없어도됨)
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        try {
            // INSERT
//            //비영속
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("MEMBERA");

            // SELECT
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());

            // UPDATE(em.persist 저장 안해도됨 )
//            findMember.setName("UPDATED_NAME");

            // DELETE
//            em.remove(findMember);

//            // 영속 (INSERT)
//            System.out.println("======BEFORE======");
//            em.persist(member);
//            System.out.println("======AFTER=======");

            // 멤버 객체를 대상으로 쿼리 제작(테이블 대상x, 객체 대상o) Member의 엔티티로 select
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .getResultList();
//                    .setFirstResult(5) offset
//                    .setMaxResults(8) limit

//            for (Member member : result) {
//                System.out.println("member.name =>" + member.getName());
//            }
            
            // 수동커밋 default
//            tx.commit();
//        }catch (Exception e){
//            tx.rollback();
//        }finally{
//            em.close();
//        }
        // 어플리케이션이 종료될때 닫아준다
//        emf.close();
    }
}
