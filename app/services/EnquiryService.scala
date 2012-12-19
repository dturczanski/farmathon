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
  
  def outstanding = EnquiryDAO.find(MongoDBObject("status" -> "pending")).toList

  def get(id: String): Enquiry = {
    get(new ObjectId(id))
  }
  
  def get(id: ObjectId): Enquiry = {
    val enq: Option[Enquiry] = EnquiryDAO.findOneById(id)
    enq.get
  }
  
  def update(enquiryName: EnquiryName): String = {
    if (enquiryName.id.equals("")){
        val enq = Enquiry(None, enquiryName.firstName, enquiryName.middleName, enquiryName.lastName, enquiryName.previousNames, "", "draft")
    	return EnquiryDAO.insert(enq).get.toString()
    } else {
      val enq = get(enquiryName.id)
      val updated = enq.copy(firstName = enquiryName.firstName,
        						middleName = enquiryName.middleName,
        						lastName = enquiryName.lastName,
        						previousNames = enquiryName.previousNames)

       EnquiryDAO.update(MongoDBObject("_id" -> updated._id), grater[Enquiry].asDBObject(updated), false, false)
       return enquiryName.id;
    }
  }
  
  def update(enquiryEmail: EnquiryEmail) {
      val enq = get(enquiryEmail.id)
      val updated = enq.copy(email = enquiryEmail.email)

       EnquiryDAO.update(MongoDBObject("_id" -> updated._id), grater[Enquiry].asDBObject(updated), false, false)
  }
  
  def getName(id: String): EnquiryName = {
    val enq = get(id)
    val name = EnquiryName(id, enq.firstName, enq.middleName, enq.lastName, enq.previousNames)
    name
  }
  
  def getEmail(id: String): EnquiryEmail = {
    val enq = get(id)
    val email = EnquiryEmail(id, enq.email)
    email
  }
  
  def updateStatus(id : String, newStatus : String) {
    val enquiry = get(id)
    EnquiryDAO.update(MongoDBObject("_id" -> enquiry._id), grater[Enquiry].asDBObject(enquiry.copy(status = newStatus)), false, false)
  }

  def findByIdAndSurname(id: String, surname: String) = EnquiryDAO.findOne(MongoDBObject("_id" -> new ObjectId(id), "lastName" -> surname))

}