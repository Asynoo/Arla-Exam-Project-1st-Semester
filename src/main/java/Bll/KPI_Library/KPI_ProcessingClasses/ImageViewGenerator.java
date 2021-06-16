package Bll.KPI_Library.KPI_ProcessingClasses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class ImageViewGenerator implements IViewGenerator{

    public ImageViewGenerator(){}

    public Region generateView(String source){
        ImageView imgView = new ImageView(new Image(source));

        Pane newPane = new Pane(imgView);

        imgView.fitWidthProperty().bind(newPane.widthProperty());
        imgView.fitHeightProperty().bind(newPane.heightProperty());
        imgView.setPreserveRatio(true);
        return newPane;
    }

}
