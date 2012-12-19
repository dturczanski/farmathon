package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.EnquiryService
import com.mongodb.casbah.Imports._

object Enquiries extends Controller {
  
   val enquirySearchForm = Form(
    tuple(
      "surname" -> nonEmptyText,
      "enquiryId" -> nonEmptyText
    )
  ) 
  
  // display list of enquiries
  def index = Action { implicit request =>
    Ok(views.html.enquiries.index(EnquiryService.all))
  }

  // display enquiry details
  def details(id: String) = Action {implicit request =>
    val enquiry = EnquiryService.get(new ObjectId(id))
  	Ok(views.html.enquiries.details(enquiry))
  }
  
  // accept enquiry
  def accept(id: String) = Action {implicit request =>
    val accepted = true
    if (accepted) EnquiryService.updateStatus(id, "accepted")
    else EnquiryService.updateStatus(id, "rejected")
  	Ok("")
  } 
  
  // decline enquiry
  def decline(id: String) = Action {implicit request =>
    EnquiryService.updateStatus(id, "rejected")
  	Ok("")
  } 
  def search = Action { implicit request =>
    enquirySearchForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.enquiries.search(formWithErrors)),
      {case (surname, enquiryId) => {
        //EnquiryService.find(surname, enquiryId);
        Ok(views.html.enquiries.detail())        
      }
      case _ =>{Ok(views.html.enquiries.search(enquirySearchForm))
      }}
      )
  }
}
