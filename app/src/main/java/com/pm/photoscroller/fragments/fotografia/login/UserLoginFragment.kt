package com.pm.photoscroller.fragments.fotografia.login

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pm.photoscroller.R
import com.pm.photoscroller.api.dto.UserDto
import com.pm.photoscroller.api.requests.UsersAPI
import com.pm.photoscroller.api.retrofit.ServiceBuilder
import com.pm.photoscroller.utils.Utils.Companion.hideKeyboard
import com.pm.photoscroller.utils.Utils.Companion.somethingWentWrong
import kotlinx.android.synthetic.main.fragment_user_login.*
import kotlinx.android.synthetic.main.fragment_user_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserLoginFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_login, container, false)
        view.btn_submit.setOnClickListener {
            hideKeyboard()
            signin(view)
        }
        return view
    }
    private fun signin(view: View){
        if(TextUtils.isEmpty(et_user_name.text.toString()) || TextUtils.isEmpty(et_password.text.toString())){
            Toast.makeText(requireContext(), getString(R.string.fill_username_and_password), Toast.LENGTH_LONG).show()
        }else{
            signinRequest(view)
        }
    }

    private fun signinRequest(view: View){
        val request = ServiceBuilder.buildService(UsersAPI::class.java)
        val call = request.signin(et_user_name.text.toString(), et_password.text.toString())

        llProgressBar.bringToFront()
        llProgressBar.visibility = View.VISIBLE

        call.enqueue(object : Callback<UserDto>{
            override fun onResponse(call : Call<UserDto>, response: Response<UserDto>){
                llProgressBar.visibility = View.GONE

                if(response.isSuccessful){
                    val userDto: UserDto = response.body()!!
                    if (userDto.status == "OK"){
                        setUserSettings(userDto)
                        findNavController().navigate(R.id.action_userLoginFragment_to_fotografiaListFragment)
                        Toast.makeText(requireContext(), getString(R.string.hello) + " " + userDto.user.first().name, Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(requireContext(), getString(resources.getIdentifier(userDto.message, "string", context?.packageName)), Toast.LENGTH_LONG).show()
                    }
                }else{
                    somethingWentWrong()
                }
            }
            override fun onFailure(call: Call<UserDto>, t: Throwable){
                llProgressBar.visibility = View.GONE
                somethingWentWrong()
            }
        })
    }
    fun setUserSettings(userDto: UserDto){
        val preferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        preferences.edit().putString("token", userDto.token).apply()
        preferences.edit().putString("users_id", userDto.user.first().id.toString()).apply()
        preferences.edit().putString("name", userDto.user.first().name).apply()
    }
}