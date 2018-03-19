
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.cvWaitKey;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * Класс загружает изображения, производит преобразования, сохраняет как .bmp
 * Часть курсовой работы
 */
public class Parameters {

    public static opencv_core.IplImage sobels(opencv_core.IplImage gray) {
        System.out.println("Sobel start...");
        opencv_core.IplImage result = cvCloneImage(gray);
        String name;
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 3; j++) {

                for (int k = 3; k < 8; k+=2) {
                    if (!(i==0 && j==0)){
                        cvSobel(
                                gray, //src
                                result, //dst
                                i, //0, 1, 2
                                j, //0, 1, 2
                                k //3x3, 5x5, 7x7, 1x1
                        );
                        name = "Sobel\\Sobel#" + i + "&" + j + "_k" + k + ".bmp";
                        cvSaveImage(name, result);
                    }
                }

            }

        }
        System.out.println("Success end of func Sobel");
        return  result;
    }

    public static opencv_core.IplImage roberts(opencv_core.IplImage gray) {
        System.out.println("Roberts start...");
        opencv_core.IplImage result = cvCloneImage(gray);
        String name;
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 3; j++) {

                for (int k = 3; k < 8; k+=2) {
                    if (!(i==0 && j==0)){

                        cvSobel(
                                gray, //src
                                result, //dst
                                i, //0, 1, 2
                                j, //0, 1, 2
                                k //3x3, 5x5, 7x7, 1x1
                        );
                        name = "Roberts\\roberts#" + i + "&" + j + "_k" + k + ".bmp";
                        cvSaveImage(name, result);
                    }
                }

            }

        }
        System.out.println("Success end of func Sobel");
        return  result;
    }

    public static opencv_core.IplImage dilate (opencv_core.IplImage gray) {
        System.out.println("Dilate start...");
        opencv_core.IplImage result = cvCloneImage(gray);
        IplConvKernel kkern;

        kkern = cvCreateStructuringElementEx(2, 2, 1, 1, CV_SHAPE_ELLIPSE); //i = radius
        for (int j = 1; j < 5; j++) {
            cvDilate(result, result, kkern, j);
            cvSaveImage("Dilate\\dilate#"+1+"#"+j+".bmp", result);
        }

        System.out.println("Success end of func Dilate");
        return  result;
    }

    public static opencv_core.IplImage erode (opencv_core.IplImage gray) {
        System.out.println("Erode start...");
        opencv_core.IplImage result = cvCloneImage(gray);
        IplConvKernel kkern;

        kkern = cvCreateStructuringElementEx(2, 2, 1, 1, CV_SHAPE_ELLIPSE); //i = radius
        for (int j = 1; j < 5; j++) {
            cvErode(result, result, kkern, j);
            cvSaveImage("Erode\\erode#"+1+"#"+j+".bmp", result);
        }

        System.out.println("Success end of func erode");
        return  result;
    }

    public static void open(String filename) {
        opencv_core.IplImage image = cvLoadImage(filename);
        if (image != null) {
            //cvShowImage(filename, image);

            opencv_core.CvSize sz = cvSize(image.width(), image.height());
            opencv_core.IplImage gr = cvCreateImage(sz, image.depth(), 1);
            cvCvtColor(image, gr, CV_BGR2GRAY);
            //cvSmooth(gr, gr, CV_GAUSSIAN, 3,3,3,3);

            //sobel
            sobels(gr);
            //dilate
            dilate(gr);

            erode(gr);

            System.out.println("Press any key to close app...");
            cvWaitKey();
        }
    }

    public static void main(String[] args) {
        open("image.bmp");
    }

}
