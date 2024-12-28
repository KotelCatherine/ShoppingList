package com.example.shoppinglist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String[] productList = {"Сгущенка", "Пирожок", "Мясо", "Хлеб", "Молоко", "Масло", "Колбаса"};

    List<String> shoppingList;
    RecyclerView recyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shoppingList = new ArrayList<>();
        shoppingList.add("Хлеб");
        shoppingList.add("Молоко");
        shoppingList.add("Масло");
        shoppingList.add("Колбаса");
        Button button = findViewById(R.id.btnAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        myAdapter = new MyAdapter(shoppingList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }

    public void createDialog() {
        final String[] selectedProduct = new String[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите действие");
        builder.setSingleChoiceItems(productList, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int item) {
                        selectedProduct[0] = productList[item];
                    }
                });
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectedProduct[0] != null) {
                    Toast.makeText(MainActivity.this, "Товар добавлен", Toast.LENGTH_SHORT).show();
                    myAdapter.addData(selectedProduct[0]);
                    recyclerView.setAdapter(myAdapter);
                }else {
                    Toast.makeText(MainActivity.this, "Товар не выбран", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Отмена", Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isDeleted = myAdapter.deleteData(selectedProduct[0]);
                if (!isDeleted) {
                    Toast.makeText(MainActivity.this, "Такого товара нет в списке", Toast.LENGTH_SHORT).show();
                }
                recyclerView.setAdapter(myAdapter);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }
}