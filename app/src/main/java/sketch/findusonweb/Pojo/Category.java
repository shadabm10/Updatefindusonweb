package sketch.findusonweb.Pojo;

/**
 * Created by developer on 14/5/18.
 */


public class Category  {

    String text;
    String Id;
    public Category(){
        this.text=text;
        this.Id=Id;
    }

    public String getText(String title){
        return text;
    }

    public String getIDId(){
        return Id;
    }
    public void setID(String Id) {
        this.Id = Id;
    }
    public void setText(String text) {
        this.text = text;
    }

}
