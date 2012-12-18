package models

import play.data.format.Formats.DateTime

case class EnquiryName (
  firstName: String,
  middleName: String,
  lastName: String,
  previousNames: String
)
