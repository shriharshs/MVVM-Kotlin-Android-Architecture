package com.task.data.remote.dto


import com.squareup.moshi.Json

data class UserDetails(
    @Json(name = "avatar_url")
    var avatarUrl: String = "",
    var bio: Any = Any(),
    var blog: String = "",
    var company: Any = Any(),
    @Json(name = "created_at")
    var createdAt: String = "",
    var email: Any = Any(),
    @Json(name = "events_url")
    var eventsUrl: String = "",
    var followers: Int = 0,
    @Json(name = "followers_url")
    var followersUrl: String = "",
    var following: Int = 0,
    @Json(name = "following_url")
    var followingUrl: String = "",
    @Json(name = "gists_url")
    var gistsUrl: String = "",
    @Json(name = "gravatar_id")
    var gravatarId: String = "",
    var hireable: Any = Any(),
    @Json(name = "html_url")
    var htmlUrl: String = "",
    var id: Int = 0,
    var location: String = "",
    var login: String = "",
    var name: String = "",
    @Json(name = "node_id")
    var nodeId: String = "",
    @Json(name = "organizations_url")
    var organizationsUrl: String = "",
    @Json(name = "public_gists")
    var publicGists: Int = 0,
    @Json(name = "public_repos")
    var publicRepos: Int = 0,
    @Json(name = "received_events_url")
    var receivedEventsUrl: String = "",
    @Json(name = "repos_url")
    var reposUrl: String = "",
    @Json(name = "site_admin")
    var siteAdmin: Boolean = false,
    @Json(name = "starred_url")
    var starredUrl: String = "",
    @Json(name = "subscriptions_url")
    var subscriptionsUrl: String = "",
    var type: String = "",
    @Json(name = "updated_at")
    var updatedAt: String = "",
    var url: String = ""
)