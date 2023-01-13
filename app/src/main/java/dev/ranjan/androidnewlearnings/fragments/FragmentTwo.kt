package dev.ranjan.androidnewlearnings.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import dev.ranjan.androidnewlearnings.R
import dev.ranjan.androidnewlearnings.databinding.FragmentTwoBinding


class FragmentTwo : Fragment() {
    private lateinit var binding: FragmentTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTwoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = (0..150).map { "Fragment Two item: $it" }.toList()
        binding.listView.adapter = ArrayAdapter(requireContext(), R.layout.list_item_view, list)
    }

}