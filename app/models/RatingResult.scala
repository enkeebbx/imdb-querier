package models

case class RatingResult (
  title : String,
  startYear : Option[Int],
  endYear : Option[Int],
  runtime : Option[Int],
  isAdult : Option[Boolean],
  averageRating : Option[Double],
  numVotes: Option[Int]
)
