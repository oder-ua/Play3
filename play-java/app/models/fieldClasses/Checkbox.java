package models.fieldClasses;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chester on 25.11.2016.
 */
@Entity
public class Checkbox extends ResponseField {
    @ElementCollection
    private List<String> allValues = new ArrayList<String>();

    @ElementCollection
    private List<String> chosenValues = new ArrayList<String>();

    public Checkbox(){
        this.setFieldType(FieldType.CHECKBOX);
    }

    public void setAllValues(List<String> values){
        allValues.addAll(values);
    }
    public void setChosenValues(List<String> chosen){
        chosenValues.addAll(chosen);
    }
    public List<String> getAllValues(){
        return allValues;
    }
    public List<String> getChosenValues(){
        return chosenValues;
    }
    public String toString(){
        StringBuilder sbr = new StringBuilder();
        sbr.append(this.getNameField()+'\t');
        sbr.append((this.getReqirency())?("Required"+'\t'):("NOT Required"+'\t'));
        sbr.append((this.getActvity())?("Active"+'\t'):("NOT Active"+'\t'));
        sbr.append("Options: "+allValues.toString()+'\t'+"Chosen values: "+chosenValues.toString());
        return sbr.toString();
    }
}
