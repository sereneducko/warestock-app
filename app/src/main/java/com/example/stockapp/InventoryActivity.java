package com.example.stockapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryActivity extends AppCompatActivity {

    private PaginationResponse paginationResponse;
    private ArrayList<ProductResponse> productList = new ArrayList<>();
    private int currentPage = 1;
    private int perPage = 10;
    private int lastPage = 1;
    private boolean isLoading = false;
    ArrayList<ModelRCInventoryItem> inventoryItemRCModelList = new ArrayList<>();
    II_RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.inventory_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.inventory_rc_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new II_RecyclerViewAdapter(this, inventoryItemRCModelList); // Create adapter with empty list
        recyclerView.setAdapter(adapter);

        getInventory();
    }

    private void getInventory() {
        if (isLoading || currentPage > lastPage) {
            return;
        }

        ApiClient.getInstance().getItems(currentPage, perPage).enqueue(new Callback<PaginationResponse>() {
            @Override
            public void onResponse(Call<PaginationResponse> call, Response<PaginationResponse> response) {
                isLoading = false;

                if (response.isSuccessful() && response.body() != null) {
                    paginationResponse = response.body();
                    lastPage = paginationResponse.getMeta().getLast_page();
                    productList.addAll(paginationResponse.data);
                    setUpInventoryItemModel();
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("InventoryActivity", "API call failed. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PaginationResponse> call, Throwable t) {
                Log.e("InventoryActivity", "API call failed.", t);
            }
        });
    }

    private void setUpInventoryItemModel() {
        inventoryItemRCModelList.clear();
        for (ProductResponse product : productList) {
            inventoryItemRCModelList.add(new ModelRCInventoryItem(
                    product.getImageUrl(),
                    product.getName(),
                    product.getCategory().getName(),
                    product.getQuantity(),
                    product.getBarcode(),
                    product.getProductPrice()
            ));
        }
    }
}