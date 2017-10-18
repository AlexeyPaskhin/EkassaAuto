package com.ekassaauto.database.entities.aui;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by user on 28.09.2017.
 */
@Entity
@Table(name = "cpa_client_cashe")
@NamedQueries({
        @NamedQuery(
                name = "deleteAllCache",
                query = "delete from CpaClientCasheEntity cache"
        )
})
public class CpaClientCasheEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "client_cache_hash")
    private String clientCacheHash;
    @Temporal(TemporalType.DATE)
    @Column(name = "request_date")
    private Calendar requestDate;
}
