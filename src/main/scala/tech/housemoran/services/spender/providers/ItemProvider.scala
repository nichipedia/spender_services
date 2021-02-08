package tech.housemoran.services.spender.providers

import tech.housemoran.services.spender.models.{CurrentItems, OnlineRetailer}

trait ItemProvider {
  def getItemsList(retailer: OnlineRetailer): CurrentItems
}