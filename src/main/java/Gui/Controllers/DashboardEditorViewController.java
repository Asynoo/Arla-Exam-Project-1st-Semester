package Gui.Controllers;

import Be.IndividualKPI;
import Be.KPIType;
import Be.Template;
import Be.TemplateGrid;
import Bll.DashboardFileManager;
import Exceptions.DatabaseException;
import Exceptions.FileHandlingException;
import Gui.Models.KPIcollectionModel;
import Gui.Models.TileCellModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * DashboardEditorViewController is responsible for the editing creation and saving of the templates.
 * This controler is called on "create new template" or eddit an existing one.
 * */

public class DashboardEditorViewController implements Initializable {
    @FXML
    private JFXTextField kpiNameField;
    @FXML
    private ChoiceBox<KPIType> kpiTypeChoiceBox;
    @FXML
    private JFXTextField kpiSouceTextField;
    @FXML
    private Label wrongInputWarning;
    @FXML
    private ScrollPane webViewKPIsScrollPane, XMLtableKPIsScrollPane, GraphKPIsScrollPane;
    @FXML
    private JFXTextField rowsTextField,columnsTextField,saveNameTextField;
    @FXML
    private Label titleLbl;
    @FXML
    private VBox addKPIVbox,dimensionsVBox,saveTemplateVbox;
    @FXML
    private StackPane editPane;
    private Template template;
    private StackPane templatePane;
    private FXMLLoader templateLoader;
    private AdminDashboardViewController adminController;
    private final DashboardFileManager dashboardFileManager = DashboardFileManager.getInstance();
    private KPIcollectionModel kpIcollectionModel;

    /**
     * This method opens the view where you ca set the dimentions on the grid pane
     * */

    public void setTemplate(Template template){
        this.template = template;
        loadJson();
        dimensionsVBox.setVisible(false);
        templatePane.setVisible(true);
        saveTemplateVbox.setVisible(false);
    }

