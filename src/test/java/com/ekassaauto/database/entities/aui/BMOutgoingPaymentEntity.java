package com.ekassaauto.database.entities.aui;

import java.util.Calendar;
import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by user on 24.11.2017.
 */

    /**
     * @author kogut on 21.11.2016.
     */
//@Audited
    @Entity
    @NamedQueries({
            @NamedQuery(name = "getBMTransactionById",
            query = "select bmope from BMOutgoingPaymentEntity bmope where id = :id"
            ),

//            @NamedQuery(name = "createDuplicateEntry",
//                    query = "insert into BMOutgoingPaymentEntity bm (id, client_account, address_1, address_2, amount, blue_cash_status, \"comment\", create_date, fee, permit, recipient_name, status, transaction_id, transfer_app_id, transfer_date, legalentity_id,\n" +
//                            "purpose_id, deal_no, in_progress, paymenttypeentity_id, pesel, create_date_time) "
//
//            ),

//            @NamedQuery(name = "findBMOutgoingByTransferAppId",
//                    query = "select bm from BMOutgoingPaymentEntity bm where bm.transferAppId=:transferAppId"
//            ),
//            @NamedQuery(
//                    name = "findBMOutgoingForPayment",
//                    query = "select bm from BMOutgoingPaymentEntity  bm where bm.legalEntity.id=:ledalEntityId " +
//                            "and bm.inProgress=:inProgress and bm.status=:status and bm.permit=:permit and bm.transferDate=:transferDate and bm.blueCashStatus=:blueStatus"
//            ),
//            @NamedQuery(
//                    name = "findBMOutgoingByPeselAndDate",
//                    query = "select bm from BMOutgoingPaymentEntity bm where bm.pesel=:pesel " +
//                            "and bm.transferDate >= :fromDate " +
//                            "and bm.transferDate <= :toDate and bm.paymentTypeEntity.id in :typeEntityIdList"
//            ),
//            @NamedQuery(
//                    name = "findDuplicateBMOutgoingPayment",
//                    query = "SELECT bm1 FROM BMOutgoingPaymentEntity bm1 where bm1.transferDate >=:fromDate and bm1.transferDate <=:toDate " +
//                            "and (bm1.permit = true or (bm1.permit = false and bm1.blueCashStatus = true)) " +
//                            "and  bm1.accountNumber IN ("+
//                            "SELECT bm2.accountNumber FROM BMOutgoingPayment bm2 where bm2.transferDate >=:fromDate and bm2.transferDate <=:toDate " +
////                        "and bm1.amount = bm2.amount " +
//                            "and bm1.pesel = bm2.pesel " +
//                            "GROUP BY bm2.accountNumber " +
//                            "HAVING count(*)>1) order by bm1.transferDate"
//            ),
//            @NamedQuery(
//                    name = "findBMOutgoingPaymentByPPATD",
//                    query = "select bm from BMOutgoingPaymentEntity bm where bm.transferDate>=:fromDate " +
//                            "and bm.transferDate<=:toDate " +
//                            "and bm.accountNumber = :accountNumber " +
//                            "and bm.pesel = :pesel " +
//                            "and (bm.permit = true or (bm.permit = false and bm.blueCashStatus = true))  order by bm.transferDate"
//            ),
//            @NamedQuery(
//                    name = "findBMOutgoingPaymentByBusinessKey",
//                    query = "select bp from BMOutgoingPaymentEntity bp where bp.transactionId = :businessKey " +
//                            "and bp.permit = true and bp.paymentTypeEntity.id in :typeList order by bp.transferDate"
//            )
    })
    @Table(name = "bm_outgoing_payment")
    public class BMOutgoingPaymentEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name="id", unique=true, nullable=false)
        private Long id;
        @Column(name = "amount")
        private Double amount;
//        @ManyToOne(fetch = FetchType.LAZY)
//        private LegalEntity legalEntity;
        @Column(name = "legalentity_id")
        private Long legalEntity;

        @Column(name = "client_account")
        private String accountNumber;
        @Column(name = "recipient_name")
        private String recipientName;
        @Column(name = "create_date")
        @Temporal(TemporalType.DATE)
        private Calendar createDate;
        @Column(name = "transfer_date")
        @Temporal(TemporalType.DATE)
        private Calendar transferDate;
        @Column(name = "status")
        private Boolean status;
        @Column(name = "comment")
        private String comment;
        @Column(name = "address_1")
        private String address1;
        @Column(name = "address_2")
        private String address2;
        @Column(name = "transaction_id")
        private String transactionId;

//        @ManyToOne(fetch = FetchType.LAZY)
//        private Purpose purpose;
        @Column(name = "purpose_id")
        private Long purpose;

        @Column(name = "permit",nullable = false, columnDefinition = "boolean default false")
        private Boolean permit;
        @Column(name = "blue_cash_status")
        private Boolean blueCashStatus;
        @Column(name = "fee")
        private Integer fee;
        @Column(name = "transfer_app_id")
        private Long transferAppId;
        @Column(name = "in_progress",nullable = false, columnDefinition = "boolean default false")
        private Boolean inProgress =false;
        @Column(name = "deal_no")
        private String dealNo;

