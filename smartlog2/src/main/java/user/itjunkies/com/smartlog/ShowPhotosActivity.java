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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import user.itjunkies.com.smartlog.cropper.CropImage;
import user.itjunkies.com.smartlog.cropper.CropImageView;

public class ShowPhotosActivity extends AppCompatActivity {

    Context context = this;
    RecyclerView recyclerView;
    static ImageView imageView;

    String TAG = "data";

    static int selPos = 0;

    AdapterPhotosRecView adapterPhotosRecView;

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

        imageView = (ImageView) findViewById(R.id.image);
        Glide.with(context).load(PhotosActivity.selected_images.get(0))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(ShowPhotosActivity.imageView);

        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapterPhotosRecView = new AdapterPhotosRecView(context, PhotosActivity.selected_images);
        recyclerView.setAdapter(adapterPhotosRecView);

        ImageView crop = (ImageView) findViewById(R.id.crop);
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity(Uri.fromFile(new File(PhotosActivity.selected_images.get(selPos))))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setActivityTitle("Crop")
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setCropMenuCropButtonTitle("Done")
                        .setRequestedSize(400, 400)
                        .setCropMenuCropButtonIcon(R.drawable.ic_tick)
                        .start(ShowPhotosActivity.this);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                //((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                PhotosActivity.selected_images.set(selPos, result.getUri().getPath());
                adapterPhotosRecView.notifyItemChanged(selPos);
                Glide.with(context).load(result.getUri().getPath())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(ShowPhotosActivity.imageView);
                Toast.makeText(this, "Cropping successful, Sample: " + result.getUri().getPath(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                Log.i(TAG, "onActivityResult: " + result.getError());
            }
        }
    }

}
