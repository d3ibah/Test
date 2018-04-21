package by.test2rest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.test2rest.internet.RestServiceRef;
import by.test2rest.internet.RestServiceText;
import by.test2rest.internet.get.Photo;
import by.test2rest.internet.get.Photos;
import by.test2rest.internet.get.Post;
import by.test2rest.internet.get.Ref;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    private Disposable disposable;
    private RestServiceText restServiceText;
    private RecyclerView recyclerView;
    private InfoAdapter infoAdapter;

    private Ref refS;
    private List<Photo> photoList;
    private RestServiceRef restServiceRef;
    public final String LOG = "text";
    public final String REFMETHOD = "flickr.photos.getRecent";
    public final String REFAPIKEY = "9f10641bd6749bb141e8ad303a1c3381";
    public final String REFEXTRAS = "description";
    public final int REFPERPAGE = 100;
    public final String REFFORMAT = "json";
    public final int REFNOJSONCALLBACK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(LOG, "OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoAdapter = new InfoAdapter();
        recyclerView = findViewById(R.id.recyclerView);

        restServiceText = RestServiceText.getInstanse();

        disposable = restServiceText.getRestApiText().getItemPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Post>>() {
                    @Override
                    public void onNext(List<Post> posts) {
                        infoAdapter.setPostList(posts);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {


                        restServiceRef = RestServiceRef.getInstanse();
                        disposable = restServiceRef.getRestApiRef().getRef(REFMETHOD, REFAPIKEY, REFEXTRAS, REFPERPAGE, REFFORMAT, REFNOJSONCALLBACK)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<Ref>() {
                                    @Override
                                    public void onNext(Ref ref) {
                                        photoList = new ArrayList<>();
                                        photoList = ref.getPhotos().getPhoto();
                                        infoAdapter.setPhotoList(photoList);

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        for (Photo photo : photoList){
                                            Log.e("photo", photo.getSecret());
                                        }
                                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                        recyclerView.setAdapter(infoAdapter);   // replace adapter to last onComlete
                                    }
                                });
                    }
                });


    }
}
