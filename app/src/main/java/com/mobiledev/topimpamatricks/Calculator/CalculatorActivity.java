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

    public static final String TAG = CalculatorActivity.class.getSimpleName();

    private Detail[] mDetails;
    private DetailRecyclerViewAdapter mAdapter;

    public static final String ARG_MATRIX = "matrix";

    public  final static String SERIALIZABLE_KEY_A = "key_a";
    public  final static String SERIALIZABLE_KEY_B = "key_b";

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
        ButterKnife.bind(this);

        CDenseMatrix64F matrixA = (CDenseMatrix64F) getIntent().getSerializableExtra(MainActivity.SERIALIZABLE_KEY_A);
        CDenseMatrix64F matrixB = (CDenseMatrix64F) getIntent().getSerializableExtra(MainActivity.SERIALIZABLE_KEY_B);

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
    }

    @OnClick(R.id.activity_camera_icon)
    public void cameraButtonClicked() {
        //startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @OnClick(R.id.activity_calculator_icon)
    public void calculatorButtonClicked() {
//        CDenseMatrix64F matrixA = CRandomMatrices.createHermitian(2, -10, 10, new Random());
//        CDenseMatrix64F matrixB = CRandomMatrices.createHermPosDef(2, new Random());
//        Intent intent = new Intent(this, CalculatorActivity.class);
//        Bundle mBundle = new Bundle();
//        mBundle.putSerializable(SERIALIZABLE_KEY, matrixA);
//        mBundle.putSerializable(SERIALIZABLE_KEY, matrixB);
//        intent.putExtras(mBundle);
//        startActivity(intent);
    }

    @OnClick(R.id.activity_keyboard_icon)
    public void keyboardButtonClicked() {
//        CDenseMatrix64F matrix = CRandomMatrices.createHermPosDef(2, new Random());
//        Intent intent = new Intent(this, Detail.class);
//        Bundle mBundle = new Bundle();
//        mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
//        intent.putExtras(mBundle);
//        startActivity(intent);
    }


}
