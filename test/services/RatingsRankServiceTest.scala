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
      val offset = 0
      val limit = 10

      // insert data
      await(genresRepository.insert(Seq(generateGenres(1, genre))))
      await(genreTitleRepository.insert(generateGenreTitles(1,1, 1)))
      await(genreTitleRepository.insert(generateGenreTitles(1,1, 2)))

      await(titleRatingsRepository.insert(generateTitleRatings(1, 1, 6.6)))
      await(titleBasicsRepository.insert(generateTitleBasics(1, "21 Jump Street", 1)))

      await(titleRatingsRepository.insert(generateTitleRatings(1, 2, 7.2)))
      await(titleBasicsRepository.insert(generateTitleBasics(1, "Harold and Kumar", 2)))

      val output = await(service.findRatingResultsDescByGenreId(genreId, offset, limit))

      assert(output.map(_.averageRating) == Seq(Some(7.2), Some(6.6)))
    }
  }
}
