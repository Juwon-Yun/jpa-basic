package DataType_Collections;

import DataType.Address;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

	@Id
	@GeneratedValue
	private Long id;
	
	// entity로 매핑
	private Address address;

	public AddressEntity() {
	}

	public AddressEntity(Long id, Address address) {
		this.id = id;
		this.address = address;
	}

	public AddressEntity(String city2, String street, String zipCode) {
		this.address = new Address(city2, street, zipCode);
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
