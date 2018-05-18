package com.android.sgvn.gymme.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.operation.LocaleManager;

import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by soan
 * <p>
 * Able to MODIFY
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final IntentFilter FILTER_KILL;
    private static final String ACTION_KILL = "ACTION_KILL";

    protected static String TAG = BaseActivity.class.getSimpleName();
    private CompositeDisposable subscriptions;

    static {
        FILTER_KILL = new IntentFilter();
        FILTER_KILL.addAction(ACTION_KILL);
    }

    private ProgressDialog mProgressDlg;
    private AlertDialog dialog;
    private Unbinder unbinder;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        registerReceiver(appReceiver, FILTER_KILL);

        subscriptions = new CompositeDisposable();

    }

    private void checkAndCallRefresh() {
        // Chek reffesh token
//        DataAccount dataAccount = AppConfig.getInstance(this).getTokenExpired();
//        if (dataAccount != null) {
//            long now = System.currentTimeMillis();
//            if (Utils.getLong(dataAccount.getExpired_at()) <= now) {
//                Toast.makeText(this, getResources().getString(R.string.token_expried), Toast.LENGTH_SHORT).show();
//                openLogin();
//            } else if ((Utils.getLong(dataAccount.getExpired_at()) - now) < 36000) {
//                //reffesh token
//                Disposable disposable = AccountManager.refreshToken(this)
//                        .subscribe(responseAccount -> {
//                            // Save token
//                            AppConfig.getInstance(BaseActivity.this).saveTokenAPI(responseAccount.getDataAccount().getToken());
//                            AppConfig.getInstance(BaseActivity.this).saveTokenExpired(responseAccount.getDataAccount());
//                        }, throwable -> {
//                        });
//                subscriptions.add(disposable);
//            }
//        }
//
//        if (!AppConfig.getInstance(this).getTokenAPI().equalsIgnoreCase("")) {
//
//            Disposable disposable = AccountManager.checkToken(this)
//                    .subscribe(responseBankuserModel -> {
//                        if (responseBankuserModel.getStatus_code() == 401) {
//                            Toast.makeText(BaseActivity.this, responseBankuserModel.getMessage(), Toast.LENGTH_SHORT).show();
//                            openLogin();
//                        }
//                    }, throwable -> {
//                        Toast.makeText(BaseActivity.this, getResources().getString(R.string.token_expried), Toast.LENGTH_SHORT).show();
//                        openLogin();
//                    });
//            subscriptions.add(disposable);
//        }
    }

    public void openLogin() {
//        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkAndCallRefresh();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(appReceiver);
        subscriptions.clear();
    }

    public CompositeDisposable getSubscriptions() {
        return subscriptions;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /*@Optional
    @OnClick(R.id.iv_left_icon)
    void onBackClick() {
        finish();
    }*/

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private final BroadcastReceiver appReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (ACTION_KILL.equals(action)) {
                finish();
            }
        }
    };

    public void killApp() {
        sendBroadcast(new Intent(ACTION_KILL));
    }

    protected void showProgressDialog() {
        if (isFinishing())
            return;
        if (mProgressDlg == null) {
            mProgressDlg = new ProgressDialog(this, R.style.MyProgressDialogTheme);
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

    protected void dismissProgressDialog() {
        if (mProgressDlg != null && mProgressDlg.isShowing() && !isFinishing()) {
            try {
                mProgressDlg.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void showMessageDialog(String title, String message, String strYes, String strNo, DialogInterface.OnClickListener onClickYes, DialogInterface.OnClickListener onClickNo) {
        if ((dialog != null && dialog.isShowing()) || isFinishing()) {
            return;
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(message);
        if (title != null && !title.equalsIgnoreCase("")) {
            alertDialog.setTitle(title);
        }
        alertDialog.setPositiveButton(strYes, onClickYes);
        if (strNo != null && !strNo.isEmpty()) {
            alertDialog.setNegativeButton(strNo, onClickNo);
        }
        alertDialog.create();
        dialog = alertDialog.show();
    }
}
