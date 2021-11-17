package DataType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*
	% 값 타입 목차 %
	 * 기본값 타입
	 * 임베디드 타입(복합 값 타입) (중요)
	 * 값 타입과 불변 객체
	 * 값 타입의 비교
	 * 값 타입 컬렉션 (중요)
	
	 * JPA의 데이터 타입 분류
	  - 엔티티 타입
	    => @Entity로 정의하는 객체
	    => 데이터가 변해도 식별자로 지속해서 추적 가능 (표현 중요)
	    => 예) 회원 엔티티의 키나 나이 값을 변경해도 식별자로 인식 가능
	    
	  - 값 타입
	   => int, Integer, String 처럼 단순히 값으로 사용하는 자바 기본 타입이나 객체
	   => 식별자가 없고 값만 있으므로 변경시 추적 불가(게시판의 content 등)
	   => 예) 숫자 100을 200으로 변경하면 완전히 다른 값으로 대체됨
	   
	 * 기본값 타입 
	  - 자바 기본 타입(int, double)
	  - 래퍼 클래스(Integer, Long)
	  - String
	 * 임베디드 타입(embedded type, 복합 값 타입) ex) x, y 의 좌표값
	 * 컬렉션 값 타입(collection value type) ex) java의 collections
	 
	 * 기본값 타입
	  - 생명 주기를 엔티티에 의존
	   => ex) 회원을 삭제하면 이름, 나이 필드도 함께 삭제
	  - 값 타입은 공유하면 x
	   => ex) 회원 이름 변경시 다른 회원의 이름도 함께 변경되면 안됨
	 * 참고 : 자바의 기본 타입은 절때 공유되지 않는다. (side effect가 없다)
	  - int, double 같은 기본 타입(primitive type)은 절대 공유 X
	  - 기본 타입은 항상 값을 복사함
	  - Integer같은 래퍼 클래스나 String 같은 특수한 클래스는 공유 가능한 객체 이지만 변경되지 않는다.
		=> (reference (참조값 => 주솟값) 를 긁어가기 때문에 값이 공유가 될 수 있다, 인스턴스를 공유하기 때문에)
	 
	 * 임베디드 타입(복합 값 타입)
	  - 새로운 값 타입을 직접 정의할 수 있음 
	  - JPA는 임베디드 타입(embedded type)이라고 한다
	  - 주로 기본 값 타입을 모아서 만들어서 복합 값 타입이라고도 한다
	  - int, String과 같은 값 타입

	 * 임베디드 타입 사용법
	  - @Embeddable : 값 타입을 정의하는 곳에 표시 ( 해당 어노테이션을 사용한 객체는 기본 생성자 설정이 필수이다 )
	  - @Embedded : 값 타입을 사용하는 곳에 표시
	  - 기본 생성자 필수
	 
	 * 임베디드 타입의 장점 ( 값 타입 )
	  - 재사용
	  - 높은 응집도
	  - Period.isWord() 처럼 해당 값 타입만 사용하는 의미 있는 메소드를 만들 수 있다 ( 객체 지향적으로 설계 가능 )
	  - 임베디드 타입을 포함한 모든 값 타입은, 값 타입을 소유한 엔티티에 생명주기를 의존한다

	 * 임베디드 타입과 테이블 매핑
	  - 임베디드 타입은 엔티티의 값일 뿐이다.
	  - 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.
	  - 객체와 테이블을 아주 세밀하게(find-grained) 매핑하는 것이 가능하다
	  - 잘 설계한 ORM 애플리케이션은 매핑한 테이블의 수보다 클래스의 수가 더 많음

	 * 임베디드 타입과 연관관계
	 
	 * @AttributeOverride : 속성 재정의
	  - 한 엔티티에서 같은 값 타입을 사용하면?
	  - 컬럼 명이 중복됨
	  - @AttributeOverrides, @AttributOverride를 사용해서 컬럼 명 속성을 재정의

	 * 임베디드 타입과 null
	  - 임베디드 타입의 값이 null이면 매핑한 컬럼 값은 모두 null


	 * part.3 값 타입과 불변 객체
	  => 값 타입은 복잡한 객체 세상을 조금이라도 단순화하려고 만든 개념이다.
	     따라서 값 타입은 단순하고 안전하게 다룰 수 있어야 한다.

	 * 값 타입 공유 참조
	  - 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험함
	  - 부작용(side effect) 발생

	* 객체 타입의 한계
	 - 항상 값을 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있다
	 - 문제는 임베디드 타입처럼 직접 정의한 값 타입은 자바의 기본 타입이 아니라 객체 타입이다.
	 - 자바 기본 타입에 값을 대입하면 값을 복사한다.
	 - 객체 타입은 참조 값을 직접 대입하는 것을 막을 방법이 없다
	 - 객체의 공유 참조는 피할 수 없다. ( 타입만 맞으면 대입 연산자로 다 들어가기 때문에 피할 수 없다 )

	* 객체 타입의 한계 예시

	   기본 타입(primitive type)
	   int a = 10;
	   int b = a; => 기본 타입은 값을 복사
	   b = 4;

	   ///////

	   객체 타입
	   Address a = new Address("Old")
	   Address b = a; => 객체 타입은 참조를 전달한다
	   b.setCity("New") => 인스턴스가 하나기 때문에 a.getCity()도 New가 된다.
	   
	 * 불변 객체
	  - 객체 타입을 수정할 수 없게 만들면 부작용을 원천 차단
	  - 값 타입은 불변 객체(immutable object)로 설계해야함
	  - 불변 객체 : 생성 시점 이후 절대 값을 변경할 수 없는 객체
	  - 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않으면 됨
	  - 참고 : Integer, String은 자바가 제공하는 대표적인 불변 객체

	 * 불변 객체의 사용으로 참조값을 공유하는 객체의 side effect 참사를 막을 수 있다.

	 * 값 타입의 비교
	  - 값 타입 : 인스턴스가 달라도 그 안에 값이 같으면 같은 것으로 봐야 함

	 * 값 타입의 비교
	  - 동일성(identity) 비교 : 인스턴스의 참조 값을 비교, == 사용
	  - 동등성(equivalence) 비교 : 인스턴스의 값을 비교, equals() 사용
	  - 값 타입은 a.equals(b)를 사용해서 동등성 비교를 해야 함
	  - 값 타입의 equals() 메소드를 적절하게 재정의 ( 주로 모든 필드에 사용함 )

	 
 */
