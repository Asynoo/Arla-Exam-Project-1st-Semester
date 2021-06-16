package Bll.KPI_Library.KPI_ProcessingClasses;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CSVgraphGenerator_StackedArea implements IViewGenerator {
    private AreaChart<Number, Number> graphType1;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private StackedAreaChart<Number, Number> stackedAreaChart;
    private NumberAxis sxAxis;
    private NumberAxis syAxis;

    public CSVgraphGenerator_StackedArea(){}

    public Region generateView(String source){
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        graphType1 = new AreaChart(xAxis, yAxis);

        sxAxis = new NumberAxis();
        syAxis = new NumberAxis();

        stackedAreaChart = new StackedAreaChart<Number, Number>(sxAxis, syAxis);

        getSeriesFromCSV(source);

        return stackedAreaChart;
    }



    public void getSeriesFromCSV(String source) {

        XYChart.Series<Number, Number> dataSeries1 = new XYChart.Series<Number, Number>();
        XYChart.Series<Number, Number> dataSeries2 = new XYChart.Series<Number, Number>();

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
                    System.out.println(calendar.get(Calendar.MONTH));
                    if(calendar.get(Calendar.MONTH) == 3){
                        System.out.println("Called may");
                        dataSeries1.setName("May");
                        dataSeries1.getData().add(new XYChart.Data(dayNumber, Integer.parseInt(data[1])));
                    }else if (calendar.get(Calendar.MONTH) == 4){
                        System.out.println("Called April");
                        dataSeries2.setName("April");
                        dataSeries2.getData().add(new XYChart.Data(dayNumber, Integer.parseInt(data[1])));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        stackedAreaChart.getData().addAll(dataSeries1, dataSeries2);
        graphType1.setLegendVisible(true);
    }
}