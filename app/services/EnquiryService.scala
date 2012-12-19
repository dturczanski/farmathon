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

  def get(id: ObjectId): Enquiry = {
    val enq: Option[Enquiry] = EnquiryDAO.findOneById(id)
    enq.get
  }
  
  def update(id: Option[ObjectId], enquiryName: EnquiryName) {
    id match {
      case Some(objectId) => {
        val enq = get(objectId)
        val updated = enq.copy(firstName = enquiryName.firstName,
        						middleName = enquiryName.middleName,
        						lastName = enquiryName.lastName,
        						previousNames = enquiryName.previousNames)
        // TODO
        //EnquiryDAO.update()
        
      }
      
      case None => {
    	val enq = Enquiry(None, enquiryName.firstName, enquiryName.middleName, enquiryName.lastName, enquiryName.previousNames, "", "draft")
    	EnquiryDAO.insert(enq)
      }
    } 

  }
}