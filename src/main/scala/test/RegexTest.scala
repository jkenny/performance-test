package test

import java.util.regex.Pattern

import org.openjdk.jmh.annotations._

/**
  * Benchmark for various tests on compiling and running regexes.
  *
  * Last run (run -i 10 -wi 10 -f 1 .*RegexTest*):
  *
  * background log: info: Benchmark                               Mode  Cnt        Score        Error  Units
  * background log: info: RegexTest.compileRegex14               thrpt   10  4247387.976 ± 169785.535  ops/s
  * background log: info: RegexTest.compileRegex35               thrpt   10  1838470.186 ±  17217.402  ops/s
  * background log: info: RegexTest.compileRegex129              thrpt   10   669315.416 ±  18425.743  ops/s
  * background log: info: RegexTest.compileRegex6343             thrpt   10    11392.471 ±    181.540  ops/s
  * background log: info: RegexTest.withMatching11               thrpt   10  5059110.730 ± 146391.700  ops/s
  * background log: info: RegexTest.withMatching23               thrpt   10  1969270.139 ±  40222.305  ops/s
  * background log: info: RegexTest.withMatching47               thrpt   10   558603.431 ±  13280.477  ops/s
  * background log: info: RegexTest.withMatching71               thrpt   10   536997.729 ±  19583.446  ops/s
  * background log: info: RegexTest.withMatching95               thrpt   10   452439.752 ±  10663.303  ops/s
  * background log: info: RegexTest.withNotMatching11            thrpt   10  4824330.084 ±  50839.703  ops/s
  * background log: info: RegexTest.withNotMatching23            thrpt   10  1192933.307 ±  33866.093  ops/s
  * background log: info: RegexTest.withNotMatching47            thrpt   10    76049.763 ±   4389.478  ops/s
  * background log: info: RegexTest.withNotMatching71            thrpt   10     4871.904 ±    255.773  ops/s
  * background log: info: RegexTest.withNotMatching95            thrpt   10      285.396 ±     11.253  ops/s
  * background log: info: RegexTest.withNotMatching11WithoutAmp  thrpt   10  9004189.696 ±  64670.024  ops/s
  * background log: info: RegexTest.withNotMatching23WithoutAmp  thrpt   10  7012331.622 ± 104309.619  ops/s
  * background log: info: RegexTest.withNotMatching47WithoutAmp  thrpt   10  5282156.263 ± 134555.271  ops/s
  * background log: info: RegexTest.withNotMatching71WithoutAmp  thrpt   10  4287394.665 ± 240362.934  ops/s
  * background log: info: RegexTest.withNotMatching95WithoutAmp  thrpt   10  3546128.120 ±  50748.464  ops/s
  */
@State(Scope.Benchmark)
class RegexTest {

  private val singletonRegex = Pattern.compile("^(.+&)*foo=.+$")

  private val matching11 = "bar=b&foo=b"
  private val matching23 = "bar=b&bar=b&foo=b&bar=b"
  private val matching47 = "bar=b&bar=b&bar=b&bar=b&foo=b&bar=b&bar=b&bar=b"
  private val matching71 = "bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&foo=b&bar=b&bar=b&bar=b"
  private val matching95 = "bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&foo=b&bar=b&bar=b&bar=b"

  private val notMatching11 = "bar=b&bar=b"
  private val notMatching23 = "bar=b&bar=b&bar=b&bar=b"
  private val notMatching47 = "bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b"
  private val notMatching71 = "bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b"
  private val notMatching95 = "bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b&bar=b"

  private val notMatching11WithoutAmp = "bar=bsbar=b"
  private val notMatching23WithoutAmp = "bar=bsbar=bsbar=bsbar=b"
  private val notMatching47WithoutAmp = "bar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=b"
  private val notMatching71WithoutAmp = "bar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=b"
  private val notMatching95WithoutAmp = "bar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=bsbar=b"

  // Compile a 14 character regex
  @Benchmark
  def compileRegex14: Unit = {
    val regex = "^(.+&)*foo=.+$"
    Pattern.compile(regex)
  }

  // Compile a 35 character regex
  @Benchmark
  def compileRegex35: Unit = {
    val regex = """^\(*\d{3}\)*( |-)*\d{3}( |-)*\d{4}$"""
    Pattern.compile(regex)
  }

  // Compile a 129 character regex
  @Benchmark
  def compileRegex129: Unit = {
    val regex = """^[a-zA-Z]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\.[a-zA-Z]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(\.[a-zA-Z]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)?$"""
    Pattern.compile(regex)
  }

