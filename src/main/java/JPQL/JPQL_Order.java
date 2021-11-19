package JPQL;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class JPQL_Order {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "ORDER_AMOUNT")
	private int orderAmount;

	@Embedded
	private JPQL_Address address;

	@ManyToOne
	@JoinColumn(name = "PROD_ID")
	private JPQL_Product product;

	public JPQL_Order() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	public JPQL_Address getAddress() {
		return address;
	}

	public void setAddress(JPQL_Address address) {
		this.address = address;
	}

	public JPQL_Product getProduct() {
		return product;
	}

	public void setProduct(JPQL_Product product) {
		this.product = product;
	}
}
