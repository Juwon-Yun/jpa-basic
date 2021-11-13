package MappedSuperClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*
	 * @MappedSuperClass (테이블이 아님)
	  - 공통 매핑 정보가 필요할 때 사용(id, name)
	  - 상속관계 매핑이 절때로 아니다
	  - 엔티티X, 테이블과 매핑X
	  - 부모 클래스를 상속받는 자식 클래스에 매핑 정보만 제공
	  - 조회, 검색 불가(em.find(BaseEntity)불가)
	  - 직접 생성해서 사용할 경우가 없으므로 추상 클래스 사용을 권장한다.
	  - 테이블과 관계 없고, 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모으는 역할
	  - 주로 등록일, 수정일, 등록자, 수정자 같은 전체 엔티티에서 공통으로 적용하는 정보를 모을 때 사용

	  % @Entity 클래스는 엔티티나 @MappedSuperClass로 지정한 클래스만 상속이 가능하다. %


 */
public class MappedSuperClass {
	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("MappedSuperClass");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();

		tx.begin();
		try{


			tx.commit();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			em.close();
		}
		emf.close();

	}
}
