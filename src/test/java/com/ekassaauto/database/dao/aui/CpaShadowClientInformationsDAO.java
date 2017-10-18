package com.ekassaauto.database.dao.aui;

import com.ekassaauto.database.entities.aui.CpaShadowClientInformationsEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by user on 24.08.2017.
 */
public class CpaShadowClientInformationsDAO {
    private EntityManager entityManager;

    public CpaShadowClientInformationsDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void setFieldsOfCpaEntityForSuccessfulPdl(CpaShadowClientInformationsEntity entity) {
        entityManager.getTransaction().begin();
        if (!entity.isEnable()) entity.setEnable(true);
        if (!entity.isAutoLogin()) entity.setAutoLogin(true);
        entityManager.getTransaction().commit();
    }

    public CpaShadowClientInformationsEntity getExistingCpaClientInformationsEntity() {
        TypedQuery<CpaShadowClientInformationsEntity> query = entityManager.createNamedQuery(
                "getExistingUsersCpaEntitiesWithoutRejectAndWithSkipPersonalData", CpaShadowClientInformationsEntity.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    public CpaShadowClientInformationsEntity getCpaClientById(Long id) {
        TypedQuery<CpaShadowClientInformationsEntity> query = entityManager.createNamedQuery("getCpaClientById",
                CpaShadowClientInformationsEntity.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

}
