package sketch.findusonweb.Pojo;

/**
 * Created by developer on 8/5/18.
 */

public class ItemCity{  String text;
    Integer imageId;
public ItemCity(String text, Integer imageId){
        this.text=text;
        this.imageId=imageId;
        }

public String getText(){
        return text;
        }

public Integer getImageId(){
        return imageId;
        }
        }