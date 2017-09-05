package user.itjunkies.com.smartlog;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by User on 05-Sep-17.
 */

public class ImagePicker {
    Activity activity;
    int image_pick_code = 101;

    public ImagePicker(Activity activity) {
        this.activity = activity;
    }

    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), image_pick_code);
    }
}
