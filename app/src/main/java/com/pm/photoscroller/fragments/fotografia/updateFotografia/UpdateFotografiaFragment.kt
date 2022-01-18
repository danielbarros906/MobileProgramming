package com.pm.photoscroller.fragments.fotografia.updateFotografia

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pm.photoscroller.R
import com.pm.photoscroller.api.dto.FotografiaDto
import com.pm.photoscroller.api.requests.FotografiaAPI
import com.pm.photoscroller.api.retrofit.ServiceBuilder
import com.pm.photoscroller.utils.Utils.Companion.getToken
import com.pm.photoscroller.utils.Utils.Companion.hideKeyboard
import com.pm.photoscroller.utils.Utils.Companion.somethingWentWrong
import com.pm.photoscroller.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_update_fotografia.*
import kotlinx.android.synthetic.main.fragment_update_fotografia.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdateFotografiaFragment : Fragment() {

    private val args by navArgs<UpdateFotografiaFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_fotografia, container, false)
        setHasOptionsMenu(true)

        view.update_fotografia_title.setText(args.currentPic.title)
        view.update_fotografia_description.setText(args.currentPic.description)
        view.update_fotografia_location.setText(args.currentPic.location)
        view.update_fotografia_iso.setText(args.currentPic.iso)
        view.update_fotografia_aperture.setText(args.currentPic.aperture)
        view.update_fotografia_shutter.setText(args.currentPic.shutter)


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_delete_menu_fotografia, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()
        if (item.itemId == R.id.menu_delete_fotografia){
            deleteFotografia()
        }
        if (item.itemId == R.id.menu_update_fotografia){
            updateFotografia()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateFotografia(){
        if (
            TextUtils.isEmpty(update_fotografia_title.text.toString()) ||
            TextUtils.isEmpty(update_fotografia_description.text.toString()) ||
            TextUtils.isEmpty(update_fotografia_location.text.toString())){
            Toast.makeText(
                requireContext(),
                getString(R.string.fill_title_description_and_location),
                Toast.LENGTH_LONG
            ).show()
        }else{
            val request = ServiceBuilder.buildService(FotografiaAPI::class.java)
            val call = request.updateFotografia(
                token = "Bearer ${getToken()}",
                id = args.currentPic.id,
                title = update_fotografia_title.text.toString(),
                description = update_fotografia_description.text.toString(),
                shutter = update_fotografia_shutter.text.toString(),
                iso = update_fotografia_iso.text.toString(),
                aperture = update_fotografia_aperture.text.toString(),
                location = update_fotografia_location.text.toString()
            )
            call.enqueue(object : Callback<FotografiaDto>{
                override fun onResponse(
                    call: Call<FotografiaDto>?,
                    response: Response<FotografiaDto>?
                ) {
                    if (response != null) {
                        if(response.isSuccessful){
                            val fotografia: FotografiaDto = response.body() !!
                            if (fotografia.status == "OK"){
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.fotografia_successfully_updated),
                                    Toast.LENGTH_LONG
                                ).show()
                                findNavController().navigate(R.id.action_updateFotografiaFragment_to_fotografiaListFragment)
                            }else{
                                Toast.makeText(
                                    requireContext(),
                                    getString(
                                        resources.getIdentifier(
                                            fotografia.message, "String",
                                            context?.packageName
                                        )
                                    ), Toast.LENGTH_LONG
                                ).show()
                            }
                        }else{
                            if (response.code()==401){
                                unauthorized(navigationHandler = {
                                    findNavController().navigate(R.id.action_updateFotografiaFragment_to_userLoginFragment)
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
    private fun deleteFotografia(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)){_, _ ->
            val request = ServiceBuilder.buildService(FotografiaAPI::class.java)
            val call = request.deleteFotografia(
                token = "Bearer ${getToken()}",
                id = args.currentPic.id
            )
            call.enqueue(object : Callback<FotografiaDto>{
                override fun onResponse(
                    call: Call<FotografiaDto>?,
                    response: Response<FotografiaDto>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful){
                            val fotografia : FotografiaDto = response.body()!!
                            if (fotografia.status == "OK"){
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.fotografia_successfully_deleted),
                                    Toast.LENGTH_LONG
                                ).show()
                                findNavController().navigate(R.id.action_updateFotografiaFragment_to_fotografiaListFragment)
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
                                    findNavController().navigate(R.id.action_updateFotografiaFragment_to_userLoginFragment)
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
        builder.setNegativeButton(getString(R.string.no)){_, _ ->}
        builder.setTitle(getString(R.string.delete))
        builder.setMessage(getString(R.string.question_delete))
        builder.create().show()
    }
}