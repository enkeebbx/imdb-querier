package services

import helpers.LunaTestSpecification
import repositories._

class DegreeCalculateServiceTest extends LunaTestSpecification {
  "DegreeCalculateServiceTest" should {
    "Degree is 1" in new WithMemDb {
      val service = instance[DegreeCalculateService]
      val nameBasicsRepository = instance[NameBasicsRepository]
      val titlePrincipalsRepository = instance[TitlePrincipalsRepository]

      val me = "Kim Yoon Ung"
      val meTconst = "1"
      val meNconst = 23

      val kevinBaconTconst = "1"
      val kevinBaconNconst = 8283

      // insert data
      await(nameBasicsRepository.insert(generateNameBasics(1, me, meNconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, "Kevin Bacon", kevinBaconNconst)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, meTconst.toInt, meNconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, kevinBaconTconst.toInt, kevinBaconNconst.toString)))

      val output = await(service.getDegreeWithKevinBacon(me))

      assert(output == Right(1))
    }

    "Degree is 2" in new WithMemDb {
      val service = instance[DegreeCalculateService]
      val nameBasicsRepository = instance[NameBasicsRepository]
      val titlePrincipalsRepository = instance[TitlePrincipalsRepository]

      val me = "Kim Yoon Ung"
      val meNconst = 23
      val meTconst1 = "1"
      val meTconst2 = "2"
      val meTconst3 = "3"

      val a1 = "A1"
      val a1Nconst = 24
      val a1Tconst1 = meTconst1
      val a1Tconst2 = "4"
      val a1Tconst3 = "5"

      val a2 = "A2"
      val a2Nconst = 25
      val a2Tconst1 = meTconst2
      val a2Tconst2 = "6"

      val a3 = "A3"
      val a3Nconst = 26
      val a3Tconst1 = meTconst3

      val a4 = "Kevin Bacon"
      val a4Nconst = 27
      val a4Tconst1 = a2Tconst2

      // insert data
      await(nameBasicsRepository.insert(generateNameBasics(1, me, meNconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a1, a1Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a2, a2Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a3, a3Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a4, a4Nconst)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, meTconst1.toInt, meNconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, meTconst2.toInt, meNconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, meTconst3.toInt, meNconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a1Tconst1.toInt, a1Nconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a1Tconst2.toInt, a1Nconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a1Tconst3.toInt, a1Nconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a2Tconst1.toInt, a2Nconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a2Tconst2.toInt, a2Nconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a3Tconst1.toInt, a3Nconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a4Tconst1.toInt, a4Nconst.toString)))

      val output = await(service.getDegreeWithKevinBacon(me))

      assert(output == Right(2))
    }

    "Degree is more than 2" in new WithMemDb {
      val service = instance[DegreeCalculateService]
      val nameBasicsRepository = instance[NameBasicsRepository]
      val titlePrincipalsRepository = instance[TitlePrincipalsRepository]

      val me = "Kim Yoon Ung"
      val meNconst = 23
      val meTconst1 = "1"
      val meTconst2 = "2"
      val meTconst3 = "3"

      val a1 = "A1"
      val a1Nconst = 24
      val a1Tconst1 = meTconst1
      val a1Tconst2 = "4"
      val a1Tconst3 = "5"

      val a2 = "A2"
      val a2Nconst = 25
      val a2Tconst1 = meTconst2
      val a2Tconst2 = "6"

      val a3 = "A3"
      val a3Nconst = 26
      val a3Tconst1 = meTconst3

      val a4 = "A4"
      val a4Nconst = 27
      val a4Tconst1 = a2Tconst2
      val a4Tconst2 = "7"

      val a5 = "Kevin Bacon"
      val a5Nconst = 28
      val a5Tconst1 = a4Tconst2

      // insert data
      await(nameBasicsRepository.insert(generateNameBasics(1, me, meNconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a1, a1Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a2, a2Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a3, a3Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a4, a4Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a5, a5Nconst)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, meTconst1.toInt, meNconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, meTconst2.toInt, meNconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, meTconst3.toInt, meNconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a1Tconst1.toInt, a1Nconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a1Tconst2.toInt, a1Nconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a1Tconst3.toInt, a1Nconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a2Tconst1.toInt, a2Nconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a2Tconst2.toInt, a2Nconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a3Tconst1.toInt, a3Nconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a4Tconst1.toInt, a4Nconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a4Tconst2.toInt, a4Nconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a5Tconst1.toInt, a5Nconst.toString)))

      val output = await(service.getDegreeWithKevinBacon(me))

      assert(output == Right(3))
    }
  }
}
