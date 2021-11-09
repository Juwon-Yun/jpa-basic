package hellojpa;

import javax.persistence.*;
import java.util.Date;

/*
    @Column
     - name => 필드와 매핑할 테이블의 컬럼 이름(기본값: 객체의 필드 이름)
     - insertable, updatable => 등록, 변경 가능 여부(기본값: true)
     - nullable(DDL) => null 값의 허용 여부를 설정한다. false로 설정하면 DDL 생성 시에 not null 제약조건이 붙는다.
     - unique(DDL) => @Table의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크 제약조건을 걸 때 사용한다.
     - columnDefinition(DDL) => 데이터베이스 컬럼 정보를 직접 줄 수 있다. (ex varchar(100) default 'EMPTY')
     - length(DDL) => 문자 길이 제약 조건, String 타입에만 사용한다.(기본값: 255)
     - precision, scale(DDL) => BigDecimal 타입에서 사용한다(BigInteger도 사용할 수 있다)
                                precision은 소수점을 포함한 전체 자릿수, scale은  소수외 자릿수다
 */

@Entity
public class Member02 {
/*
      기본 키 매핑 어노테이션 직접 할당 => @Id,
                          자동 생성 => @GeneratedValue(strategy = GenerationType.AUTO)
                                - IDENTITY : 데이터베이스에 위임, MYSQL
                                - SEQUENCE : 데이터베이스 시퀸스 Object 사용, ORACLE
                                    -> @SequenceGenerator 필요
                                - TABLE : 키 생성용 테이블 사용, 모든 DB에서 사용
                                    -> @TableGenerate 필요
                                - AUTO : 방언에 따라 자동 지정, 기본값

      @SequenceGenerator => class에 매핑한다. 
                            name => "",
                            sequenceName(데이터베이스에 매핑할 시퀸스 이름) => "",
                            initialValue => 1,
                            allocationSize => 1(기본값은 50이며, 초기값은 -49, 51 => 1, 51 => 2 => 3으로 진행된다.
                            (즉 메모리에 50단위로 메모리를 확보해 놓고 1씩 증가시킨다. 성능 최적화, 동시성 문제 없음)  
      
      @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "class에 매핑된 name값")
      private long id;
        
      * 테이블 전략 (strategy = GenerationType.TABLE)
       - 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀸스를 흉내내는 전략
       - 장점 : 모든 데이터베이스에 적용 가능
       - 단점 : 성능 이슈(최적화 x)

      @TableGenerator(
                        name(식별자 생성기 이름) = "",
                        table(키생성 테이블 명) = "",
                        pkColumnValue(시퀸스 컬럼명) = "",
                        allocationSize(시퀸스 호출에 증가하는 수) = 1)
                        
      * 권장하는 식별자 전략
       - 기본 키 제약 조건 : null 아님, 유일성, 불변
       - 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대체키)를 사용하자.
       - 예를 들어 주민등록번호도 기본 키로 적절하지 않다.
       - 권장 : Long 타입 + 대체키(시퀸스, UUID, 랜덤키를 포함한 회사 룰) + 키 생성 전략 사용

      * IDENTITY 전략 - 특징
         => @GeneratedValue(strategy = GenerationType.IDENTITY)로 설정 했을 경우에만 특별히
         => IDENTITY는 엔티티 생성시 1차 캐시에 PK값을 넣어야하는데 값을 Insert 전까지 모른다
         => 그래서, JPA는 이 경우에만 commit 이전에 persist에서 PK값을 알려준다
       - 기본 키 생성을 데이터 베이스에 위임
       - 주로 MySQL, PostgreSQL, SQL Server, DB2에서 사용한다(ex : MySQL의 AUTO_INCREAMENT)
       - JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행한다.
       - AUTO_INCREMENT는 데이터베이스에 INSERT SQL을 실행한 이후에 ID값을 알 수 있다.
       - IDENTITY 전략은 em.persist() 시점에 즉시 INCREMENT 실행하고 DB에서 식별자를 조회한다.
        
         => @GeneratedValue(strategy = GenerationType.SEQUENCE) 인 경우
         insert persist 이전에 PK값을 먼저 조회한 후에 값을 받아와서 영속성 컨텍스트에 저장한다

      *
*/

    @Id
    private long id;

    @Column(name = "name", updatable = false, nullable = false)
    private String userName;

    private Integer age;

    // enum 타입이 없는 DB는 알아서 매핑 해준다. h2 => varchar(255)
    // value(EnumType.ORDINAL => enum 순서를 데이터베이스에 저장, .STRING enum 이름을 데이터베이스에 저장)
    // Enum의 문자열로 저장한다(USER => USER, ADMIN => ADMIN)
    // 기본값이 ORDINAL 이여서 무조건 다른 값으로 지정해야한다.
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // TemporalType.Date(날짜), TIEM(시간), TIMESTAMP(날짜&시간)
    // LocalDate, LocalDateTime으로 설정 가능
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // CLOB, BLOB
    @Lob
    private String description;

    // DB에는 사용하지않고 메모리에서만 사용할 경우 @Transient
    @Transient
    private int temp;

    public Member02() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
