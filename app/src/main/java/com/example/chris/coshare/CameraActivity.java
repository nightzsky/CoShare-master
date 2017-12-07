package com.example.chris.coshare;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

//Create live view for camera through using SurfaceView and SurfaceHolder.Callback for getting camera preview input
public class CameraActivity extends AppCompatActivity {

    private SurfaceView cameraPreview;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;

    private TextView Result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraPreview = (SurfaceView) findViewById(R.id.preview);

        Result = (TextView) findViewById(R.id.result);

        createCameraSource();
    }

    //create camera live view with barcode detector
    private void createCameraSource() {
        //create barcode detector
        barcodeDetector = new BarcodeDetector.Builder(CameraActivity.this).build();
        //cameraSource is used as communication between the data received from camera live preview and the data sent to the barcode detector for analysis
        cameraSource = new CameraSource.Builder(CameraActivity.this, barcodeDetector)
                .setAutoFocusEnabled(true) //autofocus
                .setRequestedPreviewSize(1600, 1024) //set the preview size
                .build();

        //receive data from camera live view
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //to check if the permission is granted,if not, request to access to camera
                if (ActivityCompat.checkSelfPermission(CameraActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CameraActivity.this,
                            new String[]{android.Manifest.permission.CAMERA}, 1);
                    return;
                }
                try {
                    //start camera live preview
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                //this method for handling when the orientation of the screen changes from portrait to landscape and vice versa
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //stop the camerasource when the surface is destroyed
                cameraSource.stop();
            }
        });

        //attach processor to barcodeDetector to allow analysis of the qrcode received
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                //when barcode detector is released
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                //received an array of qrcodes, we only take the first qrcode we scan
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();

                //when received qrcode
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (qrcodes.size() > 0) {

                            //pass the data received, in this case, tableID, to another intent to do analysis
                            Result.setText(qrcodes.valueAt(0).displayValue);
                            Log.i("Norman",qrcodes.valueAt(0).displayValue);

                            Intent intent = new Intent(CameraActivity.this, HomePage.class);
                            intent.putExtra("barcode", qrcodes.valueAt(0).displayValue);
                            finish(); //finish this activity
                            if (isFinishing()) { //when finish, transit to the other activity
                                startActivityForResult(intent,1);
                            }


                        }

                    }
                });
            }
        });

    }



    @Override
    protected void onDestroy(){  //Activity Lifecycle, when the app is closed, release both cameraSource and barcodeDetector
        super.onDestroy();
        if (cameraSource != null || barcodeDetector != null) {
            cameraSource.release();
            barcodeDetector.release();
        }
    }

    @Override
    protected void onPause(){   //when the activity is paused (eg, when qrcode is detected), release barcodeDetector to avoid continual scanning of the qrcode and also stop the camera live view
        super.onPause();
        if (barcodeDetector != null || cameraSource != null) {
            barcodeDetector.release();
            cameraSource.stop();
        }

    }


}