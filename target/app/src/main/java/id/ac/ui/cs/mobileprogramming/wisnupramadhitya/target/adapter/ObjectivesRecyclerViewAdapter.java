package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.R;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.ObjectiveWithKeyResults;
import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.databinding.ObjectiveItemViewholderBinding;

/**
 * Objective recycler views.
 * Need refactor to BaseRecyclerAdapter because both implementation are same.
 */
public class ObjectivesRecyclerViewAdapter
        extends BaseRecyclerViewAdapter<ObjectivesRecyclerViewAdapter.ObjectiveViewHolder, ObjectiveWithKeyResults> {

    private ObjectiveClickListener mItemClickListener;

    public ObjectivesRecyclerViewAdapter() {
    }

    public ObjectivesRecyclerViewAdapter(List<ObjectiveWithKeyResults> items) {
        super(items);
    }

    @NonNull
    @Override
    public ObjectiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // binding the layout viewholder
        ObjectiveItemViewholderBinding binding = DataBindingUtil
                .inflate(layoutInflater, R.layout.objective_item_viewholder, parent, false);
        return new ObjectiveViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ObjectiveViewHolder holder, int position) {
        ObjectiveWithKeyResults objective = getItems().get(position);
        // bind the objective to view
        holder.bind(objective, view -> {
            if(mItemClickListener != null) {
                mItemClickListener.onClick(objective);
            }
        });
    }

    public void setItemClickListener(ObjectiveClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void removeItemClickListener() {
        mItemClickListener = null;
    }

    class ObjectiveViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ObjectiveItemViewholderBinding mBinding;

        ObjectiveViewHolder(@NonNull ObjectiveItemViewholderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        /**
         * Bind the data to view.
         * @param objective
         * @param clickListener
         */
        void bind(ObjectiveWithKeyResults objective, View.OnClickListener clickListener) {
            mBinding.setClickListener(clickListener);
            mBinding.setObjectiveWithKeyResults(objective);
            mBinding.executePendingBindings();
        }
    }

    public interface ObjectiveClickListener {
        void onClick(ObjectiveWithKeyResults objectiveWithKeyResults);
    }
}
