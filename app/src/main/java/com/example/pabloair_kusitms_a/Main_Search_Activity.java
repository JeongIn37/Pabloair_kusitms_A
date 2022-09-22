package com.example.pabloair_kusitms_a;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.TintInfo;

import java.util.ArrayList;
import java.util.Locale;

public class Main_Search_Activity extends AppCompatActivity {

    public static ArrayList<SearchItem> SearchList = new ArrayList<SearchItem>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        searchItem();
        setUpData(); //데이터 set
        setUpList(); //list set

    }

    private void searchItem() {
        SearchView searchView = findViewById(R.id.home_searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<SearchItem> filterSearch = new ArrayList<>();

                for (int i = 0; i < SearchList.size(); i++) {
                    SearchItem searchItem = SearchList.get(i);

                    //데이터와 비교해서 상품 이름이 있다면
                    if (searchItem.getproductName().toLowerCase().contains(newText.toLowerCase())) {
                        filterSearch.add(searchItem);
                    }
                }

                SearchAdapter searchAdapter = new SearchAdapter(getApplicationContext(), 0, filterSearch);
                return false;
            }
        });
    }

        private void setUpData() {
        for(int i =0; i<2; i++) {
            SearchItem galic = new SearchItem("청보 깐마늘 100g", R.drawable.order_galic);
            SearchList.add(galic);
            SearchItem tomahoche = new SearchItem("돈마호크&생와사비", R.drawable.product_photo2);
            SearchList.add(tomahoche);
            SearchItem golbang = new SearchItem("백골뱅이탕&알품은한치", R.drawable.product_photo4);
            SearchList.add(golbang);
            SearchItem uni = new SearchItem("성게알과 우니", R.drawable.product_photo3);
            SearchList.add(uni);
        }

        }

        private void setUpList() {
        listView = findViewById(R.id.search_listView);

        SearchAdapter searchAdapter = new SearchAdapter(getApplicationContext(), 0,SearchList);
        listView.setAdapter(searchAdapter);
        }


}