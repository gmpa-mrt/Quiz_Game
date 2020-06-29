package network;

public class Theme {

    //Fields
    private String name;
    private Integer id;

    //Constructor
    public  Theme(Integer id, String name) {
        this.name = name;
    }

    //Getter&Setter


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}

