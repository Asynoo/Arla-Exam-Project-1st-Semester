package Gui.Models;

import Be.IndividualKPI;
import Be.TemplateCell;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;

/**
 * Model made specially to connect two controllers, which follows MVC pattern. In one controller
 * selectedCell is being set, by clicking the tile, and in the other one the IndividualKPI is being set to the selectedCell
 * */

public class TileCellModel {

    private Pane selectedCell;
    private int lowerWidthBound = 150;
    private int lowerHeightBound = 150;

    public void setSelectedCell(Pane selectedCell) {
        this.selectedCell = selectedCell;
    }

    /**
     * Method to add the node to the given selectedKPI
     * */

    public void fillCell(Region renderedView, IndividualKPI kpi){
        selectedCell.getChildren().clear();
        if(selectedCell.getWidth()>lowerWidthBound && selectedCell.getHeight() > lowerHeightBound){
            selectedCell.setUserData(kpi);
            selectedCell.getChildren().add(renderedView);
            renderedView.setPadding(new Insets(5));
            renderedView.maxHeightProperty().bind(selectedCell.maxHeightProperty());
            renderedView.maxWidthProperty().bind(selectedCell.maxWidthProperty());
        }
    }
}
