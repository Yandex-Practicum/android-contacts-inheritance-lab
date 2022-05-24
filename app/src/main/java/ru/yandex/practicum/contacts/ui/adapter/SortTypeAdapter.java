package ru.yandex.practicum.contacts.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.yandex.practicum.contacts.R;
import ru.yandex.practicum.contacts.databinding.ItemSortBinding;
import ru.yandex.practicum.contacts.ui.main.SortType;
import ru.yandex.practicum.contacts.ui.model.SortTypeUI;

public class SortTypeAdapter extends RecyclerView.Adapter<SortTypeAdapter.ViewHolder> {

    private final AsyncListDiffer<SortTypeUI> differ = new AsyncListDiffer<>(
            new AdapterListUpdateCallback(this),
            new AsyncDifferConfig.Builder<>(new ListDiffCallback()).build()
    );

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemSortBinding binding = ItemSortBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setItems(List<SortTypeUI> items) {
        differ.submitList(items);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemSortBinding binding;

        public ViewHolder(@NonNull ItemSortBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SortTypeUI sortTypeUI) {
            final int sortResId = resource(sortTypeUI.getSortType());
            binding.text.setText(sortResId);
            binding.done.setVisibility(sortTypeUI.isSelected() ? View.VISIBLE : View.GONE);
        }

        private int resource(SortType sortType) {
            switch (sortType) {
                case BY_NAME:
                    return R.string.sort_by_name;
                case BY_NAME_REVERSED:
                    return R.string.sort_by_name_reversed;
                case BY_SURNAME:
                    return R.string.sort_by_surname;
                case BY_SURNAME_REVERSED:
                    return R.string.sort_by_surname_reversed;
                default:
                    throw new IllegalArgumentException("Not supported SortType");
            }
        }
    }

    static class ListDiffCallback extends DiffUtil.ItemCallback<SortTypeUI> {

        @Override
        public boolean areItemsTheSame(@NonNull SortTypeUI oldItem, @NonNull SortTypeUI newItem) {
            return oldItem.hashCode() == newItem.hashCode();
        }

        @Override
        public boolean areContentsTheSame(@NonNull SortTypeUI oldItem, @NonNull SortTypeUI newItem) {
            return oldItem.equals(newItem);
        }
    }
}
