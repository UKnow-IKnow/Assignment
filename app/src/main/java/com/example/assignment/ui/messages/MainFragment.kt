package com.example.assignment.ui.messages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.R
import com.example.assignment.adapter.MessageAdapter
import com.example.assignment.api.MessageAPI
import com.example.assignment.databinding.FragmentMainBinding
import com.example.assignment.models.Messages
import com.example.assignment.utils.NetworkResult
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val messageViewModel by viewModels<MessageViewModel>()

    @Inject
    lateinit var messageAPI: MessageAPI

    val messageAdapter = MessageAdapter(emptyArray())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.noteList.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }






//        binding.data.setOnClickListener{
//            mainViewModel.getMessages()
//        }
//        mainViewModel.msgResponse.observe(viewLifecycleOwner, Observer {
//            it.forEach{response->
//
//            Log.d("Response", response.id)
//            Log.d("Response",response.agent_id)
//            Log.d("Response",response.body)
//            Log.d("Response",response.thread_id)
//            Log.d("Response",response.user_id)
//            Log.d("Response",response.timestamp)
//            }
//        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
    }

    private fun bindObservers() {
        messageViewModel.messageLiveData.observe(viewLifecycleOwner, Observer {
            binding.mainProgressBar.isVisible = false
            when(it){
                is NetworkResult.Success->{
                    messageAdapter.setData(
                        it.data?.toTypedArray()?: arrayOf()
                    )
                }
                is NetworkResult.Error ->{
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    binding.mainProgressBar.isVisible=true
                }
            }
        })
    }

    private fun onMessageClick(messages: Messages){
        val bundle = Bundle()
        bundle.putString("message", Gson().toJson(messages))
        findNavController().navigate(R.id.action_mainFragment_to_conversationFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}