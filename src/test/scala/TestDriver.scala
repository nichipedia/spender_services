import tech.housemoran.services.spender.workers.WalmartElectronicsWorker

object TestDriver {
  def main(args: Array[String]): Unit = {
    val worker = new WalmartElectronicsWorker
    worker.getItems().foreach(println)
  }

}
