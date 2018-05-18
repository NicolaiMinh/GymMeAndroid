package com.android.sgvn.gymme.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.sgvn.gymme.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sgvn144 on 2018/04/03.
 */

public abstract class BaseFragment extends Fragment {
    protected static String TAG = BaseFragment.class.getSimpleName();

    View rootView;
    Activity activity;
    Unbinder unbinder;
    AlertDialog dialog;
    ProgressDialog mProgressDlg;
    Toast toast;

    public BaseFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        rootView = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    @Override
    @CallSuper
    public void onResume() {
        Log.v(TAG, "onResume(): " + this.toString());
        super.onResume();
    }

    @Override
    @CallSuper
    public void onPause() {
        Log.v(TAG, "onPause(): " + this.toString());
        super.onPause();
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        hideKeyboardIfNeed();
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy(): " + this.toString());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activity = activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    protected abstract int getLayout();

    public void initView() {}

    protected boolean isActivityAlive() {
        if (activity == null || activity.isFinishing() || !isAdded()) {
            return false;
        }
        return true;
    }

    protected void showToast(final String message) {
        if (isActivityAlive()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }

    protected void showProgressDialog() {
        if (!isActivityAlive())
            return;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mProgressDlg == null) {
                    mProgressDlg = new ProgressDialog(activity, R.style.MyProgressDialogTheme);
                }
                try {
                    mProgressDlg.setCancelable(false);
                    //mProgressDlg.setMessage(message);
                    mProgressDlg.show();
                    mProgressDlg.setContentView(R.layout.progress_custom);
                    //TextView tvMessage = (TextView) mProgressDlg.findViewById(R.id.tv_message_progess);
                    //tvMessage.setText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void dismissProgressDialog() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mProgressDlg != null && isActivityAlive()) {
                    try {
                        mProgressDlg.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void showMessageDialog(final String title, final String message, final String strYes, final String strNo, final DialogInterface.OnClickListener onClickYes, final DialogInterface.OnClickListener onClickNo) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (!isActivityAlive() && dialog != null && dialog.isShowing()) {
                    return;
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setCancelable(false);
                alertDialog.setMessage(message);
                if (!TextUtils.isEmpty(title)) {
                    alertDialog.setTitle(title);
                }
                alertDialog.setPositiveButton(strYes, onClickYes);
                if (strNo != null && !strNo.isEmpty()) {
                    alertDialog.setNegativeButton(strNo, onClickNo);
                }
                try {
                    alertDialog.create();
                    dialog = alertDialog.show();
                } catch (Exception e) {
                    Log.e(TAG, "Crash when showing dialog");
                }
            }
        });
    }

    private void hideKeyboardIfNeed() {
        if (activity == null) {
            return;
        }
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View v = activity.getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    protected void showNoInternetConnectionPopup() {
        showMessageDialog(null, "No Internet connection. Please check your Settings.", getString(R.string.label_go_to_settings), "Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finish();
            }
        });
    }

}
