package com.example.demodrawerlayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = view.findViewById<WebView>(R.id.webview)
        webView.loadUrl("https://thanhnien.vn/loi-dung-danh-nghia-bao-chi-de-bao-ke-xe-tai-truc-loi-hon-1-ti-dong-moi-thang-185231127195819914.htm")
    }
}