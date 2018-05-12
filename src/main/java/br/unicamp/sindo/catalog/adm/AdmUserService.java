package br.unicamp.sindo.catalog.adm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmUserService {

    @Autowired
    protected AdmUserRepository repository;

    public LoginResult validate(String user, String password) {
        List<AdmUserEntity> users = repository.findByEmailAndPassword(user, password);

        if (users == null || users.isEmpty()) {
            List<AdmUserEntity> emailUsers = repository.findByEmail(user);

            if (emailUsers == null || emailUsers.isEmpty()) {
                return LoginResult.USER_NOT_FOUND;
            } else {
                return LoginResult.INCORRECT_PASSWORD;
            }
        } else {
            return LoginResult.OK;
        }
    }

    public enum LoginResult {
        OK, USER_NOT_FOUND, INCORRECT_PASSWORD, OTHER_PROBLEM
    }
}
