package salle.android.projects.registertest.restapi.callback;

import salle.android.projects.registertest.model.Search;

public interface SearchCallback extends FailureCallback {
    void getSearchs(Search search);
    void getSearchsFailed(Throwable throwable);
}
