package com.mobiledev.topimpamatricks.MatrixCalculation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.mobiledev.topimpamatricks.R;

import org.ejml.data.CDenseMatrix64F;

/**
 * Created by maiaphoebedylansamerjan on 4/20/16.
 */
public class GridViewCustomAdapter extends BaseAdapter {
    static Activity mActivity;

    private static LayoutInflater inflater = null;
    private static CDenseMatrix64F mMatrix;
    private static double[] nums;
    private MatrixHelper mMatrixHelper;

    public GridViewCustomAdapter(Activity activity, CDenseMatrix64F matrix) {
        mActivity = activity;
        mMatrix = matrix;

        int index = matrix.getDataLength() / 2;
        nums = new double[index];


        int count = 0;
        for (int r = 0; r < matrix.getNumRows(); r++) {
            for (int c = 0; c < matrix.getNumCols(); c++) {
                nums[count] = matrix.getReal(r, c);
                count++;
            }
        }

        mMatrixHelper = new MatrixHelper();

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public final int getCount() {

        return mMatrix.getDataLength();

    }

    @Override
    public final Object getItem(int position) {
        return null;
    }

    @Override
    public final long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = null;

        v = inflater.inflate(R.layout.data, null);

        Button b = (Button) v.findViewById(R.id.matrix_number);

        if (mMatrixHelper.isReal(mMatrix)) {
            if (position < mMatrix.getDataLength() / 2) {
                b.setText("" + nums[position]);
            }
        }

        return v;
    }
}
