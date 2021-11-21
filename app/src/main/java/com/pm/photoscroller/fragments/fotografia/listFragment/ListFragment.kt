package com.pm.photoscroller.fragments.fotografia.listFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
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
}