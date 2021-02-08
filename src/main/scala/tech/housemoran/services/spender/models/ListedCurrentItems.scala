package tech.housemoran.services.spender.models
import java.util.Date

class ListedCurrentItems(val siteName: String, val items: List[RetailItem]) extends CurrentItems {

  private val date = new Date()

  override def getDate: Date = this.date

  override def getItems: List[RetailItem] = this.items

  override def getSiteName: String = this.siteName
}
