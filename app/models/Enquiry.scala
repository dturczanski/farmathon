package models

import org.scala_tools.time.Imports._
import com.mongodb.casbah.Imports._

// Enquiry (farmer's application) model
case class Enquiry (
  _id: Option[ObjectId],
  firstName: String,
  middleName: String,
  lastName: String,
  previousNames: String,
  email: String
)
