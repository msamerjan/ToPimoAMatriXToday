package com.mobiledev.topimpamatricks.Calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.mobiledev.topimpamatricks.Keyboard.MathKeyboard;
import com.mobiledev.topimpamatricks.R;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.ops.RandomMatrices;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by larspmayrand on 4/14/16.
 */
public class CalculatorActivity extends Activity {

    public static final String CALCULATOR_URL = "http://icons.iconarchive.com/icons/dtafalonso/android-lollipop/512/Calculator-icon.png";
    public static final String DEFAULT_MATRIX_URL = "http://ncalculators.com/images/formulas/2x2-matrix.png";
    public static final String KEYBOARD_URL = "https://material-design.storage.googleapis.com/publish/material_v_4/material_ext_publish/0Bx4BSt6jniD7c2M0WDlSakI4akE/usability_bidirectionality_guidelines_whennot3.png";

    public static final String TAG = CalculatorActivity.class.getSimpleName();

    private Detail[] mDetails;
    private DetailRecyclerViewAdapter mAdapter;

    public static final String ARG_MATRIX = "matrix";

    public final static String SERIALIZABLE_KEY_A = "key_a";
    public final static String SERIALIZABLE_KEY_B = "key_b";
    public final static String SERIALIZABLE_KEY = "key";

    private MathKeyboard mathKeyboard;

    @Bind(R.id.detail_recycler)
    RecyclerView mRecyclerView;

    @Bind(R.id.detail_activity_webview)
    WebView mWebView;

    @Bind(R.id.activity_calculator_icon)
    ImageButton mCalculatorIcon;

  CDenseMatrix64F mMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity);
        ButterKnife.bind(this);

        CDenseMatrix64F matrixA = (CDenseMatrix64F) getIntent().getSerializableExtra(com.mobiledev.topimpamatricks.MainActivity.SERIALIZABLE_KEY_A);
        CDenseMatrix64F matrixB = (CDenseMatrix64F) getIntent().getSerializableExtra(com.mobiledev.topimpamatricks.MainActivity.SERIALIZABLE_KEY_B);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String js = FormatHelper.matricesToLatex(matrixA, matrixB);
        mWebView.loadDataWithBaseURL("file:///android_asset/", js, "text/html", "UTF-8", null);

        mDetails = com.mobiledev.topimpamatricks.Calculator.CalculatorRecyclerViewHelper.getCalculations(matrixA, matrixB);
        mAdapter = new DetailRecyclerViewAdapter(mDetails, new DetailRecyclerViewAdapter.DetailRowOnClickListener() {
            @Override
            public void onDetailRowClick(Detail detail) {
                // toast
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.activity_detail_click_view)
    public void gridViewClicked() {

        Intent intent = new Intent(this,EditDataActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, mMatrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @OnClick(R.id.activity_camera_icon)
    public void cameraButtonClicked() {
        CDenseMatrix64F matrix = new CDenseMatrix64F(1, 1);
        matrix.set(0, 0, -1, 1);

        Intent intent = new Intent(this, DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @OnClick(R.id.activity_calculator_icon)
    public void calculatorButtonClicked() {
        CDenseMatrix64F matrix = new CDenseMatrix64F(com.mobiledev.topimpamatricks.MatrixCalculation.MatrixHelper.makeComplex(RandomMatrices.createOrthogonal(2, 2, new Random())));

        Intent intent = new Intent(this, DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @OnClick(R.id.activity_keyboard_icon)
    public void keyboardButtonClicked() {
        Intent intent = new Intent(this,EditDataActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, mMatrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

}
