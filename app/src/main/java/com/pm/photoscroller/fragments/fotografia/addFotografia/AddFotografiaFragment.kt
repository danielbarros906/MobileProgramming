package com.pm.photoscroller.fragments.fotografia.addFotografia

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pm.photoscroller.R
import com.pm.photoscroller.api.dto.FotografiaDto
import com.pm.photoscroller.api.requests.FotografiaAPI
import com.pm.photoscroller.api.retrofit.ServiceBuilder
import com.pm.photoscroller.utils.Utils.Companion.getToken
import com.pm.photoscroller.utils.Utils.Companion.getUserIdInSession
import com.pm.photoscroller.utils.Utils.Companion.hideKeyboard
import com.pm.photoscroller.utils.Utils.Companion.somethingWentWrong
import com.pm.photoscroller.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_add_fotografia.*
import kotlinx.android.synthetic.main.fragment_fotografias_list.*
import kotlinx.android.synthetic.main.fragment_user_login.*
import kotlinx.android.synthetic.main.fragment_user_login.llProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFotografiaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_fotografia, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_fotografia, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()
        if (item.itemId == R.id.menu_add_fotografia){
            addFotografia()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun addFotografia(){
        if (
            TextUtils.isEmpty(add_fotografia_title.text.toString())||
            TextUtils.isEmpty(add_fotografia_description.text.toString())||
            TextUtils.isEmpty(add_fotografia_location.text.toString())||
            TextUtils.isEmpty(add_fotografia_ISO.text.toString())||
            TextUtils.isEmpty(add_fotografia_shutter.text.toString())||
            TextUtils.isEmpty(add_fotografia_aperture.text.toString())||
            TextUtils.isEmpty(add_fotografia_photo_path.text.toString())
        ){
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_all_fields),
                Toast.LENGTH_LONG
            ).show()
        }else{
            llProgressBar.bringToFront()
            llProgressBar.visibility = View.VISIBLE
            val request = ServiceBuilder.buildService(FotografiaAPI::class.java)
            val call = request.createFotografia(
                token = "Bearer ${getToken()}",
                users_id = getUserIdInSession(),
                title = add_fotografia_title.text.toString(),
                description = add_fotografia_description.text.toString(),
                location = add_fotografia_location.text.toString(),
                iso = add_fotografia_ISO.text.toString(),
                shutter = add_fotografia_shutter.text.toString(),
                aperture = add_fotografia_aperture.text.toString(),
                photo_path = add_fotografia_photo_path.text.toString()
            )
            call.enqueue(object : Callback<FotografiaDto>{
                override fun onResponse(
                    call: Call<FotografiaDto>?,
                    response: Response<FotografiaDto>?
                ) {
                    llProgressBar.visibility = View.GONE
                    if (response != null) {
                        if (response.isSuccessful){
                            val fotografia : FotografiaDto = response.body()!!
                            if (fotografia.status == "OK"){
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.fotografia_successfully_added),
                                    Toast.LENGTH_LONG
                                ).show()
                                findNavController().navigate(R.id.action_addFotografiaFragment_to_fotografiaListFragment)
                            }else{
                                Toast.makeText(
                                    requireContext(),
                                    getString(
                                        resources.getIdentifier(
                                            fotografia.message, "string",
                                            context?.packageName
                                        )
                                    ), Toast.LENGTH_LONG
                                ).show()
                            }
                        }else{
                            if (response.code()==401){
                                unauthorized(navigationHandler = {
                                    findNavController().navigate(R.id.action_addFotografiaFragment_to_userLoginFragment)
                                })
                            }else{
                                somethingWentWrong()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<FotografiaDto>?, t: Throwable?) {
                    somethingWentWrong()
                }
            })
        }
    }
}