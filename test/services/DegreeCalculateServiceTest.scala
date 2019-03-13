package services

import helpers.LunaTestSpecification
import models.exception.KevinBaconNotFoundException
import repositories._

class DegreeCalculateServiceTest extends LunaTestSpecification {
  "DegreeCalculateServiceTest" should {
    "Degree test" in new WithMemDb {
      val service = instance[DegreeCalculateService]
      val nameBasicsRepository = instance[NameBasicsRepository]
      val titlePrincipalsRepository = instance[TitlePrincipalsRepository]

      // out of degree
      val me = "Kim Yoon Ung"
      val meNconst = 23
      val meTconst1 = "1"
      val meTconst2 = "2"
      val meTconst3 = "3"

      // out of degree
      val a1 = "Adam Sandler"
      val a1Nconst = 24
      val a1Tconst1 = meTconst1
      val a1Tconst2 = "4"
      val a1Tconst3 = "5"

      // degree 6
      val a2 = "Jim Carrey"
      val a2Nconst = 25
      val a2Tconst1 = meTconst2
      val a2Tconst2 = "6"

      // out of degree
      val a3 = "Will Farrell"
      val a3Nconst = 26
      val a3Tconst1 = meTconst3

      // degree 5
      val a4 = "Zach Galifianakis"
      val a4Nconst = 27
      val a4Tconst1 = a2Tconst2
      val a4Tconst2 = "7"

      // degree 4
      val a5 = "Jonah Hill"
      val a5Nconst = 28
      val a5Tconst1 = a4Tconst2
      val a5Tconst2 = "8"

      // degree 3
      val a6 = "Channing Tatum"
      val a6Nconst = 29
      val a6Tconst1 = a5Tconst2
      val a6Tconst2 = "9"

      // degree 2
      val a7 = "Will Smith"
      val a7Nconst = 30
      val a7Tconst1 = a6Tconst2
      val a7Tconst2 = "10"

      // degree 1
      val a8 = "Bruce Lee"
      val a8Nconst = 31
      val a8Tconst1 = a7Tconst2
      val a8Tconst2 = "11"

      // degree 6
      val a9 = "Kevin Bacon"
      val a9Nconst = 32
      val a9Tconst1 = a8Tconst2


      // insert data
      await(nameBasicsRepository.insert(generateNameBasics(1, me, "actor", meNconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a1, "actor", a1Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a2, "actor", a2Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a3, "actor", a3Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a4, "actor", a4Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a5, "actor", a5Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a6, "actor", a6Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a7, "actor", a7Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a8, "actor", a8Nconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, a9, "actor", a9Nconst)))

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
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a5Tconst2.toInt, a5Nconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a6Tconst1.toInt, a6Nconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a6Tconst2.toInt, a6Nconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a7Tconst1.toInt, a7Nconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a7Tconst2.toInt, a7Nconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a8Tconst1.toInt, a8Nconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a8Tconst2.toInt, a8Nconst.toString)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, a9Tconst1.toInt, a9Nconst.toString)))

      assert(await(service.getDegreeWithKevinBacon(me)) == Left(KevinBaconNotFoundException()))
      assert(await(service.getDegreeWithKevinBacon(a1)) == Left(KevinBaconNotFoundException()))
      assert(await(service.getDegreeWithKevinBacon(a2)) == Right(6))
      assert(await(service.getDegreeWithKevinBacon(a3)) == Left(KevinBaconNotFoundException()))
      assert(await(service.getDegreeWithKevinBacon(a4)) == Right(5))
      assert(await(service.getDegreeWithKevinBacon(a5)) == Right(4))
      assert(await(service.getDegreeWithKevinBacon(a6)) == Right(3))
      assert(await(service.getDegreeWithKevinBacon(a7)) == Right(2))
      assert(await(service.getDegreeWithKevinBacon(a8)) == Right(1))
    }

    "No association with Kevin Bacon at all" in new WithMemDb {
      val service = instance[DegreeCalculateService]
      val nameBasicsRepository = instance[NameBasicsRepository]
      val titlePrincipalsRepository = instance[TitlePrincipalsRepository]

      // No Kevin Bacon, just me
      val me = "Kim Yoon Ung"
      val meNconst = 23
      val meTconst1 = "1"
      val meTconst2 = "2"
      val meTconst3 = "3"

      val kevinBacon = "Kevin Bacon"
      val kevinBaconNconst = 24

      // insert data
      await(nameBasicsRepository.insert(generateNameBasics(1, me, "actor", meNconst)))
      await(nameBasicsRepository.insert(generateNameBasics(1, kevinBacon, "actor", kevinBaconNconst)))

      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, meTconst1.toInt, meNconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, meTconst2.toInt, meNconst.toString)))
      await(titlePrincipalsRepository.insert(generateTitlePrincipals(1, meTconst3.toInt, meNconst.toString)))

      assert(await(service.getDegreeWithKevinBacon(me)) == Left(KevinBaconNotFoundException()))
    }
  }
}
