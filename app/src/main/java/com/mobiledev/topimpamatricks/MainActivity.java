package com.mobiledev.topimpamatricks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobiledev.topimpamatricks.Calculator.CalculatorActivity;
import com.mobiledev.topimpamatricks.Calculator.DetailActivity;
import com.mobiledev.topimpamatricks.MatrixCalculation.MatrixHelper;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.ops.CRandomMatrices;
import org.ejml.ops.RandomMatrices;

import java.util.Random;


/**
 * Created by maiaphoebedylansamerjan on 4/14/16.
 */
public class MainActivity extends AppCompatActivity {
    final static String ACTION_PREFS_ONE = "com.example.prefs.PREFS_ONE";

    public final static String SERIALIZABLE_KEY_A = "key_a";
    public final static String SERIALIZABLE_KEY_B = "key_b";
    public static final String SERIALIZABLE_KEY = "key";

    public static int columns;
    public static int rows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Change these values to choose change what Activity is launched. */
        String mathematicalObject = "matrix"; // choices: "matrix", "vector", "number"
        String activityType = "details"; // "calculator" for CalculatorActivity, "details" for DetailActivity
        boolean complex = true; // set to false for real objects

        switch (mathematicalObject) {

            case "matrix":
                if (activityType.equals("calculator")) {
                    if (complex) complexMatrixCalculator();
                    else realMatrixCalculator();
                } else {
                    if (complex) complexMatrixDetails();
                    else realMatrixDetails();
                }
                break;

            case "vector":
                if (activityType.equals("calculator")) {
                    if (complex) complexVectorCalculator();
                    else realVectorCalculator();
                } else {
                    if (complex) complexVectorDetails();
                    else realVectorDetails();
                }
                break;

            case "number":
                if (activityType.equals("calculator")) {
                    if (complex) complexNumberCalculator();
                    else realNumberCalculator();
                } else {
                    if (complex) complexNumberDetails();
                    else realNumberDetails();
                }
                break;

            case "dictionary":
                dictionary();
        }

        //setContentView(R.layout.edit_text_calculator_activity);


       /* InputMethodManager inputMethodManager= (InputMethodManager)getSystemService(SimpleIME.INPUT_METHOD_SERVICE);
        EditText editText = (EditText) findViewById(R.id.entry1);
        if (inputMethodManager!= null){
        inputMethodManager.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);}*/

       /* SimpleIME simpleIME = new SimpleIME();
        simpleIME.onCreate();
        simpleIME.onCreateInputView();
        simpleIME.onInitializeInterface();*/

    }

    /**
     * Dictionary.
     */
    private void dictionary() {
//        startActivity(new Intent(this, DictionaryActivity.class));
    }

    /**
     * CalculatorActivity: complex matrix.
     */
    public void complexMatrixCalculator() {
        CDenseMatrix64F matrixA = CRandomMatrices.createHermPosDef(2, new Random());
        CDenseMatrix64F matrixB = CRandomMatrices.createRandom(2, 2, -8, 10, new Random());

        Intent intent = new Intent(this, CalculatorActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY_A, matrixA);
        mBundle.putSerializable(SERIALIZABLE_KEY_B, matrixB);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * CalculatorActivity: real matrix.
     */
    public void realMatrixCalculator() {
        CDenseMatrix64F matrixA = MatrixHelper.makeComplex(RandomMatrices.createOrthogonal(2, 2, new Random(5)));
        CDenseMatrix64F matrixB = MatrixHelper.makeComplex(RandomMatrices.createDiagonal(2, 2, 4, new Random(5)));

        Intent intent = new Intent(this, CalculatorActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrixA);
        mBundle.putSerializable(SERIALIZABLE_KEY, matrixB);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * DetailActivity: complex matrix.
     */
    public void complexMatrixDetails() {
        CDenseMatrix64F matrix = CRandomMatrices.createHermPosDef(2, new Random());

        Intent intent = new Intent(this, DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * DetailActivity: real matrix.
     */
    public void realMatrixDetails() {
        CDenseMatrix64F matrix = new CDenseMatrix64F(MatrixHelper.makeComplex(RandomMatrices.createOrthogonal(2, 2, new Random())));

        Intent intent = new Intent(this, DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * Calculator Activity: complex vector.
     */
    public void complexVectorCalculator() {
        CDenseMatrix64F matrixA = new CDenseMatrix64F(2, 1);
        matrixA.set(0, 0, 1, 1);
        matrixA.set(1, 0, 2, -1);

        CDenseMatrix64F matrixB = new CDenseMatrix64F(2, 1);
        matrixB.set(0, 0, 0, 1);
        matrixB.set(1, 0, 1, 0);

        Intent intent = new Intent(this, CalculatorActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrixA);
        mBundle.putSerializable(SERIALIZABLE_KEY, matrixB);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * CalculatorActivity: real vector.
     */
    public void realVectorCalculator() {
        CDenseMatrix64F matrixA = new CDenseMatrix64F(2, 1);
        matrixA.set(0, 0, 1, 0);
        matrixA.set(1, 0, 2, 0);

        CDenseMatrix64F matrixB = new CDenseMatrix64F(2, 1);
        matrixB.set(0, 0, 3, 0);
        matrixB.set(1, 0, 4, 0);

        Intent intent = new Intent(this, CalculatorActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrixA);
        mBundle.putSerializable(SERIALIZABLE_KEY, matrixB);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * DetailActivity: complex vector.
     */
    public void complexVectorDetails() {
        CDenseMatrix64F matrix = new CDenseMatrix64F(2, 1);
        matrix.set(0, 0, 1, 1);
        matrix.set(1, 0, 2, 0);

        Intent intent = new Intent(this, DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * DetailActivity: real vector.
     */
    public void realVectorDetails() {
        CDenseMatrix64F matrix = new CDenseMatrix64F(2, 1);
        matrix.set(0, 0, 1, 0);
        matrix.set(1, 0, 2, 0);

        Intent intent = new Intent(this, DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * CalculatorActivity: complex number.
     */
   public void complexNumberCalculator() {
        CDenseMatrix64F matrixA = new CDenseMatrix64F(1, 1);
        matrixA.set(0, 0, -1, 1);

        CDenseMatrix64F matrixB = new CDenseMatrix64F(1, 1);
        matrixB.set(0, 0, 10, 4);

        Intent intent = new Intent(this, CalculatorActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrixA);
        mBundle.putSerializable(SERIALIZABLE_KEY, matrixB);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * CalculatorActivity: real number.
     */
    public void realNumberCalculator() {
        CDenseMatrix64F matrixA = new CDenseMatrix64F(1, 1);
        matrixA.set(0, 0, 1, 0);

        CDenseMatrix64F matrixB = new CDenseMatrix64F(1, 1);
        matrixB.set(0, 0, 2, 0);

        Intent intent = new Intent(this, CalculatorActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrixA);
        mBundle.putSerializable(SERIALIZABLE_KEY, matrixB);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * DetailActivity: complex number.
     */
    public void complexNumberDetails() {
        CDenseMatrix64F matrix = new CDenseMatrix64F(1, 1);
        matrix.set(0, 0, -1, 1);

        Intent intent = new Intent(this, DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * DetailActivity: real number.
     */
    public void realNumberDetails() {
        CDenseMatrix64F matrix = new CDenseMatrix64F(1, 1);
        matrix.set(0, 0, 2, 0);

        Intent intent = new Intent(this, DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }
}