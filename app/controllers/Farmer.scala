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

def index = Action { implicit request =>
	Ok(views.html.name(farmerNameForm))
}

def farmerName = Action { implicit request =>
	farmerNameForm.bindFromRequest.fold(
		form => BadRequest(views.html.name(form)),
		enquiryName => {
			//EnquiryService.create(enquiryName)
			Redirect(routes.Farmer.farmerName()).flashing("message" -> "Names information successfully saved")
		}
	)
}

}