    @FXML
    private void returnAction() {
        Stage currentStage = (Stage)titleLbl.getScene().getWindow();
        currentStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        templateLoader = new FXMLLoader(this.getClass().getResource("/Dashboards/BaseTemplate.fxml"));
        templatePane = null;

        try {
            templatePane = templateLoader.load();
            templatePane.setStyle("-fx-background-color: gray");
            templatePane.setVisible(false);
            saveTemplateVbox.setVisible(false);
            dimensionsVBox.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        editPane.getChildren().add(templatePane);
    }

    public void setTileCellModel(TileCellModel tileCellModel) {
        ((TemplateController)templateLoader.getController()).setKPItileModel(tileCellModel);
    }

    @FXML
    private void confirmDimensions() {
        try {
            ((TemplateController)templateLoader.getController()).newGrid(Double.parseDouble(rowsTextField.getText()),Double.parseDouble(columnsTextField.getText()));
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please insert valid values", ButtonType.OK);
            alert.showAndWait();
            columnsTextField.clear();
            rowsTextField.clear();
        }
        dimensionsVBox.setVisible(false);
        templatePane.setVisible(true);
        saveTemplateVbox.setVisible(false);
    }

    public void changeDimensionsAction() {
        templatePane.setVisible(false);
        saveTemplateVbox.setVisible(false);
        dimensionsVBox.setVisible(true);
    }

    public void setKPIcollectionModel(KPIcollectionModel kpIcollectionModel) {
        this.kpIcollectionModel = kpIcollectionModel;
        try {
            kpiTypeChoiceBox.setItems(kpIcollectionModel.getKPITypeList());
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        setAccordionPane();
        ((TemplateController)templateLoader.getController()).setKPICollectionModel(kpIcollectionModel);
    }

    /**
     * Following method is responsible for the setup of the AccordionPane where all the individual KPIs are stored.
     * Depending on the type it sends it corresponding pane.
     * */


    public void setAccordionPane(){
        TilePane paneWithXMLViewButtons = new TilePane();
        TilePane paneWithGraphViewButtons = new TilePane();
        TilePane paneWithViewButtons = new TilePane();

        paneWithXMLViewButtons.getChildren().clear();
        paneWithViewButtons.getChildren().clear();
        paneWithGraphViewButtons.getChildren().clear();

        setAcordeonPaneStyles(paneWithViewButtons);
        setAcordeonPaneStyles(paneWithXMLViewButtons);
        setAcordeonPaneStyles(paneWithGraphViewButtons);

        try {
            for(IndividualKPI kpi: kpIcollectionModel.getKPIcollection()){
                if (kpi.getType().equals("WEBPAGE_KPI")|| kpi.getType().equals("IMAGE_KPI")){
                    paneWithViewButtons.getChildren().add(createButton(kpi));
                }else if (kpi.getType().equals("SPREADSHEET_KPI")){
                    paneWithXMLViewButtons.getChildren().add(createButton(kpi));
                }else{
                    paneWithGraphViewButtons.getChildren().add(createButton(kpi));
                }
            }
        } catch (DatabaseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

        webViewKPIsScrollPane.setStyle("-fx-padding: 10,10,0,0");
        XMLtableKPIsScrollPane.setStyle("-fx-padding: 10,10,0,0");
        GraphKPIsScrollPane.setStyle("-fx-padding: 10,10,0,0");

        webViewKPIsScrollPane.setContent(paneWithViewButtons);
        XMLtableKPIsScrollPane.setContent(paneWithXMLViewButtons);
        GraphKPIsScrollPane.setContent(paneWithGraphViewButtons);
    }

    private void setAcordeonPaneStyles(TilePane paneToStyle) {
        paneToStyle.setPrefTileWidth(140);
        paneToStyle.setPrefColumns(1);
        paneToStyle.setVgap(10);
    }

    /**
     * Since every KPI is represented by the button in the view those buttons are created here
     * */

    private JFXButton createButton(IndividualKPI kpi) {

        JFXButton kpiButton = new JFXButton(kpi.getName());
        kpiButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.SECONDARY){
                Stage deleteKPIStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tiles/deleteKPITile.fxml"));
                try {
                    loader.load();
                    Parent root = loader.getRoot();
                    ((DeleteKPITileController)loader.getController()).setKPI(kpi);
                    ((DeleteKPITileController)loader.getController()).setKPIcollectionModel(kpIcollectionModel);
                    deleteKPIStage.setScene(new Scene(root));
                    deleteKPIStage.setResizable(false);
                    deleteKPIStage.initStyle(StageStyle.UNDECORATED);
                    deleteKPIStage.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        kpiButton.setOnDragDetected(mouseEvent -> {
            Dragboard db = kpiButton.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();

            cb.putString("KPIDATA" + kpi.getId());

            db.setContent(cb);
            mouseEvent.consume();
        });
        kpiButton.setButtonType(JFXButton.ButtonType.FLAT);
        kpiButton.setStyle("-fx-background-color: #00aa14; -fx-background-radius: 5; -fx-text-fill: white; -fx-pref-width: 150; -fx-alignment: center; ");
        return kpiButton;
    }

    public void setAdminController(AdminDashboardViewController controller){
        adminController = controller;
    }

    private void saveJson(){
        TemplateGrid templateGrid = ((TemplateController)templateLoader.getController()).createTemplateGrid();
        try {
            dashboardFileManager.saveFile(template,templateGrid);
        } catch (FileHandlingException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Following method loads the json file containing information about pre made dashboard
     * */

    private void loadJson(){
        try {
            ((TemplateController)templateLoader.getController()).loadTemplateGrid(dashboardFileManager.loadFile(template),true);
        } catch (FileHandlingException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            returnAction();
        }
    }

    /**
     * Action upon saving the template/dashboard, opens a view with input for the filename
     * */

    @FXML
    private void saveTemplateAction() {
        if (template != null) {
            saveJson();
        }
        else {
            dimensionsVBox.setVisible(false);
            templatePane.setVisible(false);
            saveTemplateVbox.setVisible(true);
        }
    }

    @FXML
    private void loadTemplateAction() {
        loadJson();
        dimensionsVBox.setVisible(false);
        templatePane.setVisible(true);
        saveTemplateVbox.setVisible(false);
    }

    /**
     * Saving action of the template, being saved to the json file through the saveJson();
     * */
    @FXML
    private void confirmSaveAction() {
        if (template == null) {
            template = new Template(null,saveNameTextField.getText(),saveNameTextField.getText() + ".json");
        }
        adminController.addTemplate(template);
        saveJson();
        dimensionsVBox.setVisible(false);
        templatePane.setVisible(true);
        saveTemplateVbox.setVisible(false);
    }

    public Template getTemplate() {
        return template;
    }

    @FXML
    private void refreshKPIsAction() {
        ((TemplateController)templateLoader.getController()).updateKpiSizes();
    }

    public void addKPIAction() {
        if(addKPIVbox.isVisible()){
            addKPIVbox.setVisible(false);
            dimensionsVBox.setVisible(false);
            templatePane.setVisible(true);
        }else{
            addKPIVbox.setVisible(true);
            dimensionsVBox.setVisible(false);
            templatePane.setVisible(false);
        }

    }

    public void choseFileAction() {
        FileChooser KPIsource = new FileChooser();
        KPIsource.setInitialDirectory(new File("src/main/resources/Files/CSV_files"));
        Stage stage = new Stage();
        stage.setTitle("Choose KPI source");
        File file = KPIsource.showOpenDialog(stage);
        if (file != null) {
            String cwd = file.getName();
            kpiSouceTextField.setText(cwd);
        }
    }

    public void saveNewKPITool() {
        KPIType kpiType = kpiTypeChoiceBox.getSelectionModel().getSelectedItem();
        if(kpiNameField.getText().isEmpty()|| kpiSouceTextField.getText().isEmpty()||kpiType==null){
            wrongInputWarning.setVisible(true);
        }else{
            if(kpiType.getKPITypeId()==3||kpiType.getKPITypeId()==5||kpiType.getKPITypeId()==6||kpiType.getKPITypeId()==8||kpiType.getKPITypeId()==10||kpiType.getKPITypeId()==11||
                    kpiType.getKPITypeId()==12||kpiType.getKPITypeId()==13||kpiType.getKPITypeId()==15){

                String name = kpiNameField.getText();
                int kpiTypeId = kpiType.getKPITypeId();
                String kpiSource = "src/main/resources/Files/CSV_files/"+kpiSouceTextField.getText();
                IndividualKPI newKPITool = new IndividualKPI(null,name,kpiType.getName(),kpiSource);
                newKPITool.setTypeID(kpiTypeId);
                try {
                    kpIcollectionModel.saveNewKPITool(newKPITool);
                } catch (DatabaseException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }

                wrongInputWarning.setVisible(false);
                kpiNameField.clear();
                kpiSouceTextField.clear();
                addKPIVbox.setVisible(false);
                templatePane.setVisible(true);
                setAccordionPane();
            }else{
                wrongInputWarning.setVisible(true);
            }
        }
    }

    public void cancelSaveNewKPI() {
        addKPIAction();
    }
}
