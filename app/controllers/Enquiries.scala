package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.EnquiryService
import com.mongodb.casbah.Imports._
import models.Enquiry
import anorm._
import views._

object Enquiries extends Controller {
  
   val enquirySearchForm = Form(
    tuple(
      "surname" -> nonEmptyText,
      "enquiryId" -> nonEmptyText
    )
  )
  
  // display list of enquiries
  def index = Action { implicit request =>
    Ok(html.enquiries.index(EnquiryService.all))
  }

  // display enquiry details
  def details(id: String) = Action {implicit request =>
    val enquiry = EnquiryService.get(new ObjectId(id))
  	Ok(views.html.enquiries.details(enquiry))
  }
  
  // accept enquiry
  def accept(id: String) = Action {implicit request =>
    val acceptedParam = request.body.asFormUrlEncoded.get("accepted")(0)
    if (acceptedParam.toBoolean) EnquiryService.updateStatus(id, "accepted")
    else EnquiryService.updateStatus(id, "rejected")
  	Ok("")
  } 
  
  def decline(id: String) = Action {implicit request =>
    EnquiryService.updateStatus(id, "rejected")
  	Ok("")
  } 


  def searchForm= Action { implicit request =>
    Ok(html.enquiries.search(enquirySearchForm))
  }
  
  def search = Action { implicit request =>
    enquirySearchForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.enquiries.search(formWithErrors)),
      {case (surname, enquiryId) => {
          val enquiry = EnquiryService.findByIdAndSurname(enquiryId, surname)
          enquiry match{
            case None => Ok(html.enquiries.notFound())
            case enquiry => Ok(html.enquiries.detail(enquiry.get))            
          }
      }
      case _ =>{Ok(html.enquiries.search(enquirySearchForm))
      }}
      )
  }
}
