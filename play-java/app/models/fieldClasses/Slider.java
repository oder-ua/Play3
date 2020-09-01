package models.fieldClasses;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Chester on 25.11.2016.
 */
@Entity
public class Slider extends ResponseField {
    @Column(nullable = false)
    private int mnValue;

    @Column(nullable = false)
    private int mxValue;

    @Column(nullable = false)
    private int step;

    @Column(nullable = false)
    private int currentValue;

    public Slider(){
        this.setFieldType(FieldType.SLIDER);
    }

    public void setMinValue(int min){
        mnValue = min;
    }
    public void setMaxValue(int max){
        mxValue = max;
    }
    public void setStep(int stp){
        step = stp;
    }
    public void setCurrentValue(int cvalue){
        currentValue = cvalue;
    }
    public int getMinValue(){
        return mnValue;
    }
    public int getMaxValue(){
        return mxValue;
    }
    public int getStep(){
        return step;
    }
    public int getCurrentValue(){
        return currentValue;
    }
    public String toString(){
        StringBuilder sbr = new StringBuilder();
        sbr.append(this.getNameField()+'\t');
        sbr.append((this.getReqirency())?("Required"+'\t'):("NOT Required"+'\t'));
        sbr.append((this.getActvity())?("Active"+'\t'):("NOT Active"+'\t'));
        sbr.append("Options: min.value = "+Integer.toString(mnValue)+'\t'+"max.value = "+Integer.toString(mxValue)+'\t'
                +"step = "+Integer.toString(step)+'\t'+"Value: "+this.getCurrentValue());
        return sbr.toString();
    }
}
