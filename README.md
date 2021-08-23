A simple project showing advantages and disadvantages of [Pitest](https://pitest.org/),
prepared for a [Medium article](https://medium.com/nexocode/mutation-testing-too-good-to-be-true-a10105896da2) I wrote.

Run with default mutation engine:

```
./gradlew pitest
```

Run with Descartes mutation engine:

```
./gradlew pitest -Pdescartes
```

Reports can be found in `core/build/reports/pitest/` and `data/build/reports/pitest/`.
