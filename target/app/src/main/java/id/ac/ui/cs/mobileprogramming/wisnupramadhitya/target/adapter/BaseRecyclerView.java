package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.adapter;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * It is just a recycleview but add some implementations for update the items.
 * @param <VH>
 * @param <T>
 */
public abstract class BaseRecyclerView<VH extends RecyclerView.ViewHolder, T extends DiffItem<T>> extends RecyclerView.Adapter<VH> {

    private List<T> mItems;

    public BaseRecyclerView(List<T> items) {
        mItems = items;
    }

    protected List<T> getItems() {
        return mItems;
    }

    /**
     * Update the items and use {@link ItemsDiffCallback} to check the difference.
     * @param items
     */
    public void updateItems(List<T> items) {
        final ItemsDiffCallback<T> diffCallback = new ItemsDiffCallback<>(mItems, items);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        mItems.clear();
        mItems.addAll(items);
        // update to adapter, like notifyItemBlabla.
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    interface ItemClickListenerub {
        void onClick();
    }
}
