package com.example.capstoneapp;

/*
public class SecondActivity extends CameraActivity {

    //Variable to pass the cameraview id
    CameraBridgeViewBase cameraBridgeViewBase;
    // Variables: previous frame and current frame, these are going to be Matrices, and then fin
    // the difference btw both frames
    Mat curr_gray, prev_gray, diff, rgb;
    List<MatOfPoint> cntsl;
    // boolean
    boolean is_init;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // get permission for camera
        getPermission();
        is_init = false;
        //initialize the camera view
        cameraBridgeViewBase = findViewById(R.id.cameraView);
        //set listener
        cameraBridgeViewBase.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener2() {
            @Override
            public void onCameraViewStarted(int width, int height) {
            //Initialize variables
                curr_gray = new Mat();
                prev_gray = new Mat();
                rgb = new Mat();
                diff = new Mat();
                cntsl = new ArrayList<>();
            }

            @Override
            public void onCameraViewStopped() {

            }
            //this is the function that is called when the frame is captured by the android's camera
            @Override
            public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
                if(is_init){
                    prev_gray = inputFrame.gray();
                    is_init = true;
                    return prev_gray;
                }

                rgb = inputFrame.rgba();
                curr_gray = inputFrame.gray();
                //Detect noises
                Core.absdiff(curr_gray, prev_gray,diff);
                Imgproc.threshold(diff,diff,40,255,Imgproc.THRESH_BINARY);
                Imgproc.findContours(diff,cntsl,new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

                Imgproc.drawContours(rgb, cntsl,-1,new Scalar(255,0,0),4);

                for (MatOfPoint m:cntsl){
                    Rect r = Imgproc.boundingRect(m);
                    Imgproc.rectangle(rgb, r, new Scalar(0,0,255),3);
                }
                cntsl.clear();

                prev_gray = curr_gray.clone();
                return rgb;
            }
        });
        if(OpenCVLoader.initDebug()){
            cameraBridgeViewBase.enableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraBridgeViewBase.enableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraBridgeViewBase.disableView();
    }
    //App is on the background


    @Override
    protected void onPause() {
        super.onPause();
        cameraBridgeViewBase.disableView();
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(cameraBridgeViewBase);
    }

    void getPermission(){
        if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            getPermission();
        }
    }
}
*/