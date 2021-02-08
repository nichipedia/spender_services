package tech.housemoran.services.spender.workers

import tech.housemoran.services.spender.models.{GenericRetailItem, ListedCurrentItems}

trait RetailScraper {
  def getItems(): List[GenericRetailItem]
}
