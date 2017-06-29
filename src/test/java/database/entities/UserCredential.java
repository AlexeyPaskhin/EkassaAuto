package database.entities;

import javax.persistence.*;

/**
 * Created by user on 15.06.2017.
 */
@Entity
@Table(name = "userscredentials")
public class UserCredential {
    @Id
    @GeneratedValue
    private Long id;
    private String password;
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plain_user_id",nullable = false)
    private PlainUserEntity plainUserEntity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public PlainUserEntity getPlainUserEntity() {
        return plainUserEntity;
    }

    public void setPlainUserEntity(PlainUserEntity plainUserEntity) {
        this.plainUserEntity = plainUserEntity;
    }

    @Override
    public String toString() {
        return "UserCredential{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                '}';
    }
}
