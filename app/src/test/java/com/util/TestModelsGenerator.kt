package com.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.data.remote.dto.User
import com.task.data.remote.dto.UserDetails
import java.io.File
import java.lang.reflect.Type

/**
 * Created by ahmedEltaher on 3/8/17.
 */

class TestModelsGenerator {
    private var usersModel: List<User>
    private var userDetailsModel: UserDetails


    init {
        val gson = Gson()
        val usersApiResponse = getJson("UsersApiResponse.json")
        val userDetailsApiResponse = getJson("UsersDetailsApiResponse.json")
        val listType: Type = object : TypeToken<List<User>>() {}.type
        usersModel = gson.fromJson(usersApiResponse, listType)
        userDetailsModel = gson.fromJson(userDetailsApiResponse, UserDetails::class.java)
    }

    fun generateUsersModel(): List<User> {
        return usersModel
    }

    fun generateEmptyUsersModel(): List<User> {
        return listOf()
    }

    fun generateUserDetailsModel(): UserDetails {
        return userDetailsModel
    }


    /**
     * Helper function which will load JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}
