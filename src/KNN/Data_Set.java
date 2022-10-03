package KNN;

public class Data_Set {
    private double Pregnancies;
    private double Glucose;
    private double BloodPressure;
    private double SkinThickness;
    private double Insulin;
    private double BMI;
    private double DiabetesPedigreeFunction;
    private double Age;

    private double Distance;
    private int Outcome;

    private double[] Pixel = new double[785];
    private int Label;

    public double[] getPixel() {
        return Pixel;
    }

    public void setPixel(double[] pixel) {
        Pixel = pixel;
    }

    public int getLabel() {
        return Label;
    }

    public void setLabel(int label) {
        Label = label;
    }
    public double getPregnancies() {
        return Pregnancies;
    }

    public void setPregnancies(double pregnancies) {
        Pregnancies = pregnancies;
    }

    public double getGlucose() {
        return Glucose;
    }

    public void setGlucose(double glucose) {
        Glucose = glucose;
    }

    public double getBloodPressure() {
        return BloodPressure;
    }

    public void setBloodPressure(double bloodPressure) {
        BloodPressure = bloodPressure;
    }

    public double getSkinThickness() {
        return SkinThickness;
    }

    public void setSkinThickness(double skinThickness) {
        SkinThickness = skinThickness;
    }

    public double getInsulin() {
        return Insulin;
    }

    public void setInsulin(double insulin) {
        Insulin = insulin;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public double getDiabetesPedigreeFunction() {
        return DiabetesPedigreeFunction;
    }

    public void setDiabetesPedigreeFunction(double diabetesPedigreeFunction) {
        DiabetesPedigreeFunction = diabetesPedigreeFunction;
    }

    public double getAge() {
        return Age;
    }

    public void setAge(double age) {
        Age = age;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public int getOutcome() {
        return Outcome;
    }

    public void setOutcome(int outcome) {
        Outcome = outcome;
    }
}
