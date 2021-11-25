package com.pet.moneyconvertor.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pet.moneyconvertor.R
import com.pet.moneyconvertor.adapters.CurrencyAdapter
import com.pet.moneyconvertor.databinding.FragmentCurrencyListBinding
import com.pet.moneyconvertor.viewmodelfactories.CurrencyListViewModelFactory
import com.pet.moneyconvertor.viewmodels.CurrencyListViewModel
import com.pet.moneyconvertor.viewmodels.SharedLeftViewModel
import com.pet.moneyconvertor.viewmodels.SharedRightViewModel

class CurrencyListFragment : Fragment() {
    private var _binding: FragmentCurrencyListBinding? = null
    private val binding get() = _binding!!

    private val sharedLeftModel: SharedLeftViewModel by activityViewModels()
    private val sharedRightModel: SharedRightViewModel by activityViewModels()

    private val args: CurrencyListFragmentArgs by navArgs()
    private val viewModel: CurrencyListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(this, CurrencyListViewModelFactory(activity.application)).get(
            CurrencyListViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        binding.viewModel = viewModel
        val side = args.selectedSide
        binding.currencyList.adapter = CurrencyAdapter(CurrencyAdapter.OnClickListener {
                item -> when(side) {
            getString(R.string.left_button_navigation_key) -> sharedLeftModel.select(item)
            getString(R.string.right_button_navigation_key) -> sharedRightModel.select(item)
        }
            findNavController().navigate(CurrencyListFragmentDirections.actionCurrencyListFragmentToConvertFragment())
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_currency_list, menu)
        val searchView = menu.findItem(R.id.tool_bar_search).actionView as SearchView
        searchView.queryHint = getString(R.string.hint_search_view)
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchCurrency(newText)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}