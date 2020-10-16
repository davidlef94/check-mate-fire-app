package com.example.davidgormally.checkmatefire.firedoorlist

import android.view.View
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.davidgormally.checkmatefire.database.entity.FireDoor
import com.example.davidgormally.checkmatefire.repository.Repository
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class FireDoorListViewModel(private val repository: Repository) : ViewModel() {

    private val _forceUpdate = MutableLiveData<Boolean>(false)
    private var _fireDoorList: LiveData<List<FireDoor>> = _forceUpdate.switchMap { _ ->
        repository.getAllFireDoors().switchMap { filterFireDoors(it) }
    }
    val fireDoorList: LiveData<List<FireDoor>> = _fireDoorList

    // Filtering
    private var currentFiltering = FireDoorFilterType.ALL_FIRE_DOORS

    init {
        setFiltering(FireDoorFilterType.ALL_FIRE_DOORS)
    }

    fun createFireDoor(view: View) {
        val fireDoor = FireDoor()
        saveFireDoor(fireDoor)

        val action = FireDoorListFragmentDirections.actionFireDoorListFragmentToFireDoorDetailFragment(fireDoor.id)
        view.findNavController().navigate(action)
    }

    private fun saveFireDoor(fireDoor: FireDoor) = viewModelScope.launch {
        repository.insertFireDoor(fireDoor)
    }

    /*
      Filter Fire Doors
    */
    fun setFiltering(requestType: FireDoorFilterType) {
        currentFiltering = requestType
        loadFireDoors(false)
    }

    private fun filterFireDoors(fireDoorResult: List<FireDoor>): LiveData<List<FireDoor>> {
        val result = MutableLiveData<List<FireDoor>>()
        viewModelScope.launch {
            result.value = filterItems(fireDoorResult, currentFiltering)
        }

        return result
    }

    private fun filterItems(fireDoors: List<FireDoor>, filterType: FireDoorFilterType): List<FireDoor> {
        val fireDoorsToShow = ArrayList<FireDoor>()
        for (door in fireDoors) {
            when(filterType) {
                FireDoorFilterType.ALL_FIRE_DOORS -> fireDoorsToShow.add(door)
                FireDoorFilterType.REQUIRED_FIRE_DOORS -> if (door.actionRequired.toLowerCase(Locale.UK) == "y") {
                    fireDoorsToShow.add(door)
                }
                FireDoorFilterType.LABELLED -> if (door.labelled) {
                    fireDoorsToShow.add(door)
                }
                FireDoorFilterType.INSTRUMESCENT_SEAL -> if (door.instrumescentSeal) {
                    fireDoorsToShow.add(door)
                }
                FireDoorFilterType.HINGES -> if (door.threeHinges) {
                    fireDoorsToShow.add(door)
                }
                FireDoorFilterType.DOOR_CLOSURES_WORK -> if (door.doorClosuresWork) {
                    fireDoorsToShow.add(door)
                }
                FireDoorFilterType.GLAZING_AND_GLASS -> if (door.glazingAndGlass) {
                    fireDoorsToShow.add(door)
                }
                FireDoorFilterType.DOOR_LEAF_OK -> if (door.doorLeafOk) {
                    fireDoorsToShow.add(door)
                }
                FireDoorFilterType.DOOR_FRAME_OK -> if (door.doorFrameOk) {
                    fireDoorsToShow.add(door)
                }
                FireDoorFilterType.DOOR_OPERATION_OK -> if (door.doorOperationOk) {
                    fireDoorsToShow.add(door)
                }
            }
        }

        return fireDoorsToShow
    }

    private fun loadFireDoors(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }
}

@Suppress("UNCHECKED_CAST")
class FireDoorListViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (FireDoorListViewModel(repository) as T)
}