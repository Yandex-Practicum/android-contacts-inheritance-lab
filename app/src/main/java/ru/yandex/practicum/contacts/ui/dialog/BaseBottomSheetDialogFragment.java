package ru.yandex.practicum.contacts.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.yandex.practicum.contacts.databinding.FragmentBottomSheetBinding;

public abstract class BaseBottomSheetDialogFragment<T extends ViewModel> extends BottomSheetDialogFragment {

    private FragmentBottomSheetBinding binding;
    private Class<T> viewModelClass;

    protected T viewModel;

    public BaseBottomSheetDialogFragment(Class<T> viewModelClass) {
        this.viewModelClass = viewModelClass;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(viewModelClass);
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
