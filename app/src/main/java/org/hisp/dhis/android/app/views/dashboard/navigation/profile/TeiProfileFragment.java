package org.hisp.dhis.android.app.views.dashboard.navigation.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hisp.dhis.android.app.R;
import org.hisp.dhis.android.app.TrackerCaptureApp;
import org.hisp.dhis.android.app.views.dashboard.navigation.AbsTeiNavigationSectionFragment;
import org.hisp.dhis.client.sdk.ui.models.FormEntity;
import org.hisp.dhis.client.sdk.ui.rows.RowViewAdapter;

import java.util.List;

import javax.inject.Inject;

public class TeiProfileFragment extends AbsTeiNavigationSectionFragment implements TeiProfileView {

    @Inject
    TeiProfilePresenter teiProfilePresenter;
    private RowViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews(inflater, container);

        try {
            ((TrackerCaptureApp) getActivity().getApplication())
                    .getActivityComponent().inject(this);
        } catch (Exception e) {
            Log.e("DataEntryFragment", "Activity or Application is null. Vital resources have been killed.", e);
        }

        teiProfilePresenter.attachView(this);

        return recyclerView;
    }

    private void initViews(LayoutInflater inflater, @Nullable ViewGroup container) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_tei_navigation, container, false);
        adapter = new RowViewAdapter(getFragmentManager());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        teiProfilePresenter.drawProfile("Enrollment uid");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        teiProfilePresenter.detachView();
    }

    @Override
    public void drawProfileItems(List<FormEntity> formEntities) {
        //adapter.swap(formEntities);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return adapter;
    }
}
