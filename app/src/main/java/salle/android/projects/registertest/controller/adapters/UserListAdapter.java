package salle.android.projects.registertest.controller.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.model.User;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder>  {

    public static final String TAG = UserListAdapter.class.getName();
    private List<User> mUsers;
    private Context mContext;
    private int layoutId;

    public UserListAdapter(List<User> users, Context context, int layoutId) {
        mUsers = users;
        mContext = context;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called.");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new UserListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mUsers != null && mUsers.size() > 0) {
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            String nombreCompleto = mUsers.get(position).getFirstName() + " " + mUsers.get(position).getLastName();
            holder.tvName.setText(nombreCompleto);
            holder.tvLogin.setText(mUsers.get(position).getLogin());
            if (mUsers.get(position).getImageUrl() != null) {
                Glide.with(mContext)
                        .asBitmap()
                        .placeholder(R.drawable.ic_audiotrack)
                        .load(mUsers.get(position).getImageUrl())
                        .into(holder.ivPicture);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size():0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLayout;
        TextView tvName;
        TextView tvLogin;
        ImageView ivPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.track_item_layout);
            tvName = (TextView) itemView.findViewById(R.id.track_title);
            tvLogin = (TextView) itemView.findViewById(R.id.track_author);
            ivPicture = (ImageView) itemView.findViewById(R.id.track_img);
        }
    }
}
