package util;

import models.otherClasses.Admin;
import play.db.jpa.JPAApi;

import javax.inject.Inject;

/**
 * Created by Chester on 23.02.2017.
 */
public class AdminValidator {
    private static JPAApi jpaApi;

    public JPAApi getJpaApi(){
        return jpaApi;
    }

    @Inject
    public AdminValidator(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public static boolean isValidAdmin(String login,String password){
        Admin model = new Admin();
        model.setLogin(login);
        model.setPassword(password);
        Admin cmp = jpaApi.em().find(Admin.class,login);
        return (model.equals(cmp))?(true):(false);
    }
}
