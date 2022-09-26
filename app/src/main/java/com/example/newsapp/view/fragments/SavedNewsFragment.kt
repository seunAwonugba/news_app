package com.example.newsapp.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.ProjectAdapter
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by viewModels()

    private lateinit var projectAdapter: ProjectAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSavedNewsBinding.bind(view)
        setUpRecyclerView()

//        //get saved in DB and display it here(remember u add news to DB from FAB in article fragment)
//        viewModel.getDataFromDB().observe(viewLifecycleOwner, Observer {
//            projectAdapter.news = it
//        })

        //implement swipe to delete

//        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
//            //direction to drag recyclerview without effect
//        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
//             //direction to drag with effect
//        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        ){
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return true
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                //get position of list item on recyclerview
//                val position = viewHolder.adapterPosition
//                //get data to delete on the position
//                val dataToDelete = projectAdapter.news[position]
//                //finally call view model to implement delete
//                viewModel.deleteDataFromDB(dataToDelete)
//
//                //implement undo when u delete
//                Snackbar.make(
//                    binding.root,
//                    "News deleted",
//                    Snackbar.LENGTH_LONG
//                ).setAction("UNDO"){
//                    viewModel.upsert(dataToDelete)
//                }.show()
//            }
//
//        }

//        ItemTouchHelper(itemTouchHelperCallBack).apply {
//            attachToRecyclerView(binding.saveNewsFragmentRV)
//        }
//
//        projectAdapter.setListItemClickListener {
//            val passData = SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(it)
//            findNavController().navigate(passData)
//        }
    }

    private fun setUpRecyclerView(){
        projectAdapter = ProjectAdapter()
        binding.saveNewsFragmentRV.apply {
            adapter = projectAdapter
            layoutManager = LinearLayoutManager(requireContext())
            binding.saveNewsFragmentRV.addItemDecoration(
                DividerItemDecoration(
                    binding.saveNewsFragmentRV.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}