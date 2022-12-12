package com.example.assignment.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.adapter.ConversationAdapter
import com.example.assignment.databinding.FragmentConversationBinding
import com.example.assignment.models.MessageRequest
import com.example.assignment.models.Messages
import com.example.assignment.utils.NetworkResult

class ConversationFragment : Fragment() {

    private var _binding: FragmentConversationBinding? = null
    private val binding get() = _binding!!
    private val messageViewModel by activityViewModels<MessageViewModel>()

    val conversationAdapter = ConversationAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentConversationBinding.inflate(inflater, container, false)

        binding.convMessage.apply {
            adapter = conversationAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val threadId = arguments?.getInt("thread_id")
        val messages =
            messageViewModel.messageLiveData.value?.data?.filter { it.thread_id == threadId }
        conversationAdapter.update(messages as ArrayList<Messages>)
        binding.btnSend.setOnClickListener {
            setInitialData()
            binding.txtBody.setText("")
        }
        messageViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            binding.convProgress.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    it.data?.second?.let {
                        conversationAdapter.addMessage(
                            it
                        )
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.convProgress.isVisible = true
                }
            }
        })
    }

    private fun setInitialData() {
        val message = binding.txtBody.text.toString()
        val threadId = arguments?.getInt("thread_id")

        messageViewModel.postMessage(
            messageRequest = MessageRequest(
                thread_id = threadId ?: 0,
                body = message
            )
        )


    }


//    companion object {
//        private const val TAG = "ConversationFragment"
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}