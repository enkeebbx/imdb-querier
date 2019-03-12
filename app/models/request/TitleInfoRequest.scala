package models.request

case class TitleInfoRequest (title : String) {
  require(!title.isEmpty, "Title cannot be empty")
}
