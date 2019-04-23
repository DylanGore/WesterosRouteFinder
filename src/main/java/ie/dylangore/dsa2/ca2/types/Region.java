package ie.dylangore.dsa2.ca2.types;

import java.io.Serializable;

public class Region implements Serializable {

    private String name;

    public Region(String name){
        this.name = name;
        System.out.println("New Region: " + this.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return this.name;
    }
}
