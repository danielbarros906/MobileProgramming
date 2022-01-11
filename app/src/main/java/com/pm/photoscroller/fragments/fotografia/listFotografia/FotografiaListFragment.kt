package com.pm.photoscroller.fragments.fotografia.listFotografia

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pm.photoscroller.R
import com.pm.photoscroller.api.models.Fotografia
import com.pm.photoscroller.api.requests.FotografiaAPI
import com.pm.photoscroller.api.retrofit.ServiceBuilder
import com.pm.photoscroller.utils.Utils.Companion.getToken
import com.pm.photoscroller.utils.Utils.Companion.getUserIdInSession
import com.pm.photoscroller.utils.Utils.Companion.hideKeyboard
import com.pm.photoscroller.utils.Utils.Companion.somethingWentWrong
import com.pm.photoscroller.utils.Utils.Companion.unauthorized
import kotlinx.android.synthetic.main.fragment_fotografias_list.*
import kotlinx.android.synthetic.main.fragment_fotografias_list.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_user_login.*
import kotlinx.android.synthetic.main.fragment_user_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FotografiaListFragment : Fragment() {
    private var _view : View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fotografias_list, container, false)
        _view = view
        setHasOptionsMenu(true)
        getAndSetData(view)
        view.btn_add_new_fotografia_from_fotografias_list.setOnClickListener(){
            findNavController().navigate(R.id.action_fotografiaListFragment_to_addFotografiaFragment)
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fotografia_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()
        if (item.itemId == R.id.user_logout){
            logout()
        }
        if (item.itemId == R.id.fotografia_list_refresh){
            _view?.let { getAndSetData(it) }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getAndSetData(view: View){
        view.llProgressBarList.bringToFront()
        view.llProgressBarList.visibility = View.VISIBLE

        val adapter = FotografiaListAdapter(getUserIdInSession())

        val recyclerView = view.recyclerview_fotografia_list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val request = ServiceBuilder.buildService(FotografiaAPI::class.java)
        val call = request.getFotografias(token = "Bearer ${getToken()}")

        call.enqueue(object : Callback<List<Fotografia>>{
            override fun onResponse(
                call: Call<List<Fotografia>>?,
                response: Response<List<Fotografia>>?
            ) {
                llProgressBarList.visibility = View.GONE

                if (response != null) {
                    if (response.isSuccessful){
                        val fotografia: List<Fotografia> = response.body()!!
                        adapter.setData(fotografia)
                    }else{
                        if (response.code()==401){
                            unauthorized(navigationHandler = {
                                findNavController().navigate(R.id.action_fotografiaListFragment_to_userLoginFragment)
                            })
                        }else{
                            somethingWentWrong()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Fotografia>>?, t: Throwable?) {
                llProgressBarList.visibility = View.GONE
                somethingWentWrong()
            }
        })
    }
    private fun logout(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)){_, _ ->
            val preferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
            preferences.edit().putString("token", null).apply()
            findNavController().navigate(R.id.action_fotografiaListFragment_to_userLoginFragment)
        }
        builder.setNegativeButton(getString(R.string.no)){_, _ ->}
        builder.setTitle(getString(R.string.logout))
        builder.setMessage(getString((R.string.logout_question)))
        builder.create().show()
    }
}