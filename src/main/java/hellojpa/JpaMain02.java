package hellojpa;

import javax.persistence.*;

/*
    
    *영속성 컨텍스트 (엔티티를 영속화)
     - JPA를 이해하는데 가장 중요한 용어
     - "엔티티를 영구히 저장하는 환경" 이라는 뜻
     - EntityManger.persist(entity);
     - (엔티티를 영속성 컨텍스트에 저장)

    *엔티티의 생명주기
    비영속(new/transient)
     - 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
    영속(managed)
     - 영속성 컨텍스트에 관리되는 상태
    준영속(detached)
     - 영속성 컨텍스트에 저장되었다가 분리된 상태
    삭제(removed)
     - 삭제된 상태

    * 영속성 컨텍스트의 장점
     - 1차 캐시(각각 별도의 1차 캐시를 갖는다)
     - 동일성(identity) 보장
     - 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)
     - 변경 감지(Dirty Checking)
     - 지연 로딩(Lazy Loading) => 실무에서 중요함

    * 영속 엔티티의 동일성 보장 (Collections의 == 과 동일함)
     - 1차 캐시로 반복 가능한 읽기(REPEATABLE READ)등급의
       트렌젝션 격리 수준을 데이터베이스가 아닌 애플리케이션 차원에서 제공함.
    
    * 엔티티 등록 트랜잭션을 지원하는 쓰기 지연
     - 커밋하는 순간에 데이터베이스에 SQL을 보낸다(persist 단계에서 하지않음)

    * 변경 감지(Dirty Checking)
     - 엔티티(1차 캐시)와 스냅샷(1차 캐시를 복사한 내용)을 비교함
     - JPA가 엔티티와 스냅샷을 비교해서 변경 점이있으면 
     - 임시 SQL 저장소에 쿼리를 저장하고 flush()를 통해 DB를 변경함
     - 엔티티를 변경하려는 경우에는 setter로만 바꾸고 persist메소드는 사용하지 않는다.
     
    * 플러시(flush) => 보통 commit을 하는 시점에 반영됨
     - 영속성 컨텍스트의 변경내용을 데이터베이스에 반영

    * 플러시 발생
     - 변경 감지(Dirty Checking)
     - 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
     - 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송(등록, 수정, 삭제 쿼리)
    
    * 영속성 컨텍스트를 플러시하는 방법
     - em.flush() -> 직접(강제) 호출
     - 트랜잭션 커밋 - 플러시 자동 호출
     - JPQL 쿼리 실행 - 플러시 자동 호출

    * JPQL 쿼리 실행시 플러시가 자동으로 호출되는 이유
     - 코드 도중에 JPQL을 실행할 경우 JPA가 무조건 flush 를 먼저 써버리고 JPQL문을 실행함

    * 플러시 모드 옵션( em.setFlushMode() )
     - FlushModeType.AUTO => 커밋이나 쿼리를 실행할 때 플러시(기본값)
     - FlushModeType.COMMIT => 커밋할 때만 플러시
    
    * 플러시의 특징
     - 영속성 컨텍스트를 비우지 않음
     - 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
     - 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 하면됨

    * 준영속 상태(detach 메소드로 영속성을 제거할 수 있다)
     - 영속 => 준영속(detach, clear, close)
        => em.detach(entity) 특정 엔티티만 준영속 상태로 전환
        => em.clear() 영속성 컨텍스트를 완전히 초기화
        => em.close() 영속성 컨텍스트를 종료
     - 영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detach)
     - 영속성 컨텍스트가 제공하는 기능을 사용하지 못함

 */
public class JpaMain02 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        
        EntityManager em = emf.createEntityManager();
        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 객체를 생성만 해놓은 상태(비영속)
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("MEMBERB");
//            member.setAge(29);

            // 객체를 저장한 상태(영속), DB에 저장되지는 않음
            // 1차 캐시에 저장됨
//            System.out.println("======BEFORE======");
//            em.persist(member);

//            PK인 ID값을 @GeneratedValue(strategy = GenerationType.IDENTITY) 로 했을경우
//            System.out.println("member.id => " + member.getId());

//            System.out.println("======AFTER=======");

            // flush 강제 호출 쓰기지연 SQL, 변경 감지된 쿼리를 DB에 반영함
//            em.flush();

//            System.out.println("======Before Commit======");

            // 1차 캐쉬에서 조회
//            Member findMember = em.find(Member.class, "MEMBERB");
            // 1차 캐쉬에 MEMBERC가 없음 => DB 조회 => 1차 캐시에 저장
//            Member findMember2 = em.find(Member.class, "MEMBERC");

            // MEMBERB를 다시 조회하면 1차캐시에 존재하기떄문에 1번만 실행함
//            Member findMember3 = em.find(Member.class, "MEMBERB");

            // 영속성 컨텍스트에서 지움
//            em.detach(member);
            
            
            
            // commit되는 시점에 DB에 저장됨
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally{
            em.close();
        }
        emf.close();
    }
}
