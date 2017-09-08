package user.itjunkies.com.smartlog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
                Intent intent = new Intent(context, ShowPhotosActivity.class);
                startActivityForResult(intent, new ImagePicker().IMAGEPICHER_REQ);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.addItemDecoration(new ItemDecorationAlbumColumns(10, 3));
        recyclerView.setAdapter(new AdapterPhotos(context, FolderActivity.al_images, int_position));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        selected_images.clear();
    }
}
