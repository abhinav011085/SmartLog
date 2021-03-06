package user.itjunkies.com.smartlog;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by User on 06-Sep-17.
 */

public class GridViewAdapter extends ArrayAdapter<Model_images> {

    Context context;
    ViewHolder viewHolder;
    ArrayList<Model_images> al_menu = new ArrayList<>();
    int int_position;
    String TAG = "data";
    static RelativeLayout selected;

    GridViewAdapter(Context context, ArrayList<Model_images> al_menu, int int_position) {
        super(context, R.layout.adapter_photos, al_menu);
        this.al_menu = al_menu;
        this.context = context;
        this.int_position = int_position;
    }

    @Override
    public int getCount() {
        return al_menu.get(int_position).getAl_imagepath().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_menu.get(int_position).getAl_imagepath().size() > 0) {
            return al_menu.get(int_position).getAl_imagepath().size();
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_photos, parent, false);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            selected = (RelativeLayout) convertView.findViewById(R.id.selected);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(al_menu.get(int_position).getAl_imagepath().get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(viewHolder.iv_image);

        return convertView;

    }

    private static class ViewHolder {
        ImageView iv_image;
    }
}