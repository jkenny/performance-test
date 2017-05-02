package test

import org.openjdk.jmh.annotations._

import scala.util.control.Exception._

@State(Scope.Benchmark)
class ShardingTest {

  private val baseUris = Array("https://cdn1.giltcdn.com", "https://cdn2.giltcdn.com")
  private val numUris = baseUris.length

  private val HttpPrefix = "http://"
  private val HttpsPrefix = "https://"

  private val uri = "/images/share/uploads/0000/0001/7264/172648596/420x560.jpg"

  private val reps = 1000000

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  def shardFast: Unit = {
    def isFQUrl(uri: String): Boolean = uri.startsWith(HttpPrefix) || uri.startsWith(HttpsPrefix)
    def default = baseUris(uri.length % numUris) + uri
    def getShardKey(uri: String): Option[Long] = {
      val pathEnd = uri.lastIndexOf("/")
      if (pathEnd != -1) {
        val path = uri.substring(0, pathEnd)
        val lastSlash = path.lastIndexOf("/")
        if (lastSlash != -1) {
          val id = path.substring(lastSlash + 1)
          catching(classOf[NumberFormatException]).opt { id.toLong }
        } else None
      } else None
    }

    for (i <- 1 to reps) {
      if (isFQUrl(uri)) {
        uri
      } else {
        getShardKey(uri).fold(default) { shardKey =>
          baseUris((shardKey % numUris).toInt) + uri
        }
      }
    }
  }


  val FQDNHost = """https?://""".r
  val CDNImagePath = """\d{4}/\d{4}/\d{4}/(\d+)""".r

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
  def shardSlow: Unit = {
    def isFQUrl(uri: String): Boolean = FQDNHost.findPrefixOf(uri).isDefined
    def canShardPath(uri: String): Boolean = CDNImagePath.findFirstIn(uri).isDefined
    def getImageId(uri: String): Option[Long] = {
      uri.split("/") match {
        case Array(_, _, _, _, _, _, _, id, _) => Some(id.toLong)
        case _ => None
      }
    }
    for (i <- 1 to reps) {
      lazy val default = baseUris(uri.length % numUris) + uri
      if (isFQUrl(uri)) {
        uri
      } else {
        if (canShardPath(uri)) {
          getImageId(uri).map {
            id =>
              baseUris((id % numUris).toInt) + uri
          }.getOrElse(default)
        } else {
          default
        }
      }
    }
  }
}