//        @ManyToOne(fetch = FetchType.LAZY)
//        private PaymentTypeEntity paymentTypeEntity;
        @Column(name = "paymenttypeentity_id")
        private Long paymentType;

        @Column(name = "pesel")
        private String pesel;
        @Column(name = "create_date_time")
        @Temporal(TemporalType.TIMESTAMP)
        private Calendar createDateTime;
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public Double getAmount() {
            return amount;
        }
        public void setAmount(Double amount) {
            this.amount = amount;
        }
//        public LegalEntity getLegalEntity() {
//            return legalEntity;
//        }
//        public void setLegalEntity(LegalEntity legalEntity) {
//            this.legalEntity = legalEntity;
//        }


        public Long getLegalEntity() {
            return legalEntity;
        }

        public void setLegalEntity(Long legalEntity) {
            this.legalEntity = legalEntity;
        }

        public String getAccountNumber() {
            return accountNumber;
        }
        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }
        public String getRecipientName() {
            return recipientName;
        }
        public void setRecipientName(String recipientName) {
            this.recipientName = recipientName;
        }
        public Calendar getCreateDate() {
            return createDate;
        }
        public void setCreateDate(Calendar createDate) {
            this.createDate = createDate;
        }
        public Calendar getTransferDate() {
            return transferDate;
        }
        public void setTransferDate(Calendar transferDate) {
            this.transferDate = transferDate;
        }
        public Boolean getStatus() {
            return status;
        }
        public void setStatus(Boolean status) {
            this.status = status;
        }
        public String getComment() {
            return comment;
        }
        public void setComment(String comment) {
            this.comment = comment;
        }
        public String getAddress1() {
            return address1;
        }
        public void setAddress1(String address1) {
            this.address1 = address1;
        }
        public String getAddress2() {
            return address2;
        }
        public void setAddress2(String address2) {
            this.address2 = address2;
        }
        public String getTransactionId() {
            return transactionId;
        }
        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }
//        public Purpose getPurpose() {
//            return purpose;
//        }
//        public void setPurpose(Purpose purpose) {
//            this.purpose = purpose;
//        }

        public Long getPurpose() {
            return purpose;
        }

        public void setPurpose(Long purpose) {
            this.purpose = purpose;
        }

        public Boolean getPermit() {
            return permit;
        }
        public void setPermit(Boolean permit) {
            this.permit = permit;
        }
        public Boolean getBlueCashStatus() {
            return blueCashStatus;
        }
        public void setBlueCashStatus(Boolean blueCashStatus) {
            this.blueCashStatus = blueCashStatus;
        }
        public Integer getFee() {
            return fee;
        }
        public void setFee(Integer fee) {
            this.fee = fee;
        }
        public Long getTransferAppId() {
            return transferAppId;
        }
        public void setTransferAppId(Long transferAppId) {
            this.transferAppId = transferAppId;
        }
        public Boolean getInProgress() {
            return inProgress;
        }
        public void setInProgress(Boolean inProgress) {
            this.inProgress = inProgress;
        }
        public String getDealNo() {
            return dealNo;
        }
        public void setDealNo(String dealNo) {
            this.dealNo = dealNo;
        }
//        public PaymentTypeEntity getPaymentTypeEntity() {
//            return paymentTypeEntity;
//        }
//        public void setPaymentTypeEntity(PaymentTypeEntity paymentTypeEntity) {
//            this.paymentTypeEntity = paymentTypeEntity;
//        }

        public Long getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(Long paymentType) {
            this.paymentType = paymentType;
        }

        public String getPesel() {
            return pesel;
        }
        public void setPesel(String pesel) {
            this.pesel = pesel;
        }
        public Calendar getCreateDateTime() {
            return createDateTime;
        }
        public void setCreateDateTime(Calendar createDateTime) {
            this.createDateTime = createDateTime;
        }
        //    @Override
//    public String toString() {
//        return "BMOutgoingPaymentEntity{" +
//                "id=" + id +
//                ", amount=" + amount +
//                ", legalEntity=" + legalEntity +
//                ", accountNumber='" + accountNumber + '\'' +
//                ", recipientName='" + recipientName + '\'' +
//                ", createDate=" + createDate +
//                ", transferDate=" + transferDate +
//                ", status=" + status +
//                ", comment='" + comment + '\'' +
//                ", address1='" + address1 + '\'' +
//                ", address2='" + address2 + '\'' +
//                ", transactionId='" + transactionId + '\'' +
//                ", purpose=" + purpose +
//                ", permit=" + permit +
//                ", blueCashStatus=" + blueCashStatus +
//                ", fee=" + fee +
//                ", transferAppId=" + transferAppId +
//                ", inProgress=" + inProgress +
//                ", dealNo='" + dealNo + '\'' +
//                '}';
//    }
    }
//}
