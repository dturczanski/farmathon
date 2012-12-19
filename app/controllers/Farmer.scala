package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.EnquiryService
import viewmodels._

object Farmer extends Controller {
  
def farmerNameForm = Form(
	mapping(
	    "id" -> text,
		"firstName" -> nonEmptyText,
		"middleName" -> text,
		"lastName" -> nonEmptyText,
		"previousNames" -> text
	)(EnquiryName.apply)(EnquiryName.unapply)
)

def farmerEmailForm = Form(
	mapping(
	    "id" -> nonEmptyText,
		"email" -> nonEmptyText
	)(EnquiryEmail.apply)(EnquiryEmail.unapply)
)

def emptyForm = Form(
	mapping(
	    "id" -> nonEmptyText
	)(EnquiryId.apply)(EnquiryId.unapply)
)

def index = Action { implicit request =>
	Ok(views.html.farmer.name(farmerNameForm))
}

def farmerName = Action { implicit request =>
	farmerNameForm.bindFromRequest.fold(
		form => BadRequest(views.html.farmer.name(form)),
		enquiryName => {
			val id = EnquiryService.update(enquiryName)
			Ok(views.html.farmer.email(farmerEmailForm.fill(EnquiryService.getEmail(id)))).flashing("message" -> "Names information successfully saved")
		}
	)
}

def farmerEmail = Action { implicit request =>
	farmerEmailForm.bindFromRequest.fold(
	    form => BadRequest(views.html.farmer.email(form)),
	    enquiryEmail => {
	    	EnquiryService.update(enquiryEmail)
	    	Ok(views.html.farmer.verify(emptyForm.fill(EnquiryId(enquiryEmail.id))))
	    })
}

def changeDetails = Action { implicit request =>
	emptyForm.bindFromRequest.fold(
	    form => BadRequest(views.html.farmer.verify(form)),
	    enquiryId => {
	    	Ok(views.html.farmer.name(farmerNameForm.fill(EnquiryService.getName(enquiryId.id))))
	    })
}

def confirmDetails = Action { implicit request =>
	emptyForm.bindFromRequest.fold(
	    form => BadRequest(views.html.farmer.verify(form)),
	    enquiryId => {
	        EnquiryService.updateStatus(enquiryId.id, "pending")
	    	Ok(views.html.farmer.confirm(emptyForm.fill(enquiryId)))
	    })
}

}
