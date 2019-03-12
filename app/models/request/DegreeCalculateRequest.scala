package models.request

case class DegreeCalculateRequest (actor : String) {
  require(!actor.isEmpty, "Actor name cannot be empty")
}
