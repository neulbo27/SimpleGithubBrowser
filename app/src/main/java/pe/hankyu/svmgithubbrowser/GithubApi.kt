package pe.hankyu.svmgithubbrowser

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

class GithubApi {
    interface GithubApiImpl {
        @GET("/users?client_id=" + Global.clientId + "&client_secret=" + Global.clientSecret)
        fun getUserList(@Query("since") since: String): Observable<List<GithubUserModel>>
    }

    companion object {
        fun getUserList(since: String): Observable<List<GithubUserModel>> {
            return RetrofitCreator.create(GithubApiImpl::class.java).getUserList(since)
        }
    }
}