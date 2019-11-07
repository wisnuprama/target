package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objective;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.adapter.ObjectivesRecyclerViewAdapter;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.PreferenceRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.databinding.FragmentObjectivesBinding;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.Injector;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectivesViewModel;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectivesViewModelFactory;

public class ObjectivesFragment extends Fragment {

    @BindView(R.id.project_name)
    protected TextView mTextView;

    private ObjectivesViewModel mViewModel;
    private FragmentObjectivesBinding mFragmentObjectivesBinding;

    @BindView(R.id.objectives_recycler_view)
    protected RecyclerView mObjectivesRecyclerView;
    private ObjectivesRecyclerViewAdapter mObjectivesAdapter;

    public static ObjectivesFragment newInstance() {
        return new ObjectivesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mFragmentObjectivesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_objectives,
                                                          container, false);
        mFragmentObjectivesBinding.setLifecycleOwner(this);
        View view = mFragmentObjectivesBinding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
        setupObjectivesRecyclerView();
    }

    private void setupViewModel() {
        ObjectivesViewModelFactory factory = Injector.provideObjectivesViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this, factory).get(ObjectivesViewModel.class);
        mFragmentObjectivesBinding.setObjectivesViewModel(mViewModel);
        mViewModel.onActivityCreated();
        // load latest active project
        mViewModel.selectedProjectId.set(PreferenceRepository.getActiveProjectId(getActivity()));
    }

    private void setupObjectivesRecyclerView(){
        mObjectivesRecyclerView.setHasFixedSize(true);
        mObjectivesAdapter = new ObjectivesRecyclerViewAdapter(new ArrayList<>());
        mObjectivesAdapter.setItemClickListener(objective -> {
            FragmentActivity activity = getActivity();
            if(activity != null) {
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                DetailObjectiveFragment detailObjectiveFragment = (DetailObjectiveFragment) fragmentManager
                        .findFragmentById(R.id.container_objective_detail);

                int objectiveId = objective.getObjective().getId();
                if(detailObjectiveFragment != null) {
                    // two-pane layout mode
                    detailObjectiveFragment.updateObjectiveView(objectiveId);
                } else {
                    // one-pane layout mode
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.container_objectives,
                                     DetailObjectiveFragment.newInstance(objectiveId))
                            .addToBackStack(null)
                            .commit();

                }
            }
        });

        mObjectivesRecyclerView.setAdapter(mObjectivesAdapter);
        subscribeUI();
    }

    private void subscribeUI() {
        mViewModel.getObjectiveLiveData()
                .observe(getViewLifecycleOwner(),
                          objectives -> {
                              mObjectivesAdapter.updateItems(objectives);
                              mObjectivesRecyclerView.smoothScrollToPosition(0);
                          });
    }
}
