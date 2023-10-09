package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;

public class videoeditor extends AppCompatActivity {
    private static final int REQUEST_VIDEO_PICK = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;
    private VideoView videoView;
    private Button galleryButton;
    private Button editButton;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoeditor);
        videoView = findViewById(R.id.videoView);
        galleryButton = findViewById(R.id.galleryButton);
        editButton = findViewById(R.id.editButton);


        // Initialize MediaController
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        // Set video URI and configure MediaController
        //videoView.setVideoURI(selectedVideoUri);
        videoView.setMediaController(mediaController);

        // Make the VideoView visible and start playing
        videoView.setVisibility(View.VISIBLE);
        videoView.start();

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace with your video editing logic
                // For this example, we'll trim the video
                editVideo(selectedVideoUri, editedVideoUri);
            }
        });
    }

    private Uri selectedVideoUri;
    private Uri editedVideoUri;

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, REQUEST_VIDEO_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_PICK && resultCode == RESULT_OK) {
            selectedVideoUri = data.getData();
            videoView.setVideoURI(selectedVideoUri);
            videoView.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.VISIBLE);
        }

    }

    private void editVideo(Uri inputVideoUri, Uri outputVideoUri) {
        String[] ffmpegCommand = {
                "-i", inputVideoUri.getPath(),
                "-ss", "00:00:05",  // Start trimming at 5 seconds
                "-t", "00:00:10",  // Trim 10 seconds
                "-c:v", "libx264",
                "-c:a", "aac",
                "-strict", "experimental",
                outputVideoUri.getPath()
        };

        FFmpeg ffmpeg = FFmpeg.getInstance(this);
        try {
            // Execute the FFmpeg command
            ffmpeg.execute(ffmpegCommand, new ExecuteBinaryResponseHandler() {
                @Override
                public void onSuccess(String message) {
                    // Video editing was successful
                    editedVideoUri = Uri.parse(outputVideoUri.getPath());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(videoeditor.this, "Video edited successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(String message) {
                    // Handle failure
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(videoeditor.this, "Video editing failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
