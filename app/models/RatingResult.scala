package models

case class RatingResult (
  title : String,
  startYear : Option[Int] = None,
  endYear : Option[Int] = None,
  runtime : Option[Int] = None,
  isAdult : Option[Boolean] = None,
  averageRating : Option[Double] = None,
  numVotes: Option[Int] = None
)
