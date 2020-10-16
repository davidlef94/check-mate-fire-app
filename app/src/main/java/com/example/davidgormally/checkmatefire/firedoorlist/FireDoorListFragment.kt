package com.example.davidgormally.checkmatefire.firedoorlist

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.davidgormally.checkmatefire.R
import com.example.davidgormally.checkmatefire.ServiceLocator
import com.example.davidgormally.checkmatefire.databinding.FragmentFireDoorListBinding
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class FireDoorListFragment : Fragment() {

    private val viewModel by viewModels<FireDoorListViewModel> {
        FireDoorListViewModelFactory(ServiceLocator.provideRepository(requireContext()))
    }

    private lateinit var binding: FragmentFireDoorListBinding

    private lateinit var listAdapter: FireDoorListAdapter

    /*
      Search View
    */
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFireDoorListBinding.inflate(inflater).apply {
            viewmodel = viewModel

            toolbar.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_add_fire_door -> {
                        viewModel.createFireDoor(binding.root)

                        true
                    }
                    R.id.action_menu_filter -> {
                        showFilterMenu()
                        true
                    }
                    else -> false
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        setupListAdapter()
    }

    private fun setupListAdapter() {
        val viewModel = binding.viewmodel

        if (viewModel != null) {
            listAdapter = FireDoorListAdapter(viewModel)
            binding.fireDoorList.adapter = listAdapter

        } else {
            Timber.w("ViewModel not initialised when setting up adapter")
        }
    }

    /*
      Filter Menu
    */
    private fun showFilterMenu() {
        val view = activity?.findViewById<View>(R.id.action_menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.fire_door_list_filter_menu, menu)

            setOnMenuItemClickListener {
                viewModel.setFiltering(
                    when(it.itemId) {
                        R.id.action_required -> FireDoorFilterType.REQUIRED_FIRE_DOORS
                        R.id.labelled -> FireDoorFilterType.LABELLED
                        R.id.instrumescent_seal -> FireDoorFilterType.INSTRUMESCENT_SEAL
                        R.id.three_hinges -> FireDoorFilterType.HINGES
                        R.id.door_closure_work -> FireDoorFilterType.DOOR_CLOSURES_WORK
                        R.id.glazing_and_glass -> FireDoorFilterType.GLAZING_AND_GLASS
                        R.id.door_leaf_ok -> FireDoorFilterType.DOOR_LEAF_OK
                        R.id.door_frame_ok -> FireDoorFilterType.DOOR_FRAME_OK
                        R.id.door_operation_ok -> FireDoorFilterType.DOOR_OPERATION_OK

                        else -> FireDoorFilterType.ALL_FIRE_DOORS
                    }
                )
                true
            }
            show()
        }
    }
}
