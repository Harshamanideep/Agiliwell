class YogaRepository @Inject constructor(private val dao: YogaDao) {
    suspend fun getAllYoga() = dao.getAllYoga()
    suspend fun addYoga(posture: YogaPosture) = dao.insertYoga(posture)
}
