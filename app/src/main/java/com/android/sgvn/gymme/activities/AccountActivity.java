package com.android.sgvn.gymme.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sgvn.gymme.R;
import com.android.sgvn.gymme.customview.DatePickerDialogTheme;
import com.android.sgvn.gymme.utils.AppConstants;
import com.android.sgvn.gymme.utils.GetRealPathFromUri;
import com.android.sgvn.gymme.utils.ImageUtils;
import com.android.sgvn.gymme.utils.Utils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.profileCircleImage)
    CircleImageView profileCircleImage;
    @BindView(R.id.profileName)
    TextView profileName;
    @BindView(R.id.btn_editName)
    ImageView btnEditName;
    @BindView(R.id.txtGender)
    TextView txtGender;
    @BindView(R.id.txtAge)
    TextView txtAge;
    @BindView(R.id.txtWeight)
    TextView txtWeight;
    @BindView(R.id.txtHeight)
    TextView txtHeight;
    private Uri imageUri = null;


    private static final int PICK_FROM_CAMERA = 1231;


    private static final int PICK_FROM_GALLERY = 1232;
    private static final int PICKFILE_RESULT_CODE = 1233;

    final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";

    private String mNewImageFilePath = "";

    private final String TAG = AccountActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);

        //setUp toolbar
        setupToolbar();

        //get context to set image to circle image
        registerForContextMenu(profileCircleImage);


    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        //set title again
        getSupportActionBar().setTitle("");
        //get back arrow button from toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setup back arrow with another image or color
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.arrowBackButton), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }


    @OnClick({R.id.profileCircleImage, R.id.btn_editName, R.id.txtGender, R.id.txtAge, R.id.txtWeight, R.id.txtHeight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profileCircleImage:
                //open context to set profile image
                openContextMenu(profileCircleImage);
                break;
            case R.id.btn_editName:
                showEditProfileNameDialog();
                break;
            case R.id.txtGender:
//                //bottom sheet fragment
//                GenderBottomSheetFragment bottomSheetDialogFragment = new GenderBottomSheetFragment();
//                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                showGenderInputDialog();

                break;
            case R.id.txtAge:
                showCalendarDialog();
                break;
            case R.id.txtWeight:
                showWeightData();
                break;
            case R.id.txtHeight:
                showHeightData();
                break;
        }
    }


    /** ----------------------------------------------------------
     * Nếu người dùng từ chối cấp một permission và ngăn chặn ứng dụng xin quyền tiếp trong tương lai, thì sẽ lựa chọn "Never Ask Again".
     * Nếu tuỳ chọn "Never Ask Again" được người dùng lựa chọn trước khi từ chối cấp quyền, thì lần tiếp theo gọi
     * phương thức requestPermissions, dialog yêu cầu cung cấp permission sẽ không được hiển thị ra nữa, và sẽ không có gì được thực hiện.
     Việc người dùng thực hiện một hành động nhưng không có gì tương tác trở lại là một điều không tốt trong trải nghiệm người dùng.
     Vì vậy, trước khi gọi phương thức requestPermissions, cần phải giải thích tới người dùng lý do ứng dụng cần permission thông qua phương thức shouldShowRequestPermissionRationale của Activity.
     */
    /**
     * When select take a photo, the system check permission at current time
     * check CAMERA permission on Android 6+
     * And opening camera if granted
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
//require when device is run at API 23 - Android Marshmallow
    public void checkCameraPermission() {
        int hasPermissionCam = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int hasPermissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (hasPermissionCam != PackageManager.PERMISSION_GRANTED || hasPermissionStorage != PackageManager.PERMISSION_GRANTED) {

            // Permission has not been granted and must be requested.
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                    || shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //Show the reason to using this permisson
                showDialogAskPermission(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        getString(R.string.ask_permission_camera_storage), AppConstants.REQUEST_CODE_ACCESS_CAMERA);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_CODE_ACCESS_CAMERA);
            }
        } else {
            openCameraApp();
        }
    }

    /**
     * open camera app
     */
    private void openCameraApp() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = ImageUtils.createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                imageUri = Uri.fromFile(Utils.getOutputMediaFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
            }
        }
    }

    /**
     * When select choose import image from external, the system is check permission at current time
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
//require when device is run at API 23 - Android Marshmallow
    private void checkReadExternalStoragePermission() {
        //get permission from system
        int hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {//check permission has been granted or not
            //Permission has not been granted, show dialog to request permission
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //show the reason to using this permission with dialog
                showDialogAskPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, getString(R.string.ask_permission_photo_access), AppConstants.REQUEST_CODE_ACCESS_STORAGE);
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AppConstants.REQUEST_CODE_ACCESS_STORAGE);
            }
        } else {//if permission has been granted, go to the photo library in device
            gotoPhotoOfDevice();
        }

    }

    /**
     * goto photo of device to show all image
     */
    private void gotoPhotoOfDevice() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_GALLERY);//send result to onActivityResult
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * show dialog ask user to access permission
     *
     * @param permissions
     * @param message
     * @param requestCode
     */
    private void showDialogAskPermission(final String[] permissions, String message, final int requestCode) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        requestPermissions(permissions, requestCode);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.permission_access)).setMessage(message).setPositiveButton(getResources().getString(R.string.ok), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.label_cancel), dialogClickListener).show();
    }


    //onclick back arrow in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //nut home la co san trong he thong
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //get import menu to display
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_import_image_options, menu);
    }

    //select context(camera or gallery)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {//get from menu_import_image_options
            case R.id.menu_take_by_camera:
                //check permission if ok is shown camera or request
                checkCameraPermission();
                break;
            case R.id.menu_import_from_gallery:
                //check permission if ok is shown gallery or request
                checkReadExternalStoragePermission();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Override when select image from gallery or take picture
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_FROM_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (imageUri != null) {
                        // get path from uri
                        mNewImageFilePath = Utils.getPathFromUri(this, imageUri);
                        Glide.with(this).load(mNewImageFilePath).into(profileCircleImage);
                    }
                }
                break;
            case PICK_FROM_GALLERY:
                if (resultCode == RESULT_OK && data != null) {
                    Uri selectImageURI = data.getData(); // pick result from startActivityForResult of gotoPhotoOfDevice
                    //set select image to circle image
                    try {
                        if (GetRealPathFromUri.isGooglePhotosUri(selectImageURI)) {
                            mNewImageFilePath = GetRealPathFromUri.saveGooglePhotos(this, selectImageURI);
                        } else {
                            mNewImageFilePath = GetRealPathFromUri.getPath(this, selectImageURI);
                        }
                        Glide.with(this).load(mNewImageFilePath).into(profileCircleImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
            default:
                break;
        }
    }

    /**
     * Khi người dùng chọn Allow hoặc Deny, phương thức onRequestPermissionsResult của Activity
     * sẽ được gọi để trả về kết quả thông qua tham số grantResults:
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean errorMsg = false;
        switch (requestCode) {
            case AppConstants.REQUEST_CODE_ACCESS_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gotoPhotoOfDevice();
                        }
                    }, 500);

                } else {
                    errorMsg = true;
                }
                break;

            case AppConstants.REQUEST_CODE_ACCESS_CAMERA:
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            openCameraApp();
                        }
                    }, 500);
                } else {
                    errorMsg = true;
                }
                break;
        }
        if (errorMsg) {
            Toast.makeText(this, getResources().getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
        }
    }

    //custom dialog when choose editname
    private void showEditProfileNameDialog() {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View dialogVIew = layoutInflater.inflate(R.layout.custom_edit_profile_dialog, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setView(dialogVIew);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE://OK button
                        EditText updateProfileName = dialogVIew.findViewById(R.id.updateProfileName);

                        final String updateName = updateProfileName.getText().toString().trim();
                        if (!updateName.isEmpty()) {
                            profileName.setText(updateName);
                        } else {
                            profileName.setText("Guest");
                        }

                        Toast.makeText(AccountActivity.this, "Update Name Successful", Toast.LENGTH_SHORT).show();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE://cancel button
                        Toast.makeText(AccountActivity.this, "Update Name cancel", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        builder.setTitle("Change Name").setPositiveButton(getResources().getString(R.string.ok), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.label_cancel), dialogClickListener).show();

    }


    //custom dialog when choose gender
    private void showGenderInputDialog() {

        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View dialogVIew = layoutInflater.inflate(R.layout.custom_gender_dialog, null, false);

        final RadioGroup radioGroup = dialogVIew.findViewById(R.id.radioGenderGroup);
        final RadioButton maleRadioButton = dialogVIew.findViewById(R.id.radioMale);
        final RadioButton femaleRadioButton = dialogVIew.findViewById(R.id.radioFemale);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (maleRadioButton.isChecked()) {
                    maleRadioButton.setChecked(true);
                } else {
                    femaleRadioButton.setChecked(true);
                }

                //get current index isSelected
                RadioButton checkedRadioButton = radioGroup.findViewById(checkedId);
                int checkedIndex = radioGroup.indexOfChild(checkedRadioButton);
                //save Preference
                savePreferences(KEY_SAVED_RADIO_BUTTON_INDEX, checkedIndex);

            }
        });

        //load Preference gender
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int savedRadioIndex = sharedPreferences.getInt(KEY_SAVED_RADIO_BUTTON_INDEX, 0);
        RadioButton savedCheckedRadioButton = (RadioButton) radioGroup.getChildAt(savedRadioIndex);
        savedCheckedRadioButton.setChecked(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setView(dialogVIew);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE://OK button
                        final int selectedRadioButtonID = radioGroup.getCheckedRadioButtonId();
                        final RadioButton selectedRadioButton = radioGroup.findViewById(selectedRadioButtonID);
                        String selectedRadioButtonText = selectedRadioButton.getText().toString();

                        if (selectedRadioButtonID != -1) {
                            if (selectedRadioButtonText.equals("Male")) {
                                txtGender.setText(selectedRadioButtonText);
                                maleRadioButton.setChecked(true);
                            } else {
                                txtGender.setText(selectedRadioButtonText);
                                femaleRadioButton.setChecked(true);

                            }
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE://cancel button
                        break;
                }
            }
        };
        builder.setTitle("Select Gender").setPositiveButton(getResources().getString(R.string.ok), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.label_cancel), dialogClickListener).show();


    }


    @SuppressLint("ResourceType")
    private void showWeightData() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setTitle("Select your weight");
        View customView = LayoutInflater.from(this).inflate(
                R.layout.custom_list_data, null, false);
        final ListView listView = customView.findViewById(R.id.listData);
        String[] weight_data = getResources().getStringArray(R.array.weight_data);

        listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, weight_data));
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 750));
        builder.setView(customView);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) listView.getItemAtPosition(position);
                Log.d(TAG, "showWeightData Position :" + position + "  value : " + itemValue);
                txtWeight.setText(itemValue);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showHeightData() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setTitle("Select your height");
        View customView = LayoutInflater.from(this).inflate(R.layout.custom_list_data, null, false);

        final ListView listView = customView.findViewById(R.id.listData);

        String[] height_data = getResources().getStringArray(R.array.height_data);
        listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, height_data));
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 750));
        builder.setView(customView);

        final AlertDialog dialog = builder.create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) listView.getItemAtPosition(position);
                Log.d(TAG, "showHeightData Position :" + position + "  value : " + itemValue);
                txtHeight.setText(itemValue);
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    private void savePreferences(String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private void showCalendarDialog() {
        DialogFragment dialogFragment = new DatePickerDialogTheme();
        dialogFragment.show(getFragmentManager(), "DatePickerDialogTheme");
    }

}
