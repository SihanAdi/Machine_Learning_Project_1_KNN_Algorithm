package KNN;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Scatter_plot extends JFrame{

    public Scatter_plot(String title, ArrayList<Data_Set> train_set, String str1, String str2) {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset(train_set, str1, str2);

        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(
                str1 +" and " + str2 + " Scatter plot",
                "X-" + str1, "Y-" + str2, dataset,
                PlotOrientation.VERTICAL, true, true, false);


        //Changes background color
        XYPlot plot = (XYPlot)chart.getPlot();
        XYLineAndShapeRenderer xylineAndShapeRenderer = (XYLineAndShapeRenderer)plot.getRenderer();
        xylineAndShapeRenderer.setSeriesShape( 0 , new java.awt.Rectangle(0 ,1 , 5 , 5 ));
        xylineAndShapeRenderer.setSeriesShape( 1 , new java.awt.Rectangle(0 ,1 , 5 , 5 ));
        plot.setForegroundAlpha(0.3f);
        plot.setBackgroundPaint(new Color(255,228,196));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);


        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    /**
     *
     * @param train_set
     * @param str1
     * @param str2
     * @return
     */
    private XYDataset createDataset(ArrayList<Data_Set> train_set, String str1, String str2) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        HashMap<Data_Set, Double> map_x = new HashMap<>();
        HashMap<Data_Set, Double> map_y = new HashMap<>();

        for (Data_Set data : train_set){
            switch (str1){
                case "Pregnancies":
                    map_x.put(data, data.getPregnancies());
                    break;
                case "Glucose":
                    map_x.put(data,data.getGlucose());
                    break;
                case "BloodPressure":
                    map_x.put(data, data.getBloodPressure());
                    break;
                case "SkinThickness":
                    map_x.put(data,data.getSkinThickness());
                    break;
                case "Insulin":
                    map_x.put(data, data.getInsulin());
                    break;
                case "BMI":
                    map_x.put(data,data.getBMI());
                    break;
                case "DiabetesPedigreeFunction":
                    map_x.put(data, data.getDiabetesPedigreeFunction());
                    break;
                case "Age":
                    map_x.put(data,data.getAge());
                    break;
            }
            switch (str2){
                case "Pregnancies":
                    map_y.put(data, data.getPregnancies());
                    break;
                case "Glucose":
                    map_y.put(data,data.getGlucose());
                    break;
                case "BloodPressure":
                    map_y.put(data, data.getBloodPressure());
                    break;
                case "SkinThickness":
                    map_y.put(data,data.getSkinThickness());
                    break;
                case "Insulin":
                    map_y.put(data, data.getInsulin());
                    break;
                case "BMI":
                    map_y.put(data,data.getBMI());
                    break;
                case "DiabetesPedigreeFunction":
                    map_y.put(data, data.getDiabetesPedigreeFunction());
                    break;
                case "Age":
                    map_y.put(data,data.getAge());
                    break;
            }
        }

        XYSeries series1 = new XYSeries("Positive");
        XYSeries series2 = new XYSeries("Negative");

        for (Data_Set data : train_set){
            if (data.getOutcome() == 1){
                series1.add(map_x.get(data), map_y.get(data));
            }else{
                series2.add(map_x.get(data), map_y.get(data));
            }
        }

        dataset.addSeries(series1);
        dataset.addSeries(series2);



        return dataset;
    }


}
