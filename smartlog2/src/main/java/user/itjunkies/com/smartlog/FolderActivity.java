package user.itjunkies.com.smartlog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class FolderActivity extends AppCompatActivity {
    public static ArrayList<Model_images> al_images = new ArrayList<>();
    boolean boolean_folder;
    Adapter_PhotosFolder obj_adapter;
    GridView gv_folder;
    private static final int REQUEST_PERMISSIONS = 100;
    String[] permissionsRequired = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    String TAG = "data";

    static String type;
    static int colorPrimary, colorPrimaryDark;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_folder);

        type = getIntent().getStringExtra(ImagePicker.ACTION);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ImagePicker.COLOR_PRIMARY);
        getWindow().setStatusBarColor(ImagePicker.COLOR_PRIMARY_DARK);

        ImageView back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //Window.setStatusBarColor(ImagePicker.COLOR_PRIMARY_DARK);
        /*Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
        getWindow().setStatusBarColor(ImagePicker.COLOR_PRIMARY_DARK);

        gv_folder = (GridView) findViewById(R.id.gv_folder);

        gv_folder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PhotosActivity.class);
                intent.putExtra("value", i);
                startActivityForResult(intent, new ImagePicker().IMAGEPICHER_REQ);
            }
        });

        if (ActivityCompat.checkSelfPermission(FolderActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(FolderActivity.this, permissionsRequired, REQUEST_PERMISSIONS);
        }  else {
            fn_imagespath();
        }
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
        }
    }

    public ArrayList<Model_images> fn_imagespath() {
        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        if (type.equalsIgnoreCase(ImagePicker.IMAGES)) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
            cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        } else {
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

            String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME};

            final String orderBy = MediaStore.Video.Media.DATE_TAKEN;
            cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        }
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            //Log.e("data", "Column" + absolutePathOfImage);
            //Log.e("data", "Folder" + cursor.getString(column_index_folder_name));

            for (int i = 0; i < al_images.size(); i++) {
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }

            if (boolean_folder) {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAl_imagepath(al_path);
            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                Model_images obj_model = new Model_images();
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                obj_model.setAl_imagepath(al_path);

                al_images.add(obj_model);
            }
        }

        /*for (int i = 0; i < al_images.size(); i++) {
            Log.e("FOLDER", al_images.get(i).getStr_folder());
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                Log.e("FILE", al_images.get(i).getAl_imagepath().get(j));
            }
        }*/
        obj_adapter = new Adapter_PhotosFolder(getApplicationContext(), al_images);
        gv_folder.setAdapter(obj_adapter);
        return al_images;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean allGrantred = false;
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        allGrantred = true;
                    } else {
                        allGrantred = false;
                    }
                }
            }
            if (allGrantred)
                fn_imagespath();
        }
    }
}
