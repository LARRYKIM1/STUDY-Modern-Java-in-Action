# javaChap05 퀴즈

퀴즈 공용코드 menu 리스트

```java
public class Dish {

    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        MEAT,
        FISH,
        OTHER
    }

    @Override
    public String toString() {
        return name;
    }

    public static final List<Dish> menu = Arrays.asList( // 메뉴들 
            new Dish("pork", false, 800, Type.MEAT),
            new Dish("beef", false, 700, Type.MEAT),
            new Dish("chicken", false, 400, Type.MEAT),
            new Dish("french fries", true, 530, Type.OTHER),
            new Dish("rice", true, 350, Type.OTHER),
            new Dish("season fruit", true, 120, Type.OTHER),
            new Dish("pizza", true, 550, Type.OTHER),
            new Dish("prawns", false, 400, Type.FISH),
            new Dish("salmon", false, 450, Type.FISH)
    );

}
```

- 문제: 위 메뉴들을 이용해 칼로리순으로 정렬하시오.
    - 답

```java
System.out.println(menu.stream().sorted( (dish1, dish2) -> dish1.getCalories() - dish2.getCalories()).collect(toList()));;
```

- 문제: 아래 specialMenu에서 320 칼로리 보다 낮은 음식들을 출력하시오.

  ```java
  List<Dish> specialMenu = Arrays.asList(
      new Dish("season fruit", true, 120, Dish.Type.OTHER),
      new Dish("prawns", false, 300, Dish.Type.FISH),
      new Dish("rice", true, 350, Dish.Type.OTHER),
      new Dish("chicken", false, 400, Dish.Type.MEAT),
      new Dish("french fries", true, 530, Dish.Type.OTHER));
  ```

    - 답

      ```
      specialMenu.stream().filter(dish -> dish.getCalories() < 320).collect(toList());
      ```

- 문제:  위 문제는 이미 정렬이 된 상태이기에 320이상을 만나면 쇼트서킷하게 만드시오.

- 답

  ```java
  specialMenu.stream().takeWhile(dish -> dish.getCalories() < 320).forEach(System.out::println);
  ```



- 문제: specialMenu에서 앞에 3개만 출력하시오.

    - 답

      ```java
      List<Dish> dishesLimit3 = specialMenu.stream()
                      .limit(3)
                      .collect(toList());
      ```

- 문제: 메뉴의 이름들만을 뽑아내어 리스트를 만드시오.

    - 답

      ```java
      System.out.println( menu.stream().map(Dish::getName).collect(toList()) );
      ```



- 문제: 아래 단어의 길이를 뽑아내어, 리스트를 만드시오.

  ```java
  List<String> words = Arrays.asList("One", "Two", "Three", "Four", "Five", "Six");
  ```

    - 답

      ```java
      System.out.println( words.stream().map(String::length).collect(toList()) );
      ```

- 문제: 위 문제 단어를 이용해서, 모든 letter를 중복 없이 뽑아내시오.

    - 답

      ```java
      words.stream()
          .flatMap((String line) -> Arrays.stream(line.split("")))
          .distinct()
          .forEach(System.out::println);
      ```

- 문제: (어렵) 아래 두개의 숫자 리스트가 주어졌을때, 각 리스트에서 하나씩 뽑아내 더했을때 3의 배수가 될 수 있는 조합을 출력하시오.

  ```java
  List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
  List<Integer> numbers2 = Arrays.asList(6, 7, 8);
  ```

    - 답

      ```java
      List<int[]> pairs = numbers1.stream()
          .flatMap((Integer i) -> 
                   numbers2.stream().map((Integer j) -> new int[]{i, j}))
          .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
          .collect(toList());
      pairs.forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));
      ```

- 문제: menu 리스트에 채식주의자 음식이 하나라도 있으면, true를 리턴하시오.

    - 답

      ```java
      menu.stream().anyMatch(Dish::isVegetarian);
      ```



- 문제: menu 리스트에 모든 메뉴가 1000 칼로리를 넘지 않음을 확인하시오.

    - 답

      ```java
      menu.stream().noneMatch(d -> d.getCalories() >= 1000);
      ```

- 문제: 아래 숫자 리스트의 총 합계를 구하시오.

  ```java
  List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
  ```

    - 답

      ```java
      numbers.stream().reduce(0, (a, b) -> a + b); // 옵션 1
      numbers.stream().reduce(0, Integer::sum); // 옵션 2
      ```

- 문제: 위 숫자 리스트의 최대값을 구하시오.

    - 답

      ```java
      System.out.println(numbers.stream().max((a,b) -> a.compareTo(b))); // 옵션 1
      System.out.println(numbers.stream().reduce(0, (a, b) -> Integer.max(a, b))); // 옵션 2
      System.out.println(numbers.stream().reduce(Integer::max)); // 옵션 3
      ```

- 문제: 값이 있는지 없는지 모르는 숫자 리스트가 있다. 최소값을 찾으려고 하는데 값이 없을 경우를 대비하여 출력을 하시오.

    - 답

      ```java
      List<Integer> numbers = new ArrayList<>();
      Optional<Integer> min = numbers.stream().reduce(Integer::min);
      min.ifPresent(System.out::println);
      ```

- 문제: menu 리스트의 모든 칼로리의 합을 구하시오.

    - 답

      ```javascript
      // reduce 이용시
      menu.stream()
          .map(Dish::getCalories)
          .reduce(0, Integer::sum);
      
      // mapToInt 이용 
      menu.stream()
          .mapToInt(Dish::getCalories)
          .sum()
      ```

- 문제: 리스트를 배열 스트림으로 만들어서 foreach로 출력하시오.

  ```javascript
  List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
  ```

    - 답

      ```java
      Arrays.stream(numbers.toArray())
                      .forEach(System.out::println);.
      ```

