package controllers;

import models.fieldClasses.ResponseField;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import models.otherClasses.*;
import play.mvc.Security;
import security.Secured;

import javax.inject.Inject;
import java.util.List;

/**
 * Данный контроллер содержит в себе аутентификацию админа,
 * лог-аут админа,точку входа
 */
public class AdminController extends Controller {

    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public AdminController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    public Result index() {
        return ok(views.html.homepage.render());
    }

    @Transactional
    public Result adminAuth(){
        //исходные значения,которые должны быть в БД.этот блок создан чисто чтобы не заносить данные в БД перед запуском приложения
        {
            Admin admin1 = new Admin();
            admin1.setLogin("admin1");
            admin1.setPassword("qwerty");
            jpaApi.em().merge(admin1);
            Admin admin2 = new Admin();
            admin2.setLogin("admin2");
            admin2.setPassword("qawsed");
            jpaApi.em().merge(admin2);
        }
        DynamicForm adminForm = formFactory.form().bindFromRequest();
        Admin model = new Admin();
        model.setLogin(adminForm.field("login").value());
        model.setPassword(adminForm.field("password").value());
        Admin dbAdmin = jpaApi.em().find(Admin.class,model.getLogin());
        if((dbAdmin!=null)&&(dbAdmin.getPassword().equals(model.getPassword()))){
            session().clear();
            session("login",model.getLogin());
            List<ResponseField> allFields = jpaApi.em().createQuery("from ResponseField").getResultList();
            return ok(views.html.fields.render(allFields));
        }
        return redirect(routes.AdminController.index());
    }

    @Security.Authenticated(Secured.class)
    public Result logout(){
        session().clear();
        return redirect(routes.AdminController.index());
    }
}