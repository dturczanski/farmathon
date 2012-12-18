package controllers

import play.api._
import play.api.mvc._

object Enquiries extends Controller {
  // display list of enquiries
  def index = TODO

  // display enquiry details
  def details(id: Long) = Action

  // accept enquiry
  def accept(id: Long) = TODO

  // decline enquiry
  def decline(id: Long) = TODO
}
