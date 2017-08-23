package com.ekassaauto.database.entities;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by user on 15.08.2017.
 */
@Entity
@Table(name = "inst_worm_cache")
@NamedQueries({
        @NamedQuery(
                name = "getInstWormCache",
                query = "select iwc from InstWormCacheEntity iwc where iwc.id =:id"
        ),
        @NamedQuery(
                name = "getParticularInstWormCache",
                query = "select iwc from InstWormCacheEntity iwc where iwc.firstName =:firstName and iwc.pesel =:pesel and iwc.secondName =:secondName and iwc.accountNumber =:accountNumber order by cacheDate"
        )
})
public class InstWormCacheEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cache_date")
    private Calendar cacheDate;
    @Column(name = "first_name")
    private String firstName;
    private String pesel;
    private String result;
    @Column(name = "second_name")
    private String secondName;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "report", nullable = false)   с инстрепорта идет куча каскадных зависимостей, и не можно просто так удалить сущность
//    поэтому пока закоменчу джойн, позже при желании удалять прям все - прописать все каскадные зависимости
//    private InstReportEntity instReportEntity;
    @Column(name = "account_number")
    private String accountNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getCacheDate() {
        return cacheDate;
    }

    public void setCacheDate(Calendar cacheDate) {
        this.cacheDate = cacheDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

//    public InstReportEntity getInstReportEntity() {
//        return instReportEntity;
//    }
//
//    public void setInstReportEntity(InstReportEntity instReportEntity) {
//        this.instReportEntity = instReportEntity;
//    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
