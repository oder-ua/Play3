package controllers;

import models.fieldClasses.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.LegacyWebSocket;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.*;
import play.mvc.Security;
import play.mvc.WebSocket;
import security.Secured;
import javax.inject.Inject;
import java.util.*;

/**
 * Данный контроллер отвечает за взаимодействие полей с БД.
 * Содержит экшн-методы: добавления поля,редактирования поля,
 * удаления поля, а также отображения одного конкретного поля и всех полей в БД.
 * Также содержит в себе всю логику взаимодействия веб-сокета
 */
public class FieldController extends Controller {

    private final FormFactory formFactory;
    private final JPAApi jpaApi;
    private Set<WebSocket.Out<String>> socketsSet = new HashSet<>();

    @Inject
    public FieldController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    public Result addField() {
        //используется динамическая форма,т.к. неизвестно,какое поле будет добавляться
        DynamicForm requested = formFactory.form().bindFromRequest();
        String fieldtype = requested.bindFromRequest().field("fieldType").value().toUpperCase().replaceAll(" ","");
        String fieldname = requested.bindFromRequest().field("fieldName").value();
        String active = requested.bindFromRequest().field("isActive").value();
        String requireness = requested.bindFromRequest().field("isRequired").value();
        List<String> listOfOptions= new ArrayList<String>();
        String options = requested.bindFromRequest().field("optionsList").value();
        if((options!=null)&&(!options.isEmpty())) {
            String[] tmp = options.split(System.lineSeparator());
            for (String x : tmp) {
                listOfOptions.add(x);
            }
        }
        switch (fieldtype) {
            case "CHECKBOX":
                Checkbox chbx = new Checkbox();
                chbx.setNameField(fieldname);
                chbx.setActive((active!=null)?(true):(false));
                chbx.setRequired((requireness!=null)?(true):(false));
                chbx.setAllValues((!listOfOptions.isEmpty())?(listOfOptions):(null));
                jpaApi.em().merge(chbx);
                break;
            case "COMBOBOX":
                Combobox cmbx = new Combobox();
                cmbx.setNameField(fieldname);
                cmbx.setActive((active!=null)?(true):(false));
                cmbx.setRequired((requireness!=null)?(true):(false));
                cmbx.setComboboxOptions((!listOfOptions.isEmpty())?(listOfOptions):(null));
                jpaApi.em().merge(cmbx);
                break;
            case "RADIOBUTTON":
                RadioButton rb = new RadioButton();
                rb.setNameField(fieldname);
                rb.setActive((active!=null)?(true):(false));
                rb.setRequired((requireness!=null)?(true):(false));
                rb.setRbOptions((!listOfOptions.isEmpty())?(listOfOptions):(null));
                jpaApi.em().merge(rb);
                break;
            case "DATE":
                SelectedDate sdt = new SelectedDate();
                sdt.setNameField(fieldname);
                sdt.setActive((active!=null)?(true):(false));
                sdt.setRequired((requireness!=null)?(true):(false));
                jpaApi.em().merge(sdt);
                break;
            case "SINGLELINETEXT":
                SingleLineText slt = new SingleLineText();
                slt.setNameField(fieldname);
                slt.setActive((active!=null)?(true):(false));
                slt.setRequired((requireness!=null)?(true):(false));
                jpaApi.em().merge(slt);
                break;
            case "SLIDER":
                Slider slider = new Slider();
                slider.setNameField(fieldname);
                slider.setActive((active!=null)?(true):(false));
                slider.setRequired((requireness!=null)?(true):(false));
                slider.setMaxValue(Integer.parseInt(requested.bindFromRequest().field("mxValue").value()));
                slider.setMinValue(Integer.parseInt(requested.bindFromRequest().field("mnValue").value()));
                slider.setStep(Integer.parseInt(requested.bindFromRequest().field("step").value()));
                slider.setCurrentValue(Integer.parseInt(requested.bindFromRequest().field("currentValue").value()));
                jpaApi.em().merge(slider);
                break;
            case "TEXTAREA":
                Textarea txa = new Textarea();
                txa.setNameField(fieldname);
                txa.setActive((active!=null)?(true):(false));
                txa.setRequired((requireness!=null)?(true):(false));
                jpaApi.em().merge(txa);
                break;
        }
        jpaApi.em().createQuery("delete from UserResponse").executeUpdate();
        jpaApi.em().createNativeQuery("DELETE FROM singleresponse_srvalues").executeUpdate();
        jpaApi.em().createQuery("delete from SingleResponse").executeUpdate();
        return redirect(routes.FieldController.showAllFields());
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    public Result editField(int fieldid) {
        ResponseField model = jpaApi.em().createQuery("from ResponseField where id="+fieldid,ResponseField.class).getSingleResult();
        DynamicForm requested = formFactory.form().bindFromRequest();
        String fname = requested.bindFromRequest().field("fieldName").value();
        String activeness = requested.bindFromRequest().field("isActive").value();
        String requireness = requested.bindFromRequest().field("isRequired").value();
        List<String> listOfOptions= new ArrayList<String>();
        String options = requested.bindFromRequest().field("optionsList").value();
        if((options!=null)&&(!options.isEmpty())) {
            String[] tmp = options.split(System.lineSeparator());
            for (String x : tmp) {
                listOfOptions.add(x);
            }
        }
        switch (model.getFieldType()){
            case CHECKBOX:
                Checkbox chbx = jpaApi.em().find(Checkbox.class,fieldid);
                chbx.setNameField(fname);
                chbx.setActive((activeness!=null)?(true):(false));
                chbx.setRequired((requireness!=null)?(true):(false));
                chbx.setAllValues((!listOfOptions.isEmpty())?(listOfOptions):(null));
                jpaApi.em().merge(chbx);
                break;
            case COMBOBOX:
                Combobox cmbx = jpaApi.em().find(Combobox.class,fieldid);
                cmbx.setNameField(fname);
                cmbx.setActive((activeness!=null)?(true):(false));
                cmbx.setRequired((requireness!=null)?(true):(false));
                cmbx.setComboboxOptions((!listOfOptions.isEmpty())?(listOfOptions):(null));
                jpaApi.em().merge(cmbx);
                break;
            case RADIOBUTTON:
                RadioButton rb = jpaApi.em().find(RadioButton.class,fieldid);
                rb.setNameField(fname);
                rb.setActive((activeness!=null)?(true):(false));
                rb.setRequired((requireness!=null)?(true):(false));
                rb.setRbOptions((!listOfOptions.isEmpty())?(listOfOptions):(null));
                jpaApi.em().merge(rb);
                break;
            case DATE:
                SelectedDate sdt = jpaApi.em().find(SelectedDate.class,fieldid);
                sdt.setNameField(fname);
                sdt.setActive((activeness!=null)?(true):(false));
                sdt.setRequired((requireness!=null)?(true):(false));
                jpaApi.em().merge(sdt);
                break;
            case SINGLELINETEXT:
                SingleLineText slt = jpaApi.em().find(SingleLineText.class,fieldid);
                slt.setNameField(fname);
                slt.setActive((activeness!=null)?(true):(false));
                slt.setRequired((requireness!=null)?(true):(false));
                jpaApi.em().merge(slt);
                break;
            case SLIDER:
                Slider slider = jpaApi.em().find(Slider.class,fieldid);
                slider.setNameField(fname);
                slider.setActive((activeness!=null)?(true):(false));
                slider.setRequired((requireness!=null)?(true):(false));
                slider.setMaxValue(Integer.parseInt(requested.bindFromRequest().field("mxValue").value()));
                slider.setMinValue(Integer.parseInt(requested.bindFromRequest().field("mnValue").value()));
                slider.setStep(Integer.parseInt(requested.bindFromRequest().field("step").value()));
                slider.setCurrentValue(Integer.parseInt(requested.bindFromRequest().field("currentValue").value()));
                jpaApi.em().merge(slider);
                break;
            case TEXTAREA:
                Textarea txa = jpaApi.em().find(Textarea.class,fieldid);
                txa.setNameField(fname);
                txa.setActive((activeness!=null)?(true):(false));
                txa.setRequired((requireness!=null)?(true):(false));
                jpaApi.em().merge(txa);
                break;
        }
        jpaApi.em().createQuery("delete from UserResponse").executeUpdate();
        jpaApi.em().createNativeQuery("DELETE FROM singleresponse_srvalues").executeUpdate();
        jpaApi.em().createQuery("delete from SingleResponse").executeUpdate();
        return redirect(routes.FieldController.showAllFields());
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    public Result showField(String cmd){
        if(cmd.contains("edit")){
            int id = Integer.parseInt(cmd.subSequence(cmd.indexOf('(')+1,cmd.indexOf(')')).toString());
            ResponseField tmpfield = jpaApi.em().createQuery("from ResponseField where id="+id,ResponseField.class).getSingleResult();
            return ok(views.html.create_edit.render(cmd,tmpfield.getNameField(),tmpfield.getActvity(),tmpfield.getReqirency(),tmpfield.getFieldType()));
        }
        return ok(views.html.create_edit.render(cmd,"",false,false,null));
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    public Result showAllFields(){
        List<ResponseField> allFields = jpaApi.em().createQuery("from ResponseField").getResultList();
        return ok(views.html.fields.render(allFields));
    }

    @Transactional
    @Security.Authenticated(Secured.class)
    public Result deleteField(int id){
        String query = "delete from ResponseField where id="+id;
        jpaApi.em().createQuery(query).executeUpdate();
        jpaApi.em().createQuery("delete from UserResponse").executeUpdate();
        jpaApi.em().createNativeQuery("DELETE FROM singleresponse_srvalues").executeUpdate();
        jpaApi.em().createQuery("delete from SingleResponse").executeUpdate();
        return redirect(routes.FieldController.showAllFields());
    }

    @Transactional
    public LegacyWebSocket<String> fieldsWebSocket(){
        return WebSocket.whenReady((in,out) -> {
            socketsSet.add(out);
            in.onMessage((String message) -> {
                socketsSet.forEach((x) -> {
                    if(message.contains("new response:")){
                        StringBuilder tmpsbr = new StringBuilder();
                        tmpsbr.append("<tr>");
                        Map<String,List<String>> responses = new HashMap<String,List<String>>();//содержит все значения конкретного поля
                        List<String> order = new ArrayList<String>();//порядок полей в респонсе
                        Arrays.asList(message.replace("new response:","").replaceAll("[\\n\\t;]",",").split("&")).stream().forEach((z) -> {
                            String realkey = z.substring(0,z.indexOf('='));
                            if(!order.contains(realkey)){
                                order.add(realkey);
                            }
                            List<String> tmp = (responses.get(realkey)==null)?(new ArrayList<String>()):(responses.get(realkey));
                            tmp.add(z.substring(z.indexOf('=')+1));
                            responses.put(realkey,tmp);
                        });
                        order.forEach((c) -> {
                            tmpsbr.append("<td>"+responses.get(c).toString()+"</td>");
                        });
                        tmpsbr.append("</tr>");
                        x.write("new response:"+tmpsbr.toString());
                    }
                    if(message.equals("field change")){
                        x.write("update");
                    }
                    x.write("fields count:"+jpaApi.withTransaction((entityManager) -> {
                        return entityManager.createQuery("select count(*) from ResponseField").getSingleResult().toString();}));
                    x.write("responses count:"+jpaApi.withTransaction((entityManager -> {
                        return entityManager.createQuery("select count(*) from UserResponse").getSingleResult().toString();}))
                    );
                });
            });
            in.onClose(() -> socketsSet.remove(out));
        });
    }

}
