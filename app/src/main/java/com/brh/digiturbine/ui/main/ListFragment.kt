package com.brh.digiturbine.ui.main

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import coil.api.load
import com.brh.digiturbine.DTApp
import com.brh.digiturbine.MainActivity
import com.brh.digiturbine.R
import com.brh.digiturbine.State
import com.brh.digiturbine.databinding.CardItemBinding
import com.brh.digiturbine.databinding.MainFragmentBinding
import com.brh.digiturbine.model.AdItem
import com.google.android.material.snackbar.Snackbar
import com.list.rados.fast_list.bind
import javax.inject.Inject

class ListFragment : Fragment(R.layout.main_fragment) {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    @Inject lateinit var vmFactory: ViewModelProvider.Factory

    init {
        DTApp.component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)

        requireActivity().onBackPressedDispatcher.addCallback{
            if (viewModel.detailLiveData.hasActiveObservers())
                requireActivity().supportFragmentManager.popBackStackImmediate()
            else
                requireActivity().finish()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), vmFactory).get(MainViewModel::class.java)

        viewModel.listLiveData.observe(viewLifecycleOwner){state->
            when(state) {
                State.Idle -> {
                    viewModel.fetchData()
                }
                is State.Error -> {
                    Snackbar.make(binding.main,
                        state.msg ?: getString(R.string.err_unknown),
                        Snackbar.LENGTH_LONG).show()
                }
                State.Loading -> {
                    (requireActivity() as MainActivity).showProgress(true)
                }
                is State.Content<*> -> {
                    (requireActivity() as MainActivity).showProgress(false)
                    val list =  state.data as List<AdItem>
                    //make use of lib that makes working with recyclerviews easier
                    binding.rv.bind<AdItem, CardItemBinding>(list).map({vg->
                        CardItemBinding.inflate(layoutInflater, vg, false)},
                        {_,_-> true}
                    ){ad ->
                        this.tvTitle.text = ad.productName
                        this.ivIcon.load(Uri.parse(ad.productThumbnail))
                        this.ivRating.load(ad.averageRatingImageURL)
                        this.tvDownload.setText(getString(R.string.download_cnt, ad.numberOfDownloads))

                        root.setOnClickListener {
                            viewModel.itemSeleted(ad)
                            //if there is not an active observer to detaiLiveData then we know
                            //we are not on dual screen (large screen) device
                            if (!viewModel.detailLiveData.hasActiveObservers()) {
                                (requireActivity() as MainActivity).showDetail()
                            }
                        }
                    }
                }
            }
        }
    }

}