package models.responseClasses;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Chester on 09.12.2016.
 */
@Entity
public class UserResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SingleResponse> responsesList = new ArrayList<SingleResponse>();

    public final void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public void addSingleResponse(SingleResponse sr){
        responsesList.add(sr);
    }
    public void removeSingleResponse(SingleResponse sr){
        if(!responsesList.isEmpty()){
            responsesList.remove(sr);
        }
    }
    public SingleResponse getSingleResponse(int i){
        return responsesList.get(i);
    }
    public void printUserResponse(){
        Stream<SingleResponse> stream = responsesList.stream();
        stream.forEach((n) -> {System.out.println(n.toString());});
    }
    public List<SingleResponse> getResponsesList(){
        return responsesList;
    }
}
