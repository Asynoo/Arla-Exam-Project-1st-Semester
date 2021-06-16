package Bll.KPI_Library;

import Be.IndividualKPI;
import Bll.KPI_Library.KPI_ProcessingClasses.*;
import javafx.scene.layout.Region;

public class KPI_PaneCreator_Test {
    private Region theView;
    public String caseUsed = "";

    public KPI_PaneCreator_Test() {
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

    private void createCSV_LineChart(IndividualKPI kpi){
        caseUsed = kpi.getType();
    }

    private void createCSV_AreaChart(IndividualKPI kpi) {
        caseUsed = kpi.getType();
    }

    private void createCSV_AreaChart1(IndividualKPI kpi) {
        caseUsed = kpi.getType();
    }

    private void createCSV_AreaOverlay(IndividualKPI kpi){
        caseUsed = kpi.getType();
    }

    private void createCSV_StackedArea(IndividualKPI kpi){
        caseUsed = kpi.getType();
    }

    private void createCSV_PieChart(IndividualKPI kpi) {
        caseUsed = kpi.getType();
    }

    private void createSpreadsheet(IndividualKPI kpi) {
        caseUsed = kpi.getType();
    }

    private void createIMG(IndividualKPI kpi){
        caseUsed = kpi.getType();
    }

    private void createWebView(IndividualKPI kpi){
        caseUsed = kpi.getType();
    }

}
