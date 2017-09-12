package user.itjunkies.com.smartlog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class ShowVideosActivity extends AppCompatActivity {

    Context context = this;
    RecyclerView recyclerView;

    AdapterVideosRecView adapterPhotosRecView;
    MediaController mediaController;

    static VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_videos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.putStringArrayListExtra(new ImagePicker().DATA, PhotosActivity.selected_images);
                setResult(RESULT_OK, intent);
                finish();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PhotosActivity.selected_images.clear();
                    }
                }, 1000);
            }
        });

        final ImageView play = (ImageView) findViewById(R.id.play);
        videoview = (VideoView) findViewById(R.id.video);
        //Uri uri = Uri.fromFile(new File(PhotosActivity.selected_images.get(0)));
        //videoview.setVideoURI(uri);
        videoview.setVideoPath(PhotosActivity.selected_images.get(0));
        videoview.seekTo(200);

        videoview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (videoview.isPlaying()) {
                    videoview.pause();
                    play.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play.setVisibility(View.GONE);
                videoview.start();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapterPhotosRecView = new AdapterVideosRecView(context, PhotosActivity.selected_images);
        recyclerView.setAdapter(adapterPhotosRecView);
    }

}
