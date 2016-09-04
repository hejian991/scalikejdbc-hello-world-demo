package my

import org.specs2.mutable.Specification

class HelloSpec extends Specification {

  "Hello" should {
    "say hello to someone" in {
      Hello.hello("sbt") === "Hello, sbt"
    }
  }

}
