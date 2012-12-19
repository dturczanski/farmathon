package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.EnquiryService

object Enquiries extends Controller {
  
   val enquirySearchForm = Form(
    tuple(
      "surname" -> nonEmptyText,
      "enquiryId" -> nonEmptyText
    )
  ) 
  
  // display list of enquiries
  }

  // display enquiry details
  def details(id: String) = TODO

  // accept enquiry
  def accept(id: String) = TODO

  // decline enquiry
  def decline(id: String) = TODO

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
