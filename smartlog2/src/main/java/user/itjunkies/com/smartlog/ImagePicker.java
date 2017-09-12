package user.itjunkies.com.smartlog;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by User on 05-Sep-17.
 */

public class ImagePicker {
    static Activity activity;
    public int IMAGEPICHER_REQ = 101;
    public String DATA = "data";
    public static String ACTION="action";
    public static String IMAGES="images";
    public static String VIDEOS="videos";

    public ImagePicker(Activity activity) {
        this.activity = activity;
    }

    public ImagePicker() {
    }

    public void pickImage(int image_pick_code) {
        /*Intent intent = new Intent();
        intent.setType("image*//*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), image_pick_code);*/
        Intent intent = new Intent(activity, FolderActivity.class);
        activity.startActivity(intent);
    }
}
