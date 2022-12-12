package com.example.assignment.ui.login

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.models.Messages
import com.example.assignment.models.UserRequest
import com.example.assignment.models.UserResponse
import com.example.assignment.repository.UserRepository
import com.example.assignment.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData


    val msgResponse: MutableLiveData<List<Messages>> = MutableLiveData()

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun validateCredential(username: String, password: String): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            result = Pair(false, "Please provide required information")
        }
        return result
    }

//    fun getMessages(){
//        viewModelScope.launch {
//           try {
//               val response = userRepository.getMessages()
//               msgResponse.value = response
//           }catch (e:Exception){}
//        }
//    }
}