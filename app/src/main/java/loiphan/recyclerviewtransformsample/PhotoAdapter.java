package loiphan.recyclerviewtransformsample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017, VNG Corp. All rights reserved.
 *
 * @author loiphan <loipn@vng.com.vn>
 * @version 1.0
 * @since August 23, 2017
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    public interface PhotoAdapterCallBack {
        void onItemClick(ImageView imageView, int position);
    }

    private PhotoAdapterCallBack mCallBack;

    public PhotoAdapter(PhotoAdapterCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mImageView.setTransitionName("photo_" + position);
        holder.txtPosition.setText(String.valueOf(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.onItemClick(holder.mImageView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView mImageView;
        @BindView(R.id.txtPosition)
        TextView txtPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
