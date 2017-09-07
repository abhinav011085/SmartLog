package user.itjunkies.com.smartlog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by User on 07-Sep-17.
 */

public class AdapterPhotos extends RecyclerView.Adapter<AdapterPhotos.Holder> {
    Context context;
    ArrayList<Model_images> al_images;
    int int_position;
    String TAG = "data";

    public AdapterPhotos(Context context, ArrayList<Model_images> al_images, int int_position) {
        this.al_images = al_images;
        this.context = context;
        this.int_position = int_position;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_photos, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Glide.with(context).load(al_images.get(int_position).getAl_imagepath().get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.iv_image);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return al_images.get(int_position).getAl_imagepath().size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        RelativeLayout selected;

        public Holder(View itemView) {
            super(itemView);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            selected = (RelativeLayout) itemView.findViewById(R.id.selected);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selected.getVisibility() == View.GONE) {
                        selected.setVisibility(View.VISIBLE);
                        PhotosActivity.selected_images.add(al_images.get(int_position).getAl_imagepath().get(getAdapterPosition()));
                        PhotosActivity.done.setVisibility(View.VISIBLE);
                    } else {
                        selected.setVisibility(View.GONE);
                        PhotosActivity.selected_images.remove(al_images.get(int_position).getAl_imagepath().get(getAdapterPosition()));
                        if (PhotosActivity.selected_images.size() == 0)
                            PhotosActivity.done.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}
