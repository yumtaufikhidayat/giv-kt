package com.taufik.adeptforms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.taufik.adeptforms.R
import com.taufik.adeptforms.data.utils.DummyData
import com.taufik.adeptforms.databinding.BottomSheetReportBinding
import com.taufik.adeptforms.ui.adapter.home.ReportsBottomSheetAdapter

class ReportsBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetReportBinding? = null
    private val binding get() = _binding!!

    private lateinit var reportsAdapter: ReportsBottomSheetAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickCardDismiss()

        setData()
    }

    private fun onClickCardDismiss() {
        binding.apply {
            cardDismiss.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun setData() {
        reportsAdapter = ReportsBottomSheetAdapter(DummyData.getReportsBottomSheetData())
        binding.apply {
            rvReports.layoutManager = LinearLayoutManager(requireActivity())
            rvReports.setHasFixedSize(true)
            rvReports.adapter = reportsAdapter
        }
    }

    override fun getTheme(): Int = R.style.BottomSheetMenuTheme

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}