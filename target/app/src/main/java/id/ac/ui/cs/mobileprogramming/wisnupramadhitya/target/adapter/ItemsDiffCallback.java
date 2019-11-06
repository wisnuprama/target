package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.adapter;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * Helper for check the difference between two list.
 * @param <T>
 */
public class ItemsDiffCallback<T extends DiffItem<T>> extends DiffUtil.Callback {

    private List<T> oldList;
    private List<T> newList;

    public ItemsDiffCallback(List<T> oldList, List<T> newList) {
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
        T oldItem = oldList.get(oldItemPosition);
        T newItem = newList.get(newItemPosition);
        return oldItem.isItemTheSame(newItem);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = oldList.get(oldItemPosition);
        T newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}
