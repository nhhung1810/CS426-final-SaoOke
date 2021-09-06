package com.example.blockchainapp.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.blockchainapp.Constants;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;

public class ImageChangeHandler {
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private String storagePermission[];

    private AppCompatActivity activity;
    private ImageView imageView;
    private String filename;

    public ImageChangeHandler(AppCompatActivity activity, ImageView imageView, String filename) {
        this.activity = activity;
        this.imageView = imageView;
        this.filename = filename;
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    public void handleOnRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode){
            case STORAGE_REQUEST_CODE:
                if(grantResults.length > 0){
                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        pickGallery();
                    } else {
                        Toast.makeText(activity.getBaseContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void initializeImage() {
        File file = new File(activity.getApplicationContext().getFilesDir().toString() + '/' + Constants.RESOURCE_LOCATION + "/avatar.png");
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(bitmap);
        }
    }

    public void pickImage() {
        if (!checkStoragePermission()){
            requestStoragePermission();
        } else {
            pickGallery();
        }
    }

    private void pickGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(activity, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean resultWriteStorage = ContextCompat.checkSelfPermission(activity.getBaseContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return resultWriteStorage;
    }

    public void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            Uri imageUri;
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                imageUri = data.getData();
                CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON)
                        .start(activity);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            try {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    Uri resultUri = result.getUri();
                    imageView.setImageURI(resultUri);
                    File folder = new File(activity.getBaseContext().getFilesDir(), Constants.RESOURCE_LOCATION);
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    File writeFile = new File(folder.getAbsolutePath() + "/avatar.png");
                    Log.d("File", writeFile.getAbsolutePath());
                    try {
                        FileOutputStream fout = new FileOutputStream(writeFile);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getBaseContext().getContentResolver(), resultUri);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
                        fout.flush();
                        fout.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Toast.makeText(activity.getBaseContext(), "" + error, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
