package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Relation02_Locker {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	//읽기전용 일대일 관계 양방향 매핑
	@OneToOne(mappedBy = "locker")
	private Relation02_Member member;

	public Relation02_Member getMember() {
		return member;
	}

	public void setMember(Relation02_Member member) {
		this.member = member;
	}

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
}
