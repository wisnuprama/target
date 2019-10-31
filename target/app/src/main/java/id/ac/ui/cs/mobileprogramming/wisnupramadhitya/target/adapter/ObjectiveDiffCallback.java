package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.adapter;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.data.model.ObjectiveWithKeyResults;

public class ObjectiveDiffCallback extends DiffUtil.Callback {

    private List<ObjectiveWithKeyResults> oldList;
    private List<ObjectiveWithKeyResults> newList;

    public ObjectiveDiffCallback(List<ObjectiveWithKeyResults> oldList, List<ObjectiveWithKeyResults> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        ObjectiveWithKeyResults oldItem = oldList.get(oldItemPosition);
        ObjectiveWithKeyResults newItem = newList.get(newItemPosition);
        return oldItem.getObjective().getId().equals(newItem.getObjective().getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ObjectiveWithKeyResults oldItem = oldList.get(oldItemPosition);
        ObjectiveWithKeyResults newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}
