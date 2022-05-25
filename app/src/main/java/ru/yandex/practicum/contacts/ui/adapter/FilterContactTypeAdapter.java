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
import java.util.function.Consumer;

import ru.yandex.practicum.contacts.R;
import ru.yandex.practicum.contacts.databinding.ItemFilterBinding;
import ru.yandex.practicum.contacts.ui.main.FilterContactType;
import ru.yandex.practicum.contacts.ui.model.FilterContactTypeUi;

public class FilterContactTypeAdapter extends RecyclerView.Adapter<FilterContactTypeAdapter.ViewHolder> {

    private final AsyncListDiffer<FilterContactTypeUi> differ = new AsyncListDiffer<>(
            new AdapterListUpdateCallback(this),
            new AsyncDifferConfig.Builder<>(new ListDiffCallback()).build()
    );

    private final Consumer<FilterContactTypeUi> clickListener;

    public FilterContactTypeAdapter(Consumer<FilterContactTypeUi> clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemFilterBinding binding = ItemFilterBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setItems(List<FilterContactTypeUi> items) {
        differ.submitList(items);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemFilterBinding binding;

        private FilterContactTypeUi data;

        public ViewHolder(@NonNull ItemFilterBinding binding, Consumer<FilterContactTypeUi> clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(v -> clickListener.accept(data));
            this.binding.selected.setOnClickListener(v -> clickListener.accept(data));
        }

        public void bind(FilterContactTypeUi data) {
            this.data = data;
            final int sortResId = resource(data.getContactType());
            binding.text.setText(sortResId);
            binding.selected.setChecked(data.isSelected());
        }

        private int resource(FilterContactType contactType) {
            switch (contactType) {
                case ALL:
                    return R.string.filter_contact_type_all;
                case TELEGRAM:
                    return R.string.filter_contact_type_telegram;
                case WHATS_APP:
                    return R.string.filter_contact_type_whatsapp;
                case VIBER:
                    return R.string.filter_contact_type_viber;
                case SIGNAL:
                    return R.string.filter_contact_type_signal;
                case THREEMA:
                    return R.string.filter_contact_type_threema;
                case PHONE:
                    return R.string.filter_contact_type_phone;
                case EMAIL:
                    return R.string.filter_contact_type_email;
                default:
                    throw new IllegalArgumentException("Not supported SortType");
            }
        }
    }

    static class ListDiffCallback extends DiffUtil.ItemCallback<FilterContactTypeUi> {

        @Override
        public boolean areItemsTheSame(@NonNull FilterContactTypeUi oldItem, @NonNull FilterContactTypeUi newItem) {
            return oldItem.getContactType() == newItem.getContactType();
        }

        @Override
        public boolean areContentsTheSame(@NonNull FilterContactTypeUi oldItem, @NonNull FilterContactTypeUi newItem) {
            return oldItem.equals(newItem);
        }
    }
}
