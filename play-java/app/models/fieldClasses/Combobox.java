package models.fieldClasses;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chester on 25.11.2016.
 */
@Entity
public class Combobox extends ResponseField {
    @ElementCollection
    private List<String> comboboxOptions = new ArrayList<String>();

    @Column
    private String currentOption;

    public Combobox(){
        this.setFieldType(FieldType.COMBOBOX);
    }

    public void setComboboxOptions(List<String> optionList){
        comboboxOptions.addAll(optionList);
    }
    public void setCurrentOption(String option){
        currentOption = option;
    }
    public List<String> getComboboxOptions(){
        return comboboxOptions;
    }
    public String getCurrentOption(){
        return currentOption;
    }
    public String toString(){
        StringBuilder sbr = new StringBuilder();
        sbr.append(this.getNameField()+'\t');
        sbr.append((this.getReqirency())?("Required"+'\t'):("NOT Required"+'\t'));
        sbr.append((this.getActvity())?("Active"+'\t'):("NOT Active"+'\t'));
        sbr.append("Options: "+comboboxOptions.toString()+'\t'+"Current value: "+currentOption);
        return sbr.toString();
    }
}
