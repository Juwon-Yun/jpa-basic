package ReferenceMapping;

import javax.persistence.*;

/*
	별도의 설정을 하지않고 extends를 하면 단일 테이블전략을 사용한다
	@DiscriminatorColumn을 지정하면 DTYPE 컬럼이 생긴다
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Item {
	@Id @GeneratedValue
	private Long id;

	private String name;

	private int price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
