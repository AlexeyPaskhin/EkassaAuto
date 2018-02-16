package com.ekassaauto.database.dao.aui;

import com.ekassaauto.database.entities.aui.BMOutgoingPaymentEntity;
import com.ekassaauto.database.entities.aui.SuperDealEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Calendar;

import static com.ekassaauto.Registration.*;

/**
 * Created by user on 24.11.2017.
 */
public class BMOutgoingPaymentDAO {
    private EntityManager entityManager;

    public BMOutgoingPaymentDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createDuplicateBMTransaction(Long id) {

//        while (Calendar.getInstance().before("2017-11-24 16:20:00")) {
        entityManager.getTransaction().begin();
        TypedQuery<BMOutgoingPaymentEntity> query = entityManager.createNamedQuery(
                "getBMTransactionById", BMOutgoingPaymentEntity.class);
        query.setParameter("id", id);
        query.setMaxResults(1);
        BMOutgoingPaymentEntity bm = query.getSingleResult();
        BMOutgoingPaymentEntity duplicateBM = new BMOutgoingPaymentEntity();

        duplicateBM.setAccountNumber(bm.getAccountNumber());
        duplicateBM.setAddress1("");
        duplicateBM.setAddress2("");
        duplicateBM.setAmount(bm.getAmount());
        duplicateBM.setBlueCashStatus(false);
        duplicateBM.setPermit(true);
        duplicateBM.setRecipientName(bm.getRecipientName());
        duplicateBM.setStatus(false);
        duplicateBM.setTransactionId(bm.getTransactionId());
        duplicateBM.setTransferDate(bm.getTransferDate());
        duplicateBM.setLegalEntity(bm.getLegalEntity());
        duplicateBM.setPurpose(bm.getPurpose());
        duplicateBM.setInProgress(false);
        duplicateBM.setPaymentType(bm.getPaymentType());
        duplicateBM.setPesel(bm.getPesel());
        duplicateBM.setCreateDate(Calendar.getInstance());
        duplicateBM.setCreateDateTime(Calendar.getInstance());

        entityManager.persist(duplicateBM);
        entityManager.getTransaction().commit();
    }

    public void createBMTransactionsWithDifferentPesel() {
        String uniqueTransactionId = String.valueOf(Calendar.getInstance().getTimeInMillis());

        for (int i = 1; i <= 15; i++) {
            //todo add an entry at the super_deal table with responsive start amount sum
            Calendar timeMinus15Min = Calendar.getInstance();
            timeMinus15Min.add(Calendar.MINUTE, -15);  //чтоб обойти правило, что обрабатываются только транзакции, созданные ранее чем за 10 мин
            entityManager.getTransaction().begin();
//            SuperDealEntity superDealEntity = new SuperDealEntity();
//            System.out.println(superDealEntity.getId());  раз purpose_id = 61354(пролонг балуна) и
//            paymenttypeentity_id = 11, можно суперлил не создавать, будет выполнятся транзакция

            BMOutgoingPaymentEntity bmTransaction = new BMOutgoingPaymentEntity();

            bmTransaction.setAccountNumber(bankAccount);
            bmTransaction.setAddress1("");
            bmTransaction.setAddress2("");
            bmTransaction.setAmount(200D);
            bmTransaction.setBlueCashStatus(false);
            bmTransaction.setPermit(true);
            bmTransaction.setRecipientName("kek lol");
            bmTransaction.setStatus(false);
            bmTransaction.setTransactionId(uniqueTransactionId);
            bmTransaction.setTransferDate(Calendar.getInstance());
            bmTransaction.setLegalEntity(29L);
            bmTransaction.setPurpose(61354L);
            bmTransaction.setInProgress(false);
            bmTransaction.setPaymentType(11L);
            bmTransaction.setPesel(pesel + i);
            bmTransaction.setCreateDate(Calendar.getInstance());
            bmTransaction.setCreateDateTime(timeMinus15Min);

            entityManager.persist(bmTransaction);
            entityManager.getTransaction().commit();
        }
    }
}
