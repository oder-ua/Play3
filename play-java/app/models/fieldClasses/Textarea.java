package models.fieldClasses;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Chester on 25.11.2016.
 */
@Entity
public class Textarea extends ResponseField {
    @Column(nullable = false,length = 511)
    private String textareaValue="";

    public Textarea(){
        this.setFieldType(FieldType.TEXTAREA);
    }
    public void setTextareaValue(String someText){
        textareaValue = someText;
    }
    public String getTextareaValue(){
        return textareaValue;
    }
    public String toString(){
        StringBuilder sbr = new StringBuilder();
        sbr.append(this.getNameField()+'\t');
        sbr.append((this.getReqirency())?("Required"+'\t'):("NOT Required"+'\t'));
        sbr.append((this.getActvity())?("Active"+'\t'):("NOT Active"+'\t'));
        sbr.append("Value: "+this.getTextareaValue());
        return sbr.toString();
    }
}
