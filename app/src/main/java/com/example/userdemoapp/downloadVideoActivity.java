package com.example.userdemoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FileDownloadTask.TaskSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class downloadVideoActivity extends AppCompatActivity {

    private Uri videUri;
    private static final int REQUEST_CODE = 101;
    private StorageReference videoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(17, 0, 17));
        setContentView(R.layout.activity_download_video);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        StorageReference storageReference =
                FirebaseStorage.getInstance().getReference();
        videoRef = storageReference.child("/videos/"+ uid + "/userIntro.mp4");

    }
}