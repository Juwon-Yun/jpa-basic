package hellojpa;

import hellojpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        
        // 엔티티 매니저 생성
        EntityManager em = emf.createEntityManager();
        
        // 트렌젝션
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // INSERT
//            //비영속
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("MEMBERA");

            // SELECT
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());

            // UPDATE(em.persist 저장 안해도됨 )
            findMember.setName("UPDATED_NAME");

            // DELETE
//            em.remove(findMember);

//            // 영속 (INSERT)
//            System.out.println("======BEFORE======");
//            em.persist(member);
//            System.out.println("======AFTER=======");

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally{
        em.close();
        emf.close();
        }
    }
}
