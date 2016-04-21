package com.mobiledev.topimpamatricks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.mobiledev.topimpamatricks.MatrixCalculation.DetailActivity;
import com.mobiledev.topimpamatricks.MatrixCalculation.MatrixHelper;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.ops.RandomMatrices;

import java.util.Random;

/**
 * Created by maiaphoebedylansamerjan on 4/14/16.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public  final static String SERIALIZABLE_KEY = "key";
    public static final int numCol = 3;
    public static final int numRow = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Start DetailActivity for randomly generated complex matrix. */
//        double[][] array = new double[][]
//                {{Math.random() * 30 - 15, Math.random() * 30 - 15, Math.random() * 30 - 15, 0},
//                        {Math.random() * 30 - 15, Math.random() * 30 - 15, 0, Math.random() * 30 - 15}};
//        DenseMatrix64F matrix = new DenseMatrix64F(array);
        CDenseMatrix64F matrix = MatrixHelper.makeComplex(RandomMatrices.createOrthogonal(2, 2, new Random(5)));
        Intent intent = new Intent(this,DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
