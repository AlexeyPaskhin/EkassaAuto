package com.ekassaauto.database.entities.risk;

import javax.persistence.*;

/**
 * Created by user on 29.09.2017.
 */
@Entity
@Table(name = "bodeals")
@NamedQueries({
        @NamedQuery(
                name = "getBoDealsByPesel",
                query = "select bde from BoDealsEntity bde where pesel = :pesel"
        )
})
public class BoDealsEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String pesel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
}
