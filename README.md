A simple project showing advantages and disadvantages of [Pitest](https://pitest.org/).

Run with default mutation engine:

```
./gradlew pitest
```

Run with Descartes mutation engine:

```
./gradlew pitest -Pdescartes
```

Reports can be found in `core/build/reports/pitest/` and `data/build/reports/pitest/`.
