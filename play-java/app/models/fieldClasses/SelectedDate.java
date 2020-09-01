package models.fieldClasses;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chester on 25.11.2016.
 */
@Entity
public class SelectedDate extends ResponseField {
    @Column
    @Temporal(TemporalType.DATE)
    private Date chosedDate;

    public SelectedDate(){
        this.setFieldType(FieldType.DATE);
    }

    public void setChosedDate(Date date){
        chosedDate = date;
    }
    public Date getChosedDate(){
        return chosedDate;
    }
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
        StringBuilder sbr = new StringBuilder();
        sbr.append(this.getNameField()+'\t');
        sbr.append((this.getReqirency())?("Required"+'\t'):("NOT Required"+'\t'));
        sbr.append((this.getActvity())?("Active"+'\t'):("NOT Active"+'\t'));
        sbr.append("Value: "+sdf.format(chosedDate));
        return sbr.toString();
    }
    public String dateToString(){
        SimpleDateFormat sdate = new SimpleDateFormat("dd.MM.YYYY");
        return (String)sdate.format(this.chosedDate);
    }
}
