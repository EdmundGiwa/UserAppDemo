package com.example.userdemoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;

import java.io.File;
import java.io.IOException;

public class VideoPage extends AppCompatActivity{

    private Uri videUri;
    private static final int REQUEST_CODE = 101;
    private StorageReference videoRef;

   Button watchVideoBtn;
   LinearLayout downloadPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(17, 0, 17));
        setContentView(R.layout.activity_video_page);

        watchVideoBtn = findViewById(R.id.watch);
        downloadPage = findViewById(R.id.downloadUpPage);

        watchVideoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadPage.setVisibility(View.VISIBLE);
            }
        });

String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

StorageReference storageReference =
        FirebaseStorage.getInstance().getReference();
videoRef = storageReference.child("/videos/"+ uid + "/userIntro.mp4");
    }



    public void upload(View view) {
        if(videUri != null){
            UploadTask uploadTask = videoRef.putFile(videUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(VideoPage.this,
                            "Upload Failed" + e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(VideoPage.this,
                            "Upload Complete :)",
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    updateProgress(taskSnapshot);
                }
            });
        }else{
            Toast.makeText(VideoPage.this, "There's nothing to upload", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateProgress(UploadTask.TaskSnapshot taskSnapshot){
        long fileSize = taskSnapshot.getTotalByteCount();

        long uploadBytes = taskSnapshot.getBytesTransferred();

        long progress = (100 * uploadBytes) / fileSize;

        ProgressBar progressBar  = findViewById(R.id.pBar);
        progressBar.setProgress((int) progress);
    }


    public void record(View view) {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(videoIntent, REQUEST_CODE);
    }

    public void download(View view) {
   try {
       final File localFile = File.createTempFile("userIntro", "mp4");

       videoRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
               Toast.makeText(VideoPage.this, "Download Complete",
                       Toast.LENGTH_SHORT).show();

               final VideoView videoView =(VideoView) findViewById(R.id.videoView);
               videoView.setVideoURI(Uri.fromFile(localFile));
               videoView.start();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(VideoPage.this, "Download failed: "+e.getLocalizedMessage(),
                       Toast.LENGTH_SHORT).show();
           }
       });
   } catch (Exception e){
       Toast.makeText(VideoPage.this, "Failed to create temp File: "+ e.getLocalizedMessage()
               , Toast.LENGTH_SHORT).show();
   }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        videUri = data.getData();
        if(requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                Toast.makeText(this, "Video saved to:\n "+
                        videUri, Toast.LENGTH_SHORT).show();
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Video recording cancelled"
                        , Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Failed to record video", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openDownPage(View view) {
        Intent intent = new Intent(VideoPage.this, downloadVideoActivity.class);
        startActivity(intent);
    }
}