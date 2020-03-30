package salle.android.projects.registertest.restapi.callback;

import java.util.ArrayList;

import salle.android.projects.registertest.model.Genre;
import salle.android.projects.registertest.model.Track;

public interface GenreCallback extends FailureCallback {

    void onGenresReceive(ArrayList<Genre> genres);
    void onTracksByGenre(ArrayList<Track> tracks);
}

