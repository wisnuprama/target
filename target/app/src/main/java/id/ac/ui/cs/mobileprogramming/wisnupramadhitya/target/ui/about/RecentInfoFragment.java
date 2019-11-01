package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.adapter.RecentInfoRecyclerViewAdapter;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.databinding.FragmentRecentInfoBinding;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.util.Injector;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.RecentInfoViewModel;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.viewmodel.RecentInfoViewModelFactory;

public class RecentInfoFragment extends Fragment {

    private RecentInfoViewModel mViewModel;
    private FragmentRecentInfoBinding mBinding;

    @BindView(R.id.recent_info_recycler_view)
    protected RecyclerView mRecentInfoRecyclerView;
    private RecentInfoRecyclerViewAdapter mRecentInfoAdapter;

    public static RecentInfoFragment newInstance() {
        return new RecentInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent_info,
                                           container, false);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
        setupRecentInfoRecyclerView();
    }

    private void setupViewModel() {
        RecentInfoViewModelFactory vmFactory = Injector
                .provideRecentInfoViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this, vmFactory).get(RecentInfoViewModel.class);
        mBinding.setRecentInfoViewModel(mViewModel);
    }

    private void setupRecentInfoRecyclerView() {
        mRecentInfoRecyclerView.setHasFixedSize(true);
        mRecentInfoAdapter = new RecentInfoRecyclerViewAdapter(new ArrayList<>());
        mRecentInfoRecyclerView.setAdapter(mRecentInfoAdapter);
        subscribeUI();
    }

    private void subscribeUI() {
        mViewModel.getRecentInfoLiveData()
                .observe(getViewLifecycleOwner(),
                         recentInfos -> mRecentInfoAdapter.updateItems(recentInfos));
    }
}
