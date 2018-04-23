package com.ekassaauto.database.entities.aui;

/**
 * Created by user on 16.02.2018.
 */
import javax.persistence.*;
@Entity
@Table(name = "scoring")
//@NamedQueries({
//        @NamedQuery(
//                name = "findByBusinessKey",
//                query = "SELECT s FROM Scoring s where s.businessKey = :businessKey"
//        ),
//})
public class ScoringEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "business_key", unique = true)
    private String businessKey;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "userCredentials_id")
    private UserCredential userCredentials;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inst_worm_cache_id")
    private InstWormCacheEntity instWurmCache;

    @Column(name = "app_new_score")
    private Integer scoreAppNew;
    @Column(name = "app_exist_score")
    private Integer scoreAppExist;
    @Column(name = "bank_report_score")
    private Integer bankReportScore;
    @Column(name = "final_score")
    private Integer scoreFinal;
    @Column(name = "bank_report_index1")
    private Double bankReportIndex1;
    @Column(name = "bank_report_index2")
    private Double bankReportIndex2;
    @Column(name = "bank_report_index3")
    private Double bankReportIndex3;
    @Column(name = "bank_report_index4")
    private Double bankReportIndex4;
    @Column(name = "bank_report_index5")
    private Double bankReportIndex5;
    @Column(name = "bank_report_index6")
    private Double bankReportIndex6;
    @Column(name = "bank_report_index7")
    private Double bankReportIndex7;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBusinessKey() {
        return businessKey;
    }
    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
    public UserCredential getUserCredentials() {
        return userCredentials;
    }
    public void setUserCredentials(UserCredential userCredentials) {
        this.userCredentials = userCredentials;
    }
    public InstWormCacheEntity getInstWurmCache() {
        return instWurmCache;
    }
    public void setInstWurmCache(InstWormCacheEntity instWurmCache) {
        this.instWurmCache = instWurmCache;
    }
    public Integer getScoreAppNew() {
        return scoreAppNew;
    }
    public void setScoreAppNew(Integer scoreAppNew) {
        this.scoreAppNew = scoreAppNew;
    }
    public Integer getScoreAppExist() {
        return scoreAppExist;
    }
    public void setScoreAppExist(Integer scoreAppExist) {
        this.scoreAppExist = scoreAppExist;
    }
    public Integer getBankReportScore() {
        return bankReportScore;
    }
    public void setBankReportScore(Integer bankReportScore) {
        this.bankReportScore = bankReportScore;
    }
    public Integer getScoreFinal() {
        return scoreFinal;
    }
    public void setScoreFinal(Integer scoreFinal) {
        this.scoreFinal = scoreFinal;
    }
    public Double getBankReportIndex1() {
        return bankReportIndex1;
    }
    public void setBankReportIndex1(Double bankReportIndex1) {
        this.bankReportIndex1 = bankReportIndex1;
    }
    public Double getBankReportIndex2() {
        return bankReportIndex2;
    }
    public void setBankReportIndex2(Double bankReportIndex2) {
        this.bankReportIndex2 = bankReportIndex2;
    }
    public Double getBankReportIndex3() {
        return bankReportIndex3;
    }
    public void setBankReportIndex3(Double bankReportIndex3) {
        this.bankReportIndex3 = bankReportIndex3;
    }
    public Double getBankReportIndex4() {
        return bankReportIndex4;
    }
    public void setBankReportIndex4(Double bankReportIndex4) {
        this.bankReportIndex4 = bankReportIndex4;
    }
    public Double getBankReportIndex5() {
        return bankReportIndex5;
    }
    public void setBankReportIndex5(Double bankReportIndex5) {
        this.bankReportIndex5 = bankReportIndex5;
    }
    public Double getBankReportIndex6() {
        return bankReportIndex6;
    }
    public void setBankReportIndex6(Double bankReportIndex6) {
        this.bankReportIndex6 = bankReportIndex6;
    }
    public Double getBankReportIndex7() {
        return bankReportIndex7;
    }
    public void setBankReportIndex7(Double bankReportIndex7) {
        this.bankReportIndex7 = bankReportIndex7;
    }
}
