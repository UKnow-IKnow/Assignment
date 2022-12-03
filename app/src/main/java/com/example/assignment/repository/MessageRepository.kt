package com.example.assignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment.api.MessageAPI
import com.example.assignment.models.MessageRequest
import com.example.assignment.models.Messages
import com.example.assignment.utils.NetworkResult
import com.example.assignment.utils.TokenManager
import org.json.JSONObject
import javax.inject.Inject

class MessageRepository @Inject constructor(private val messageAPI: MessageAPI ,
                                            val tokenManager: TokenManager) {

    private val _messageData = MutableLiveData<NetworkResult<List<Messages>>>()
    val messageLiveData get() = _messageData


    private val _statusLiveData = MutableLiveData<NetworkResult<Pair<Boolean, String>>>()
    val statusLiveData get() = _statusLiveData

    suspend fun getMessages(){
        _messageData.postValue(NetworkResult.Loading())
        try {
            val response = messageAPI.getMessages(
                hashMapOf(
                    "X-Branch-Auth-Token" to tokenManager.getToken().toString()
                )
            )
            _messageData.postValue(NetworkResult.Success(response))
        }catch (e:Exception){
            _messageData.postValue(NetworkResult.Error("Something Went Wrong"))
        }


    }

    suspend fun postMessage(messageRequest: MessageRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = messageAPI.postMessage(messageRequest)
        if (response.isSuccessful && response.body() != null){
            _statusLiveData.postValue(NetworkResult.Success(Pair(true,"")))
        }
        else{
            _statusLiveData.postValue(NetworkResult.Error("something wrong"))
        }
    }


}