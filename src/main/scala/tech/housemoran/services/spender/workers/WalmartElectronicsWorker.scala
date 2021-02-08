package tech.housemoran.services.spender.workers
import java.util.Date

import com.fasterxml.jackson.databind.ObjectMapper

import scala.jdk.CollectionConverters._
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import tech.housemoran.services.spender.models.GenericRetailItem

import scala.collection.mutable.ListBuffer

class WalmartElectronicsWorker extends RetailScraper {

  private def lookup(json: java.util.Map[String, Object]): Option[Object] = {
    val map = json.asScala
    for (key <- map.keySet) {
      val obj = map(key)
      if (key == "items") {
        return Some(obj)
      }
      obj match {
        case x: java.util.Map[_, _] => {
          val mer = lookup(x.asInstanceOf[java.util.Map[String, Object]])
          if (mer.nonEmpty) {
            return mer
          }
        }
        case x: java.util.List[_] => {
          for (f <- x.asScala) {
            val mer = lookup(f.asInstanceOf[java.util.Map[String, Object]])
            if (mer.nonEmpty) {
              return mer
            }
          }
        }
        case _ => // Do nuttin
      }
    }
    None
  }

  override def getItems(): List[GenericRetailItem] = {
    val date = new Date()
    val url = "https://www.walmart.com/browse/electronics/3944"
    WalmartElectronicsWorker.log.info(s"Getting Walmart Electronics for ${date}")
    val resp = Jsoup.connect(url).execute
    val document = Jsoup.parse(resp.body)
    val walmartJson = document.select("script[type=application/json]").iterator().asScala
    walmartJson
      .filter(f => f.toString.contains("items"))
      .map(f => {
        val mapper = new ObjectMapper()
        val map = mapper.readValue(f.html(), classOf[java.util.Map[String, Object]])
        lookup(map)
      })
      .filter(f => f.nonEmpty)
      .map(f => {
        f
          .get
          .asInstanceOf[java.util.List[Object]]
          .asScala
          .map(f => {
            val map = f.asInstanceOf[java.util.Map[String, Object]].asScala
            val retailItem = new GenericRetailItem
            retailItem.name = map("title").asInstanceOf[String]
            val primaryOffer = map("primaryOffer").asInstanceOf[java.util.Map[String, Object]].asScala
            if (primaryOffer.keySet.contains("maxPrice") && primaryOffer.keySet.contains("minPrice")) {
              retailItem.price = primaryOffer("maxPrice").toString.toDouble
              retailItem.salePrice = primaryOffer("minPrice").toString.toDouble
            } else if (primaryOffer.keySet.contains("listPrice")) {
              retailItem.price = primaryOffer("listPrice").toString.toDouble
              retailItem.salePrice = primaryOffer("offerPrice").toString.toDouble
            } else {
              retailItem.price = primaryOffer("offerPrice").toString.toDouble
              retailItem.salePrice = primaryOffer("offerPrice").toString.toDouble
            }
            retailItem.pictureUrl = map("imageUrl").asInstanceOf[String]
            retailItem.department = map("department").asInstanceOf[String]
            retailItem.productCategory = map("productCategory").asInstanceOf[String]
            if (map.keySet.contains("customerRating")) {
              retailItem.rating = map("customerRating").toString.toDouble
            } else {
              retailItem.rating = -1
            }
            retailItem.seller = "Walmart.com"
            retailItem
          })
          .toList
      })
      .next()
  }
}

object WalmartElectronicsWorker {
  val log = LoggerFactory.getLogger(classOf[WalmartElectronicsWorker])
}
