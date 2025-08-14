package com.sarmad.moody.data.local.datasource.mood

//import com.sarmad.moody.data.local.entity.mood.Mood
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//
//class FakeDefaultMoodLocalDataSource : MoodLocalDataSource {
//    private val moodsData = mutableListOf<Mood>()
//    private val weatherTypesData = mutableListOf<String>()
//    private val moodsState = MutableStateFlow<List<Mood>>(emptyList())
//    private val weatherTypesState = MutableStateFlow<List<String>>(emptyList())
//    private var moodIdCounter = 1L
//
//    override suspend fun insertMood(mood: Mood): Long {
//        moodsData.add(mood)
//        moodsState.value = moodsData.toList()
//        val id = moodIdCounter++
//        return id
//    }
//
//    override suspend fun getMoodById(id: Int): Mood? {
//        return moodsData.find { it.id == id }
//    }
//
//    override fun getAllMoodsStream(): Flow<List<Mood>> {
//        return moodsState.asStateFlow()
//    }
//
//    override suspend fun deleteMood(mood: Mood) {
//        moodsData.removeIf { it.id == mood.id }
//        moodsState.value = moodsData.toList()
//    }
//
//    override suspend fun getAllWeatherTypes(): Flow<List<String>> {
//        return weatherTypesState.asStateFlow()
//    }
//
//    override fun getAllMoods(): List<Mood> {
//        return moodsData.toList()
//    }
//
//    // Utility for test setup
//    fun setWeatherTypes(types: List<String>) {
//        weatherTypesData.clear()
//        weatherTypesData.addAll(types)
//        weatherTypesState.value = weatherTypesData.toList()
//    }
//
//    fun setMoods(moods: List<Mood>) {
//        moodsData.clear()
//        moodsData.addAll(moods)
//        moodsState.value = moodsData.toList()
//    }
//}