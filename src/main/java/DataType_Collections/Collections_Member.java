//package DataType_Collections;
//
//import DataType.Address;
//import DataType.Period;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//public class Collections_Member {
//
//	@Id
//	@GeneratedValue
//	@Column(name = "MEMBER_ID")
//	private  long id;
//
//	@Column(name = "USERNAME")
//	private String username;
//
//	@Embedded
//	private AddressEntity homeAddress;
//
//	@Embedded
//	private Period period;
//
//	// 컬럼 이름은 FOOD_NAME
//	// 기본키는 MEMBER_ID
//	@ElementCollection
//	@CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
//	@Column(name = "FOOD_NAME")
//	private Set<String> favoriteFoods = new HashSet<>();
//
////	@ElementCollection
////	@CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
////	private List<Address> addressHistory = new ArrayList<>();
//
//	// 일대다 단방향으로 매핑
//	// 값타입으로 하는것보다 훨씬 최적화가 잘 되어있다.
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name = "MEMBER_ID")
//	private List<AddressEntity> addressHistory = new ArrayList<>();
//
//	public Collections_Member() {
//	}
//
//	public Collections_Member(long id, String username, AddressEntity homeAddress, Set<String> favoriteFoods, List<AddressEntity> addressHistory) {
//		this.id = id;
//		this.username = username;
//		this.homeAddress = homeAddress;
//		this.favoriteFoods = favoriteFoods;
//		this.addressHistory = addressHistory;
//	}
//
//	public long getId() {
//		return id;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public AddressEntity getHomeAddress() {
//		return homeAddress;
//	}
//
//	public Set<String> getFavoriteFoods() {
//		return favoriteFoods;
//	}
//
//	public List<AddressEntity> getAddressHistory() {
//		return addressHistory;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public void setHomeAddress(AddressEntity homeAddress) {
//		this.homeAddress = homeAddress;
//	}
//
//	public void setFavoriteFoods(Set<String> favoriteFoods) {
//		this.favoriteFoods = favoriteFoods;
//	}
//
//	public void setAddressHistory(List<AddressEntity> addressHistory) {
//		this.addressHistory = addressHistory;
//	}
//}
