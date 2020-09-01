package models.fieldClasses;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Chester on 25.11.2016.
 */
@Entity
public class SingleLineText extends ResponseField {
    @Column(nullable = false,length = 31)
    private String textValue="";

    public SingleLineText(){
        this.setFieldType(FieldType.SINGLELINETEXT);
    }
    public void setTextValue(String text){
        textValue = text;
    }
    public String getTextValue(){
        return textValue;
    }
    public String toString(){
        StringBuilder sbr = new StringBuilder();
        sbr.append(this.getNameField()+'\t');
        sbr.append((this.getReqirency())?("Required"+'\t'):("NOT Required"+'\t'));
        sbr.append((this.getActvity())?("Active"+'\t'):("NOT Active"+'\t'));
        sbr.append("Value: "+this.getTextValue());
        return sbr.toString();
    }
}
