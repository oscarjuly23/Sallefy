package salle.android.projects.registertest.controller.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.adapters.PlaylistListAdapter;
import salle.android.projects.registertest.controller.adapters.TrackListAdapter;
import salle.android.projects.registertest.controller.adapters.UserListAdapter;
import salle.android.projects.registertest.controller.callbacks.FragmentCallback;
import salle.android.projects.registertest.controller.callbacks.PlaylistAdapterCallback;
import salle.android.projects.registertest.controller.callbacks.TrackListCallback;
import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Search;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.model.User;
import salle.android.projects.registertest.restapi.callback.SearchCallback;
import salle.android.projects.registertest.restapi.callback.TrackCallback;
import salle.android.projects.registertest.restapi.manager.SearchManager;
import salle.android.projects.registertest.restapi.manager.TrackManager;

public class SearchFragment extends Fragment implements TrackListCallback, TrackCallback, MaterialSearchBar.OnSearchActionListener, PlaylistAdapterCallback, SearchCallback, FragmentCallback, Toolbar.OnMenuItemClickListener {

    public static final String TAG = SearchFragment.class.getName();

    private RecyclerView mRecyclerViewTracks;
    private RecyclerView mRecyclerViewPlaylists;
    private RecyclerView mRecyclerViewUsers;
    private ArrayList<Track> mTracks;
    private int currentTrack = 0;
    private int index = 0;
    private FragmentCallback callback;
    private ArrayList<Playlist> mPlaylists;
    private ArrayList<User> mUsers;

    MaterialSearchBar searchBar;


    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.callback = (FragmentCallback) getActivity();
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
                        String url ="http://sallefy.eu-west-3.elasticbeanstalk.com/track/"+index;
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("tet/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, url);
                        startActivity(Intent.createChooser(intent, "Share with: "));
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_search, container, false);
        initViews(v);
        return v;
    }


    private void initViews(View v) {
        searchBar = v.findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        Log.d("LOG_TAG", getClass().getSimpleName() + ": text " + searchBar.getText());
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mRecyclerViewTracks = (RecyclerView) v.findViewById(R.id.tracks_recyclerview);
        LinearLayoutManager managerTracls = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        TrackListAdapter adapterTracks = new TrackListAdapter(this, getActivity(), null, null);
        mRecyclerViewTracks.setLayoutManager(managerTracls);
        mRecyclerViewTracks.setAdapter(adapterTracks);

        mRecyclerViewPlaylists = (RecyclerView) v.findViewById(R.id.playlist_recyclerview);
        LinearLayoutManager managerPlaylist = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        PlaylistListAdapter adapterPlaylist = new PlaylistListAdapter(null, getActivity(), this, R.layout.playlist_item);
        mRecyclerViewPlaylists.setLayoutManager(managerPlaylist);
        mRecyclerViewPlaylists.setAdapter(adapterPlaylist);

        mRecyclerViewUsers = (RecyclerView) v.findViewById(R.id.usuarios_recyclerview);
        LinearLayoutManager managerUser = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        UserListAdapter adapterUser = new UserListAdapter(null, getActivity(),  R.layout.playlist_item);
        mRecyclerViewUsers.setLayoutManager(managerUser);
        mRecyclerViewUsers.setAdapter(adapterUser);
    }

    private void getData(CharSequence text) {
        SearchManager.getInstance(getContext()).getMySearch(text.toString(),this);
        mTracks = new ArrayList<>();
        mPlaylists = new ArrayList<>();
        mUsers = new ArrayList<>();
    }

    public int getIndex(){
        return currentTrack;
    }

    public ArrayList<Track> getmTracks(){
        return mTracks;
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
    @Override
    public void onFailure(Throwable throwable) {

    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   TrackListCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onTrackSelected(View v, Fragment fragment, int idTrack) {
        index = idTrack;
        showPopup(v, R.style.MenuPopup, fragment);
    }
    @Override
    public void onTrackSelected(int index) {
        callback.updateTrack(mTracks, index);
    }
    @Override
    public void onTrackLike(int index) {
        TrackManager.getInstance(getContext()).likeTrack(mTracks.get(index).getId(),this);
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   MaterialSearchBar.OnSearchActionListener   *   *   *   *   *
     **********************************************************************************************/
    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        getData(text);
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.closeSearch();
                break;
        }
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   PlaylistAdapterCallback   *   *   *   *   *    *   *   *   *
     **********************************************************************************************/

    @Override
    public void onPlaylistClick(Playlist playlist) {
        Fragment fragment = null;
        fragment = PlaylistFragment.getInstance(playlist);
        onChangeFragment(fragment);
    }

    @Override
    public void onPlaylistClick(int index) {

    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   SearchAdapterCallback   *   *   *   *   *    *   *   *   *
     **********************************************************************************************/

    @Override
    public void getSearchs(Search search) {
        mTracks = (ArrayList<Track>) search.getTracks();
        mPlaylists = (ArrayList<Playlist>) search.getPlaylits();
        mUsers = (ArrayList<User>) search.getUsers();

        TrackListAdapter adapterTrack = new TrackListAdapter(this, getActivity(), mTracks, this);
        mRecyclerViewTracks.setAdapter(adapterTrack);

        PlaylistListAdapter playlistListAdapter = new PlaylistListAdapter(mPlaylists, getActivity(), this, R.layout.playlist_item);
        mRecyclerViewPlaylists.setAdapter(playlistListAdapter);

        UserListAdapter userListAdapter = new UserListAdapter(mUsers, getActivity(), R.layout.playlist_item);
        mRecyclerViewUsers.setAdapter(userListAdapter);

    }

    @Override
    public void getSearchsFailed(Throwable throwable) {

    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   FragmentCallback   *   *   *   *   *    *   *   *   *
     **********************************************************************************************/


    @Override
    public void onChangeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void updateTrack(ArrayList<Track> mTracks, int index) {

    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   OnMenuClickListener   *   *   *   *   *    *   *   *   *
     **********************************************************************************************/


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_toPlaylist:
                System.out.println("Añadir");
                //Toast.makeText(getContext(), "Añadir" + Toast.LENGTH_SHORT);
                return true;
            case R.id.share:
                System.out.println("Compartir");
                //Toast.makeText(getContext(), "Compartir" + Toast.LENGTH_SHORT);
                return true;
            default:
                return false;
        }
    }
}