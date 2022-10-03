package KNN;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    /**
     * execute knn
     * create diabetes plot
     * show the actual ”wall-clock” time
     * Pixel matrix visualization
     * visualization of line graphs
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        //diabetes database
        File train_csv = new File(
                "/Users/adisihansun/Desktop/machine learning/project_1/Machine_Learning_Project_1_KNN_Algorithm/src/KNN/train.csv");
        File test_csv = new File("/Users/adisihansun/Desktop/machine learning/project_1/Machine_Learning_Project_1_KNN_Algorithm/src/KNN/test.csv");

        //digit database
        File train_csv_digit = new File(
                "/Users/adisihansun/Desktop/machine learning/project_1/Machine_Learning_Project_1_KNN_Algorithm/src/KNN/digit_train");
        File test_csv_digit = new File("/Users/adisihansun/Desktop/machine learning/project_1/Machine_Learning_Project_1_KNN_Algorithm/src/KNN/digit_test");
        try {
            //The knn implementation of the digit database.
            Scanner scanner4 = new Scanner(System.in);
            System.out.print("execute knn, Input your choice (digit or diabetes): ");
            String knn = scanner4.nextLine();
            if (knn.equals("digit")){
                //The process of visualization and selection of k values is not included.
                long start2 = System.currentTimeMillis();//The start time of the knn algorithm for the digit database.
                KNN_Implement knn_implement_digit = new KNN_Implement(train_csv_digit, true);
                ArrayList<Data_Set> train_set_digit = Normalized(knn_implement_digit.set,true);
                Test test_digit = new Test(test_csv_digit,true);
                ArrayList<Data_Set> test_set_digit = Normalized(test_digit.set, true);
                int error_count_digit = 0;
                int[][] l = new int[10][10];
                int count = 0;
                for (Data_Set test_data : test_set_digit) {
                    int label = knn_implement_digit.knn(test_data, train_set_digit, 3, true, 2);//3
                    l[test_data.getLabel()][label] += 1;
                    count++;
                    if (label != test_data.getLabel()) {
                        error_count_digit++;
                    }
                }
                int count_0 = 0;
                for (int i = 0; i < 10; i++){
                    count_0 += l[3][i];
                }
                long end2 = System.currentTimeMillis();
                System.out.println("the actual ”wall-clock” time is " + (end2 - start2) + "miliseconds");
                System.out.println((count == test_set_digit.size()));
                System.out.println("digit error rate：" + (double)error_count_digit / test_set_digit.size() * 100 + "%");
                System.out.println("digit accuracy：" + (double)(test_set_digit.size() - error_count_digit) / test_set_digit.size() * 100 + "%");
                System.out.println("digit 3 accuracy：" + (double)l[3][3] / count_0 * 100 + "%");
                System.out.println("Confusion matrix:");
                for (int[] arr : l){
                    for (int i : arr){
                        System.out.print(" | " + i + " | ");
                    }
                    System.out.print("\n");
                }
            }else{
                //The knn implementation of the diabetes database.
                //The process of visualization and selection of k values is not included.
                long start = System.currentTimeMillis();//The start time of the knn algorithm for the diabetes database.
                KNN_Implement knn_implement = new KNN_Implement(train_csv);
                ArrayList<Data_Set> train_set = Normalized(knn_implement.set, false);
                Test test = new Test(test_csv);
                ArrayList<Data_Set> test_set = Normalized(test.set, false);
                int error_count = 0;
                int TP = 0;
                int FP = 0;
                int FN = 0;
                int TN = 0;
                for (Data_Set test_data : test_set) {
                    int label = knn_implement.knn(test_data, train_set, 11, false, 1);//21
                    if (label != test_data.getOutcome()) {
                        if (label == 0){
                            FN++;
                        }else{
                            FP++;
                        }
                        error_count++;
                    }else{
                        if (label == 0){
                            TN++;
                        }else{
                            TP++;
                        }
                    }
                }
                long end = System.currentTimeMillis();
                System.out.println("      positive" + " | " + "negative");
                System.out.println("true " + " | " + TP + " | " + TN);
                System.out.println("false" + " | " + FP + " | " + FN);
                System.out.println("error rate：" + (double)error_count / test_set.size() * 100 + "%");
                System.out.println("Accuracy：" + (double)(TP + TN) / (double)(FP + FN + TP + TN) * 100 + "%");
                System.out.println("the actual ”wall-clock” time is " + (end - start) + "miliseconds");


                //create diabetes plot
                Scanner scanner3 = new Scanner(System.in);
                System.out.print("create diabetes plot, Input your choice (true or false): ");
                String Scatter_Plot = scanner3.nextLine();
                if (Scatter_Plot.equals("true")){
                    Create_Scatter_Plot();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Pixel matrix visualization
        Scanner scanner = new Scanner(System.in);
        System.out.print("Pixel matrix visualization, Input your choice (true or false): ");
        String show_digit = scanner.nextLine();
        if (show_digit.equals("true")){
            int[] arrayimage = {
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,47,134,214,255,254,176,138,14,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,27,188,253,253,196,160,160,231,253,181,48,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,30,215,253,230,86,4,0,0,8,182,253,186,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,130,253,208,23,0,0,0,0,0,212,253,156,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,183,253,81,0,0,0,0,0,0,239,213,21,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,234,253,31,0,0,0,0,0,26,243,104,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,142,253,171,6,0,0,0,20,209,253,72,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,56,235,253,196,80,50,25,136,253,253,72,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,59,193,250,253,245,229,232,228,253,72,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,25,98,98,98,44,253,234,28,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,42,253,234,28,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,42,253,253,72,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,42,253,230,18,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,42,253,252,68,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,42,253,231,21,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,135,253,222,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,76,253,222,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,42,253,222,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,42,253,229,17,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,42,253,185,13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
            };//Enter the pixel matrix to visualize
            int[][] arr = new int[28][28];
            int j = 0;
            for (int i = 0; i < arrayimage.length; i++){
                arr[j][i % 28] = arrayimage[i];
                if ((i + 1) % 28 == 0){
                    j++;
                }
            }
            Digit_Show show = new Digit_Show();
            JFrame jf = new JFrame();
            JLabel jl = new JLabel();
            ImageIcon ii = new ImageIcon(show.intToImg(arr));
            jl.setIcon(ii);
            jf.add(jl);
            jf.pack();
            jf.setVisible(true);
        }

        //visualization of graphs
        //About the relationship between k and validation error，
        Scanner scanner2 = new Scanner(System.in);
        System.out.print("a line graph about k and accuracy, Input your choice (digit or diabetes) (): ");
        String graphs = scanner2.nextLine();
        if (graphs.equals("digit")){
            System.out.print("digit may take a while to run due to 10-fold cross-validation, please be patient");
            EventQueue.invokeLater(() -> {
                var ex = new Line_chart(true);
                ex.setVisible(true);
            });//Visualization of the graph of the digit database
        }else{
            EventQueue.invokeLater(() -> {
                var ex = new Line_chart(false);
                ex.setVisible(true);
            });//Visualization of the graph of the diabetes database
        }
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

        if (digit) {
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
     * Find the maximum and minimum values for each feature in the diabetes database.
     * @param data_set
     * @return Map<String, Double>
     */
    public static Map<String, Double> findMaxAndMin(ArrayList<Data_Set> data_set) {

        Map<String, Double> map = new HashMap<String, Double>();
        double Max_Pregnancies = Integer.MIN_VALUE;
        double Max_Glucose= Integer.MIN_VALUE;
        double Max_BloodPressure= Integer.MIN_VALUE;
        double Max_SkinThickness= Integer.MIN_VALUE;
        double Max_Insulin= Integer.MIN_VALUE;
        double Max_BMI= Integer.MIN_VALUE;
        double Max_DiabetesPedigreeFunction= Integer.MIN_VALUE;
        double Max_Age= Integer.MIN_VALUE;

        double Min_Pregnancies = Integer.MAX_VALUE;
        double Min_Glucose = Integer.MAX_VALUE;
        double Min_BloodPressure = Integer.MAX_VALUE;
        double Min_SkinThickness = Integer.MAX_VALUE;
        double Min_Insulin = Integer.MAX_VALUE;
        double Min_BMI = Integer.MAX_VALUE;
        double Min_DiabetesPedigreeFunction = Integer.MAX_VALUE;
        double Min_Age = Integer.MAX_VALUE;


        for (Data_Set data : data_set) {
            if (data.getPregnancies() > Max_Pregnancies) {
                Max_Pregnancies = data.getPregnancies();
            }
            if (data.getInsulin() > Max_Insulin) {
                Max_Insulin = data.getInsulin();
            }
            if (data.getGlucose() > Max_Glucose) {
                Max_Glucose = data.getGlucose();
            }
            if (data.getSkinThickness() > Max_SkinThickness) {
                Max_SkinThickness = data.getSkinThickness();
            }
            if (data.getBMI() > Max_BMI) {
                Max_BMI = data.getBMI();
            }
            if (data.getDiabetesPedigreeFunction() > Max_DiabetesPedigreeFunction) {
                Max_DiabetesPedigreeFunction = data.getDiabetesPedigreeFunction();
            }
            if (data.getAge() > Max_Age) {
                Max_Age = data.getAge();
            }
            if (data.getBloodPressure() > Max_BloodPressure) {
                Max_BloodPressure = data.getBloodPressure();
            }


            if (data.getPregnancies() < Min_Pregnancies) {
                Min_Pregnancies = data.getPregnancies();
            }
            if (data.getInsulin() < Min_Insulin) {
                Min_Insulin = data.getInsulin();
            }
            if (data.getGlucose() < Min_Glucose) {
                Min_Glucose = data.getGlucose();
            }
            if (data.getSkinThickness() < Min_SkinThickness) {
                Min_SkinThickness = data.getSkinThickness();
            }
            if (data.getBMI() < Min_BMI) {
                Min_BMI = data.getBMI();
            }
            if (data.getDiabetesPedigreeFunction() < Min_DiabetesPedigreeFunction) {
                Min_DiabetesPedigreeFunction = data.getDiabetesPedigreeFunction();
            }
            if (data.getAge() < Min_Age) {
                Min_Age = data.getAge();
            }
            if (data.getBloodPressure() < Min_BloodPressure) {
                Min_BloodPressure = data.getBloodPressure();
            }
        }
        map.put("Max_Pregnancies", Max_Pregnancies);
        map.put("Max_Glucose", Max_Glucose);
        map.put("Max_BloodPressure", Max_BloodPressure);
        map.put("Max_SkinThickness", Max_SkinThickness);
        map.put("Max_Insulin", Max_Insulin);
        map.put("Max_BMI", Max_BMI);
        map.put("Max_DiabetesPedigreeFunction", Max_DiabetesPedigreeFunction);
        map.put("Max_Age", Max_Age);

        map.put("Min_Pregnancies", Min_Pregnancies);
        map.put("Min_Glucose", Min_Glucose);
        map.put("Min_BloodPressure", Min_BloodPressure);
        map.put("Min_SkinThickness", Min_SkinThickness);
        map.put("Min_Insulin", Min_Insulin);
        map.put("Min_BMI", Min_BMI);
        map.put("Min_DiabetesPedigreeFunction", Min_DiabetesPedigreeFunction);
        map.put("Min_Age", Min_Age);


        return map;

    }

    /**
     *Find the maximum and minimum values for each pixel location in the MNIST digits database.
     * @param data_set
     * @return Map<String, Map<Integer, Double>>
     */
    public static Map<String, Map<Integer, Double>> findMaxAndMin_Digit(ArrayList<Data_Set> data_set){
        Map<String, Map<Integer, Double>> map = new HashMap<String, Map<Integer, Double>>();
        Map<Integer, Double> max_map = new HashMap<Integer, Double>();
        Map<Integer, Double> min_map = new HashMap<Integer, Double>();

        for (Data_Set data : data_set){
            for (int i = 0; i < data.getPixel().length; i++){
                double Max = max_map.getOrDefault(i, 0.0) < data.getPixel()[i] ? data.getPixel()[i] : max_map.getOrDefault(i, 0.0);
                max_map.put(i, Max);
                double Min = min_map.getOrDefault(i, 0.0) > data.getPixel()[i] ? data.getPixel()[i] : min_map.getOrDefault(i, 0.0);
                min_map.put(i, Min);
            }
        }
        map.put("Max", max_map);
        map.put("Min", min_map);
        return map;
    }

    /**
     * Create scatter plot
     * @throws IOException
     */
    public static void Create_Scatter_Plot() throws IOException {
        File All_data_csv = new File(
                "/Users/adisihansun/Desktop/machine learning/project_1/Machine_Learning_Project_1_KNN_Algorithm/src/KNN/diabetes.csv");

        KNN_Implement knn_implement = new KNN_Implement(All_data_csv);
        ArrayList<Data_Set> train_set = Normalized(knn_implement.set, false);
        ArrayList<String> feature_set = new ArrayList<>(){{
            add("Pregnancies");
            add("Glucose");
            add("BloodPressure");
            add("SkinThickness");
            add("Insulin");
            add("BMI");
            add("DiabetesPedigreeFunction");
            add("Age");
        }};
        int count = 0;
        for (int i = 0; i < feature_set.size(); i++){
            String str1 = feature_set.get(i);

            for (String str2 : feature_set){
                if (str1 == str2){
                    continue;
                }
                count++;
                SwingUtilities.invokeLater(() -> {
                    Scatter_plot plot = new Scatter_plot("Scatter plot", train_set, str1, str2);
                    plot.setSize(1000, 1000);
                    plot.setLocationRelativeTo(null);
                    plot.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    plot.setVisible(true);
                });
            }

        }
        System.out.println(count);
    }

}
