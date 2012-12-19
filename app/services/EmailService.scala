package services

import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import org.scala_tools.time.Imports._
import models._
import viewmodels._
import com.novus.salat.dao.SalatDAO
import com.typesafe.plugin._
import play.api.Play.current

object EmailService {

  def sendConfirmationEmail(id: String) {
  
	val enq: Enquiry = EnquiryService.get(id)
    
	val mail = use[MailerPlugin].email
	mail.setSubject("Your application details")
	mail.addRecipient(enq.firstName + " " + enq.lastName + " <" + enq.email + ">", enq.email)
	mail.addFrom("Gdansk Hakaton Team <l.skibski@kainos.com>")
	val contents: String = "Hello " + enq.firstName +",\n\nYour application details:\n\n" +  enq.firstName + " " +
			enq.middleName + " " + enq.lastName + " " + enq.previousNames + "\n" + enq.email +
			"\nApplication reference number: " + id + "\n\nRegards,\nFarmathon Team";
	mail.send(contents)
  }
  
  def sendAcceptanceEmail(id: String) {
  
	val enq: Enquiry = EnquiryService.get(id)
    
	val mail = use[MailerPlugin].email
	mail.setSubject("Your application has been accepted")
	mail.addRecipient(enq.firstName + " " + enq.lastName + " <" + enq.email + ">", enq.email)
	mail.addFrom("Gdansk Hakaton Team <l.skibski@kainos.com>")
	val contents: String = "Hello " + enq.firstName +",Your application has been accepted.\n\nYour application details:\n\n" +  enq.firstName + " " +
			enq.middleName + " " + enq.lastName + " " + enq.previousNames + "\n" + enq.email +
			"\nApplication reference number: " + id + "\n\nRegards,\nFarmathon Team";
	mail.send(contents)
  }
  
  def sendRejectionEmail(id: String) {
  
	val enq: Enquiry = EnquiryService.get(id)
    
	val mail = use[MailerPlugin].email
	mail.setSubject("Your application has been accepted")
	mail.addRecipient(enq.firstName + " " + enq.lastName + " <" + enq.email + ">", enq.email)
	mail.addFrom("Gdansk Hakaton Team <l.skibski@kainos.com>")
	val contents: String = "Hello " + enq.firstName +",Your application has been rejected.\n\nYour application details:\n\n" +  enq.firstName + " " +
			enq.middleName + " " + enq.lastName + " " + enq.previousNames + "\n" + enq.email +
			"\nApplication reference number: " + id + "\n\nRegards,\nFarmathon Team";
	mail.send(contents)
  }
  
}