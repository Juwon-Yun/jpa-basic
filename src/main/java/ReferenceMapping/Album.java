package ReferenceMapping;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/*
	@DiscriminatorValue("NAME")으로 DTYPE에 들어갈 데이터의 이름을 정해줄 수 있다.

 */
@Entity
@DiscriminatorValue("A")
public class Album  extends  Item{

	private String artist;

}
