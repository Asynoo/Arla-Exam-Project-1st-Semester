package Bll.KPI_Library.KPI_ProcessingClasses;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CSVgraphGenerator_LineChart implements IViewGenerator {

    private LineChart<Number, Number> graphType1;
    private NumberAxis xAxis;
    private NumberAxis yAxis;

    public CSVgraphGenerator_LineChart(){}


    public Region generateView(String source){
        xAxis = new NumberAxis(1,31,1);
        yAxis = new NumberAxis();
        graphType1 = new LineChart(xAxis, yAxis);

        getSeriesFromCSV(source);
        return graphType1;
    }



    public void getSeriesFromCSV(String source) {

        XYChart.Series<Number, Number> dataSeries1 = new XYChart.Series<Number, Number>();

        try{
            BufferedReader csvReader = new BufferedReader(new FileReader(source));
            String row;
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(";");
                if(data[0].contains("AxisNames")){
                    //xAxis.setLabel(data[1]);
                    //yAxis.setLabel(data[2]);
                    System.out.println("Label names are set");
                }else {
                    Date providedDate = new SimpleDateFormat("dd.MM.yyyy").parse(data[0]);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(providedDate);
                    int dayNumber = calendar.get(Calendar.DAY_OF_MONTH);
                    System.out.println(dayNumber);

                    dataSeries1.getData().add(new XYChart.Data(dayNumber, Integer.parseInt(data[1])));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        graphType1.getData().add(dataSeries1);
        graphType1.setLegendVisible(false);
    }

}
