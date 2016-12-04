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
        for (int i = 1; i <= 5; i++) {
            images.add("ViewWinner.jpg");
            images.add("update Profile.jpg");
            images.add("upload submission.jpg");
            images.add("viewdash.jpg");
            images.add("viewsponsor.jpg");
        }
    }
 
    public List<String> getImages() {
        return images;
    }
}