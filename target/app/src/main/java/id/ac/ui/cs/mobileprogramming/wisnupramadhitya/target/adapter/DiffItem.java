package id.ac.ui.cs.mobileprogramming.wisnupramadhitya.target.adapter;

/**
 * Interface for item class that used in BaseRecyclerViewAdapter.
 * @param <T>
 */
public interface DiffItem<T> {
    boolean isItemTheSame(T other);
}
