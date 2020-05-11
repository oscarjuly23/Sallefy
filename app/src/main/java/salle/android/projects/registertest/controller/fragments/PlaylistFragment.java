package salle.android.projects.registertest.controller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.adapters.TrackListAdapter;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.controller.callbacks.TrackListCallback;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.PlaylistCallback;
import salle.android.projects.registertest.restapi.callback.TrackCallback;
import salle.android.projects.registertest.restapi.manager.PlaylistManager;
import salle.android.projects.registertest.restapi.manager.TrackManager;

public class PlaylistFragment extends Fragment implements TrackListCallback, PlaylistCallback, TrackCallback {
    public static final String TAG = PlaylistFragment.class.getName();

    private static final String FOLLOW_VIEW = "Siguiendo";
    private static final String UNFOLLOW_VIEW = "Seguir";

    private ImageView imageView;
    private TextView tvName;
    private TextView tvOwner;
    private Button btnFollow;

    private RecyclerView mRecyclerView;

    private Playlist playlist;
    private FragmentCallback callback;

    public PlaylistFragment(Playlist p){
        this.playlist = p;
    }

    public static PlaylistFragment getInstance(Playlist p) {
        return new PlaylistFragment(p);
    }

    public void showPopup(View v, int style, Fragment fragment) {
        Context wraper = new ContextThemeWrapper(getContext(),style);
        PopupMenu popupMenu = new PopupMenu(wraper, v);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu, popupMenu.getMenu());

        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.add_toPlaylist:
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case R.id.share:

                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        callback = (FragmentCallback) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_playlist, container, false);
        initViews(v);
        return v;
    }



    private void initViews(View v){
        mRecyclerView = v.findViewById(R.id.dynamic_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(this, getActivity(), (ArrayList<Track>) playlist.getTracks(), this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        imageView = v.findViewById(R.id.playlist_photo);
        if (playlist.getThumbnail() != null){
            Glide.with(this).load(playlist.getThumbnail()).into(imageView);
        }

        tvName = v.findViewById(R.id.name_playlist);
        tvName.setText(playlist.getName());
        tvOwner = v.findViewById(R.id.autor_playlist);
        tvOwner.setText(playlist.getUser().getLogin());

        PlaylistManager.getInstance(getContext()).isFollowingPlaylist(playlist.getId(),this);

        btnFollow = v.findViewById(R.id.follow_playlist);
        btnFollow.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (btnFollow.getTag().equals(UNFOLLOW_VIEW)) {
                followPlaylist();
                btnFollow.setTag(FOLLOW_VIEW);
                btnFollow.setText(FOLLOW_VIEW);
            } else {
                unFollowPlaylist();
                btnFollow.setTag(UNFOLLOW_VIEW);
                btnFollow.setText(UNFOLLOW_VIEW);
            }
        }
    });
    }

    private void followPlaylist() {
        PlaylistManager.getInstance(getContext()).followPlaylist(playlist.getId(),this);
    }

    private void unFollowPlaylist() {
        PlaylistManager.getInstance(getContext()).followPlaylist(playlist.getId(),this);
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   TrackListCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onTrackSelected(View v, Fragment fragment) {
        showPopup(v, R.style.MenuPopup, fragment);
    }
    @Override
    public void onTrackSelected(int index) {
        callback.updateTrack((ArrayList<Track>) playlist.getTracks(), index);
    }
    @Override
    public void onTrackLike(int index) {
        TrackManager.getInstance(getContext()).likeTrack(playlist.getTracks().get(index).getId(), this);
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   PlaylistCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onShowPlaylist(List<Playlist> playlists) {

    }
    @Override
    public void onShowPlaylistFailure(Throwable throwable) {

    }
    @Override
    public void onCreateSuccess(Playlist playlist) {

    }
    @Override
    public void onCreateFailed(Throwable throwable) {

    }
    @Override
    public void onUpdateSucces(Playlist playlist) {

    }
    @Override
    public void onFollowSucces(Playlist playlist) {

    }
    @Override
    public void getIsFollowed(Playlist playlist) {
        if (playlist.isFollowed()) {
            btnFollow.setTag(FOLLOW_VIEW);
            btnFollow.setText(FOLLOW_VIEW);
        } else {
            btnFollow.setTag(UNFOLLOW_VIEW);
            btnFollow.setText(UNFOLLOW_VIEW);
        }
    }
    @Override
    public void onFailure(Throwable throwable) {

    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   TrackCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onTracksReceived(List<Track> tracks) {

    }
    @Override
    public void onNoTracks(Throwable throwable) {

    }
    @Override
    public void onPersonalTracksReceived(List<Track> tracks) {

    }
    @Override
    public void onUserTracksReceived(List<Track> tracks) {

    }
    @Override
    public void onCreateTrack() {

    }
    @Override
    public void onLikeSuccess(Track track) {

    }
}