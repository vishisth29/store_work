import java.io.Serializable;

//Object is assumed to be person, each person has a unique id

//implements Serializable
//So can be made into a byte stream for saving in the HashMap value
public class object implements Serializable{


    public String name;
    public int id;

    public object(){
    }
    public object(String name, int id){
        this.name=name;
        this.id=id;
    }

}



