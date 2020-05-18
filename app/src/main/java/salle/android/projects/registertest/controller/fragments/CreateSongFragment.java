package salle.android.projects.registertest.controller.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import salle.android.projects.registertest.R;
import salle.android.projects.registertest.controller.dialogs.StateDialog;
import salle.android.projects.registertest.restapi.callback.GenreCallback;
import salle.android.projects.registertest.restapi.callback.TrackCallback;
import salle.android.projects.registertest.restapi.manager.CloudinaryManager;
import salle.android.projects.registertest.restapi.manager.GenreManager;
import salle.android.projects.registertest.model.Genre;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.utils.Constants;

public class CreateSongFragment extends Fragment implements GenreCallback, TrackCallback {

    public static final String TAG = CreateSongFragment.class.getName();

    private EditText etTitle;
    private Spinner mSpinner;
    private TextView mFilename;
    private Button btnFind, btnAccept;

    private ArrayList<String> mGenres;
    private ArrayList<Genre> mGenresObjs;
    private Uri mFileUri;

    private Context mContext;

    public CreateSongFragment() {
    }

    public static CreateSongFragment getInstance(){
        return new CreateSongFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_create_song, container, false);
        mContext = getContext();
        initViews(v);
        getData();
        return v;
    }

    private void initViews(View v) {
        etTitle = (EditText) v.findViewById(R.id.create_song_title);
        mFilename = (TextView) v.findViewById(R.id.create_song_file_name);

        mSpinner = (Spinner) v.findViewById(R.id.create_song_genre);

        btnFind = (Button) v.findViewById(R.id.create_song_file);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAudioFromStorage();
            }
        });

        btnAccept = (Button) v.findViewById(R.id.create_song_accept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkParameters()) {
                    etTitle.setFocusable(false);
                    showStateDialog(false);
                    uploadToCloudinary();
                }
            }
        });

    }

    private void getData() {
        GenreManager.getInstance(getContext()).getAllGenres(this);
    }

    private boolean checkParameters() {
        if (!etTitle.getText().toString().equals("")) {
            if (mFileUri != null) {
                return true;
            }
        }
        return false;
    }

    private void showStateDialog(boolean completed) {
        StateDialog.getInstance(getContext()).showStateDialog(completed);
    }

    private void getAudioFromStorage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/mpeg");
        startActivityForResult(Intent.createChooser(intent, "Choose a song"), Constants.STORAGE.SONG_SELECTED);
    }

    private void uploadToCloudinary() {
        Genre genre = new Genre();
        for (Genre g: mGenresObjs) {
            if (g.getName().equals(mSpinner.getSelectedItem().toString())) {
                genre = g;
            }
        }
        CloudinaryManager.getInstance(getContext(), this).uploadAudioFile(mFileUri, etTitle.getText().toString(), genre);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.STORAGE.SONG_SELECTED && resultCode == getActivity().RESULT_OK) {
            mFileUri = data.getData();
            mFilename.setText(mFileUri.toString());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   GenreCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onGenresReceive(ArrayList<Genre> genres) {
        mGenresObjs = genres;
        mGenres = (ArrayList<String>) genres.stream().map(Genre -> Genre.getName()).collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, mGenres);
        mSpinner.setAdapter(adapter);
    }

    @Override
    public void onTracksByGenre(ArrayList<Track> tracks) {

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
        StateDialog.getInstance(getContext()).showStateDialog(true);
        Thread watchDialog = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (StateDialog.getInstance(mContext).isDialogShown()){}
                    //getActivity().finish();
                } catch (Exception e) {
                }
            }
        });
        watchDialog.start();
    }
    @Override
    public void onLikeSuccess(Track track) {

    }

    @Override
    public void getTrack(Track track) {

    }
}