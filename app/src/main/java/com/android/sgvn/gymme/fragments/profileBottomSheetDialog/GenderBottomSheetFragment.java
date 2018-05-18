package com.android.sgvn.gymme.fragments.profileBottomSheetDialog;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sgvn.gymme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenderBottomSheetFragment extends BottomSheetDialogFragment {


    public GenderBottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gender_bottom_sheet, container, false);
    }

}
