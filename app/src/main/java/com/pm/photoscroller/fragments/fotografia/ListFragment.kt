package com.pm.photoscroller.fragments.fotografia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pm.photoscroller.R
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        view.btnAddProductFromList.setOnClickListener(){
            findNavController().navigate(R.id.action_listFragment_to_addFragment2)
        }

        return view
    }
}