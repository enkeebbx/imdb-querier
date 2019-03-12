package models

import models.entity.NameBasics

case class DirectorWriterNameBasics (
  director : Seq[NameBasics],
  writer : Seq[NameBasics]
)
