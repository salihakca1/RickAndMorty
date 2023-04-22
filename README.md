# Rick And Morty 
Kullanıcının Rick And Morty karakterlerini ve konumlarını seçebileceği ve seçilen karakterin detaylarını görebileceği ekranlar bulunan Kotlin dili hazırlanmış Android mobil uygulama.<br/><br/>
**Kullanılan Teknolojiler & Yapılar**<br/>
•	Jetpack Compose -- Android için bildirime dayalı UI kiti.<br/>
•	MVVM Mimarisi – (Model – View- ViewModel)<br/>
•	Paging -- Sayfalandırma<br/>
•	Dagger Hilt -- Dependency Injection çerçevesi<br/>
•	DataStore – Veri depolama<br/>
•	Glide – Resimleri yükleme<br/>
•	Navigation --Ekranlar arasında gezinme<br/>
•	LiveData – Yaşam döngüsü<br/>
•	Retrofit -- HTTP istemcisi<br/>
•	Coroutines -- Bir eşzamanlı tasarım deseni kitaplığı<br/><br/>
		API hakkında<br/>
API belgelerinin bağlantısı: https://rickandmortyapi.com/<br/><br/>
		**Ekranlar**<br/>
1-	Splash Screen (Karşılama Ekranı) -> Uygulamaya girdiğinizde sizi ilk Splash Screen karşılar. Uygulamaya ilk açışınız ise uygulamayı tanıtıcı bir fotoğraf ve altında Welcome! yazısını görürsünüz. Eğer daha önce uygulamayı açtıysanız bu sefer Hello! yazacaktır.<br/>
private object DataStoreKeys{<br/>
            val isFirstRun = booleanPreferencesKey(Constants.FIRST_RUN_KEY)<br/>
        }<br/>
        suspend fun saveRunInfo(isFirst: Boolean){<br/>
            dataStore.edit {<br/>
                it[DataStoreKeys.isFirstRun] = isFirst<br/>
                }<br/>
               }<br/>
         suspend fun readRunInfo(): Boolean{<br/>
            val p = dataStore.data.first()<br/>
            return p[DataStoreKeys.isFirstRun]?:false<br/>
                }<br/><br/>

**2-	Main Screen (Ana Ekran)->** Bu ekranda yatay kayan ve dikey kayan listeleme mevcuttur. Yatay kayan listeleme de Rick And Morty televizyon dizisinde geçen konumlar listelenmiştir. İlk başta 20 tane konum görüyoruz kaydırdıkça sayfa sayfa konumlar çekiliyor, her bir sayfa 20 konum getirmektedir ve ekranda kullanıcıya gösteriliyor. Dikey kayan listeme de Rick And Morty dizisinde bulunan seçili konumdaki karakterler listelenmiştir.<br/> <br/>

- **Paging ile verileri sayfa sayfa çekme**<br/>
class PagingRepository @Inject constructor (private val rickAndMortyDaoRepository: RickAndMortyDaoRepository): PagingSource<Int, Results>() {<br/>
<br/>
override fun getRefreshKey(state: PagingState<Int, Results>): Int? {<br/>
    return state.anchorPosition?.let { anchorPosition -><br/>
        val anchorPage = state.closestPageToPosition(anchorPosition)<br/>
        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)<br/>
    }<br/>
}<br/>
<br/>
@SuppressLint("SuspiciousIndentation")<br/>
override suspend fun load(params: LoadParams<Int>): LoadResult<Int,Results> {<br/>
    return try {<br/>
        val nextPageNumber = params.key ?: 1<br/>
        val response = rickAndMortyDaoRepository.getLocations(nextPageNumber)<br/>
        val locations = response.body()!!.results?: emptyList()<br/>
        val nextPage = if (response.body()!!.info.next.isNullOrEmpty()) null else nextPageNumber + 1<br/>
            LoadResult.Page(<br/>
            data = locations ,<br/>
            prevKey = null,<br/>
            nextKey = nextPage<br/>
        )<br/>
    } catch (e: Exception) {<br/>
        LoadResult.Error(e)<br/>
    }<br/>
  }<br/>
	}<br/>
	<br/>
val locations= Pager(PagingConfig(pageSize = 20)) {<br/>
    PagingRepository(repo)<br/>
}.flow.cachedIn(viewModelScope)<br/>
	<br/>
3- Detail Screen (Detay Ekranı)-> Bu ekranda; Main Screen ekranında seçilen karakterin detayları görünmektedir. Detaylar bu ekrana Main Screen’den Parcelable yapısını kullanarak getirilmektedir. <br/><br/>
val character =  navController.previousBackStackEntry?.savedStateHandle?.get<Characters>("character")<br/>
character?.let {<br/>
    CharacterDetailScreen(navController = navController, character = character)<br/>
}<br/>
<br/>
navController.currentBackStackEntry!!.savedStateHandle.set(<br/>
    key = "character",<br/>
    value = character<br/>
)<br/>



