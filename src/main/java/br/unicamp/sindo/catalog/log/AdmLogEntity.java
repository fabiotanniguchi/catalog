package br.unicamp.sindo.catalog.log;

import br.unicamp.sindo.catalog.utils.repository.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "log_adm")
public class AdmLogEntity extends BaseEntity {

    private String userEmail;
    private String actionDescription;

    @Column(name = "user_email", nullable = false)
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Column(name = "action_desc")
    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }
}
