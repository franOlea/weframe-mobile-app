package weframe.com.weframeandroidclient.picture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import weframe.com.weframeandroidclient.R;

public class PictureGalleryAdapter extends RecyclerView.Adapter<PictureGalleryAdapter.ViewHolder> {
    private ArrayList<Picture> galleryList;
    private Context context;

    public PictureGalleryAdapter(Context context, ArrayList<Picture> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public PictureGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_thumbnail, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureGalleryAdapter.ViewHolder viewHolder, int i) {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageBitmap(getPictureByFile(galleryList.get(i).getFile()));
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Image", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //your stuff
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

    private Bitmap getPictureByFile(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth()/10, bitmap.getHeight()/10);
        return bitmap;
    }
}
