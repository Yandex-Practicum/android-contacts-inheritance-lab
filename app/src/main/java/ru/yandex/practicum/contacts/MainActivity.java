package ru.yandex.practicum.contacts;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import java.util.List;
import java.util.Objects;

import ru.yandex.practicum.contacts.databinding.ActivityMainBinding;
import ru.yandex.practicum.contacts.ui.adapter.ContactAdapter;
import ru.yandex.practicum.contacts.ui.main.MainViewModel;
import ru.yandex.practicum.contacts.ui.main.MenuClick;
import ru.yandex.practicum.contacts.ui.main.UiState;
import ru.yandex.practicum.contacts.ui.model.ContactUi;
import ru.yandex.practicum.contacts.utils.widget.EditTextUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        adapter = new ContactAdapter();
        binding.recycler.setAdapter(adapter);

        final DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.item_decoration)));
        binding.recycler.addItemDecoration(decoration);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getContactsLiveDate().observe(this, this::updateContacts);
        viewModel.getUiStateLiveDate().observe(this, this::updateUiState);

        EditTextUtils.addTextListener(binding.searchLayout.searchText, query -> viewModel.updateSearchText(query.toString()));
        EditTextUtils.debounce(binding.searchLayout.searchText, query -> viewModel.search(query.toString()));
        binding.searchLayout.resetButton.setOnClickListener(view -> clearSearch());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_sort) {
            toast(R.string.menu_sort);
            return true;
        }
        if (id == R.id.menu_filter) {
            toast(R.string.menu_filter);
            return true;
        }
        if (id == R.id.menu_search) {
            viewModel.onMenuClick(MenuClick.SEARCH);
            toast(R.string.menu_search);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }

    private void updateContacts(List<ContactUi> contacts) {
        adapter.setItems(contacts);
        binding.recycler.scrollToPosition(0);
        if (contacts.size() > 0) {
            binding.recycler.setVisibility(View.VISIBLE);
            binding.nothingFound.setVisibility(View.GONE);
        } else {
            binding.recycler.setVisibility(View.GONE);
            binding.nothingFound.setVisibility(View.VISIBLE);
        }
    }

    private void updateUiState(UiState uiState) {
        if (uiState.finishing) {
            finish();
            return;
        }
        binding.searchLayout.getRoot().setVisibility(uiState.searchVisibility ? View.VISIBLE : View.GONE);
        binding.searchLayout.resetButton.setVisibility(uiState.resetSearchButtonVisibility ? View.VISIBLE : View.GONE);
    }

    private void clearSearch() {
        binding.searchLayout.searchText.setText("");
        viewModel.search("");
    }

    private void toast(@StringRes int res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }
}