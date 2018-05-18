package com.android.sgvn.gymme.fragments.tabMainFragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.activities.AccountActivity;
import com.android.sgvn.gymme.activities.SettingsActivity;
import com.android.sgvn.gymme.fragments.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends BaseFragment {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_Settings)
    Button btnSettings;
    @BindView(R.id.btn_Account)
    Button btnAccount;
    @BindView(R.id.btn_Helps)
    Button btnHelps;

    Unbinder unbinder;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_Settings, R.id.btn_Account, R.id.btn_Helps})
    public void onClickButton(View view) {
        switch (view.getId()){
            case R.id.btn_Settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
            case R.id.btn_Account:
                startActivity(new Intent(getActivity(), AccountActivity.class));
                break;
            case R.id.btn_Helps:
                //show help in webpage
                break;
        }
    }
}
