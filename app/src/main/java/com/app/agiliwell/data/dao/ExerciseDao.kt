@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise_table")
    suspend fun getAllExercises(): List<Exercise>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)
}
