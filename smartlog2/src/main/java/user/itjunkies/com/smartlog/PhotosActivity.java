package user.itjunkies.com.smartlog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity {
    int int_position;
    private GridView gridView;
    GridViewAdapter adapter;
    RecyclerView recyclerView;

    public static ArrayList<Model_images> selected_images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_photos);

        //gridView = (GridView) findViewById(R.id.gv_folder);
        int_position = getIntent().getIntExtra("value", 0);
        //adapter = new GridViewAdapter(this, FolderActivity.al_images, int_position);
        //gridView.setAdapter(adapter);

        recyclerView = (RecyclerView) findViewById(R.id.recView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new AdapterPhotos(this, FolderActivity.al_images, int_position));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        selected_images.clear();
    }
}
