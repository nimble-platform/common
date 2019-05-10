package eu.nimble.utility.serialization;

public class JsonSerializer {

    private String value;

    private boolean isFirstProperty;

    public JsonSerializer(){
        value = "";
        isFirstProperty = true;
    }

    public void put(String propertyName,String propertyValue){
        if(!isFirstProperty)
            value += ",";
        else
            isFirstProperty = false;
        value += "\""+propertyName+"\""+":"+propertyValue;
    }

    public String toString(){
        return "{"+value+"}";
    }
}