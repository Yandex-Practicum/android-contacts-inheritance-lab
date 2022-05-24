package ru.yandex.practicum.contacts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.yandex.practicum.contacts.databinding.ActivityMainBinding;
import ru.yandex.practicum.contacts.ui.adapter.ContactAdapter;
import ru.yandex.practicum.contacts.ui.dialog.SortDialogFragment;
import ru.yandex.practicum.contacts.ui.main.MainViewModel;
import ru.yandex.practicum.contacts.ui.main.MenuClick;
import ru.yandex.practicum.contacts.ui.main.SortType;
import ru.yandex.practicum.contacts.ui.model.ContactUi;
import ru.yandex.practicum.contacts.utils.widget.EditTextUtils;

@SuppressLint("UnsafeExperimentalUsageError")
public class MainActivity extends AppCompatActivity {

    public static final String SORT_TAG = "SORT_TAG";

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private ContactAdapter adapter;

    private final Map<Integer, BadgeDrawable> badges = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        adapter = new ContactAdapter();
        binding.recycler.setAdapter(adapter);

        final DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.item_contact_decoration)));
        binding.recycler.addItemDecoration(decoration);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getContactsLiveDate().observe(this, this::updateContacts);
        viewModel.getUiStateLiveDate().observe(this, this::updateUiState);

        EditTextUtils.addTextListener(binding.searchLayout.searchText, query -> viewModel.updateSearchText(query.toString()));
        EditTextUtils.debounce(binding.searchLayout.searchText, query -> viewModel.search(query.toString()));
        binding.searchLayout.resetButton.setOnClickListener(view -> clearSearch());

        getSupportFragmentManager().setFragmentResultListener(SortDialogFragment.REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                final String key = requestKey;
                final Bundle bundle = result;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        createBadges();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_sort) {
            SortDialogFragment.newInstance(SortType.BY_NAME).show(getSupportFragmentManager(), SORT_TAG);
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

    private void updateUiState(MainViewModel.UiState uiState) {
        if (uiState.finishing) {
            finish();
            return;
        }
        binding.searchLayout.getRoot().setVisibility(uiState.searchVisibility ? View.VISIBLE : View.GONE);
        binding.searchLayout.resetButton.setVisibility(uiState.resetSearchButtonVisibility ? View.VISIBLE : View.GONE);
        updateBadges(uiState);
    }

    private void updateBadges(MainViewModel.UiState uiState) {
        updateBadge(uiState.sortBadge, R.id.menu_sort);
        updateBadge(uiState.filterBadge, R.id.menu_filter);
        updateBadge(uiState.searchBadge, R.id.menu_search);
    }

    private void updateBadge(MainViewModel.UiState.MenuBadge badge, @IdRes int menuItemId) {
        final BadgeDrawable drawable = Objects.requireNonNull(badges.get(menuItemId));
        if (badge != null) {
            drawable.setVisible(true);
            if (badge.value > 0) {
                drawable.setNumber(badge.value);
            } else {
                drawable.clearNumber();
            }
        } else {
            drawable.setVisible(false);
        }
    }

    private void createBadges() {
        badges.put(R.id.menu_sort, createBadge());
        badges.put(R.id.menu_filter, createBadge());
        badges.put(R.id.menu_search, createBadge());

        for (Map.Entry<Integer, BadgeDrawable> entry : badges.entrySet()) {
            BadgeUtils.attachBadgeDrawable(entry.getValue(), binding.toolbar, entry.getKey());
        }
    }

    private BadgeDrawable createBadge() {
        final BadgeDrawable drawable = BadgeDrawable.create(this);
        drawable.setBackgroundColor(ContextCompat.getColor(this, R.color.color_red));
        drawable.setVisible(false);
        return drawable;
    }

    private void clearSearch() {
        binding.searchLayout.searchText.setText("");
        viewModel.search("");
    }

    private void toast(@StringRes int res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }
}