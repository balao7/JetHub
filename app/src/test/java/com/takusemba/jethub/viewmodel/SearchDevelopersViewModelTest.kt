package com.takusemba.jethub.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.takusemba.jethub.model.SimpleDeveloper
import com.takusemba.jethub.repository.SearchDevelopersRepository
import com.takusemba.jethub.util.createSimpleDeveloper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ObsoleteCoroutinesApi
@RunWith(JUnit4::class)
class SearchDevelopersViewModelTest {

  @get:Rule var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  @MockK private lateinit var searchDevelopersRepository: SearchDevelopersRepository

  @Before
  @ExperimentalCoroutinesApi
  fun setUp() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(TestCoroutineDispatcher())
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `initial state`() {
    runBlocking {

      val observer = mockk<Observer<List<SimpleDeveloper>>>(relaxed = true)

      coEvery { searchDevelopersRepository.searchDevelopers("") } returns listOf(
        createSimpleDeveloper(id = 1),
        createSimpleDeveloper(id = 2),
        createSimpleDeveloper(id = 3)
      )

      val viewModel = SearchDevelopersViewModel(searchDevelopersRepository)

      viewModel.searchedDevelopers.observeForever(observer)
      viewModel.viewModelScope.coroutineContext[Job]!!.children.forEach { it.join() }

      verify { observer.onChanged(match { it.size == 3 }) }
    }
  }

  @Test
  fun `search repos`() {
    runBlocking {

      val observer = mockk<Observer<List<SimpleDeveloper>>>(relaxed = true)

      coEvery { searchDevelopersRepository.searchDevelopers("") } returns emptyList()

      coEvery { searchDevelopersRepository.searchDevelopers("something") } returns listOf(
        createSimpleDeveloper(id = 1),
        createSimpleDeveloper(id = 2),
        createSimpleDeveloper(id = 3)
      )

      val viewModel = SearchDevelopersViewModel(searchDevelopersRepository)

      viewModel.search("something")

      viewModel.searchedDevelopers.observeForever(observer)
      viewModel.viewModelScope.coroutineContext[Job]!!.children.forEach { it.join() }

      verify { observer.onChanged(match { it.size == 3 }) }
    }
  }
}
