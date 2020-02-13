package com.task.data.remote.dto
import com.google.gson.annotations.SerializedName


data class UserDetails(
    @SerializedName("avatar_url")
    var avatarUrl: String = "",
    var bio: Any = Any(),
    var blog: String = "",
    var company: Any = Any(),
    @SerializedName("created_at")
    var createdAt: String = "",
    var email: Any = Any(),
    @SerializedName("events_url")
    var eventsUrl: String = "",
    var followers: Int = 0,
    @SerializedName("followers_url")
    var followersUrl: String = "",
    var following: Int = 0,
    @SerializedName("following_url")
    var followingUrl: String = "",
    @SerializedName("gists_url")
    var gistsUrl: String = "",
    @SerializedName("gravatar_id")
    var gravatarId: String = "",
    var hireable: Any = Any(),
    @SerializedName("html_url")
    var htmlUrl: String = "",
    var id: Int = 0,
    var location: String = "",
    var login: String = "",
    var name: String = "",
    @SerializedName("node_id")
    var nodeId: String = "",
    @SerializedName("organizations_url")
    var organizationsUrl: String = "",
    @SerializedName("public_gists")
    var publicGists: Int = 0,
    @SerializedName("public_repos")
    var publicRepos: Int = 0,
    @SerializedName("received_events_url")
    var receivedEventsUrl: String = "",
    @SerializedName("repos_url")
    var reposUrl: String = "",
    @SerializedName("site_admin")
    var siteAdmin: Boolean = false,
    @SerializedName("starred_url")
    var starredUrl: String = "",
    @SerializedName("subscriptions_url")
    var subscriptionsUrl: String = "",
    var type: String = "",
    @SerializedName("updated_at")
    var updatedAt: String = "",
    var url: String = ""
)