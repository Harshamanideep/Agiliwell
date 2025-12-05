@Dao
interface YogaDao {
    @Query("SELECT * FROM yoga_table")
    suspend fun getAllYoga(): List<YogaSession>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYoga(yogaSession: YYogaSession)
}
