package Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

	@Id
	@GeneratedValue
	@Column(name = "PARENT_ID")
	private Long id;

	@Column(name = "PARENT_NAME")
	private  String name;

//	@OneToMany(mappedBy = "parent", orphanRemoval = true)
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Child> childList = new ArrayList<>();

	public void addChild(Child child){
		childList.add(child);
		child.setParent(this);

	}

	public List<Child> getChildList() {
		return childList;
	}

	public void setChildList(List<Child> childList) {
		this.childList = childList;
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