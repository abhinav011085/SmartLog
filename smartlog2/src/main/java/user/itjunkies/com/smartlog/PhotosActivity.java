package user.itjunkies.com.smartlog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhotosActivity extends AppCompatActivity {
    Context context = this;
    int int_position;
    private GridView gridView;
    GridViewAdapter adapter;
    RecyclerView recyclerView;

    public static ArrayList<String> selected_images = new ArrayList<>();
    static TextView done;
    String TAG = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_photos);

        //gridView = (GridView) findViewById(R.id.gv_folder);
        int_position = getIntent().getIntExtra("value", 0);
        //adapter = new GridViewAdapter(this, FolderActivity.al_images, int_position);
        //gridView.setAdapter(adapter);

        done = (TextView) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FolderActivity.type.equalsIgnoreCase(ImagePicker.IMAGES)) {
                    Intent intent = new Intent(context, ShowPhotosActivity.class);
                    startActivityForResult(intent, new ImagePicker().IMAGEPICHER_REQ);
                } else {
                    Intent intent = new Intent(context, ShowVideosActivity.class);
                    startActivityForResult(intent, new ImagePicker().IMAGEPICHER_REQ);
                }
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.addItemDecoration(new ItemDecorationAlbumColumns(10, 3));
        recyclerView.setAdapter(new AdapterPhotos(context, FolderActivity.al_images, int_position));

        ImageView take_pic = (ImageView) findViewById(R.id.take_pic);
        take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(PhotosActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    takePicture();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == new ImagePicker().IMAGEPICHER_REQ && resultCode == RESULT_OK
                && null != data) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra(new ImagePicker().DATA, PhotosActivity.selected_images);
            setResult(RESULT_OK, intent);
            finish();
        } else if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                selected_images.add(file.getPath());
                Intent intent = new Intent(context, ShowPhotosActivity.class);
                startActivityForResult(intent, new ImagePicker().IMAGEPICHER_REQ);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            }
        }
    }

    Uri file;

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100);
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        selected_images.clear();
    }
}
