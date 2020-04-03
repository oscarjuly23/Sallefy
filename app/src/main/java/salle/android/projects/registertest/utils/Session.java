package salle.android.projects.registertest.utils;

import android.content.Context;

import java.util.ArrayList;

import salle.android.projects.registertest.model.Playlist;
import salle.android.projects.registertest.model.Track;
import salle.android.projects.registertest.model.User;
import salle.android.projects.registertest.model.UserRegister;
import salle.android.projects.registertest.model.UserToken;

public class Session {

    public static Session sSession;
    private static Object mutex = new Object();

    private Context mContext;

    private UserRegister mUserRegister;
    private User mUser;
    private UserToken mUserToken;

    private boolean audioEnabled;

    private Track mTrack;
    private Playlist mPlaylist;
    private ArrayList<Track> mTracks;
    private int mIndex;
    private boolean isPlaying;

    public static Session getInstance(Context context) {
        Session result = sSession;
        if (result == null) {
            synchronized (mutex) {
                result = sSession;
                if (result == null)
                    sSession = result = new Session();
            }
        }
        return result;
    }

    private Session() {}

    public Session(Context context) {
        this.mContext = context;
        this.mUserRegister = null;
        this.mUserToken = null;
    }

    public void resetValues() {
        mUserRegister = null;
        mUserToken = null;
    }

    public UserRegister getUserRegister() {
        return mUserRegister;
    }

    public void setUserRegister(UserRegister userRegister) {
        mUserRegister = userRegister;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public void setAudioEnabled(boolean audioEnabled) {
        this.audioEnabled = audioEnabled;
    }


    public UserToken getUserToken() {
        return mUserToken;
    }

    public void setUserToken(UserToken userToken) {
        this.mUserToken = userToken;
    }

    public boolean isAudioEnabled() {
        return audioEnabled;
    }

    public Track getTrack() {
        return mTrack;
    }

    public void setTrack(Track track) {
        mTrack = track;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public void setTrack(ArrayList<Track> tracks, int index) {
        mTracks = tracks;
        mIndex = index;
        mTrack = tracks.get(index);
    }

    public void setTrack(Playlist playlist, int index) {
        mTracks = (ArrayList<Track>) playlist.getTracks();
        mIndex = index;
        mTrack = mTracks.get(index);
    }

    public Playlist getPlaylist() {
        return mPlaylist;
    }

    public void setPlaylist(Playlist playlist) {
        mPlaylist = playlist;
    }

    public ArrayList<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        mTracks = tracks;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
