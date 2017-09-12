package user.itjunkies.com.smartlogexample;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
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
        //new ImagePicker(this).pickImage(PICK_IMAGE_MULTIPLE);
        Intent intent = new Intent(this, FolderActivity.class);
        startActivityForResult(intent, new ImagePicker().IMAGEPICHER_REQ);
    }

    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
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
