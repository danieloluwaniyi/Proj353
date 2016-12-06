package model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class ImagesView {
     
    private List<String> images;
    private List<String> images2;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            images.add("ad1.gif");
            images.add("ad2.jpg");
            images.add("ad3.jpg");
        }
        images2 = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            images2.add("ad4.jpg");
            images2.add("ad5.jpg");
            images2.add("ad6.jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
    
    public List<String> getImages2() {
        return images2;
    }
    
    
}