public class DataType {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();

		tx.begin();
		try {
//			DataType_Member member = new DataType_Member();
//			member.setUserName("MemberA");
//			member.setAddress(new Address("city", "street", "10000"));
//			member.setWorkPeriod(new Period());
//			em.persist(member);

			Address address = new Address("city", "street", "10000");

			DataType_Member member = new DataType_Member();
			member.setUserName("member1");
			member.setAddress(address);
			em.persist(member);

			Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipCode());

			DataType_Member member2 = new DataType_Member();
			member2.setUserName("member2");
//			member2.setAddress(address);
			// 복사한걸 들고오기 때문에 영향이없다. => 서로 다른 값 city, newCity가 들어감
			member2.setAddress(copyAddress);
			em.persist(member2);

			// member1, member2 둘다 city가 newCity로 데이터가 들어간다 ( side effect ) 
			// 만약에 값을 고의로 공유시키고 싶다면 @Embedded 보다는 @entity로 해야한다.
			member.getAddress().setCity("newCity");

			// setter를 없애고 생성자로만 값을 대입.
//			address.setCity();
//			address.setStreet();
//			address.setZipCode();
			
			// Setter가 없이 즉, 불변 객체 형식으로 값을 대입한다
			Address newAddress = new Address("NewCity", address.getStreet(), address.getZipCode());

			DataType_Member member3 = new DataType_Member();
			member3.setUserName("member3");
			member3.setAddress(newAddress);
			em.persist(member3);



			tx.commit();
		}catch (Exception e){
			tx.rollback();
		}finally {
			em.close();
		}
		emf.close();

	}
}
