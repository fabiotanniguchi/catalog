package br.unicamp.sindo.catalog.adm;

import br.unicamp.sindo.catalog.utils.repository.BaseEntity;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "adm_user")
public class AdmUserEntity extends BaseEntity {

    private String email;
    private String password;

    @Id
    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
