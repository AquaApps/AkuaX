package fan.akua.widget.acgview;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class AcgImgProcessor {
    static {
        if (!OpenCVLoader.initDebug())
            Log.e("OpenCV", "Unable to load OpenCV!");
        else
            Log.d("OpenCV", "OpenCV loaded Successfully!");
    }

    public static Bitmap process(Bitmap bitmap,double threshold) {
        Mat rawMat = bitmapToMat(bitmap);
        Mat grayMat = new Mat();
        Imgproc.cvtColor(rawMat, grayMat, Imgproc.COLOR_BGR2GRAY);

        Mat edges = new Mat();
        double avgBrightness = AcgImgProcessor.calculateBrightness(grayMat);
        Imgproc.Canny(grayMat, edges, avgBrightness, threshold);
        Mat mat = performDilation(edges, 5);
        return matToBitmap(mat);
    }
    // 计算图像的亮度均值
    public static double calculateBrightness(Mat grayMat) {

        // 计算灰度图像的均值
        Scalar meanScalar = Core.mean(grayMat);
        double meanValue = meanScalar.val[0];

        return meanValue;
    }
    // 执行形态学膨胀操作
    public static Mat performDilation(Mat inputMat, int kernelSize) {
        Mat outputMat = new Mat();

        // 定义膨胀操作的结构元素（内核）
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(kernelSize, kernelSize));

        // 执行膨胀操作
        Imgproc.dilate(inputMat, outputMat, kernel);

        return outputMat;
    }
    // 将Bitmap转换为Mat
    public static Mat bitmapToMat(Bitmap bitmap) {
        Mat mat = new Mat(bitmap.getHeight(), bitmap.getWidth(), CvType.CV_8UC4);
        Utils.bitmapToMat(bitmap, mat);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGBA2BGR); // 可选，如果需要将Bitmap转换为BGR格式的Mat
        return mat;
    }

    // 将Mat转换为Bitmap
    public static Bitmap matToBitmap(Mat mat) {
        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }
}
