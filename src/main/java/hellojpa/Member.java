package hellojpa;

import javax.persistence.*;

/*
    엔티티 매핑
    
    @Entity 속성 정리
    @Entity가 붙은 클래스는 JPA 가 관리한다(엔티티)
    JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
    * 주의점
     - 기본 생성자 필수(파라미터가 없는 public 또는 protected 생성자)
     - final 클래스, enum, interface, inner 클래스 사용 X
     - 저장할 필드에 final 사용 X

    * 속성 name
     - JPA에서 사용할 엔티티 이름을 지정한다.
     - 기본값 : 클래스 이름을 그대로 사용
     - 같은 클래스 이름이 없으면 가급적 기본값을 사용한다.

    @Table 속성
     - @Table은 엔티티와 매핑할 테이블 지정
      => name : 매핑할 테이블 이름 (기본값: 엔티티 이름을 사용)
      => catalog : 데이터베이스 catalog 매핑
      => schema : 데이터베이스 schema 매핑
      => uniqueConstraints(DDL) : DDL 생성 시에 유니크 제약 조건 생성
    
    *데이터베이스 스키마 자동 생성(ex: drop table Member if exists => create table Member, 지우고 생성함)
     - DDL을 애플리케이션 실행 시점에 자동 생성
     - 테이블 중심 => 객체 중심
     - 데이터베이스 방언을 활용해서 데이터베이스에 맞는 적절한 DDL 생성한다.
     - 이렇게 생성된 DDL은 개발 장비에서만 사용
     - 생성된 DDL은 운영서버에서는 사용하지 않거나, 적절히 다듬은 후 사용한다.

    *

 */

// alt + insert 단축키로 빠르게 만듬 eclipse의 ctrl + alt + s

//@Table(name = "MBR", schema="")
//@Table(uniqueConstraints = {@UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = {"name", "AGE"})})
//@Entity
public class Member {
    
    // @ID => PK로 설정
    @Id
    private long id;
//    @Column(name = "name")
    private String name;
    /*
        DDL 생성 기능
        DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다.
     */
//    @Column(unique = true, length = 10)
    private int age;

    // 기본생성자
    public Member(){

    };
    
    // 커스텀 생성자를 쓰려면 기본생성자가 있어야된다
    public Member(long id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
