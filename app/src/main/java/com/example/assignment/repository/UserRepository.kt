package com.example.assignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment.api.UserAPI
import com.example.assignment.models.UserRequest
import com.example.assignment.models.UserResponse
import com.example.assignment.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userAPI: UserAPI
) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData


    suspend fun loginUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.login(userRequest)
        //Log.d(TAG, response.body().toString())

        handleResponse(response)
    }

    private fun handleResponse(response: retrofit2.Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }


//    suspend fun getMessages(): List<Messages> {
//        return .getMessages(
//            hashMapOf(
//                "X-Branch-Auth-Token" to "HOQHZFslcn9BgMzU5a6IMA"
//            )
//        )
//    }

}