  // Compile a 6343 character regex
  @Benchmark
  def compileRegex6343: Unit = {
    val regex = """(?:(?:\r\n)?[ \t])*(?:(?:(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*))*@(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*))*|(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*)*\<(?:(?:\r\n)?[ \t])*(?:@(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*))*(?:,@(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*))*)*:(?:(?:\r\n)?[ \t])*)?(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*))*@(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*))*\>(?:(?:\r\n)?[ \t])*)|(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*)*:(?:(?:\r\n)?[ \t])*(?:(?:(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*))*@(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*))*|(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*)*\<(?:(?:\r\n)?[ \t])*(?:@(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*))*(?:,@(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*))*)*:(?:(?:\r\n)?[ \t])*)?(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*))*@(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*))*\>(?:(?:\r\n)?[ \t])*)(?:,\s*(?:(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*))*@(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*))*|(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*)*\<(?:(?:\r\n)?[ \t])*(?:@(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*))*(?:,@(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*))*)*:(?:(?:\r\n)?[ \t])*)?(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|"(?:[^\"\r\\]|\\.|(?:(?:\r\n)?[ \t]))*"(?:(?:\r\n)?[ \t])*))*@(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*)(?:\.(?:(?:\r\n)?[ \t])*(?:[^()<>@,;:\\".\[\] \000-\031]+(?:(?:(?:\r\n)?[ \t])+|\Z|(?=[\["()<>@,;:\\".\[\]]))|\[([^\[\]\r\\]|\\.)*\](?:(?:\r\n)?[ \t])*))*\>(?:(?:\r\n)?[ \t])*))*)?;\s*)"""
    Pattern.compile(regex)
  }

  // Testing an 11 character string that doesn't match
  @Benchmark
  def withNotMatching11: Unit = {
    singletonRegex.matcher(notMatching11).matches()
  }

  // Testing a 23 character string that doesn't match
  @Benchmark
  def withNotMatching23: Unit = {
    singletonRegex.matcher(notMatching23).matches()
  }

  // Testing a 47 character string that doesn't match
  @Benchmark
  def withNotMatching47: Unit = {
    singletonRegex.matcher(notMatching47).matches()
  }

  // Testing a 71 character string that doesn't match
  @Benchmark
  def withNotMatching71: Unit = {
    singletonRegex.matcher(notMatching71).matches()
  }

  // Testing a 95 character string that doesn't match
  @Benchmark
  def withNotMatching95: Unit = {
    singletonRegex.matcher(notMatching95).matches()
  }

  // Testing an 11 character string with no ampersands that doesn't match
  @Benchmark
  def withNotMatching11WithoutAmp: Unit = {
    singletonRegex.matcher(notMatching11WithoutAmp).matches()
  }

  // Testing a 23 character string with no ampersands that doesn't match
  @Benchmark
  def withNotMatching23WithoutAmp: Unit = {
    singletonRegex.matcher(notMatching23WithoutAmp).matches()
  }

  // Testing a 47 character string with no ampersands that doesn't match
  @Benchmark
  def withNotMatching47WithoutAmp: Unit = {
    singletonRegex.matcher(notMatching47WithoutAmp).matches()
  }

  // Testing a 71 character string with no ampersands that doesn't match
  @Benchmark
  def withNotMatching71WithoutAmp: Unit = {
    singletonRegex.matcher(notMatching71WithoutAmp).matches()
  }

  // Testing a 95 character string with no ampersands that doesn't match
  @Benchmark
  def withNotMatching95WithoutAmp: Unit = {
    singletonRegex.matcher(notMatching95WithoutAmp).matches()
  }

  // Testing an 11 character string that matches
  @Benchmark
  def withMatching11: Unit = {
    singletonRegex.matcher(matching11).matches()
  }

  // Testing a 23 character string that matches
  @Benchmark
  def withMatching23: Unit = {
    singletonRegex.matcher(matching23).matches()
  }

  // Testing a 47 character string that matches
  @Benchmark
  def withMatching47: Unit = {
    singletonRegex.matcher(matching47).matches()
  }

  // Testing a 71 character string that matches
  @Benchmark
  def withMatching71: Unit = {
    singletonRegex.matcher(matching71).matches()
  }

  // Testing a 95 character string that matches
  @Benchmark
  def withMatching95: Unit = {
    singletonRegex.matcher(matching95).matches()
  }
}
