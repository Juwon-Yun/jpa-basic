package DataType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DataType_Member {

	@Id
	@GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(name = "USERNAME")
	private String userName;

	// 기간
	@Embedded
	private Period workPeriod;

	// 주소
	@Embedded
	private Address address;

//	@Embedded
//	@AttributeOverrides({
//			@AttributeOverride(name = "city", column = @Column("work_city")),
//			@AttributeOverride(name = "street", column = @Column("work_street")),
//			@AttributeOverride(name = "zipcode", column = @Column("work_zipcode"))
//	})
//	private Address workAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Period getWorkPeriod() {
		return workPeriod;
	}

	public void setWorkPeriod(Period workPeriod) {
		this.workPeriod = workPeriod;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
