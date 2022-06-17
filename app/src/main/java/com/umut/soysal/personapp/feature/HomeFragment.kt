package com.umut.soysal.personapp.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.umut.soysal.personapp.core.base.ViewModelFragment
import com.umut.soysal.personapp.core.extension.collectWhenResumed
import com.umut.soysal.personapp.data.model.Person
import com.umut.soysal.personapp.databinding.FragmentHomeBinding
import com.umut.soysal.personapp.feature.adapter.PersonListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment: ViewModelFragment<HomeViewModel>() {

    override val viewModel: HomeViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: PersonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        viewModel.fetchPersonList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        listenerScroll()
        swipePullRefresh()
        viewModel.personList.collectWhenResumed(viewLifecycleOwner, ::setUiData)
    }

    private fun setAdapter() {
        with(binding) {
            adapter = PersonListAdapter()
            personRecyclerView.adapter = adapter.apply {
                onLoadData = ::onLoadData
            }
        }
    }

    private fun swipePullRefresh() {
        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = false
            viewModel.pullRefreshData()
        }
    }

    private fun onLoadData(isLoading: Boolean) {
        if(isLoading) {
            viewModel.fetchPersonList()
        }
    }

    private fun setUiData(list: List<Person>) {
        adapter.submitList(list)
    }

    private fun listenerScroll() {
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}