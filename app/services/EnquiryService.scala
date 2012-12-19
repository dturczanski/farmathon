package services

import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import org.scala_tools.time.Imports._
import models._
import viewmodels._
import com.novus.salat.dao.SalatDAO

object EnquiryDAO extends SalatDAO[Enquiry, ObjectId](collection = MongoConnection()("farmathon")("enquiries"))

object EnquiryService {

  def all = EnquiryDAO.find(MongoDBObject.empty).toList

  def create(enquiryName: EnquiryName) {
    val enq = Enquiry(None, enquiryName.firstName, enquiryName.middleName, enquiryName.lastName, enquiryName.previousNames, "", "draft")
    EnquiryDAO.insert(enq);
  }
}