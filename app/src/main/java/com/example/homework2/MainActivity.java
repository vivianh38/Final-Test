package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.InstallCallbackInterface;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.aruco.Aruco;
import org.opencv.aruco.Dictionary;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.QRCodeDetector;


public class MainActivity extends AppCompatActivity {
    //Vivian Huang
    ImageView imageView;
    ImageView imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!OpenCVLoader.initDebug()){
            Log.d("test","OpenCV not available");
        }
        else{
            Log.d("test", "OpenCV is fine");
        }

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        Button btn = findViewById(R.id.button);

        Log.d("viv","alright so far");


        btn.setOnClickListener(new View.OnClickListener() {
            int count;
            @Override
            public void onClick(View view) {

                if(count != 1){
                    Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    Bitmap bmm = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();
                    String output = decodeQRCode(bm);


                    //textView.setText(output);

                    Mat img2 = new Mat(bmm.getWidth(), bmm.getHeight(), CvType.CV_8UC4);
                    Utils.bitmapToMat(bmm, img2);

                    Log.d("viv", " "+ output);

                    Scalar lineColor = new Scalar(255, 0, 0, 255);
                    int lineWidth = 8;


                    String [] lines = output.split(" ");

                    Point[] points = {new Point(), new Point(), new Point(), new Point(), new Point(), new Point()};
                    int totalSets = lines.length;

                    for(int i = 0; i < totalSets; i++) {
                        String [] coords = lines[i].split(",");

                        points[i] = new Point(Integer.parseInt(coords[0])/2,Integer.parseInt(coords[1])/2);

                        Log.d("viv", " "+points[i]);

                        //Imgproc.line(img2, points[0], points[1], lineColor, lineWidth);
                        Log.d("viv","alright so far!!");
                    }
                    Imgproc.line(img2, points[0], points[1], lineColor, lineWidth);
                    Imgproc.line(img2, points[1], points[2], lineColor, lineWidth);
                    Imgproc.line(img2, points[2], points[3], lineColor, lineWidth);
                    Imgproc.line(img2, points[3], points[4], lineColor, lineWidth);
                    Imgproc.line(img2, points[4], points[5], lineColor, lineWidth);


                    imageView.setImageBitmap(bm);

                    Bitmap bitmap2 = Bitmap.createBitmap(img2.width(), img2.height(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(img2, bitmap2);
                    imageView2.setImageBitmap(bitmap2);

                }
                count++;
            }
        });
    }



    String decodeQRCode(Bitmap bitmap){
        Mat mat = new Mat(bitmap.getWidth(), bitmap.getHeight(), CvType.CV_8UC4);
        Utils.bitmapToMat(bitmap,mat);
        QRCodeDetector qrCodeDetector = new QRCodeDetector();
        String result = qrCodeDetector.detectAndDecode(mat);
        Log.d("viv","outcome:"+result);
        return result;
    }
}
