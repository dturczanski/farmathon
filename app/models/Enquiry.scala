package models

import play.data.format.Formats.DateTime

case class Enquiry (
  _id: String,
  firstName: String,
  middleName: Option[String],
  lastName: String,
  previousNames: Option[String],
  email: String,
  createdAt: DateTime
)
