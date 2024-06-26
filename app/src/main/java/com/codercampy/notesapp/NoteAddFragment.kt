package com.codercampy.notesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.codercampy.notesapp.databinding.FragmentAddNoteBinding
import com.codercampy.notesapp.db.Note
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NoteAddFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            onBackPress()
        }

        binding.btnSave.setOnClickListener {
            addNote()
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })
    }

    private fun onBackPress() {
        val title = binding.inputTitle.editText?.text?.trim()?.toString()
        val body = binding.inputBody.editText?.text?.trim()?.toString()
        if (!title.isNullOrEmpty() || !body.isNullOrEmpty()) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Warning")
                .setMessage("Are you sure you want to go back? Your data will be lost!")
                .setNegativeButton("Stay", null)
                .setPositiveButton("Go Back") { dialog, which ->
                    findNavController().navigateUp()
                }.show()
        } else {
            findNavController().navigateUp()
        }
    }

    private fun addNote() {
        val title = binding.inputTitle.editText?.text?.trim()?.toString()
        val body = binding.inputBody.editText?.text?.trim()?.toString()

        if (title.isNullOrEmpty() || body.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please enter data", Toast.LENGTH_SHORT).show()
            return
        }

        setFragmentResult("note", Bundle().apply {
            putParcelable("note", Note(System.currentTimeMillis().toString(), title, body))
        })
        findNavController().navigateUp()
    }

}