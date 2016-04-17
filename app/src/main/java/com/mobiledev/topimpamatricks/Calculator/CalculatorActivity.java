package com.mobiledev.topimpamatricks.Calculator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.graphics.Matrix;

import com.mobiledev.topimpamatricks.MatrixCalculation.Detail;
import com.mobiledev.topimpamatricks.MatrixCalculation.DetailActivity;
import com.mobiledev.topimpamatricks.MatrixCalculation.DetailRecyclerViewAdapter;
import com.mobiledev.topimpamatricks.MatrixCalculation.FormatHelper;
import com.mobiledev.topimpamatricks.MainActivity;
import com.mobiledev.topimpamatricks.R;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.ops.CRandomMatrices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by maiaphoebedylansamerjan on 4/17/16.
 */
public class CalculatorActivity extends Activity {

    public static final String CALCULATOR_URL = "http://icons.iconarchive.com/icons/dtafalonso/android-lollipop/512/Calculator-icon.png";
    public static final String DEFAULT_MATRIX_URL = "http://ncalculators.com/images/formulas/2x2-matrix.png";
    public static final String KEYBOARD_URL = "https://material-design.storage.googleapis.com/publish/material_v_4/material_ext_publish/0Bx4BSt6jniD7c2M0WDlSakI4akE/usability_bidirectionality_guidelines_whennot3.png";

    public static final String API_KEY = "4571bd58-9662-4d0d-9dfa-23f0479db860";

    public static final String TAG = DetailActivity.class.getSimpleName();

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int SELECT_PICTURE = 1;
    private static final int TAKE_PICTURE = 2;
    private String mImageFullPathAndName = "";
    private Intent intent;

    private Detail[] mDetails;
    private DetailRecyclerViewAdapter mAdapter;

    public static final String ARG_MATRIX = "matrix";
    public  final static String SERIALIZABLE_KEY = "key";

    @Bind(R.id.activity_main_camera_image)
    ImageView mCameraImage;

    @Bind(R.id.detail_recycler)
    RecyclerView mRecyclerView;

    @Bind(R.id.detail_activity_webview)
    WebView mWebView;

    @Bind(R.id.activity_calculator_icon)
    ImageButton mCalculatorIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity);

        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        ButterKnife.bind(this);

        CDenseMatrix64F matrixA = (CDenseMatrix64F) getIntent().getSerializableExtra(MainActivity.SERIALIZABLE_KEY);
        CDenseMatrix64F matrixB = (CDenseMatrix64F) getIntent().getSerializableExtra(MainActivity.SERIALIZABLE_KEY);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String js = FormatHelper.matricesToLatex(matrixA, matrixB);
        mWebView.loadDataWithBaseURL("file:///android_asset/", js, "text/html", "UTF-8", null);

        mDetails = CalculatorRecyclerViewHelper.getCalculations(matrixA, matrixB);
        mAdapter = new DetailRecyclerViewAdapter(mDetails, new DetailRecyclerViewAdapter.DetailRowOnClickListener() {
            @Override
            public void onDetailRowClick(Detail detail) {
                // toast
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        //Workorder workorder = (Workorder) intent.getSerializableExtra("SomeUniqueKey");

//        double[][] array = new double[][]{{1, 1, 3, 0}, {5, 0, 7, 0}};
//        CDenseMatrix64F matrix = new CDenseMatrix64F(array); // by columns then rows: real, im
//        mMatrix = matrix;
//
//        Log.d(TAG, "real at r = 0, c = 0 " + matrix.getReal(0, 0));
//        Log.d(TAG, "real at r = 0, c = 1 " + matrix.getReal(0, 1));
//        Log.d(TAG, "real at r = 1, c = 0 " + matrix.getReal(1, 0));
//        Log.d(TAG, "real at r = 1, c = 1 " + matrix.getReal(1, 1));
//        Log.d(TAG, "imaginary at r = 0, c = 0 " + matrix.getImaginary(0, 0));
//        Log.d(TAG, "imaginary at r = 0, c = 1 " + matrix.getImaginary(0, 1));
//        Log.d(TAG, "imaginary at r = 1, c = 0 " + matrix.getImaginary(1, 0));
//        Log.d(TAG, "imaginary at r = 1, c = 1 " + matrix.getImaginary(1, 1));


    }

    @OnClick(R.id.activity_camera_icon)
    public void cameraButtonClicked() {
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @OnClick(R.id.activity_calculator_icon)
    public void calculatorButtonClicked() {
        CDenseMatrix64F matrixA = CRandomMatrices.createHermitian(2, -10, 10, new Random());
        CDenseMatrix64F matrixB = CRandomMatrices.createHermPosDef(2, new Random());
        Intent intent = new Intent(this, CalculatorActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrixA);
        mBundle.putSerializable(SERIALIZABLE_KEY, matrixB);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @OnClick(R.id.activity_keyboard_icon)
    public void keyboardButtonClicked() {
        CDenseMatrix64F matrix = CRandomMatrices.createHermPosDef(2, new Random());
        Intent intent = new Intent(this, Detail.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            /*if (resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                // get the selected image full path and name
                mImageFullPathAndName = cursor.getString(columnIndex);
                cursor.close();
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mCameraImage.setImageBitmap(imageBitmap);
            }*/

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), fileUri);
                bitmap = rescaleBitmap(bitmap, 600, 600);
                mCameraImage.setImageBitmap(rotateBitmap(bitmap, 90));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(fileUri);
                this.sendBroadcast(mediaScanIntent);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                Log.e(TAG, "File Not Found");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e(TAG, "Exception");
            }
        }
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "ToPimpAMatrix");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("ToPimpAMatrix", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void DoShowImagePicker(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE);
    }

    // Resize an image
    public Bitmap rescaleBitmap(Bitmap bm, int newWidth, int newHeight) {
        int w = bm.getWidth();
        int h = bm.getHeight();
        float scaleWidth = ((float) newWidth) / w;
        float scaleHeight = ((float) newHeight) / h;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bm, 0, 0, w, h, matrix, false);
    }

    // Rotate an image
    private Bitmap rotateBitmap(Bitmap pic, int deg) {
        // Create two matrices that will be used to rotate the bitmap
        Matrix rotate90DegAntiClock = new Matrix();
        rotate90DegAntiClock.preRotate(deg);
        return Bitmap.createBitmap(pic, 0, 0, pic.getWidth(), pic.getHeight(), rotate90DegAntiClock, true);
    }

}
