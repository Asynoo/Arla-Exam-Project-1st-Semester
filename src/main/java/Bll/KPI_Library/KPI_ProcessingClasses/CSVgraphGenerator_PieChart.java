package Bll.KPI_Library.KPI_ProcessingClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CSVgraphGenerator_PieChart implements IViewGenerator {

    public CSVgraphGenerator_PieChart(){}

    private PieChart pieChart;
    private VBox vBox;

    public Region generateView(String source){
        pieChart = new PieChart();
        vBox = new VBox();

        pieChart.setLegendVisible(false);

        drawPieChart(source);

        return vBox;
    }

    public void drawPieChart(String source){


        int sumColumn1 = 0;
        int sumColumn2 = 0;
        int sumColumn3 = 0;
        int sumColumn4 = 0;

        String[] columnNames = new String[6];
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader(source));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(";");
                if(data[0].contains("Legend")){

                     columnNames = data;

                }else {
                    sumColumn1 += Integer.parseInt(data[1]);
                    sumColumn2 += Integer.parseInt(data[2]);
                    sumColumn3 += Integer.parseInt(data[3]);
                    sumColumn4 += Integer.parseInt(data[4]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(columnNames[1],sumColumn1),
                new PieChart.Data(columnNames[2],sumColumn2),
                new PieChart.Data(columnNames[3], sumColumn3),
                new PieChart.Data(columnNames[4], sumColumn4)
        );
        int sumAll = sumColumn1+sumColumn2+sumColumn3+sumColumn4;
        javafx.scene.control.Label total = new javafx.scene.control.Label("Total lost units this month:  "+sumAll);
        total.setPadding(new Insets(10, 50, 0, 0));
        pieChart.setData(pieChartData);

        vBox.getChildren().add(pieChart);
        vBox.getChildren().add(total);
    }

}
