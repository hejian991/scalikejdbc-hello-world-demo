package my

import scalikejdbc._

object Hello extends App {
  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:hello", "user", "pass")

  implicit val session = AutoSession

  sql"""
    create table members (
      id serial not null primary key,
      name varchar(64),
      created_at timestamp not null
    )
  """.execute().apply()
  Seq("Alice", "Bob", "Chris").foreach { name =>
    sql"insert into members(name, created_at) values($name, current_timestamp)".update().apply()
  }
  val entities: List[Map[String, Any]] = sql"select * from members".map(_.toMap).list.apply()

  import java.time._

  case class Member(id: Long, name: Option[String], createdAt: ZonedDateTime)
  object Member extends SQLSyntaxSupport[Member] {
    override val tableName = "members"
    def apply(rs: WrappedResultSet) = new Member(
      rs.long("id"), rs.stringOpt("name"), rs.zonedDateTime("created_at")
    )
  }

  val members: List[Member] = sql"select * from members".map(Member.apply).list.apply()
  println("------- members --------")
  members.foreach(println)

  val m = Member.syntax("m")
  val name = "Alice"
  val alice: Option[Member] = withSQL {
    select.from(Member as m).where.eq(m.name, name)
  }.map(Member.apply).single.apply()
  println("------ Alice ---------")
  println(alice)
}
