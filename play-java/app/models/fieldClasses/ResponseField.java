package models.fieldClasses;

import javax.persistence.*;

/**
 * Created by Chester on 25.11.2016.
 * Абстракция поля,от которой наследуются все поля БД.Содержит базовые характеристики любого поля.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ResponseField {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected int id;

    @Column(unique = true)
    protected String fieldName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    protected FieldType fieldType;

    @Column(nullable = false)
    protected boolean isRequired=false;

    @Column(nullable = false)
    protected boolean isActive=false;

    public void setNameField(String name){
        fieldName = name;
    }
    public void setFieldType(FieldType ftype){
        fieldType = ftype;
    }
    public void setRequired(boolean req){
        isRequired = req;
    }
    public void setActive(boolean act){
        isActive = act;
    }
    public final void setFieldID(int id){ this.id=id; }
    public String getNameField(){
        return fieldName;
    }
    public FieldType getFieldType(){
        return fieldType;
    }
    public boolean getReqirency(){
        return isRequired;
    }
    public boolean getActvity(){
        return isActive;
    }
    public int getFieldID(){ return id; }
}
