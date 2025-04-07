package com.example.stockapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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
    final private ArrayList<ProductResponse> productList = new ArrayList<>();
    private int currentPage = 1;
    private int lastPage = 1;
    private boolean isLoading = false;
    ArrayList<ModelRCInventoryItem> inventoryItemRCModelList = new ArrayList<>();
    II_RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
//    TextView errorTextView;
    LinearLayout buttonBar;

    LinearLayout navigationButtonBar;

    //pagination variable
    int visiblePageCount = 5;
    int halfVisible = visiblePageCount / 2;


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
        navigationButtonBar = findViewById(R.id.inventory_pageNavButtonBar);

        getInventory(null);
    }

    private void getInventory(int pageNumber) {
        currentPage = pageNumber;
        getInventory(null);
    }

    private void getInventory(String url) {
        int perPage = 10;
        Call<PaginationResponse> call;

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

                    setUpInventoryItemModel();
                    adapter.notifyItemInserted(0);
//                    logInventoryItems();

                    recyclerView.setHasFixedSize(true);// Optional but good practice
                    recyclerView.scrollToPosition(0);
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

        productList.clear();
        productList.addAll(paginationResponse.data);
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

        MetaResponse metaData = paginationResponse.getMeta();
        LinkResponse linkData = paginationResponse.getLinks();

        final int firstPage = 1;

        if (buttonBar != null && navigationButtonBar !=null) {
            buttonBar.removeAllViews();
            navigationButtonBar.removeAllViews();
        }

        generateNavigationButton(
                "<",
                currentPage>1,
                v -> {
                    currentPage--;
                    getInventory(linkData.getPrev());
                },
                navigationButtonBar
        );

        generateNavigationButton(
                "1",
                true,
                v -> {
                    currentPage = 1;
                    getInventory(linkData.getFirst());
                },
                buttonBar
        );

        if (firstPage + (halfVisible) < currentPage) {
            generateEllipsis();
        }

        numberedButton(currentPage);

        if (lastPage - (halfVisible) > currentPage) {
            generateEllipsis();
        }

        generateNavigationButton(
                String.valueOf(metaData.getLast_page()),
                true,
                v -> {
                    currentPage = lastPage;
                    getInventory(linkData.getLast());
                },
                buttonBar
        );

        generateNavigationButton(
                ">",
                currentPage < lastPage,
                v -> {
                    currentPage++;
                    getInventory(linkData.getNext());
                },
                navigationButtonBar
        );

    }

    private void numberedButton(int currentPage) {
        int centerIndex = currentPage;

        for (int i = centerIndex - halfVisible; i <= centerIndex + halfVisible; i++) {
            final int indexNumber = i;
            if (i <= 1 || i >= lastPage) {
                continue;
            }

            Button number = new Button(this);
            number.setText(String.valueOf(i));
            number.setOnClickListener(v -> getInventory(indexNumber));
            addAndSpaceButton(number, buttonBar);
        }
    }

    private void generateEllipsis() {
        Button ellipsis = new Button(this);
        ellipsis.setText("...");
        addAndSpaceButton(ellipsis, buttonBar);
    }

    private void generateNavigationButton (String text, boolean condition, View.OnClickListener listener, LinearLayout view) {
        Button navButton = new Button(this);
        navButton.setText(text);
        navButton.setEnabled(condition);
        navButton.setOnClickListener(listener);
        addAndSpaceButton(navButton, view);
    }

    private void addAndSpaceButton(Button button, LinearLayout view){

        button.setSingleLine(true);
        button.setMinEms(2);
        button.setTextSize(12);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        button.setLayoutParams(params);

        view.addView(button);
    }

//    private void logInventoryItems() {
//        Log.d("InventoryActivity", "Inventory Item List Contents:");
//        for (ModelRCInventoryItem item : inventoryItemRCModelList) {
//            Log.d("InventoryActivity", "Item: " +
//                    "Image URL: " + item.getImageUrl() + ", " +
//                    "Name: " + item.getProductName() + ", " +
//                    "Category: " + item.getProductCategory() + ", " +
//                    "Quantity: " + item.getQuantity() + ", " +
//                    "Barcode: " + item.getBarcode() + ", " +
//                    "Price: " + item.getProductPrice());
//        }
//        Log.d("InventoryActivity", "LastPage: " + paginationResponse.getMeta().getLast_page());
//        Log.d("InventoryActivity", "CurrentPage: " + currentPage);
//
//    }
}