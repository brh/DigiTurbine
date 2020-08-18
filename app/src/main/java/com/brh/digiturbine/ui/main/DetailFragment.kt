package com.brh.digiturbine.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.brh.digiturbine.DTApp
import com.brh.digiturbine.R
import com.brh.digiturbine.databinding.FragmentDetailBinding
import com.brh.digiturbine.databinding.SimpleTextBinding
import com.brh.digiturbine.hide
import com.brh.digiturbine.model.AdItem
import com.brh.digiturbine.show
import com.list.rados.fast_list.bind
import javax.inject.Inject
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

class DetailFragment : Fragment(R.layout.fragment_detail) {

    lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: MainViewModel
    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DTApp.component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view);
        viewModel = ViewModelProvider(requireActivity(), vmFactory).get(MainViewModel::class.java)

        viewModel.detailLiveData.observe(viewLifecycleOwner){adItem ->
            binding.tvSelect.hide()
            adItem?.let {
                /**
                 * Since this isn't a real app, just dump the details into detail view
                 * using reflection to easily go through the properties
                 */
                binding.rv.bind<KProperty1<AdItem, *>, SimpleTextBinding>(AdItem::class.memberProperties.toList())
                    .map({parent-> SimpleTextBinding.inflate(layoutInflater, parent, false)},
                        {_,_-> true}){item: KProperty1<AdItem, *> ->
                        this.tv.setText("${item.name}: ${item.get(it)}")
                    }
            } ?: run {
                binding.tvSelect.show()
            }
        }


    }

    companion object {
        const val BACKSTACK_NAME = "detailbkstack"
    }
}