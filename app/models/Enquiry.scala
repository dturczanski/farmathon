package models

import play.data.format.Formats.DateTime
import com.mongodb.casbah.Imports._

// Enquiry (farmer's application) model
case class Enquiry (
  id: ObjectId,
  firstName: String,
  middleName: Option[String],
  lastName: String,
  previousNames: Option[String],
  email: String,
  createdAt: DateTime
)
