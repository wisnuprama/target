package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.RecentInfo;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.databinding.RecentinfoItemViewholderBinding;

public class RecentInfoRecyclerViewAdapter extends BaseRecyclerView<RecentInfoRecyclerViewAdapter.RecentInfoViewHolder, RecentInfo> {

    public RecentInfoRecyclerViewAdapter(List<RecentInfo> items) {
        super(items);
    }

    @NonNull
    @Override
    public RecentInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecentinfoItemViewholderBinding binding = DataBindingUtil
                .inflate(layoutInflater, R.layout.recentinfo_item_viewholder, parent, false);
        return new RecentInfoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentInfoViewHolder holder, int position) {
        RecentInfo recentInfo = getItems().get(position);
        holder.bind(recentInfo, view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recentInfo.getURL()));
            view.getContext().startActivity(browserIntent);
        });
    }

    class RecentInfoViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        RecentinfoItemViewholderBinding mBinding;

        RecentInfoViewHolder(@NonNull RecentinfoItemViewholderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        /**
         * Bind the data to view.
         * @param recentInfo
         * @param clickListener
         */
        void bind(RecentInfo recentInfo, View.OnClickListener clickListener) {
            mBinding.setClickListener(clickListener);
            mBinding.setRecentInfo(recentInfo);
            mBinding.executePendingBindings();
        }
    }
}
