package com.github.ameen.emplyeemanagement.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.ameen.emplyeemanagement.databinding.ActivityMainBinding
import com.github.ameen.emplyeemanagement.domain.model.SkillData
import com.github.ameen.emplyeemanagement.domain.model.SkillsDomainData
import com.github.ameen.emplyeemanagement.presentation.util.loadJSONFromAsset
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding

    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        loadSkillsFromLocalFile()
    }

    private fun loadSkillsFromLocalFile() {
        try {
            val skillsArray: JSONObject = JSONObject(loadJSONFromAsset(this))
            val skills = Gson().fromJson<SkillsDomainData>(
                skillsArray.toString(),
                SkillsDomainData::class.java
            )
            Timber.e("Skills: $skills")

            insertAllSkillsToLocal(skills.data)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun insertAllSkillsToLocal(skills: List<SkillData>) {
        mainViewModel.insertAllSkills(skills)

        lifecycleScope.launchWhenStarted {
            mainViewModel.skillsDataInsertionState.collectLatest {
                if (it.isNotEmpty())
                    Timber.e("$it")
            }
        }
    }
}