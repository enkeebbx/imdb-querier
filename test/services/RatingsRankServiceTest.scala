package services

import helpers.LunaTestSpecification
import repositories.{GenreTitlesRepository, GenresRepository, TitleBasicsRepository, TitlesRatingsRepository}

class RatingsRankServiceTest extends LunaTestSpecification {
  "RatingsQueryServiceTest" should {
    "ratings" in new WithMemDb {
      val service = instance[RatingsRankService]
      val genresRepository = instance[GenresRepository]
      val genreTitleRepository = instance[GenreTitlesRepository]
      val titleRatingsRepository = instance[TitlesRatingsRepository]
      val titleBasicsRepository = instance[TitleBasicsRepository]
      val genreId = 1
      val genre= "horror"
      val title = "horror movie"
      val size = 100
      val tconst = 1

      val list = generateGenreTitles(size, genreId)
      // insert data
      await(genresRepository.insert(generateGenres(size, genre)))
      await(genreTitleRepository.insert(generateGenreTitles(size, genreId)))
      await(titleRatingsRepository.insert(generateTitleRatings(size)))
      await(titleBasicsRepository.insert(generateTitleBasics(size, title)))

      val output = await(service.findRatingResultsDescByGenreId(genreId))

      println(output)
    }
  }
}
