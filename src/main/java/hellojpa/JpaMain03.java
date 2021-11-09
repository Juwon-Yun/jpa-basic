package hellojpa;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/*
    연관관계 매핑 기초
    * 객체와 테이블 연관관계의 차이를 이해
    * 객체의 참조와 테이블의 외래 키를 매핑
    * 용어 이해
        - 방향(Direction) : 단방향, 양방향
        - 다중성(Multiplicity) : 다대일(N:1), 일대다(1:N), 일대일(1:1), 다대다(N:M) 이해
        - 연관관계의 주인(Owner) : 객체 양방향 연관관계는 관리할 주인이 필요하다.

    (FK가 있는 테이블이 N, PK만 있는 테이블이 1)

    * 객체를 테이블에 맞추어 데이터 중심으로 모델링하면, 협력 관계를 만들 수 없다.
        - 테이블은 외래 키로 조인을 사용해서 연관된 테이블을 찾는다.
        - 객체는 참조를 사용해서 연관된 객체를 찾는다.
        - 테이블과 객체 사이에는 이런 큰 간격이 있다.

    * 단방향 연관 관계(단방향 참조관계 member => team)

    * 양방향 연관 관계(양방향 참조관계 member <==> team)
        - 객체에서 양방향 참조관계를 가져도 테이블은 아무 상관이 없다.
        - 테이블에서는 외래 키로 서로의 연관을 다 알 수 있지만,
            객체는 멤버가 팀을 가졌지만 팀에서는 멤버를 가질수 없엇다.
        - Team 에서

    * 양방향 연관관계 주의 - 실습
        - 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자( team.getMembers().add(member) )
        - 연관관계 편의 메소드를 생성하자
        - 양방향 매핑시에 무한 루프를 조심하자 (stack overflow)
            => 예 : toString(), lombok,
                    JSON 생성 라이브러리(Spring에서의 경우 Controller에서 Entity를 반환하면 안됨, 굳이 하려면 VO에다 값을 넣어 반환)
        - 연관관계 편의메소드는 1에 넣어도 되고, 다에 넣어도 된다

    * 양방향 매핑 정리 (설계 단계에서는 무조건 단방향 매핑만 사용)
        - 단방향 매핑만으로도 이미 연관관계 매핑은 완료
        - 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐
        - JPQL에서 역방향으로 탐색할 일이 많음
        - 단방향 매핑을 잘 하면 양방향은 필요할 때만 추가해도 됨 (테이블에 영향을 주지 않음)
    
    * 연관관계의 주인을 정하는 기준
        - 비즈니스 로직을 기준으로 연관관계의 주인을 선택하면 안됨
        - 연관관계의 주인은 외래 키의 위치를 기준으로 정해야함

 */
public class JpaMain03 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        
        EntityManager em = emf.createEntityManager();
        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Relation01_Team team = new Relation01_Team();
            team.setName("TeamA");
            em.persist(team);

            Relation01_Member member = new Relation01_Member();
            member.setUsername("member1");
            // 이게 제일 중요 ***연관관계의 주인에 값을 넣는다***
//            member.changeTeam(team);
            em.persist(member);
            
            //연관관계 편의 메소드를 양쪽에 실행하면 문제가 생길 수 있으니 하나는 주석처리함
            team.addMember(member);

            // 읽기전용 데이터에 값을 넣는다(JPA가 사용하진 않는다)
            // 넣지 않아도 값은 나오지만 2가지 문제가 발생함
            // 양방향 데이터통신을 할때는 양쪽에 값을 다 넣어주는게 맞다.
//             team.getMembers().add(member);

            // 영속성 컨텍스트를 DB에 다 쏴버린 후에 컨텍스트를 깨끗이 지운다, 이걸 써주면 밑의 줄에서 깔끔하게 DB에서 다시 조회해온다
             em.flush();
             em.clear();

            Relation01_Team findTeam = em.find(Relation01_Team.class, team.getId()); // 1차 캐시 들어가있음
            List<Relation01_Member> members = findTeam.getMembers();

            System.out.println("==============================================");
            for (Relation01_Member m : members) {
                System.out.println("m => " + m.getUsername());
            }
            System.out.println("==============================================");

            // em.find하면 1차 캐시에서 가져온다
//            Relation01_Member find_relation01_member = em.find(Relation01_Member.class, member.getId());
            /*
                양방향 연관관계

             */
//            List<Relation01_Member> members = find_relation01_member.getTeam().getMembers();
//            for (Relation01_Member m : members){
//                System.out.println("m => " + m.getUsername());
//            }

            /*
                단방향 연관관계
                @ManyToOne
        	    @JoinColumn(name = "TEAM_ID")
        	    두 어노테이션이 자동으로 이어질 수 있게 한다
             */
//            Relation01_Team findTeam = relation01_member.getTeam();
//            System.out.println("findTeam =>" + findTeam.getName());
//
//            Relation01_Team newTeam = em.find(Relation01_Team.class, 100L);
//            relation01_member.setTeam(newTeam);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally{
            em.close();
        }
        emf.close();
    }
}
