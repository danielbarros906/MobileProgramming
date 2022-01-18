package com.pm.photoscroller.fragments.fotografia.listFragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pm.photoscroller.R
import com.pm.photoscroller.data.viewmodel.FotografiaViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment: Fragment() {
    private lateinit var mFotografiaViewModel: FotografiaViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        setHasOptionsMenu(true)

        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mFotografiaViewModel = ViewModelProvider(this).get(FotografiaViewModel::class.java)
        mFotografiaViewModel.readAllFotografia.observe(viewLifecycleOwner, Observer {
                fotografia -> adapter.setData(fotografia)
        })
        view.btnAddFotografiaFromList.setOnClickListener(){
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        return view;
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_login, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.user_login) {
            openlogin()
        }

        return super.onOptionsItemSelected(item)
    }

    private  fun openlogin(){
        findNavController().navigate(R.id.action_listFragment_to_userLoginFragment)
    }
}