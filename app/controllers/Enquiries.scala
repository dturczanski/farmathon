package controllers

import auth.Secured

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services._
import com.mongodb.casbah.Imports._
import models.Enquiry
import anorm._
import views._

object Enquiries extends Controller with Secured {

  val enquirySearchForm = Form(
    tuple(
      "surname" -> nonEmptyText,
      "enquiryId" -> nonEmptyText))

  // display list of enquiries
  def index = withAuth { username =>
    implicit request =>
      Ok(html.enquiries.index(EnquiryService.all))
  }

  // display enquiry details
  def details(id: String) = withAuth { username =>
    implicit request =>
      val enquiry = EnquiryService.get(new ObjectId(id))
      Ok(views.html.enquiries.details(enquiry))
  }

  // accept enquiry
  def accept(id: String) = withAuth { username =>
    implicit request =>
      val acceptedParam = request.body.asFormUrlEncoded.get("accepted")(0)
      if (acceptedParam.toBoolean)
      {
    	  EnquiryService.updateStatus(id, "accepted")
    	  EmailService.sendAcceptanceEmail(id)
        } else {
        	EnquiryService.updateStatus(id, "rejected")
        	EmailService.sendRejectionEmail(id)
        }
      Redirect(routes.Enquiries.details(id))
  }

  def searchForm = Action { implicit request =>
    Ok(html.enquiries.search(enquirySearchForm, None))
  }

  def search = Action { implicit request =>
    enquirySearchForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.enquiries.search(formWithErrors, None)),
      {
        case (surname, enquiryId) => {
          val enquiry = EnquiryService.findByIdAndSurname(enquiryId, surname)
          enquiry match {
            case None => Ok(html.enquiries.search(enquirySearchForm, Some("Application not found")))
            case enquiry => Ok(html.enquiries.detail(enquiry.get))
          }
        }
      })
  }
}
