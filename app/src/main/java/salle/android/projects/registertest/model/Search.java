package salle.android.projects.registertest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Search implements Serializable {

    @SerializedName("tracks")
    private List<Track> tracks = null;
    @SerializedName("playlists")
    private List<Playlist> playlists = null;
    @SerializedName("users")
    private List<User> users = null;

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Playlist> getPlaylits() {
        return playlists;
    }

    public void setPlaylits(List<Playlist> playlits) {
        this.playlists = playlits;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
