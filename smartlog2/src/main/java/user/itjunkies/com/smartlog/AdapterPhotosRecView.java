package user.itjunkies.com.smartlog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by User on 07-Sep-17.
 */

class AdapterPhotosRecView extends RecyclerView.Adapter<AdapterPhotosRecView.Holder> {
    Context context;
    ArrayList<String> imageList;

    public AdapterPhotosRecView(Context context, ArrayList<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imageview, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Glide.with(context).load(imageList.get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.image);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        SquareImageView image;

        public Holder(View itemView) {
            super(itemView);
            image = (SquareImageView) itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Glide.with(context).load(imageList.get(getAdapterPosition()))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(ShowPhotosActivity.imageView);
                }
            });
        }
    }
}
