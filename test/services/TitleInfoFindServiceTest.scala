package services

import helpers.LunaTestSpecification
import models.{Cast, Crew, TitleInfo}
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
      val tconst = 1
      val title = "21 Jump Street"
      val directors = "10, 11"
      val writers = "12, 13, 14"
      val actor1 = "Channing Tatum"
      val nconst1 = "1"
      val actor2 = "Jonah Hill"
      val nconst2 = "2"

      // insert data
      await(titleBasicsRepository.insert(generateTitleBasics(1, title, tconst)))
      await(titleCrewRepository.insert(generateTitleCrew(1, directors, writers, tconst)))
      await(titleActorsRepository.insert(generateTitleActors(1, tconst, nconst1)))
      await(titleActorsRepository.insert(generateTitleActors(1, tconst, nconst2)))
      await(nameBasicsRepository.insert(generateNameBasics(1, actor1, "actor", nconst1.toInt)))
      await(nameBasicsRepository.insert(generateNameBasics(1, actor2, "actor", nconst2.toInt)))
      await(nameBasicsRepository.insert(generateNameBasics(1, "Jim", "director", 10)))
      await(nameBasicsRepository.insert(generateNameBasics(1, "Mike", "director", 11)))
      await(nameBasicsRepository.insert(generateNameBasics(1, "Seth", "writer", 12)))
      await(nameBasicsRepository.insert(generateNameBasics(1, "Dan", "writer", 13)))
      await(nameBasicsRepository.insert(generateNameBasics(1, "Jonah", "writer", 14)))

      val output = await(service.findTitleInfo(title))

      assert(output == Right(TitleInfo(
        "21 Jump Street",
        "genres",
        Some(1992),
        Some(10),
        Some(true),
        Some(1993),
        1,
        Seq(Crew("Jim","director",Some(1993),Some(1992)), Crew("Mike","director",Some(1993),Some(1992))),
        Seq(Crew("Seth","writer",Some(1993),Some(1992)), Crew("Dan","writer",Some(1993),Some(1992)), Crew("Jonah","writer",Some(1993),Some(1992))),
        Seq(Cast("Channing Tatum","actor",Some(1993),Some(1992)), Cast("Jonah Hill","actor",Some(1993),Some(1992)))
      )))
    }
  }
}
