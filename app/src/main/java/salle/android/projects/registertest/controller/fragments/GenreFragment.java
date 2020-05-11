package salle.android.projects.registertest.controller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.adapters.TrackListAdapter;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.controller.callbacks.TrackListCallback;
import salle.android.projects.registertest.model.Genre;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.restapi.callback.GenreCallback;
import salle.android.projects.registertest.restapi.callback.TrackCallback;
import salle.android.projects.registertest.restapi.manager.GenreManager;
import salle.android.projects.registertest.restapi.manager.TrackManager;

public class GenreFragment extends Fragment implements GenreCallback, TrackListCallback, TrackCallback {
    public static final String TAG = GenreFragment.class.getName();

    private TextView tvGenre;
    private RecyclerView mTracksView;
    private Genre genre;

    private ArrayList<Track> mTracks;
    private FragmentCallback callback;

    public GenreFragment(Genre genre){
        this.genre=genre;
    }

    public static GenreFragment getInstance(Genre genre) {
        return new GenreFragment(genre);
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
        View v =inflater.inflate(R.layout.fragment_genre, container, false);
        initViews(v);
        getData();
        return v;
    }

    private void initViews(View v){
        mTracksView = (RecyclerView) v.findViewById(R.id.dynamic_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        TrackListAdapter trackListAdapter = new TrackListAdapter( this, getActivity(), null, null);
        mTracksView.setLayoutManager(manager);
        mTracksView.setAdapter(trackListAdapter);

        tvGenre = v.findViewById(R.id.textView);
        tvGenre.setText(genre.getName());
    }

    private void getData() {
        GenreManager.getInstance(getActivity()).getTracksByGenre(genre.getId(),this);
        mTracks = new ArrayList<>();
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   GenreCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onGenresReceive(ArrayList<Genre> genres) {

    }
    @Override
    public void onTracksByGenre(ArrayList<Track> tracks) {
        mTracks = (ArrayList) tracks;
        TrackListAdapter adapter = new TrackListAdapter(this, getActivity(), mTracks, this);
        mTracksView.setAdapter(adapter);
    }
    @Override
    public void onFailure(Throwable throwable) {

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
        callback.updateTrack(mTracks, index);
    }
    @Override
    public void onTrackLike(int index) {
        TrackManager.getInstance(getContext()).likeTrack(mTracks.get(index).getId(), this);
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