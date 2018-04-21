package by.test2rest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import by.test2rest.internet.RestServiceText;
import by.test2rest.internet.get.ItemPost;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Disposable disposable;
    private RestServiceText restServiceText;
    private RecyclerView recyclerView;
    private InfoAdapter infoAdapter;
    public final String LOG = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(LOG, "OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        restServiceText = RestServiceText.getInstanse();

        disposable = restServiceText.getRestApiText().getItemPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ItemPost>() {
                    @Override
                    public void onNext(ItemPost itemPost) {
                        Log.e(LOG, "OnNext");
                        infoAdapter = new InfoAdapter();
                        infoAdapter.setPostList(itemPost.getItemPostList());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG, "OnError");

                    }

                    @Override
                    public void onComplete() {
                        Log.e(LOG, "OnComplete");
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setAdapter(infoAdapter);
                    }
                });
    }
}
