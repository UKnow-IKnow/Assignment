package com.example.assignment.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.assignment.R
import com.example.assignment.databinding.FragmentLoginBinding
import com.example.assignment.models.UserRequest
import com.example.assignment.utils.NetworkResult
import com.example.assignment.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by activityViewModels<MainViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val validationResult = validateUserInput()
            if (validationResult.first){
                mainViewModel.loginUser(getUserInput())
            }
            else{
                binding.txtError.text = validationResult.second
            }
            bindObservers()
        }
    }

    private fun getUserInput(): UserRequest{
        val username = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest(username,password)
    }

    private fun validateUserInput(): Pair<Boolean, String>{
        val userRequest = getUserInput()
        return mainViewModel.validateCredential(userRequest.username,userRequest.password)
    }

    private fun bindObservers() {
        mainViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.token)
                    try {
                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                    }catch (e:Exception){}
                }
                is NetworkResult.Error -> {
                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}