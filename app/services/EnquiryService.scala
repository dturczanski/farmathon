package services

import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import models._

object EnquiryService {
  val data = MongoConnection()("farmathon")("enquiries")

  all = data.map(grater[Enquiry].asObject(_)).toList
}