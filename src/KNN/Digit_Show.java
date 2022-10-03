package KNN;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Digit_Show{

    /**
     * Convert a data matrix to a visualization.
     * @param pxls
     * @return Image
     */
    public Image intToImg(int[][] pxls){
        BufferedImage image = new BufferedImage(pxls.length, pxls[0].length, BufferedImage.TYPE_INT_RGB);
        int[] pxlsr=new int[pxls[0].length*pxls.length];
        int k=0;
        for(int i=0;i<pxls.length;i++){
            for(int j=0;j<pxls[0].length;j++){
                pxlsr[k++]=pxls[i][j];
            }
        }
        image.setRGB(0, 0, pxls[0].length, pxls.length, pxlsr, 0, pxls[0].length);
        return image;
    }

}









