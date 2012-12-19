package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.EnquiryService
import viewmodels.EnquiryName

object Farmer extends Controller {
  
def farmerNameForm = Form(
	mapping(
		"firstName" -> nonEmptyText,
		"middleName" -> text,
		"lastName" -> nonEmptyText,
		"previousNames" -> text
	)(EnquiryName.apply)(EnquiryName.unapply)
)

def farmerEmailForm = Form(
	mapping(
		"email" -> nonEmptyText
	)(EnquiryEmail.apply)(EnquiryEmail.unapply)
)

def index = Action { implicit request =>
	Ok(views.html.farmer.name(farmerNameForm))
}

def farmerName = Action { implicit request =>
	farmerNameForm.bindFromRequest.fold(
		form => BadRequest(views.html.farmer.name(form)),
		enquiryName => {
			EnquiryService.update(None, enquiryName)
			Ok(views.html.farmer.email(farmerEmailForm)).flashing("message" -> "Names information successfully saved")
		}
	)
}

def farmerEmail = Action { implicit request =>
	farmerEmailForm.bindFromRequest.fold(
	    form => BadRequest(views.html.farmer.email(form)),
	    enquiryName => {
	    	EnquiryService.sdfsd(enquiryEmail)
	    	Redirect(router.farmer)
	    })
}

}
