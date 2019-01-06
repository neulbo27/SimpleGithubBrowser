package pe.hankyu.svmgithubbrowser

import io.reactivex.Observable
import pe.hankyu.svmgithubbrowser.model.UserDetailsModel
import pe.hankyu.svmgithubbrowser.model.UserListModel
import pe.hankyu.svmgithubbrowser.utils.Global
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class GithubApi {
    interface GithubApiImpl {
        @GET("/users?client_id=" + Global.clientId + "&client_secret=" + Global.clientSecret)
        fun getUserList(@Query("since") since: String): Observable<List<UserListModel>>

        @GET("/users/{username}?client_id=" + Global.clientId + "&client_secret=" + Global.clientSecret)
        fun getUserDetails(@Path("username") userName: String): Observable<UserDetailsModel>

        @GET("/users/{username}/repos?client_id=" + Global.clientId + "&client_secret=" + Global.clientSecret)
        fun getUserRepos(@Path("username") userName: String): Observable<List<UserDetailsModel>>
    }

    companion object {
        fun getUserList(since: String): Observable<List<UserListModel>> {
            return RetrofitCreator.create(GithubApiImpl::class.java).getUserList(since)
        }

        fun getUserDetails(userName: String): Observable<UserDetailsModel> {
            return RetrofitCreator.create(GithubApiImpl::class.java).getUserDetails(userName)
        }

        fun getUserRepos(userName: String): Observable<List<UserDetailsModel>> {
            return RetrofitCreator.create(GithubApiImpl::class.java).getUserRepos(userName)
        }
    }
}