package DataType_Collections;

import DataType.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

/*
	 * 값 타입 컬렉션 사용
	  - 값 타입 저장 예제
	  - 값 타입 조회 예제
	    => 값 타입 컬렉션도 지연 로딩 전략 사용
	  - 값 타입 수정 예제
	  - 참고 : 값 타입 컬렉션은 영속성 전에(Cascade) + 고아 객체 제거 기능을 필수로 가진다고 볼 수 있다.
	
	 *** 컬렉션들은 전부 기본값이 지연로딩이다 ***

	 *** 값 타입은 member만 lifecycle이 돌고 나머지는 값만 가져오는 방식 ***
	
	 * 값 타입 컬렉션의 제약사항
	  => delete collection DataType_Collections.Collections_Member.addressHistory
      => insert collection row DataType_Collections.Collections_Member.addressHistory
      => insert collection row DataType_Collections.Collections_Member.addressHistory

      - 값 타입은 에티티와 다르게 식별자 개념이 없다.
      - 값은 변경하면 추적이 어렵다.
      - 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고,
            값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다,
      - 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본 키를 구성해야 함 : null 입력X, 중복 저장X

      * 값 타입 컬렉션 대안
        - 실무에서는 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 고려함
        - 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용함
        - 영속성 전이(Cascade) + 고아 객체 제거를 사용해서 값 타입 컬렉션 처럼 사용
        - ex) AddressEntity

	  * 일대다 단방향은 update 쿼리가 나갈 수 밖에 없다.

	  * 값 타입을 엔티티로 승급시켜서 사용한다. ( 최적화 등등 ) 값 이력이 남아서 값 추적이 가능해 진다.

	  * 정리
	    - 엔티티 타입의 특징
	      => 식별자 O
	      => 생명 주기 관리
	      => 공유
	    - 값 타입의 특징
	      => 식별자 X
	      => 생명 주기를 엔티티에 의존
	      => 공유하지 않는 것이 안전(복사해서 사용)
	      => 불변 객체로 만드는 것이 안전
	  
	  ***
	      ( 값 타입 사용시 꼭 기억해야함 )
	      값 타입은 정말 값 타입이라 판단될 때만 사용한다
		  엔티티와 값 타입을 혼동해서 엔티티를 값 타입으로 만들면 절때 안됨
		  식별자가 필요하고, 지속해서 값을 추적, 변경해야 한다면 그것은 값 타입이 아닌 엔티티
	  ***

*/
public class CollectionsMain {
	public static void main(String[] args) {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("preHello");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
//		try {
//			Collections_Member member = new Collections_Member();
//			member.setUsername("member1");
//			member.setHomeAddress(new AddressEntity("city1", "street", "1000"));
			
			// 값 타입 컬렉션을 따로  persist하지 않았지만 라이프사이클에 포함된다 
			//  => 값 타입 Embeddable 이여서 가능하다
			// member에서 값을 입력하면 됨
			// orphanremoval = true , cascade.all을 다 킨 효과와 같다
//			member.getFavoriteFoods().add("치킨");
//			member.getFavoriteFoods().add("족발");
//			member.getFavoriteFoods().add("피자");

//			member.getAddressHistory().add(new AddressEntity("old1", "street", "1000"));
//			member.getAddressHistory().add(new AddressEntity("old2", "street", "1000"));

//			em.persist(member);

//			em.flush();
//			em.clear();
			
			// 컬렉션들은 전부 지연로딩이 기본값이다.
//			System.out.println("=========================================");
//			Collections_Member findMember = em.find(Collections_Member.class, member.getId());
//			System.out.println("=========================================");

//			List<Address> addressHistory = findMember.getAddressHistory();
//			for (Address add: addressHistory) {
//				System.out.println("add => " + add.getCity());
//			}

//			Set<String> favoriteFoods = findMember.getFavoriteFoods();
//			for (String food: favoriteFoods) {
//				System.out.println("food => " + food);
//			}

			// City1 => City2
			
			// 이렇게 할 경우 side effect 발생
//			findMember.getHomeAddress().setCity("City2");

			// 새로 넣어야 한다 ( 참조값, 인스턴스 공유 문제 )
//			AddressEntity a = findMember.getHomeAddress();
//			findMember.setHomeAddress(new AddressEntity("city2", a.getStreet(), a.getZipCode()));

			// 치킨 => 한식
			// String은 삭제하고 다시 추가해야함 (당연한 과정)
//			findMember.getFavoriteFoods().remove("치킨");
//			findMember.getFavoriteFoods().add("한식");

			// 값 타입은 member만 lifecycle이 돌고 나머지는 값만 가져오는 방식

			// equals, hashcode 를 무조건 명시 해야 참조값을 지운다
//			System.out.println("=========================================");
//			findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "1000"));
//			findMember.getAddressHistory().add(new AddressEntity("newCity1", "street", "1000"));
//			System.out.println("=========================================");


			
//			tx.commit();
//		}catch (Exception e){
//			tx.rollback();
//		}finally {
//			em.close();
//		}
//		emf.close();
	}
}
