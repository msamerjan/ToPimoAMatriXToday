package com.mobiledev.topimpamatricks.MatrixCalculation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.mobiledev.topimpamatricks.R;

import butterknife.Bind;

/**
 * Created by maiaphoebedylansamerjan on 4/20/16.
 */
public class EditDetailActivity  extends Activity {
    //Should allow user to edit the data in the grid space clicked, then updates all matrix info


    @Bind(R.id.matrix_number)
    Button mGridItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        System.out.println("Clicked");
        mGridItem.setText("");

        mGridItem.setInputType(InputType.TYPE_CLASS_NUMBER);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput((mGridItem), InputMethodManager.SHOW_IMPLICIT);


    }
}
