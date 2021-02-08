package tech.housemoran.services.spender.providers
import org.slf4j.LoggerFactory
import tech.housemoran.services.spender.models.{CurrentItems, OnlineRetailer}

import scala.collection.mutable

class CachedItemProvider extends ItemProvider {

  private val map = new  mutable.HashMap[OnlineRetailer, CurrentItems]()

  override def getItemsList(retailer: OnlineRetailer): CurrentItems = {
    val items = map(retailer)
    if (items == null) {
      // Throw Exception
      CachedItemProvider.log.info("Need to throw exception when nothing in map!")
    }
    items
  }
}

object CachedItemProvider {
  val log = LoggerFactory.getLogger(classOf[CachedItemProvider])
}
