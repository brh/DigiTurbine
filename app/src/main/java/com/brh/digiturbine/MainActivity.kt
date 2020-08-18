package com.brh.digiturbine

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.fragment.app.transaction
import com.brh.digiturbine.databinding.MainActivityBinding
import com.brh.digiturbine.ui.main.DetailFragment
import com.brh.digiturbine.ui.main.ListFragment

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    lateinit var viewProgress: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewProgress = findViewById(R.id.progress)

        val detailView: FragmentContainerView? = findViewById(R.id.fcv_detail)
        val listView: FragmentContainerView = findViewById(R.id.fcv_list)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fcv_list, ListFragment())
                detailView?.let {
                    add(R.id.fcv_detail, DetailFragment())
                }
            }
        }
    }

    fun showDetail(){
        supportFragmentManager.commit {
            addToBackStack(DetailFragment.BACKSTACK_NAME)
            add(R.id.fcv_list, DetailFragment())
        }
    }

    fun showProgress(show: Boolean) {
        if (show)
            viewProgress.show()
        else
            viewProgress.hide()
    }
}