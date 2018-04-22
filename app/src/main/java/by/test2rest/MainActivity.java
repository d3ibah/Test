package by.test2rest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import by.test2rest.internet.RestServiceRef;
import by.test2rest.internet.RestServiceText;
import by.test2rest.internet.get.Photo;
import by.test2rest.internet.get.Post;
import by.test2rest.internet.get.Ref;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Disposable disposable;
    private RestServiceText restServiceText;
    private RecyclerView recyclerView;
    private InfoAdapter infoAdapter;

    private List<Photo> photoList;
    private RestServiceRef restServiceRef;

    // parts of the url
    public final String REFMETHOD = "flickr.photos.getRecent";
    public final String REFAPIKEY = "9f10641bd6749bb141e8ad303a1c3381";
    public final String REFEXTRAS = "description";
    public final int REFPERPAGE = 100;
    public final String REFFORMAT = "json";
    public final int REFNOJSONCALLBACK = 1;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // fix orientation
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // implement click on item RecyclerView
        infoAdapter = new InfoAdapter(new ClickListener() {
            @Override
            public void onClick(Photo photo, Post post) {
                intent = new Intent(MainActivity.this, BigImageActivity.class);

                String photoSize;
                String textBody;

                photoSize = "n";

               /* Size Suffixes
                * The letter suffixes are as follows:
                * s	small square 75x75
                * q	large square 150x150
                * t	thumbnail, 100 on longest side
                * m	small, 240 on longest side
                * n	small, 320 on longest side
                * -	medium, 500 on longest side
                * z	medium 640, 640 on longest side
                * c	medium 800, 800 on longest side
                * b	large, 1024 on longest side*
                * h	large 1600, 1600 on longest side
                * k	large 2048, 2048 on longest side */

                String imageUrl = "https://farm" + photo.getFarm() + ".staticflickr.com/"
                        + photo.getServer() + "/" + photo.getId() + "_" + photo.getSecret()
                        + "_" + photoSize + ".jpg";
                textBody = post.getBody();

                intent.putExtra("url", imageUrl);
                intent.putExtra("text", textBody);

                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);

        restServiceText = RestServiceText.getInstanse();


        // start Rest service for get text and image url and inflate RecyclerView
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
                                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                        recyclerView.setAdapter(infoAdapter);
                                    }
                                });
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
