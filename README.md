Performance Test
================

Sbt project for running micro benchmarking test. Based off the [sbt-jhm plugin](https://github.com/ktoso/sbt-jmh).

Running
-------

See [sbt-jhm plugin](https://github.com/ktoso/sbt-jmh) for full documentation but for a simple run:

`run -i 10 -wi 10 -f 1`

To filter to a certain set of tests:

`run -i 10 -wi 10 -f 1 .*RegexTest*`

After you add a test you need to clean and recompile so that the benchmark list is updated. I.e.

```
clean
jmh:compile
```

To view the current benchmark list:

`run -l`
