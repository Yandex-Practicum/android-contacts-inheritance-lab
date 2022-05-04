package ru.yandex.practicum.contacts;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import ru.yandex.practicum.contacts.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.searchLayout.getRoot().setVisibility(View.VISIBLE);
            }
        }, 2000);
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
            toast(R.string.menu_search);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toast(@StringRes int res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }
}