- 문제: 메뉴 중 칼로리가 제일 높은 음식을 가져오시오.

    - 답

      ```
      menu.stream().max(comparing(Dish::getCalories));
      ```



- 문제: mapToInt를 사용해서, menu의 칼로리들 중에 최대값을 출력하시오. 값이 없을 경우 대비하시오.

    - 답

      ```java
      menu.stream()
          .mapToInt(Dish::getCalories)
          .max().ifPresent(System.out::println);
      ```

- 문제: IntStream을 이용해서, 1부터 100 사이에 3의 배수의 개수를 세시오.

    - 답

      ```java
      System.out.println(IntStream.rangeClosed(1, 100)
                      .filter(n -> n % 3 == 0).count());
      ```

- 문제: 아래 단어들로 스트림을 만드시오

  ```
  ("Java 8", "Lambdas", "In", "Action")
  ```

    - 답

      ```
      Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
      ```

- 문제: 모든 단어를 대문자로 바꾼후, 출력하시오.

  ```
  Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
  ```

    - 답

      ```
      stream.map(String::toUpperCase).forEach(System.out::println);
      ```

- 문제: 아래 배열을 스트림을 이용해, 합계를 구하시오.

  ```
  int[] numbers = {2, 3, 5, 7, 11, 13};
  ```

    - 답

      ```
      Arrays.stream(numbers).sum()
      ```

- 문제: 스트림의 iterate를 이용해서, 10개까지 2의 배수가 출력이 되게 코드를 작성하시오.

    - 답

      ```
      Stream.iterate(0, n -> n + 2)
                      .limit(10)
                      .forEach(System.out::println);
      ```

- 문제: 스트림의 iterate를 이용해서, 피보나치 수열을 10개까지 출력하시오.

    - 답

      ```java
      Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
          .limit(10)
          .forEach(t -> System.out.printf("(%d, %d)", t[0], t[1]));
      
      Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
          .limit(10)
          .map(t -> t[0])
          .forEach(System.out::println);
      ```

      ```java
      // 이런것도 된다 
      IntSupplier fib = new IntSupplier() {
          private int previous = 0;
          private int current = 1;
      
          @Override
          public int getAsInt() {
              int nextValue = previous + current;
              previous = current;
              current = nextValue;
              return previous;
          }
      };
      
      IntStream.generate(fib)
          .limit(10)
          .forEach(System.out::println);
      ```



- 문제: data.txt 에 있는 단어의 개수를 세시오.

    - 답

      ```
      // lines는 stream을 리턴한다. 
      Files.lines(Paths.get("chap5/data.txt"), Charset.defaultCharset())
                      .flatMap(line -> Arrays.stream(line.split(" ")))
                      .distinct()
                      .count();
      ```

## 실전

```java
package chap05;

import java.util.Objects;

public class Trader {
    private String name;
    private String city;

    public Trader(String n, String c) {
        name = n;
        city = c;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String newCity) {
        city = newCity;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + (city == null ? 0 : city.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Trader)) {
            return false;
        }
        Trader o = (Trader) other;
        boolean eq = Objects.equals(name, o.getName());
        eq = eq && Objects.equals(city, o.getCity());
        return eq;
    }

    @Override
    public String toString() {
        return String.format("Trader:%s in %s", name, city);
    }
}
```

```java
Trader raoul = new Trader("Raoul", "Cambridge");
Trader mario = new Trader("Mario", "Milan");
Trader alan = new Trader("Alan", "Cambridge");
Trader brian = new Trader("Brian", "Cambridge");

List<Transaction> transactions = Arrays.asList(
    new Transaction(brian, 2011, 300),
    new Transaction(raoul, 2012, 1000),
    new Transaction(raoul, 2011, 400),
    new Transaction(mario, 2012, 710),
    new Transaction(mario, 2012, 700),
    new Transaction(alan, 2012, 950)
);
```

- 문제: Find all transactions from year 2011 and sort them by value (small to high)

    - 답

      ```
      List<Transaction> tr2011 = transactions.stream()
                      .filter(transaction -> transaction.getYear() == 2011)
                      .sorted(comparing(Transaction::getValue))
                      .collect(toList());
      System.out.println(tr2011);
      ```

- 문제: What are all the unique cities where the traders work?

    - 답

      ```
      List<String> cities = transactions.stream()
                      .map(transaction -> transaction.getTrader().getCity())
                      .distinct()
                      .collect(toList());
      System.out.println(cities);
      ```

- 문제: Find all traders from Cambridge and sort them by name.

    - 답

      ```java
       List<Trader> traders = transactions.stream()
                      .map(Transaction::getTrader)
                      .filter(trader -> trader.getCity().equals("Cambridge"))
                      .distinct()
                      .sorted(comparing(Trader::getName))
                      .collect(toList());
      System.out.println(traders);
      ```

- 문제: Return a string of all traders' names sorted alphabetically.

    - 답

      ```java
      String traderStr = transactions.stream()
                      .map(transaction -> transaction.getTrader().getName())
                      .distinct()
                      .sorted()
                      .reduce("", (n1, n2) -> n1 + n2);
      System.out.println(traderStr);
      ```

- 문제: Are there any trader based in Milan?

    - 답

      ```
      transactions.stream()
                     .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
      ```

- 문제: Print all transactions' values from the traders living in Cambridge.

    - 답

      ```java
      transactions.stream()
          .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
          .map(Transaction::getValue)
          .forEach(System.out::println);
      ```



- 문제: What's the highest value in all the transactions?

    - 답

      ```java
      int highestValue = transactions.stream()
          .map(Transaction::getValue)
          .reduce(0, Integer::max);
      System.out.println(highestValue);
      ```

- 

  



















