package Gui.Controllers;

import Be.IndividualKPI;
import Be.TemplateCell;
import Be.TemplateGrid;
import Bll.KPI_Library.KPI_Pane_Creator;
import Exceptions.DatabaseException;
import Gui.Models.KPIcollectionModel;
import Gui.Models.TileCellModel;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class TemplateController{

    @FXML
    private VBox zoomBox;
    @FXML
    private GridPane basePane;
    List<VBox> cellList = new ArrayList<>();
    List<VBox> resizeRectangle = new ArrayList<>();
    private boolean resizeInvalid = false;

    private Integer topX = null,topY = null,botX = null,botY = null;

    private TileCellModel kpiTileModel;
    private KPIcollectionModel kpIcollectionModel;


    public void setDimensions(Double rows,Double columns) {
        /**
         * remove rows and columns
         * */
        basePane.getChildren().clear();
        basePane.getRowConstraints().clear();
        basePane.getColumnConstraints().clear();
        //add rows
        for (int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100/rows);
            basePane.getRowConstraints().add(row);
        }
        /**
         * add columns to the future pane
         * */
        for (int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/columns);
            basePane.getColumnConstraints().add(column);
        }
    }

    public void newGrid(Double rows,Double columns){
        setDimensions(rows, columns);
        fillCells();
    }

    private void fillCells(){
        basePane.getChildren().clear();
        cellList.clear();
        for (int x = 0; x < basePane.getColumnCount(); x++) {
            for (int y = 0; y < basePane.getRowCount(); y++) {
                VBox kpiCell = new VBox();
                // height bound to row height
                kpiCell.prefHeightProperty().bind(basePane.getRowConstraints().get(1).percentHeightProperty());
                // width bound to column width
                kpiCell.prefWidthProperty().bind(basePane.getColumnConstraints().get(1).percentWidthProperty());
                kpiCell.setStyle("-fx-background-color: white;");
                setupCellEdit(kpiCell);
                cellList.add(kpiCell);
                basePane.add(kpiCell,x,y,1,1);
            }
        }
    }

    private void loadCells(List<TemplateCell> templateCells,boolean editMode){
        basePane.getChildren().clear();
        cellList.clear();
        for (TemplateCell templateCell : templateCells) {
            VBox kpiCell = new VBox();
            kpiCell.prefHeightProperty().bind(basePane.getRowConstraints().get(1).percentHeightProperty());
            kpiCell.prefWidthProperty().bind(basePane.getColumnConstraints().get(1).percentWidthProperty());
            kpiCell.setStyle("-fx-background-color: white;");
            kpiCell.setVisible(templateCell.isVisible());
            cellList.add(kpiCell);
            basePane.add(kpiCell, templateCell.getX(), templateCell.getY(), templateCell.getWidth(), templateCell.getHeight());
            if (templateCell.getKpiID() != null) {
                try {
                    for (IndividualKPI kpi : kpIcollectionModel.getKPIcollection()) {
                        if (templateCell.getKpiID().equals(kpi.getId())) {
                            kpiCell.setUserData(kpi);
                        }
                    }
                } catch (DatabaseException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }

            }
            if (editMode) {
                setupCellEdit(kpiCell);
            } else {
                setupCellPreview(kpiCell);
                if (GridPane.getColumnSpan(kpiCell) == 1 && GridPane.getRowSpan(kpiCell) == 1 && templateCell.getKpiID() == null) {
                    kpiCell.setVisible(false);
                }
            }
        }
    }


    private void setSelectedCell(VBox cell) {
        kpiTileModel.setSelectedCell(cell);
        if(cell.getUserData() != null){
            KPI_Pane_Creator pane = new KPI_Pane_Creator();
            kpiTileModel.fillCell(pane.createPane((IndividualKPI)cell.getUserData()),(IndividualKPI)cell.getUserData());
        }
    }

    private void setupCellEdit(VBox kpiCell){
        //removing KPIs via right-click
        kpiCell.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.SECONDARY){
                kpiCell.getChildren().clear();
                kpiCell.setUserData(null);
            }
        });

        //changing the cursor on edges of the tile
        kpiCell.setOnMouseMoved(event -> {
            if(event.getX() < 5 || event.getX() > kpiCell.getWidth()-5) kpiCell.setCursor(Cursor.H_RESIZE);
            else if(event.getY() < 5 || event.getY() > kpiCell.getHeight()-5) kpiCell.setCursor(Cursor.V_RESIZE);
            else kpiCell.setCursor(Cursor.DEFAULT);
        });

        // starting the resize
        kpiCell.setOnDragDetected(mouseEvent -> {
            // check if the mouse is at the edge
            if(mouseEvent.getX()< 5 || mouseEvent.getX() > kpiCell.getWidth()-5 || mouseEvent.getY()< 5 || mouseEvent.getY() > kpiCell.getHeight()-5){
                kpiCell.setStyle("-fx-background-color: lightBlue");
                cellList.forEach(cell -> {
                    if(cell != kpiCell) {
                        // green if cell is eligible for resize (1x1)
                        // red if cell is ineligible
                        if (GridPane.getRowSpan(cell) == 1 && GridPane.getColumnSpan(cell) == 1) {
                            cell.setStyle("-fx-background-color: lightGreen");
                        } else
                            cell.setStyle("-fx-background-color: #ef4e43");
                    }
                });

                Dragboard db = kpiCell.startDragAndDrop(TransferMode.ANY);
                ClipboardContent cb = new ClipboardContent();
                // clipboard filled with string of the KPIcell
                // and put into the dragboard
                cb.putString("CELLDATA" + GridPane.getColumnIndex(kpiCell) + ";" + GridPane.getRowIndex(kpiCell) +
                        ";" + GridPane.getColumnSpan(kpiCell) + ";" + GridPane.getRowSpan(kpiCell));

                db.setContent(cb);
                mouseEvent.consume();
            }
        });


        kpiCell.setOnDragEntered(dragEvent -> {
            if(dragEvent.getDragboard().hasString()) {
                String clipString = dragEvent.getDragboard().getString();
                if(clipString.contains("CELLDATA")){
                    List<Integer> cellCoords = new ArrayList<>();
                    int resizeParentTopX, resizeParentTopY,resizeParentBottomX,resizeParentBottomY;

                    resizeRectangle.clear();
                    resizeInvalid = false;
                    clipString = clipString.replace("CELLDATA","");
                    for (String data: clipString.split(";")) {
                        cellCoords.add(Integer.valueOf(data));
                    }
                    resizeParentTopX = cellCoords.get(0);
                    resizeParentTopY = cellCoords.get(1);
                    resizeParentBottomX = cellCoords.get(0) + cellCoords.get(2) - 1;
                    resizeParentBottomY = cellCoords.get(1) + cellCoords.get(3) - 1;

                    cellList.forEach(cell -> {
                        int bottomX = GridPane.getColumnIndex(cell) + GridPane.getColumnSpan(cell) - 1;
                        int bottomY = GridPane.getRowIndex(cell) + GridPane.getRowSpan(cell) - 1;

                        if(((GridPane.getColumnIndex(cell) <= GridPane.getColumnIndex(kpiCell) && bottomX >= resizeParentTopX)
                            || ((bottomX >= GridPane.getColumnIndex(kpiCell) && GridPane.getColumnIndex(cell) <= resizeParentBottomX)))
                            && ((GridPane.getRowIndex(cell) <= GridPane.getRowIndex(kpiCell) && (bottomY >= resizeParentTopY))
                            || ((bottomY >= GridPane.getRowIndex(kpiCell) && (GridPane.getRowIndex(cell) <= resizeParentBottomY))
                        ))){
                            resizeRectangle.add(cell);
                            if(GridPane.getRowIndex(cell) == resizeParentTopY && GridPane.getColumnIndex(cell) == resizeParentTopX)
                                cell.setStyle("-fx-background-color: lightBlue");
                            else if (GridPane.getRowSpan(cell) != 1 || GridPane.getColumnSpan(cell) != 1 || resizeInvalid) {
                                cell.setStyle("-fx-background-color: #ef4e43");
                                resizeInvalid = true;
                            }
                            else {
                                cell.setStyle("-fx-background-color: yellow");
                            }
                        }
                        else if ((GridPane.getRowSpan(cell) != 1 || GridPane.getColumnSpan(cell) != 1))
                            cell.setStyle("-fx-background-color: #ef4e43");
                        else
                            cell.setStyle("-fx-background-color: lightGreen");
                    });
                }
                else if(clipString.contains("KPIDATA")) {
                    clipString = clipString.replace("KPIDATA","");
                    int kpiID = Integer.parseInt(clipString);
                    IndividualKPI cellKpi = (IndividualKPI)kpiCell.getUserData();
                    cellList.forEach(cell -> cell.setStyle("-fx-background-color: white"));
                    if(cellKpi == null){
                        kpiCell.setStyle("-fx-background-color: lightGreen");
                    }
                    else if (cellKpi.getId() == kpiID){
                        kpiCell.setStyle("-fx-background-color: #ef4e43");
                    }
                    else {
                        kpiCell.setStyle("-fx-background-color: yellow");
                    }
                }
            }
            dragEvent.consume();
            kpiCell.setCursor(Cursor.CROSSHAIR);
        });

        // called in the tile the drag originated from
        kpiCell.setOnDragDone(dragEvent -> {
            if(dragEvent.getDragboard().hasString()) {
                String clipString = dragEvent.getDragboard().getString();
                if (clipString.contains("CELLDATA") && !resizeInvalid) {

                    // go through each cell in the resize rectangle and get the coordinates of every side
                    resizeRectangle.forEach(cell -> {
                        int bottomX = GridPane.getColumnIndex(cell) + GridPane.getColumnSpan(cell) - 1;
                        int bottomY = GridPane.getRowIndex(cell) + GridPane.getRowSpan(cell) - 1;
                        if (topX == null || GridPane.getColumnIndex(cell) < topX)
                            topX = GridPane.getColumnIndex(cell);
                        if (topY == null || GridPane.getRowIndex(cell) < topY)
                            topY = GridPane.getRowIndex(cell);
                        if (botX == null || bottomX > botX)
                            botX = bottomX;
                        if (botY == null || bottomY > botY)
                            botY = bottomY;
                    });
                    resizeRectangle.forEach(cell -> {
                        // resize the top left cell based on the size of the resize rectangle
                        if(GridPane.getColumnIndex(cell).equals(topX) && GridPane.getRowIndex(cell).equals(topY)){
                            GridPane.setColumnSpan(cell,Math.abs(topX-botX)+1);
                            GridPane.setRowSpan(cell,Math.abs(topY-botY)+1);
                        }
                        else
                            cell.setVisible(false);
                    });
                    topX = null;
                    topY = null;
                    botX = null;
                    botY = null;
                    resizeRectangle.clear();
                }
            }
            basePane.getChildren().forEach(node -> node.setStyle("-fx-background-color: white"));

            dragEvent.consume();
        });

        kpiCell.setOnDragDropped(dragEvent -> {
            if(dragEvent.getDragboard().hasString()) {
                String clipString = dragEvent.getDragboard().getString();
                if (clipString.contains("KPIDATA")) {
                    clipString = clipString.replace("KPIDATA", "");
                    int kpiID = Integer.parseInt(clipString);
                    KPI_Pane_Creator pane_creator = new KPI_Pane_Creator();
                    try {
                        for (IndividualKPI kpi : kpIcollectionModel.getKPIcollection()) {
                            if(kpi.getId() == kpiID){
                                kpiTileModel.setSelectedCell(kpiCell);
                                kpiTileModel.fillCell(pane_creator.createPane(kpi), kpi);
                            }
                        }
                    } catch (DatabaseException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                        alert.showAndWait();
                    }
                    cellList.forEach(cell -> cell.setStyle("-fx-background-color: white"));
                }
            }
        });

        kpiCell.setOnDragOver(dragEvent -> {

            if(dragEvent.getDragboard().hasString()) {
                dragEvent.acceptTransferModes(TransferMode.ANY);
            }
            dragEvent.consume();
            kpiCell.setCursor(Cursor.CROSSHAIR);
        });
    }

    private void setupCellPreview(VBox cell){
        cell.setOnContextMenuRequested(contextMenuEvent -> {
            if(cell.getUserData() != null){
                KPI_Pane_Creator pane_creator = new KPI_Pane_Creator();
                kpiTileModel.setSelectedCell(zoomBox);
                kpiTileModel.fillCell(pane_creator.createPane((IndividualKPI)cell.getUserData()),(IndividualKPI)cell.getUserData());
                zoomBox.setVisible(true);
                basePane.setDisable(true);
                basePane.setOpacity(.6);
            }
        });
    }

    @FXML
    private void zoomOut() {
        zoomBox.setVisible(false);
        basePane.setDisable(false);
        basePane.setOpacity(1);
        zoomBox.getChildren().clear();
    }

    public void updateKpiSizes(){
        for (VBox cell : cellList) {
            setSelectedCell(cell);
        }
    }

    public void setKPItileModel(TileCellModel kpiTileModel) {
        this.kpiTileModel = kpiTileModel;
    }

    public TemplateGrid createTemplateGrid(){
        List<TemplateCell> templateCellList = new ArrayList<>();
        TemplateGrid templateGrid = new TemplateGrid(basePane.getRowCount(),basePane.getColumnCount());

        for (VBox cell : cellList) {
            int x = GridPane.getColumnIndex(cell);
            int y = GridPane.getRowIndex(cell);
            int width = GridPane.getColumnSpan(cell);
            int height = GridPane.getRowSpan(cell);
            Integer kpiId = null;
            if(cell.getUserData() != null){
                kpiId = ((IndividualKPI)cell.getUserData()).getId();
            }
            templateCellList.add(new TemplateCell(x,y,width,height,cell.isVisible(),kpiId));
        }
        templateGrid.setTemplateCells(templateCellList);
        return templateGrid;
    }

    public void loadTemplateGrid(TemplateGrid templateGrid,boolean editMode) {
        setDimensions(templateGrid.getRows(),templateGrid.getColumns());
        loadCells(templateGrid.getTemplateCells(),editMode);
    }

    public void setKPICollectionModel(KPIcollectionModel kpIcollectionModel) {
        this.kpIcollectionModel = kpIcollectionModel;
    }

}
