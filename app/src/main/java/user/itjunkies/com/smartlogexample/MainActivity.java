package user.itjunkies.com.smartlogexample;

import android.Manifest;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import user.itjunkies.com.smartlog.FolderActivity;
import user.itjunkies.com.smartlog.ImagePicker;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    String TAG = "data";
    private static final int REQUEST_PERMISSIONS = 100;
    String[] permissionsRequired = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
    }

    public void onClick(View v) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, permissionsRequired, REQUEST_PERMISSIONS);
        } else {
            showPopup();
        }

    }

    List<String> imagesEncodedList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == new ImagePicker().IMAGEPICHER_REQ && resultCode == RESULT_OK
                && null != data) {
            imagesEncodedList = data.getStringArrayListExtra(new ImagePicker().DATA);

            String path = "";
            for (int i = 0; i < imagesEncodedList.size(); i++) {
                path += "\n" + imagesEncodedList.get(i);
            }
            textView.setText(path);
        }
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
                if (allGrantred)
                    showPopup();
            }
        }
    }

    private void showPopup() {
        final CharSequence[] items = {"Images", "Videos"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //builder.setTitle("Select The Action");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    Intent intent = new Intent(MainActivity.this, FolderActivity.class);
                    intent.putExtra(ImagePicker.ACTION, ImagePicker.IMAGES);
                    startActivityForResult(intent, new ImagePicker().IMAGEPICHER_REQ);
                } else {
                    Intent intent = new Intent(MainActivity.this, FolderActivity.class);
                    intent.putExtra(ImagePicker.ACTION, ImagePicker.VIDEOS);
                    startActivityForResult(intent, new ImagePicker().IMAGEPICHER_REQ);
                }
            }
        });
        builder.show();
        //new ImagePicker(this).pickImage(PICK_IMAGE_MULTIPLE);
    }
}
