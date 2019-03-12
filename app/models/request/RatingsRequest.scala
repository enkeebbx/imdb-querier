package models.request

case class RatingsRequest (genre : String) {
  require(!genre.isEmpty, "Genre cannot be empty")
}
