package com.task.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "avatar_url")
    var avatarUrl: String = "",
    @Json(name = "events_url")
    var eventsUrl: String = "",
    @Json(name = "followers_url")
    var followersUrl: String = "",
    @Json(name = "following_url")
    var followingUrl: String = "",
    @Json(name = "gists_url")
    var gistsUrl: String = "",
    @Json(name = "gravatar_id")
    var gravatarId: String = "",
    @Json(name = "html_url")
    var htmlUrl: String = "",
    var id: Int = 0,
    var login: String = "",
    @Json(name = "node_id")
    var nodeId: String = "",
    @Json(name = "organizations_url")
    var organizationsUrl: String = "",
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
    var url: String = ""
)