package models.entity

case class TitlePrincipals (
  category: String,
  principalCast : String,
  ordering : String,
  tconst : Option[Int] = None,
  characters : String,
  job : String,
  nconst : String
)
