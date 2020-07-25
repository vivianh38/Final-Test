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
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Vivian Huang
    ImageView imageView;
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
        Button btn = findViewById(R.id.button);
        Log.d("viv","alright so far");
        btn.setOnClickListener(new View.OnClickListener() {
            int count;
            @Override
            public void onClick(View view) {
                if(count != 1){
                    Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    String output = decodeQRCode(bm);
                    //textView.setText(output);
                    Mat img = new Mat(bm.getWidth(), bm.getHeight(), CvType.CV_8UC4);
                    Utils.bitmapToMat(bm, img);
                    Scalar lineColor = new Scalar(255, 0, 0, 255);
                    int lineWidth = 8;
                    String [] lines = output.split(";");
                    int totalSets = lines.length;
                    for(int i = 0; i < totalSets; i++) {
                        String [] coor = lines[i].split(" ");
                        int totalCoors = coor.length;
                        Point[] points = {new Point(), new Point()};
                        for(int k = 0; k < totalCoors; k++){
                            String [] xny = coor[k].split(",");
                            points[k] = new Point(Integer.parseInt(xny[0]),Integer.parseInt(xny[1]));
                        }
                        Imgproc.line(img, points[0], points[1], lineColor, lineWidth);
                    }
                    Bitmap bitmap = Bitmap.createBitmap(img.width(), img.height(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(img, bitmap);
                    imageView.setImageBitmap(bitmap);
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
