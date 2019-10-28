package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.objectives;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.Project;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.source.repository.PreferenceRepository;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.Injector;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectivesViewModel;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.ObjectivesViewModelFactory;

public class ObjectivesFragment extends Fragment {

    @BindView(R.id.project_name)
    protected TextView mTextView;

    private ObjectivesViewModel mViewModel;

    public static ObjectivesFragment newInstance() {
        return new ObjectivesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_objectives,
                                                          container, false);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ObjectivesViewModelFactory factory = Injector.provideObjectivesViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this, factory).get(ObjectivesViewModel.class);
        mViewModel.selectedProjectId.set(PreferenceRepository.getActiveProjectId(getActivity()));
        mViewModel.projectLiveData.observe(this, new Observer<Project>() {
            @Override
            public void onChanged(@NonNull Project project) {
                mTextView.setText(project.getProjectName());
            }
        });

    }
}
