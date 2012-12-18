package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {
  
   val enquirySearchForm = Form(
    tuple(
      "surname" -> nonEmptyText,
      "enquiryId" -> nonEmptyText
    )
  )  
  
  def index = Action {
    Ok(views.html.index(enquirySearchForm))
  }  
}