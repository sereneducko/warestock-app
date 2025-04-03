package com.example.stockapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    LinearLayout buttonBar;


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

        buttonBar = findViewById(R.id.inventory_pageButtonBar);

        getInventory(null);
    }

    private void getInventory(String url) {
        Call<PaginationResponse> call = null;

        if (isLoading || currentPage > lastPage) {
            return;
        }

        if (url == null || url.isEmpty()) {
            call = ApiClient.getInstance().getItems(currentPage, perPage);
        } else {
            call = ApiClient.getInstance().getItemsByUrl(url);
        }

        call.enqueue(new Callback<PaginationResponse>() {
            @Override
            public void onResponse(Call<PaginationResponse> call, Response<PaginationResponse> response) {
                isLoading = false;

                if (response.isSuccessful() && response.body() != null) {
                    paginationResponse = response.body();
                    currentPage = paginationResponse.getMeta().getCurrent_page();
                    lastPage = paginationResponse.getMeta().getLast_page();
                    productList.addAll(paginationResponse.data);
                    recyclerView.removeAllViews();
                    setUpInventoryItemModel();
                    logInventoryItems();
                    adapter.notifyItemInserted(0);
                    generatePageButton();
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
                    product.getUnit(),
                    product.getBarcode(),
                    product.getProductPrice()
            ));
        }
    }

    private void generatePageButton(){
        if (buttonBar != null) {
            buttonBar.removeAllViews();
        }

//        Button prevButton = new Button(this);
//        prevButton.setText("Prev");
//        prevButton.setEnabled(currentPage>1);
//        prevButton.setOnClickListener(v -> {
//            currentPage--;
//            getInventory(paginationResponse.getLinks().getPrev());
//            generatePageButton();
//        });
//        buttonBar.addView(prevButton);
//
//        Button nextButton = new Button(this);
//        nextButton.setText("Next");
//        prevButton.setOnClickListener(v -> {
//            currentPage++;
//            getInventory(paginationResponse.getLinks().getPrev());
//            generatePageButton();
//        });
//        buttonBar.addView(nextButton);

        for (int i = 1; i < paginationResponse.getMeta().getLast_page() - 1; i++) {
            final int pageNumber = i;
            if (paginationResponse.getMeta().getLinks().get(i).getLabel() == null) {
                continue;
            }
            Button number = new Button(this);
            number.setText(paginationResponse.getMeta().getLinks().get(pageNumber).getLabel());
            number.setOnClickListener(v -> {
                currentPage = pageNumber;
                getInventory(paginationResponse.getMeta().getLinks().get(pageNumber).getUrl());

            });
            buttonBar.addView(number);
        }

    }

    private void logInventoryItems() {
        Log.d("InventoryActivity", "Inventory Item List Contents:");
        for (ModelRCInventoryItem item : inventoryItemRCModelList) {
            Log.d("InventoryActivity", "Item: " +
                    "Image URL: " + item.getImageUrl() + ", " +
                    "Name: " + item.getProductName() + ", " +
                    "Category: " + item.getProductCategory() + ", " +
                    "Quantity: " + item.getQuantity() + ", " +
                    "Barcode: " + item.getBarcode() + ", " +
                    "Price: " + item.getProductPrice());
        }
        Log.d("InventoryActivity", "LastPage: " + paginationResponse.getMeta().getLast_page());
    }
}