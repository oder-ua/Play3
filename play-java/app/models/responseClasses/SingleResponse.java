package models.responseClasses;

import models.fieldClasses.ResponseField;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chester on 25.11.2016.
 */
@Entity
public class SingleResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private ResponseField feild;

    @ElementCollection
    private List<String> srvalues = new ArrayList<String>();

    public void setFeild(ResponseField field){
        this.feild=field;
    }
    public void setValues(List<String> values){
        this.srvalues.addAll(values);
    }
    public ResponseField getFeild(){
        return feild;
    }
    public List<String> getValues(){
        return srvalues;
    }
    public String toString(){
        return this.feild.toString();
    }
}
