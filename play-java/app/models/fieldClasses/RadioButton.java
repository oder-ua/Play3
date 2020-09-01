package models.fieldClasses;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chester on 25.11.2016.
 */
@Entity
public class RadioButton extends ResponseField {
    @ElementCollection
    private List<String> rbOptions = new ArrayList<String>();

    @Column
    private String chosedValue;

    public RadioButton(){
        this.setFieldType(FieldType.RADIOBUTTON);
    }

    public void setRbOptions(List<String> options){
        rbOptions.addAll(options);
    }
    public void setChosedValue(String value){
        chosedValue = value;
    }
    public List<String> getRbOptions(){
        return rbOptions;
    }
    public String getChosedValue(){
        return chosedValue;
    }
    public String toString(){
        StringBuilder sbr = new StringBuilder();
        sbr.append(this.getNameField()+'\t');
        sbr.append((this.getReqirency())?("Required"+'\t'):("NOT Required"+'\t'));
        sbr.append((this.getActvity())?("Active"+'\t'):("NOT Active"+'\t'));
        sbr.append("Options: "+rbOptions.toString()+'\t'+"Chosen value: "+chosedValue);
        return sbr.toString();
    }
}
