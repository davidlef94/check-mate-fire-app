package com.example.davidgormally.checkmatefire.repository

import com.example.davidgormally.checkmatefire.data.FakeDataSource
import com.example.davidgormally.checkmatefire.database.entity.FireDoor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

@ExperimentalCoroutinesApi
class RepositoryTest {

    private val fireDoor1 = FireDoor()
    private val fireDoor2 = FireDoor()
    private val fireDoor3 = FireDoor()

    private val fireDoors = listOf(fireDoor1, fireDoor2, fireDoor3).sortedBy { it.id }

    private lateinit var fireDoorLocalDataSource: FakeDataSource
    private lateinit var repository: Repository

    @Before
    fun createRepository() {
        fireDoor1.no = "1234"
        fireDoor1.actionRequired = "y"

        fireDoor2.no = "1234"
        fireDoor2.actionRequired = "y"

        fireDoor3.no = "1234"
        fireDoor3.actionRequired = "y"

        fireDoorLocalDataSource = FakeDataSource(fireDoors.toMutableList())
        //repository = Repository(fireDoorLocalDataSource, null)
    }
}