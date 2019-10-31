package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.ObjectiveWithKeyResults;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.databinding.ObjectiveItemViewholderBinding;

public class ObjectivesRecyclerViewAdapter extends RecyclerView.Adapter<ObjectivesRecyclerViewAdapter.ObjectiveViewHolder> {

    private List<ObjectiveWithKeyResults> mObjectives;

    public ObjectivesRecyclerViewAdapter(List<ObjectiveWithKeyResults> objectives) {
        mObjectives = objectives;
    }

    @NonNull
    @Override
    public ObjectiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ObjectiveItemViewholderBinding binding = DataBindingUtil
                .inflate(layoutInflater, R.layout.objective_item_viewholder, parent, false);
        return new ObjectiveViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectiveViewHolder holder, int position) {
        ObjectiveWithKeyResults objective = mObjectives.get(position);
        holder.bind(objective, view -> {
            System.out.println(objective.getObjective().getId());
        });
    }


    @Override
    public int getItemCount() {
        return mObjectives.size();
    }

    public void updateItems(List<ObjectiveWithKeyResults> objectives) {
        final ObjectiveDiffCallback diffCallback = new ObjectiveDiffCallback(this.mObjectives, objectives);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        mObjectives.clear();
        mObjectives.addAll(objectives);
        diffResult.dispatchUpdatesTo(this);
    }

    class ObjectiveViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ObjectiveItemViewholderBinding mBinding;

        ObjectiveViewHolder(@NonNull ObjectiveItemViewholderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(ObjectiveWithKeyResults objective, View.OnClickListener clickListener) {
            mBinding.setClickListener(clickListener);
            mBinding.setObjectiveWithKeyResults(objective);
            mBinding.executePendingBindings();
        }
    }
}
