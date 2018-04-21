package by.test2rest.internet;

import by.test2rest.internet.get.ItemPost;
import by.test2rest.internet.get.Post;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RestApiText {

    @GET("posts")
    Observable<ItemPost> getItemPosts();
}
