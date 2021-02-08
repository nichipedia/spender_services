package tech.housemoran.services.spender.models

import java.util.Date

trait CurrentItems {
  def getDate: Date
  def getItems: List[RetailItem]
  def getSiteName: String
}
