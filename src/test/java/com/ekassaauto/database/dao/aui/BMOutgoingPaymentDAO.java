package com.ekassaauto.database.dao.aui;

import com.ekassaauto.database.entities.aui.BMOutgoingPaymentEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Calendar;

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
}
