package KNN;

import java.io.File;
import java.io.IOException;


public class Test extends KNN_Implement{
    /**
     * Read the file and store it in the Diabetes_Data class.
     * Subclass of KNN_Implement
     * @param csv
     * @throws IOException
     */
    public Test(File csv) throws IOException {
        super(csv);
    }

    public Test(File csv, boolean digit) throws IOException {
        super(csv, digit);
    }
}
