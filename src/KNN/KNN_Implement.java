package KNN;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class KNN_Implement {
    public ArrayList<Data_Set> set;

    public KNN_Implement(File csv) throws IOException{
        set = Read_file1(csv);
    }

    public KNN_Implement(File csv, boolean digit) throws IOException{
        set = Read_file2(csv);
    }

    /**
     *Read the diabetes file and store it in the Data_Set class.
     * @param csv
     * @throws ArrayList<Data_Set>
     */
    public ArrayList<Data_Set> Read_file1(File csv){
        ArrayList<Data_Set> list = new ArrayList<>();
        try{
            BufferedReader textFile = new BufferedReader(new FileReader(csv));
            String lineDta = "";

            while ((lineDta = textFile.readLine()) != null){
                if (!Character.isDigit(lineDta.charAt(0))){
                    continue;
                }else {
                    Data_Set diabetes_data = new Data_Set();
                    String[] str = lineDta.split(",");
                    diabetes_data.setPregnancies(Double.valueOf(str[0]));

                    diabetes_data.setGlucose(Double.valueOf(str[1]));

                    diabetes_data.setBloodPressure(Double.valueOf(str[2]));

                    diabetes_data.setSkinThickness(Double.valueOf(str[3]));

                    diabetes_data.setInsulin(Double.valueOf(str[4]));

                    diabetes_data.setBMI(Double.valueOf(str[5]));

                    diabetes_data.setDiabetesPedigreeFunction(Double.valueOf(str[6]));

                    diabetes_data.setAge(Double.valueOf(str[7]));

                    diabetes_data.setOutcome(Integer.valueOf(str[8]));
                    list.add(diabetes_data);
                }

            }

        }catch (FileNotFoundException e){
            System.out.println("Can not find file");
        }catch (IOException e){
            System.out.println("file read error");
        }
        return list;
    }

    /**
     * Read the MNIST digits file and store it in the Data_Set class.
     * @param csv
     * @return ArrayList<Data_Set>
     */
    public ArrayList<Data_Set> Read_file2(File csv){
        ArrayList<Data_Set> list = new ArrayList<>();
        try{
            BufferedReader textFile = new BufferedReader(new FileReader(csv));
            String lineDta = "";

            while ((lineDta = textFile.readLine()) != null){
                if (!Character.isDigit(lineDta.charAt(0))){
                    continue;
                }else {
                    Data_Set data_set = new Data_Set();
                    String[] str = lineDta.split(",");
                    data_set.setLabel(Integer.valueOf(str[0]));
                    String[] s = Arrays.copyOfRange(str, 1, 785);
                    double[] doubleValues = Arrays.stream(s).mapToDouble(Double::parseDouble).toArray();
                    data_set.setPixel(doubleValues);

                    list.add(data_set);
                }

            }

        }catch (FileNotFoundException e){
            System.out.println("Can not find file");
        }catch (IOException e){
            System.out.println("file read error");
        }
        return list;
    }

    /**
     * Calculate the distance at different p-values
     * @param new_data
     * @param old_data
     * @param Digit
     * @param p
     * @return distance
     */
    public double Calculate_Distance(Data_Set new_data, Data_Set old_data, boolean Digit, int p){
        double distance = 0;
        double ans;
        if (Digit){
            if (p == Integer.MAX_VALUE){
                for (int i = 0; i < new_data.getPixel().length; i++){//可以隔列计算
                    distance = Math.max(Math.abs(new_data.getPixel()[i] - old_data.getPixel()[i]), distance);
                }

            }else{
                for (int i = 0; i < new_data.getPixel().length; i++){//可以隔列计算
                    distance += Math.pow(Math.abs(new_data.getPixel()[i] - old_data.getPixel()[i]), p);
                }
            }

            if (p == 1){
                ans = distance;
            }else if (p == 2){
                ans = Math.sqrt(distance);
            }else{
                ans = distance;
            }

        }else {
            if (p == Integer.MAX_VALUE){
                distance = Math.max(Math.abs(new_data.getPregnancies() - old_data.getPregnancies()), distance);
                distance = Math.max(Math.abs(new_data.getGlucose() - old_data.getGlucose()), distance);
                distance = Math.max(Math.abs(new_data.getBloodPressure() - old_data.getBloodPressure()), distance);
                distance = Math.max(Math.abs(new_data.getSkinThickness() - old_data.getSkinThickness()), distance);
                distance = Math.max(Math.abs(new_data.getInsulin() - old_data.getInsulin()), distance);
                distance = Math.max(Math.abs(new_data.getBMI() - old_data.getBMI()), distance);
                distance = Math.max(Math.abs(new_data.getDiabetesPedigreeFunction() - old_data.getDiabetesPedigreeFunction()), distance);
                distance = Math.max(Math.abs(new_data.getAge() - old_data.getAge()), distance);
            }else{
                distance += Math.pow(Math.abs(new_data.getPregnancies() - old_data.getPregnancies()), p);
                distance += Math.pow(Math.abs(new_data.getGlucose() - old_data.getGlucose()), p);
                distance += Math.pow(Math.abs(new_data.getBloodPressure() - old_data.getBloodPressure()), p);
                distance += Math.pow(Math.abs(new_data.getSkinThickness() - old_data.getSkinThickness()), p);
                distance += Math.pow(Math.abs(new_data.getInsulin() - old_data.getInsulin()), p);
                distance += Math.pow(Math.abs(new_data.getBMI() - old_data.getBMI()), p);
                distance += Math.pow(Math.abs(new_data.getDiabetesPedigreeFunction() - old_data.getDiabetesPedigreeFunction()), p);
                distance += Math.pow(Math.abs(new_data.getAge() - old_data.getAge()), p);
            }

            if (p == 1){
                ans = distance;
            }else if (p == 2){
                ans = Math.sqrt(distance);
            }else{
                ans = distance;
            }
        }
        return ans;
    }

    /**
     * First, calculate the distance between the current node and the data set, sort,
     * select the data in the first k data sets, calculate their frequency, and return the prediction.
     * @param diabetes_data
     * @param set
     * @param k
     * @param Digit
     * @param p
     * @return
     */
    public int knn(Data_Set diabetes_data, ArrayList<Data_Set> set, int k, boolean Digit, int p){
        //Calculate distance
        for (Data_Set old_data : set){
            double distance = Calculate_Distance(diabetes_data, old_data, Digit, p);
            old_data.setDistance(distance);
        }
        //Ascending sort
        Collections.sort(set, Comparator.comparingDouble(Data_Set::getDistance));
        //Determine the frequency of the first k points
        if (Digit){
            double[] label = new double[10];
            double tmp = k;
            for (int i = 0; i < k; i++) {
                Data_Set data = set.get(i);
                switch (data.getLabel()){
                    case 0:
//                        label[0]+=tmp;
                        label[0] += (double)1 + (double) tmp / data.getDistance();
                        break;
                    case 1:
//                        label[1]+=tmp;
                        label[1] += (double)1 + (double) tmp / data.getDistance();
                        break;
                    case 2:
//                        label[2]+=tmp;
                        label[2] += (double)1 + (double) tmp / data.getDistance();
                        break;
                    case 3:
//                        label[3]+=tmp;
                        label[3] += (double)1 + (double) tmp / data.getDistance();
                        break;
                    case 4:
//                        label[4]+=tmp;
                        label[4] += (double)1 + (double) tmp / data.getDistance();
                        break;
                    case 5:
//                        label[5]+=tmp;
                        label[5] += (double)1 + (double) tmp / data.getDistance();
                        break;
                    case 6:
//                        label[6]+=tmp;
                        label[6] += (double)1 + (double) tmp / data.getDistance();
                        break;
                    case 7:
//                        label[7]+=tmp;
                        label[7] += (double)1 + (double) tmp / data.getDistance();
                        break;
                    case 8:
//                        label[8]+=tmp;
                        label[8] += (double)1 + (double) tmp / data.getDistance();
                        break;
                    case 9:
//                        label[9]+=tmp;
                        label[9] += (double)1 + (double) tmp / data.getDistance();
                        break;
                }
                tmp--;
            }
            double ans = 0;
            int index = 0;
            for (int i = 0; i < 10; i++){
                if (ans < label[i]){
                    ans = label[i];
                    index = i;
                }
            }
            return index;
        }else {
            double label_0 = 0;
            double label_1 = 0;
//            double tmp = k;
//            double a = 1;
//            double b = 0;
//            double c = 10;
            for (int i = 0; i < k; i++){
                Data_Set data = set.get(i);
                if (data.getOutcome() == 0){
//                label_0 += 1;
//                label_0 += 1 + tmp;
//                label_0 += (double) 1 + a * Math.pow(Math.E, -(Math.pow(data.getDistance() - b, 2) / (2 * Math.pow(c, 2))));
                    label_0 += (double)1 + (double) 1 / data.getDistance();
                }else{
//                label_1 += 1;
//                label_1 += (double) 1 + a * Math.pow(Math.E, -(Math.pow(data.getDistance() - b, 2) / (2 * Math.pow(c, 2))));
                    label_1 += (double)1 + (double) 1 / data.getDistance();
                }
//                tmp--;
            }
            //prediction
            return label_0 > label_1 ? 0 : 1;
        }
    }

}
