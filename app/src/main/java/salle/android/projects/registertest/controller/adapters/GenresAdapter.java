package salle.android.projects.registertest.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.callbacks.GenreAdapterCallback;
import salle.android.projects.registertest.model.Genre;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.ViewHolder> {

    public static final String TAG = GenresAdapter.class.getName();

    private Context mContext;
    private GenreAdapterCallback mCallback;
    private int layoutId;
    private ArrayList<Genre> mGenres;

    public GenresAdapter(ArrayList<Genre> genres, Context context, GenreAdapterCallback callback, int layoutId) {
        mGenres = genres;
        mContext = context;
        mCallback = callback;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false);
        return new GenresAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (mGenres != null && mGenres.size() > 0) {
            holder.btnGenre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null)
                        mCallback.onClickGenre(mGenres.get(position));
                }
            });
        }
        holder.btnGenre.setText(mGenres.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (mGenres != null ? mGenres.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnGenre = (Button) itemView.findViewById(R.id.item_genre_btn);
        }
    }
}