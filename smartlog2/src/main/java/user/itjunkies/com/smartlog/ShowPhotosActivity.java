package user.itjunkies.com.smartlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ShowPhotosActivity extends AppCompatActivity {

    Context context = this;
    RecyclerView recyclerView;
    static ImageView imageView;

    String TAG = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(context, ImagePicker.activity.getClass());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putStringArrayListExtra(new ImagePicker().DATA, PhotosActivity.selected_images);
                setResult(new ImagePicker().IMAGEPICHER_REQ, intent);
                startActivityForResult(intent, new ImagePicker().IMAGEPICHER_REQ);*/
                Intent intent=new Intent();
                intent.putStringArrayListExtra(new ImagePicker().DATA, PhotosActivity.selected_images);
                setResult(RESULT_OK,intent);
                finish();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PhotosActivity.selected_images.clear();
                    }
                }, 1000);
            }
        });

        imageView = (ImageView) findViewById(R.id.image);
        Glide.with(context).load(PhotosActivity.selected_images.get(0))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(ShowPhotosActivity.imageView);

        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new AdapterPhotosRecView(context, PhotosActivity.selected_images));
    }

}
