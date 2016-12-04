package model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class ImagesView {
     
    private List<String> images;
     
    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            images.add("ad1.gif");
            images.add("ad2.jpg");
            images.add("ad3.jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
}