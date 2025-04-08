package com.example.stockapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddProductActivity extends AppCompatActivity {
// TODO: 07/04/2025 add the logic for storing the user given data and sending them using the api

    EditText productName;
    EditText productSku;
    EditText productUnit;
    String productImageUrl;
    // TODO: 08/04/2025 process image url adding
    AutoCompleteTextView productCategory;
    EditText productBarcode;
    // TODO: 08/04/2025 add barcode
    EditText productDescription;
    EditText productThreshold;
    Button submitButton;
    int price;
    int number;
    CategoryListResponse categoryListResponse;
    final ArrayList<CategoryResponse> categoryResponseArrayList = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_products);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_inventory_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        productName = findViewById(R.id.add_inventory_itemName_field);
        productSku = findViewById(R.id.add_inventory_sku_field);
        productUnit = findViewById(R.id.add_inventory_unit_field);
        productCategory = findViewById(R.id.add_inventory_category_field);
        productBarcode = findViewById(R.id.add_inventory_barcode_field);
        productDescription = findViewById(R.id.add_inventory_description_field);
        productThreshold = findViewById(R.id.add_inventory_threshold_field);
        number = Integer.parseInt(productThreshold.getText().toString());
        submitButton = findViewById(R.id.add_inventory_submit_button);
        price = 50000;

        setUpCategoryData();

        submitButton.setOnClickListener(v-> submitData());

        // bind all the view with a input
        // validate (if possible)
        // have the user be able to select from the available category
        // add a new category if needed
        // make the button do the post
    }

    public void setUpCategoryData(){

        Call<CategoryListResponse> call = ApiClient.getInstance().getCategories();

        call.enqueue(new Callback<CategoryListResponse>() {
            @Override
            public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {
                if (response.isSuccessful()) {
                    categoryListResponse = response.body();
                    categoryResponseArrayList.addAll(categoryListResponse.getCategories());

                    for (CategoryResponse category : categoryResponseArrayList) {
                        categories.add(new Category(category.getCategoryId(), category.getCategoryName()));
                    }

                    ArrayAdapter<Category> adapter = new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_dropdown_item_1line,
                            categories
                    );


                } else {
                    Log.e("AddProductActivity", "API call failed. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CategoryListResponse> call, Throwable t) {
                Log.e("AddProductActivity", "API call failed.", t);
            }
        });
    }

    public void submitData(){

        ProductRequest newProduct = new ProductRequest(
                productName.getText().toString(),
                productSku.getText().toString(),
                productImageUrl,
                productUnit.getText().toString(),
                productCategory.getText().toString(),
                productBarcode.getText().toString(),
                productDescription.getText().toString(),
                // TODO: 08/04/2025 replace the number and price with data from the batches
                number,
                price
        );

        Call<ProductResponse> call;

        call = ApiClient.getInstance().postProduct(newProduct);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()){

                    Intent intent = new Intent(AddProductActivity.this, InventoryActivity.class);

                    startActivity(intent);

                    finish();
                    //redirect to the new products page with the newest item showing.
                } else {
                    Log.e("AddProductActivity", "API call failed. Response code: " + response.code());
                    //explain why
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("AddProductActivity", "API call failed.", t);
            }
        });
    }
}
