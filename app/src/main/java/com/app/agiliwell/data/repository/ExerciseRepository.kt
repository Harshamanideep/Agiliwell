class ExerciseRepository @Inject constructor(private val dao: ExerciseDao) {
    suspend fun getAllExercises() = dao.getAllExercises()
    suspend fun addExercise(exercise: Exercise) = dao.insertExercise(exercise)
}
