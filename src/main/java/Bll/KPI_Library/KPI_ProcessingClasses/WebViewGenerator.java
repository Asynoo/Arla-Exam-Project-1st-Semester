package Bll.KPI_Library.KPI_ProcessingClasses;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebViewGenerator implements IViewGenerator {

    public WebViewGenerator(){}

    public Region generateView(String source){
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        engine.load(source);

        Pane newPane = new Pane(webView);
        webView.maxHeightProperty().bind(newPane.heightProperty());
        webView.maxWidthProperty().bind(newPane.widthProperty());

        return newPane;
    }
}
