package models

import play.data.format.Formats.DateTime

case class Enquiry (
  _id: String,
  firstName: String,
  lastName: String,
  email: String,
  createdAt: DateTime
)
