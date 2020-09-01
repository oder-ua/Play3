package models.otherClasses;

import util.AdminValidator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Chester on 25.11.2016.
 */
@Entity
public class Admin{
    @Id
    private String login;

    @Column(unique = true)
    private String password;

    public Admin(){    }
    public void setLogin(String lg){
        login = lg;
    }
    public void setPassword(String psw){
        password = psw;
    }
    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }
    public String toString(){
        return login+" "+password;
    }

    public String validate(){
        return (AdminValidator.isValidAdmin(login,password))?(null):("Wrong login or password. Try again");
    }
}

