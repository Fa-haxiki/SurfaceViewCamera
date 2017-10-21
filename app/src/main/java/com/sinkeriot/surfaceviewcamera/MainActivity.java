package com.sinkeriot.surfaceviewcamera;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private Button recordbutton;
    private Button stopbutton;
    private MediaRecorder mediaRecorder;
    File videoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".mp4");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(176, 144);
        surfaceView.getHolder().setKeepScreenOn(true);
        recordbutton = (Button) this.findViewById(R.id.recordbutton);
        stopbutton = (Button) this.findViewById(R.id.stopbutton);



    }

    @Override
    protected void onResume() {
        super.onResume();
        //startRecording();

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        if (mediaRecorder != null) {
//            mediaRecorder.stop();
//            mediaRecorder.release();
//            mediaRecorder = null;
//        }
//    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage (android.os.Message msg) {
            switch (msg.what){
                case 1:
                    try {
                        Thread.sleep(20000);
                        stopRecording();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    startRecording();

                    break;
            }
        }
    };

    //    @zz
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_DOWN){
//            layout.setVisibility(ViewGroup.VISIBLE);
//        }
//        return super.onTouchEvent(event);
//    }

    public void record(View v) {
        switch (v.getId()) {
            case R.id.recordbutton:
                try {
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setVideoSize(320, 240);
                    mediaRecorder.setVideoFrameRate(5);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                    mediaRecorder.setOutputFile(videoFile.getAbsolutePath());
                    mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recordbutton.setEnabled(false);
                stopbutton.setEnabled(true);
                break;

            case R.id.stopbutton:
                if (mediaRecorder != null) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                }
                recordbutton.setEnabled(true);
                stopbutton.setEnabled(false);
                break;
        }
    }

    private void startRecording() {
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setVideoSize(320, 240);
            mediaRecorder.setVideoFrameRate(5);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mediaRecorder.setOutputFile(videoFile.getAbsolutePath());
            mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;

            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        }
    }
}
