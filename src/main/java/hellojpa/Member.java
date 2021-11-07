package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Table(name = "MEMBER")
@Entity
public class Member {
    
    // ID => PK로 설정
    @Id
    private long id;
//    @Column(name = "name")
    private String name;

    // 기본생성자
    public Member(){

    };
    
    // 커스텀 생성자를 쓰려면 기본생성자가 있어야된다
    public Member(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
