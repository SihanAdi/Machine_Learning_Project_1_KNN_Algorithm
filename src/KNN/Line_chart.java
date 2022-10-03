package KNN;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import static KNN.Main.findMaxAndMin;
import static KNN.Main.findMaxAndMin_Digit;

public class Line_chart extends JFrame{
    private File train_csv = new File(
            "/Users/adisihansun/Desktop/machine learning/project_1/Machine_Learning_Project_1_KNN_Algorithm/src/KNN/train.csv");
    private File digit_train_csv = new File(
            "/Users/adisihansun/Desktop/machine learning/project_1/Machine_Learning_Project_1_KNN_Algorithm/src/KNN/digit_train");

    public Line_chart(boolean Digit) {

        initUI(Digit);

    }

    /**
     * @param digit
     */
    private void initUI(boolean digit) {
        XYDataset dataset = createDataset(digit);
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Normalize datasets to avoid a substantial numerical gap between different data due to other units
     * leading to the prediction result being dominated by particular data
     * @param data_set
     * @param digit
     * @return ArrayList<Data_Set> Normalized set
     */
    public static ArrayList<Data_Set> Normalized(ArrayList<Data_Set> data_set, boolean digit){
        ArrayList<Data_Set> list = new ArrayList<>();

        if (digit){
//            Map<String, Map<Integer, Double>> map = findMaxAndMin_Digit(data_set);
//            for (Data_Set data : data_set){
//                double[] pix = new double[785];
//                for (int i = 0; i < data.getPixel().length; i++){
//                    if (map.get("Max").get(i) - map.get("Min").get(i) == 0){
//                        pix[i] = data.getPixel()[i];
//                    }else{
//                        pix[i] = (data.getPixel()[i] - map.get("Min").get(i)) / (map.get("Max").get(i) - map.get("Min").get(i));
//                    }
//                }
//                data.setPixel(pix);
//                list.add(data);
//            }
            for (Data_Set data : data_set){
                for (int i = 0; i < data.getPixel().length; i++){
                    if (data.getPixel()[i] > 0){
                        data.getPixel()[i] = 1;
                    }
                }
                list.add(data);
            }

        }else{
            Map<String, Double> map = findMaxAndMin(data_set);
            for (Data_Set data : data_set){
                data.setPregnancies((double)(data.getPregnancies() - map.get("Min_Pregnancies")) / (map.get("Max_Pregnancies") - map.get("Min_Pregnancies")));
                data.setInsulin((double)(data.getInsulin() - map.get("Min_Insulin")) / (map.get("Max_Insulin") - map.get("Min_Insulin")));
                data.setGlucose((double)((data.getGlucose() - map.get("Min_Glucose")) / (map.get("Max_Glucose") - map.get("Min_Glucose"))) * 3);
                data.setSkinThickness((double)(data.getSkinThickness() - map.get("Min_SkinThickness")) / (map.get("Max_SkinThickness") - map.get("Min_SkinThickness")));
                data.setBMI((double)(data.getBMI() - map.get("Min_BMI")) / (map.get("Max_BMI") - map.get("Min_BMI")));
                data.setDiabetesPedigreeFunction((double)(data.getDiabetesPedigreeFunction() - map.get("Min_DiabetesPedigreeFunction")) / (map.get("Max_DiabetesPedigreeFunction") - map.get("Min_DiabetesPedigreeFunction")));
                data.setAge((double)(data.getAge() - map.get("Min_Age")) / (map.get("Max_Age") - map.get("Min_Age")));
                data.setBloodPressure((double)(data.getBloodPressure() - map.get("Min_BloodPressure")) / (map.get("Max_BloodPressure") - map.get("Min_BloodPressure")));
                list.add(data);
            }
        }
        return list;
    }

    /**
     * create lines
     * @param digit
     * @return XYDataset
     */
    private XYDataset createDataset(Boolean digit) {
        var series = new XYSeries("p = 2, Validation accuracy");
        var series2 = new XYSeries("p = 1, Validation accuracy");
        var series3 = new XYSeries("p = ∞, Validation accuracy");
        var series4 = new XYSeries("3 : k Validation accuracy");
        try {

            if (digit){
                int fold = 10;
                KNN_Implement knn_shuffling = new KNN_Implement(digit_train_csv, true);
//                Collections.shuffle(knn_shuffling.set);
                ArrayList<ArrayList<Data_Set>> Sublist = sublist(knn_shuffling.set, fold);
                int len = knn_shuffling.set.size() - Sublist.get(0).size();

                double ans = 0;
                int index = 1;
                for (int k = 1; k < (int) Math.sqrt(len); k++){
                    double sum_acc = 0;
                    double sum_acc1 = 0;
                    for (int i = 0; i < fold; i++){
                        ArrayList<Data_Set> test_set = Sublist.get(i);
                        test_set = Normalized(test_set, true);
                        ArrayList<Data_Set> train_set = new ArrayList<>();
                        for (int j = 0; j < fold; j++){
                            if (j != i){
                                train_set.addAll(Sublist.get(j));
                            }
                        }
                        train_set = Normalized(train_set, true);
                        int[][] l = new int[10][10];
                        int error_count_digit = 0;
                        for (Data_Set test_data : test_set) {
                            int label = knn_shuffling.knn(test_data, train_set, k, true, 2);//21
                            l[test_data.getLabel()][label] += 1;
                            if (label != test_data.getLabel()) {
                                error_count_digit++;
                            }
                        }
                        int count_0 = 0;
                        for (int d = 0; d < 10; d++){
                            count_0 += l[8][d];
                        }
                        double sub_acc = (double)(test_set.size() - error_count_digit) / (double)test_set.size();
                        double sub_acc1 = (double)l[3][3] / count_0;
                        sum_acc += sub_acc;
                        sum_acc1 += sub_acc1;
                    }
                    double acc = sum_acc / fold;
                    double acc1 = sum_acc1 / fold;
                    if (ans <= acc){
                        ans = acc;
                        index = k;
                    }

                    series.add(k, acc);
                    series4.add(k, acc1);
                }
                System.out.println(ans);
                System.out.println(index);
                System.out.println(digit);
            }else{
                int fold = 10;
                KNN_Implement knn_shuffling = new KNN_Implement(train_csv);
                Collections.shuffle(knn_shuffling.set);

                ArrayList<ArrayList<Data_Set>> Sublist = sublist(knn_shuffling.set, fold);
                int len = knn_shuffling.set.size() - Sublist.get(0).size();

                double ans1 = 0;
                int index1 = 1;
                double ans2 = 0;
                int index2 = 1;
                double ans3 = 0;
                int index3 = 1;
                for (int k = 1; k < (int) Math.sqrt(len); k += 2){//这里改了
                    double sum_acc1 = 0;
                    double sum_acc2 = 0;
                    double sum_acc3 = 0;
                    for (int i = 0; i < fold; i++){
                        ArrayList<Data_Set> test_set = Sublist.get(i);
                        ArrayList<Data_Set> train_set = new ArrayList<>();
                        for (int j = 0; j < fold; j++){
                            if (j != i){
                                train_set.addAll(Sublist.get(j));
                            }
                        }
                        train_set = Normalized(train_set, false);
                        test_set = Normalized(test_set, false);
                        int error_count1 = 0;
                        int error_count2 = 0;
                        int error_count3 = 0;
                        for (Data_Set test_data : test_set) {
                            int label1 = knn_shuffling.knn(test_data, train_set, k, false, 2);//14
                            int label2 = knn_shuffling.knn(test_data, train_set, k, false, 1);//14
                            int label3 = knn_shuffling.knn(test_data, train_set, k, false, Integer.MAX_VALUE);//14
                            if (label1 != test_data.getOutcome()) {
                                error_count1++;
                            }
                            if (label2 != test_data.getOutcome()) {
                                error_count2++;
                            }
                            if (label3 != test_data.getOutcome()) {
                                error_count3++;
                            }
                        }
                        double sub_acc1 = (double)(test_set.size() - error_count1) / (double)test_set.size();
                        sum_acc1 += sub_acc1;
                        double sub_acc2 = (double)(test_set.size() - error_count2) / (double)test_set.size();
                        sum_acc2 += sub_acc2;
                        double sub_acc3 = (double)(test_set.size() - error_count3) / (double)test_set.size();
                        sum_acc3 += sub_acc3;
                    }
                    double acc1 = sum_acc1 / fold;
                    if (ans1 < acc1){
                        ans1 = acc1;
                        index1 = k;
                    }
                    series.add(k, acc1);
                    double acc2 = sum_acc2 / fold;
                    if (ans2 < acc2){
                        ans2 = acc2;
                        index2 = k;
                    }

                    series2.add(k, acc2);
                    double acc3 = sum_acc3 / fold;
                    if (ans3 < acc3){
                        ans3 = acc3;
                        index3 = k;
                    }

                    series3.add(k, acc3);
                }
                System.out.println("p = 2, " + ans1);
                System.out.println(index1);
                System.out.println("p = 1, " + ans2);
                System.out.println(index2);
                System.out.println("p = ∞, " + ans3);
                System.out.println(index3);
                System.out.println("Pima");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        if (!digit){
            dataset.addSeries(series2);
            dataset.addSeries(series3);
        }else{
            dataset.addSeries(series4);
        }
        return dataset;
    }

    /**
     * The dataset is divided into ten parts according to the requirements of 10-fold cross-validation.
     * @param All_set
     * @param fold
     * @return
     */
    public ArrayList<ArrayList<Data_Set>> sublist(ArrayList<Data_Set> All_set, int fold){
        int count = All_set.size() / fold;
        int i = 0;
        ArrayList<ArrayList<Data_Set>> ans = new ArrayList<>();
        ArrayList<Data_Set> tmp = new ArrayList<>();
        while (i < All_set.size()){
            if ((i + 1) % count == 0){
                if ((All_set.size() - (i + 1)) < count){
                    while (i < All_set.size()){
                        tmp.add(All_set.get(i));
                        i++;
                    }
                    ans.add(tmp);
                    break;
                }
                tmp.add(All_set.get(i));
                ans.add(tmp);
                tmp = new ArrayList<>();
            }else{
                tmp.add(All_set.get(i));
            }
            i++;
        }
        return ans;
    }

    /**
     *
     * @param dataset
     * @return JFreeChart
     */
    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "K VS Validation error",
                "K",
                "Validation error",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        renderer.setSeriesPaint(1, Color.green);
        renderer.setSeriesStroke(1, new BasicStroke(1.0f));
        renderer.setSeriesPaint(2, Color.blue);
        renderer.setSeriesStroke(2, new BasicStroke(1.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle(
                "K VS Validation error",
                new Font("Serif", java.awt.Font.BOLD, 18))
        );
        return chart;
    }

}
