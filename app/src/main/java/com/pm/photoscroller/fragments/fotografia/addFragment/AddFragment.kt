package com.pm.photoscroller.fragments.fotografia.addFragment

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pm.photoscroller.R
import com.pm.photoscroller.data.entities.Fotografia
import com.pm.photoscroller.data.viewmodel.FotografiaViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment: Fragment() {

    private lateinit var mFotografiaViewModel: FotografiaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)
        mFotografiaViewModel = ViewModelProvider(this).get(FotografiaViewModel::class.java)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_product, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //hideKeyboard()

        if (item.itemId == R.id.menu_add){
            addFotografia()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addFotografia(){
        if (!isValid()){
            return Toast.makeText(
                requireContext(),
                getString(R.string.empty_fotografia_name),
                Toast.LENGTH_LONG
            ).show()
        }

        val fotografia = Fotografia(0, fotografiaName.text.toString(), "")
        mFotografiaViewModel.addFotografia(fotografia)

        Toast.makeText(
            requireContext(),
            getString(R.string.fotografia_successfully_added),
            Toast.LENGTH_LONG
        ).show()
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }
    private fun isValid() : Boolean{
        return !TextUtils.isEmpty(fotografiaName.text.toString())
    }
}