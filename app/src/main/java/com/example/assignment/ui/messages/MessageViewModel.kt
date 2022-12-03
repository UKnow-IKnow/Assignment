package com.example.assignment.ui.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.models.MessageRequest
import com.example.assignment.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val messageRepository: MessageRepository): ViewModel() {

    val messageLiveData get() = messageRepository.messageLiveData
    val statusLiveData get() = messageRepository.statusLiveData
    init {
        getMessage()
    }

    fun getMessage(){
        viewModelScope.launch {
            messageRepository.getMessages()
        }
    }

    fun postMessage(messageRequest: MessageRequest){
        viewModelScope.launch {
            messageRepository.postMessage(messageRequest)
        }
    }
}