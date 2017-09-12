package user.itjunkies.com.smartlogexample;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
    }

    public void onClick(View v) {
        final CharSequence[] items = {"Images", "Videos"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //builder.setTitle("Select The Action");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0){
                    Intent intent = new Intent(MainActivity.this, FolderActivity.class);
                    intent.putExtra(ImagePicker.ACTION, ImagePicker.IMAGES);
                    startActivityForResult(intent, new ImagePicker().IMAGEPICHER_REQ);
                }else {
                    Intent intent = new Intent(MainActivity.this, FolderActivity.class);
                    intent.putExtra(ImagePicker.ACTION, ImagePicker.VIDEOS);
                    startActivityForResult(intent, new ImagePicker().IMAGEPICHER_REQ);
                }
            }
        });
        builder.show();
        //new ImagePicker(this).pickImage(PICK_IMAGE_MULTIPLE);

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
}
