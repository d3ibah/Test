package by.test2rest.internet;

import java.util.List;

import by.test2rest.internet.get.Post;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RestApiText {

    @GET("posts")
    Observable<List<Post>> getItemPosts();
}
