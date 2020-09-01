package controllers;

import models.fieldClasses.*;
import models.responseClasses.SingleResponse;
import models.responseClasses.UserResponse;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import javax.inject.Inject;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Данный контроллер отвечает за взаимодействие респонсов с БД
 * Содержит в себе экшн-методы: создания респонса на основании имеющихся полей в бд и их атрибута isActive,
 * добавления респонса,а также отображения всех респонсов в БД
 */
public class ResponsesController extends Controller {
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public ResponsesController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional
    public Result addUserResponse(){
        UserResponse response = new UserResponse();
        Map<String,String> responsedata = formFactory.form().bindFromRequest().get().getData();//значения всех полей респонса
        Map<String,List<String>> checkboxes = new HashMap<String,List<String>>();//значения всех чекбоксов.этот "костыль" связан с реализацией чекбокса
        responsedata.entrySet().stream().forEach((x) -> {
            if(x.getKey().contains("[")){
                String realkey = x.getKey().substring(0,x.getKey().indexOf('['));
                List<String> tmp = (checkboxes.get(realkey)==null)?(new ArrayList<String>()):(checkboxes.get(realkey));
                tmp.add(x.getValue());
                checkboxes.put(realkey,tmp);
            }
        });
        responsedata.entrySet().stream().filter((x) -> !x.getKey().contains("[")).forEach((n) -> {
            ResponseField tmpField = jpaApi.em().createQuery("from ResponseField where id=" + n.getKey(), ResponseField.class).getSingleResult();
            SingleResponse tmpSingleResponse = new SingleResponse();
            tmpSingleResponse.setFeild(tmpField);
            tmpSingleResponse.setValues(Arrays.asList(URLDecoder.decode(n.getValue())).stream().filter(x -> !x.equals("")).collect(Collectors.toList()));
            response.addSingleResponse(tmpSingleResponse);
            jpaApi.em().merge(tmpSingleResponse);
        });
        if((checkboxes!=null)&&(!checkboxes.isEmpty())){
            checkboxes.entrySet().forEach((p) -> {
                ResponseField tmpField = jpaApi.em().createQuery("from ResponseField where id=" + p.getKey(), ResponseField.class).getSingleResult();
                SingleResponse tmpSingleResponse = new SingleResponse();
                tmpSingleResponse.setFeild(tmpField);
                tmpSingleResponse.setValues(p.getValue());
                response.addSingleResponse(tmpSingleResponse);
                jpaApi.em().merge(tmpSingleResponse);
            });
        }
        jpaApi.em().merge(response);
        return redirect(routes.AdminController.index());
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    public Result showAllResponses(){
        List<UserResponse> responses = jpaApi.em().createQuery("from UserResponse").getResultList();
        return (!responses.isEmpty())?(ok(views.html.responses.render(responses.get(0), responses))):(internalServerError("No responses found"));
    }

    @Transactional
    public Result createUserResponse(){
        //fields of userreponse
        List<ResponseField> fields = jpaApi.em().createQuery("from ResponseField where isActive=true").getResultList();
        DynamicForm dnm = formFactory.form();
        return (!fields.isEmpty())?(ok(views.html.response_collector.render(fields,dnm))):(internalServerError("No active fields to fill"));
    }
}
