package services

import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import org.scala_tools.time.Imports._
import models._
import viewmodels._

object EnquiryService {
  val data = MongoConnection()("farmathon")("enquiries")

  def all = data.map(grater[Enquiry].asObject(_)).toList
  
  def get(id: ObjectId): Enquiry = {
    val o : DBObject = MongoDBObject("id" -> id)
    val enq : Enquiry = grater[Enquiry].asObject(o)
    enq
  }
  
  def update(id: Option[ObjectId], enquiryName: EnquiryName) {
    id match {
      case Some(objectId) => {
        val enq = get(objectId)
        enq.firstName = enquiryName.firstName
        
      }
      
      case None => {
    	val enq = Enquiry(None, enquiryName.firstName, enquiryName.middleName, enquiryName.lastName, enquiryName.previousNames, "")
    	data += grater[Enquiry].asDBObject(enq)
      }
    } 

  }
}