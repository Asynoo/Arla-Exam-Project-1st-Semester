package Bll.KPI_Library;

import Be.IndividualKPI;
import Bll.KPI_Library.KPI_ProcessingClasses.*;
import Bll.KPI_Library.KPI_ProcessingClasses.WebViewGenerator;
import javafx.scene.layout.Region;

/**
 * This class receives the kpi and returns generated view.
 * The structure of the class is createPane() method
 * Then the kpi gets to the switch statment which determins which type of the KPI
 * is about to be handled, based on type, correct pane generator is caled.
 * the output of individual controller is assigned to the variable theView, which
 * is eventually returned but the createPane() method.
 * */

public class KPI_Pane_Creator {

    private Region theView;
    public String caseUsed = "LINE_CHART";
    public KPI_Pane_Creator() {
    }

    public Region createPane(IndividualKPI kpi) {

        determinKPItype(kpi);
        return theView;
    }

    private void determinKPItype(IndividualKPI kpi) {

        switch (kpi.getType()) {
            case "LINE_CHART" -> createCSV_LineChart(kpi);
            case "AREA_CHART" -> createCSV_AreaChart(kpi);
            case "AREA_CHART1" -> createCSV_AreaChart1(kpi);
            case "AREA_OVERLAY" -> createCSV_AreaOverlay(kpi);
            case "STACKED_AREA" -> createCSV_StackedArea(kpi);
            case "PIE_CHART" -> createCSV_PieChart(kpi);
            case "SPREADSHEET_KPI" -> createSpreadsheet(kpi);
            case "IMAGE_KPI" -> createIMG(kpi);
            case "WEBPAGE_KPI" -> createWebView(kpi);
        }
    }

    /**
     * Here are the methods that represent individual viewGenerators.
     * */

    private void createCSV_LineChart(IndividualKPI kpi){
        caseUsed = "LINE_CHART";
        CSVgraphGenerator_LineChart csvType1 = new CSVgraphGenerator_LineChart();
        theView = csvType1.generateView(kpi.getSource());
    }

    private void createCSV_AreaChart(IndividualKPI kpi) {
        CSVgraphGenerator_AreaChart csvType2 = new CSVgraphGenerator_AreaChart();
        theView = csvType2.generateView(kpi.getSource());

    }

    private void createCSV_AreaChart1(IndividualKPI kpi) {
        CSVgraphGenerator_AreaChart1 csvType2 = new CSVgraphGenerator_AreaChart1();
        theView = csvType2.generateView(kpi.getSource());
    }

    private void createCSV_AreaOverlay(IndividualKPI kpi){
        CSVgraphGenerator_AreaChartOverlay csvType3 = new CSVgraphGenerator_AreaChartOverlay();
        theView = csvType3.generateView(kpi.getSource());
    }

    private void createCSV_StackedArea(IndividualKPI kpi){
        CSVgraphGenerator_StackedArea csvType4 = new CSVgraphGenerator_StackedArea();
        theView = csvType4.generateView(kpi.getSource());
    }

    private void createCSV_PieChart(IndividualKPI kpi) {
        CSVgraphGenerator_PieChart csvType3 = new CSVgraphGenerator_PieChart();
        theView = csvType3.generateView(kpi.getSource());
    }

    private void createSpreadsheet(IndividualKPI kpi) {
        SpreadsheetGenerator sg = new SpreadsheetGenerator();
        try{
            theView = sg.generateView(kpi.getSource());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createIMG(IndividualKPI kpi){
        ImageViewGenerator imageGenerator = new ImageViewGenerator();
        theView = imageGenerator.generateView(kpi.getSource());
    }

    private void createWebView(IndividualKPI kpi){
        WebViewGenerator webViewGenerator = new WebViewGenerator();
        theView = webViewGenerator.generateView(kpi.getSource());
    }


}