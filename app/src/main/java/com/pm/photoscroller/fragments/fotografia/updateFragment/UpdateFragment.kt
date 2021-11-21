package com.pm.photoscroller.fragments.fotografia.updateFragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pm.photoscroller.R
import com.pm.photoscroller.data.entities.Fotografia
import com.pm.photoscroller.data.viewmodel.FotografiaViewModel

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mFotografiaViewModel: FotografiaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        mFotografiaViewModel = ViewModelProvider(this).get(FotografiaViewModel::class.java)
        view.updateFotografiaName.setText(args.currentProduct.name)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_update_delete_fotografia, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //hideKeyboard()
        if (item.itemId == R.id.menu_update){
            updateFotografia()
        }
        if (item.itemId == R.id.menu_delete){
            deleteFotografia()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun updateFotografia(){
        if (!isValid()){
            return Toast.makeText(
                requireContext(),
                getString(R.string.empty_fotografia_name),
                Toast.LENGTH_LONG
            ).show()
        }
        val fotografia = Fotografia(args.currentFotografia.id, updateFotografiaName.text.toString(),"")
        mFotografiaViewModel.updateFotografia(fotografia)

        Toast.makeText(
            requireContext(),
            getString(R.string.fotografia_successfully_updated),
            Toast.LENGTH_LONG
        ).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }
    private fun deleteFotografia(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)){
            _, _-> mFotografiaViewModel.deleteFotografia(args.currentFotografia)
            Toast.makeText(
                requireContext(),
                getString(R.string.fotografia_successfully_deleted),
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton(getString(R.string.no)){_, _ -> }
        builder.setTitle(getString(R.string.delete))
        builder.setMessage(getString(R.string.question_delete))
        builder.create().show()
    }
    private fun isValid(): Boolean{
        return !TextUtils.isEmpty(updateFotografiaName.text.toString())
    }
}