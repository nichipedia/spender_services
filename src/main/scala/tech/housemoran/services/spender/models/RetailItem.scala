package tech.housemoran.services.spender.models

trait RetailItem {
  def salePrice: Double
  def price: Double
  def productCategory: String
  def rating: Double
  def department: String
  def seller: String
  def name: String
  def url: String
  def pictureUrl: String
}
