package services

import helpers.LunaTestSpecification
import repositories._

class TitleInfoFindServiceTest extends LunaTestSpecification {
  "TitleInfoServiceTest" should {
    "findTitleInfo" in new WithMemDb {
      val service = instance[TitleInfoFindService]
      val titleBasicsRepository = instance[TitleBasicsRepository]
      val titleCrewRepository = instance[TitleCrewRepository]
      val titleActorsRepository = instance[TitleActorsRepository]
      val nameBasicsRepository = instance[NameBasicsRepository]
      val genreId = 1
      val genre= "horror"
      val size = 100
      val tconst = 1
      val title = "title"
      val actor = "actor"

      val list = generateGenreTitles(size, genreId)

      // insert data
      await(titleBasicsRepository.insert(generateTitleBasics(size, title)))
      await(titleCrewRepository.insert(generateTitleCrew(size)))
      await(titleActorsRepository.insert(generateTitleActors(size)))
      await(nameBasicsRepository.insert(generateNameBasics(size, actor, 1)))

      val output = await(service.findTitleInfo(title))

      println(output)
    }
  }
}
