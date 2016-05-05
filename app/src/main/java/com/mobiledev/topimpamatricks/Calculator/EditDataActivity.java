package com.mobiledev.topimpamatricks.Calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;

import com.mobiledev.topimpamatricks.MainActivity;
import com.mobiledev.topimpamatricks.MatrixCalculation.MatrixHelper;
import com.mobiledev.topimpamatricks.R;

import org.ejml.data.CDenseMatrix64F;
import org.ejml.ops.RandomMatrices;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by maiaphoebedylansamerjan on 5/4/16.
 */
public class EditDataActivity extends Activity {

    private static final String SERIALIZABLE_KEY = "key";
    public static int numCol;
    public static int numRow;


    @Bind(R.id.grid_view)
    GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_text_calculator_activity);

        ButterKnife.bind(this);

        CDenseMatrix64F mMatrix = (CDenseMatrix64F) getIntent().getSerializableExtra(MainActivity.SERIALIZABLE_KEY);
        mMatrix.print();

        GridViewCustomAdapter mAdapter = new GridViewCustomAdapter(this, mMatrix);
        mGridView.setAdapter(mAdapter);
        mGridView.setNumColumns(MainActivity.columns);

//        mMatrixGridItem.setInputType(InputType.TYPE_CLASS_NUMBER);
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput((mMatrixGridItem), InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.new_matrix_button)
        public void newMatrixButtonClicked() {
        numCol++;
        numRow++;
            while(numCol == numRow){
                CDenseMatrix64F matrix = MatrixHelper.makeComplex(RandomMatrices.createOrthogonal(numRow, numCol, new Random()));
                Intent intent = new Intent(this, EditText.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(SERIALIZABLE_KEY, matrix);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        }
    }
