package controllers

import play.api._
import play.api.mvc._
import services.EnquiryService

object Enquiries extends Controller {
  // display list of enquiries
  def index = TODO

  // display enquiry details
  def details(id: String) = TODO

  // accept enquiry
  def accept(id: String) = TODO

  // decline enquiry
  def decline(id: String) = TODO

  def search = Action { implicit request =>
    Application.enquirySearchForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.index(formWithErrors)),
      {case (surname, enquiryId) => {
        //EnquiryService.find(surname, enquiryId);
        Ok(views.html.enquiries.enquiry())        
      }}
      )
  }
}
