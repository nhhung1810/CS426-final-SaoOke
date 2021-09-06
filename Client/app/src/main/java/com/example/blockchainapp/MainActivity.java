package com.example.blockchainapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blockchainapp.Account.AccountActivity;
import com.example.blockchainapp.Auth.LoginActivity;
import com.example.blockchainapp.Campaign.CampaignOptionSelectActivity;
import com.example.blockchainapp.Transaction.GrantActivity;
import com.example.blockchainapp.HelpRequest.HelpRequestOptionSelectActivity;
import com.example.blockchainapp.Log.HistoryActivity;
import com.example.blockchainapp.Transaction.DonationActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.Locale;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivity extends AppCompatActivity {
    private TextView tv_username;
    private TextView tv_balance;
    private ImageView avatar;

    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private String storagePermission[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // loadLandingScreen(3000);

        initializeAvatar();

        if (!Constants.SESSION_ACTIVE) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Initialize();
        }
    }

    private void initializeAvatar() {
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        avatar = findViewById(R.id.person);
        File file = new File(this.getFilesDir().toString() + '/' + Constants.RESOURCE_LOCATION + "/avatar.png");
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            avatar.setImageBitmap(bitmap);
        }
    }

    private void Initialize() {
        tv_username = findViewById(R.id.tv_dashboard);
        tv_balance = findViewById(R.id.tv_balance);



        // BlockchainUtils.GetBalance(tv_balance);
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        tv_username.setText(Constants.USERNAME + "'s Dashboard");
        tv_balance.setText(currencyFormatter.format(Constants.BALANCE).toString() + " VNĐ");
    }

    public void DonateTab(View view) {
        Intent intent = new Intent(this, DonationActivity.class);
        startActivity(intent);
    }

    public void AccountTab(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void HistoryTab(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void RequestTab(View view) {
        Intent intent = new Intent(this, HelpRequestOptionSelectActivity.class);
        startActivity(intent);
    }

    public void CampaignTab(View view) {
        Intent intent = new Intent(this, CampaignOptionSelectActivity.class);
        startActivity(intent);
    }

    public void GrantTab(View view) {
        Intent intent = new Intent(this, GrantActivity.class);
        startActivity(intent);
    }

    public void Logout(View view) {
        Constants.SESSION_ACTIVE = false;
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void changeAvatar(View view) {
        if (!checkStoragePermission()){
            requestStoragePermission();
        } else {
            pickGallery();
        }
    }

    private void pickGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean resultWriteStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return resultWriteStorage;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode){
            case STORAGE_REQUEST_CODE:
                if(grantResults.length > 0){
                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri imageUri;
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                imageUri = data.getData();
                CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            try {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    avatar.setImageURI(resultUri);
                    File folder = new File(this.getFilesDir(), Constants.RESOURCE_LOCATION);
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    File writeFile = new File(folder.getAbsolutePath() + "/avatar.png");
                    Log.d("File", writeFile.getAbsolutePath());
                    try {
                        FileOutputStream fout = new FileOutputStream(writeFile);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
                        fout.flush();
                        fout.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}